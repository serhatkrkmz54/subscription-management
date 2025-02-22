package org.abonehatirlatici.neoduyorum.service;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.config.JwtService;
import org.abonehatirlatici.neoduyorum.entity.*;
import org.abonehatirlatici.neoduyorum.entity.enums.EmailTemplateName;
import org.abonehatirlatici.neoduyorum.entity.enums.Gender;
import org.abonehatirlatici.neoduyorum.exceptions.GenelHataKodlari;
import org.abonehatirlatici.neoduyorum.exceptions.GenelHataKodlariExc;
import org.abonehatirlatici.neoduyorum.repo.NotificationRepository;
import org.abonehatirlatici.neoduyorum.repo.PaymentRepository;
import org.abonehatirlatici.neoduyorum.repo.TokenRepository;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.request.AuthenticationRequest;
import org.abonehatirlatici.neoduyorum.request.RegistrationRequest;
import org.abonehatirlatici.neoduyorum.request.UpdateProfileRequest;
import org.abonehatirlatici.neoduyorum.response.AuthenticationResponse;
import org.abonehatirlatici.neoduyorum.response.PaymentPlanAddResponse;
import org.abonehatirlatici.neoduyorum.response.UserProfileResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PaymentRepository paymentRepository;
    private final ExpoPushNotificationService expoPushNotificationService;
    private final NotificationRepository notificationRepository;

    public UserProfileResponse getUserByProfile(User user) {
        /*User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı."));*/

        List<PaymentPlanAddResponse> paymentPlans = paymentRepository.findByUser(user)
                .stream()
                .map(paymentPlan -> PaymentPlanAddResponse.builder()
                        .id(paymentPlan.getId())
                        .userId(paymentPlan.getUser().getId())
                        .abonelikAdi(paymentPlan.getAbonelikAdi())
                        .odemeMiktari(paymentPlan.getOdemeMiktari())
                        .odemeBirimi(paymentPlan.getOdemeBirimi())
                        .baslangicTarihi(paymentPlan.getBaslangicTarihi())
                        .bitisTarihi(paymentPlan.getBitisTarihi())
                        .frequency(paymentPlan.getFrequency())
                        .last4Digits(paymentPlan.getLast4Digits())
                        .cardName(paymentPlan.getCardName())
                        .build())
                .toList();

        return UserProfileResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .gender(String.valueOf(user.getGender()))
                .settings(Optional.ofNullable(user.getSettings())
                        .map(Settings::getNotificationDays)
                        .orElse(5))
                .paymentPlans(paymentPlans)
                .build();
    }

    public void register(RegistrationRequest request) throws MessagingException {
        var existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new GenelHataKodlariExc(GenelHataKodlari.EMAIL_ALREADY_EXISTS);
        }
        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .expoPushToken(request.getExpoPushToken())
                .accountNonLocked(false)
                .enabled(false)
                .build();
        userRepository.save(user);
        sendValidationEmail(user);

        if (user.getExpoPushToken() != null) {
            String message = "Aktivasyon kodunuz " +user.getEmail()+ " adresine gönderilmiştir. Kayıt işlemini tamamlayın!";
            expoPushNotificationService.sendPushNotification(user.getExpoPushToken(), "Hesap Aktivasyonu", message);
            Notification notification = Notification.builder()
                    .user(user)
                    .notificationDate(LocalDate.now())
                    .createdDate(LocalDateTime.now())
                    .message(message)
                    .status("SENT")
                    .build();
            notificationRepository.save(notification);
        }
    }

    @Transactional
    public void updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı."));

        user.setFullName(request.getFullName());

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getGender()!=null) {
            user.setGender(Gender.valueOf(request.getGender()));
        }

        if (request.getNewPassword()!=null && !request.getNewPassword().isEmpty()) {
            if (request.getOldPassword()==null || !passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("Eski şifre hatalı.");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        userRepository.save(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                newToken,
                "Hesap Aktifleştirme"
        );

    }

    @Transactional
    public void resendActivationToken(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Kullanıcı Bulunamadı"));
        if (user.isEnabled()) {
            throw new RuntimeException("Hesap zaten aktif.");
        }
        tokenRepository.deleteByUser(user);
        sendValidationEmail(user);
        if (user.getExpoPushToken() != null) {
            String message = "Aktivasyon kodunuz " +user.getEmail()+ " adresinize tekrar gönderilmiştir. Kayıt işleminizi mail adresinize giderek tamamlayın!";
            expoPushNotificationService.sendPushNotification(user.getExpoPushToken(), "Hesap Aktivasyonu", message);
            Notification notification = Notification.builder()
                    .user(user)
                    .notificationDate(LocalDate.now())
                    .createdDate(LocalDateTime.now())
                    .message(message)
                    .status("SENT")
                    .build();
            notificationRepository.save(notification);
        }
    }
    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        SecureRandom random = new SecureRandom();
        return random.ints(6,0,characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new,StringBuilder::append,StringBuilder::append)
                .toString();
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        if (request.getExpoPushToken() != null) {
            user.setExpoPushToken(request.getExpoPushToken());
            userRepository.save(user);
        }
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    //    @Transactional
    public void activeAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(()-> new RuntimeException("Geçersiz token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Aktivasyon tokeni sona erdi. Yeni token email adresinize gönderilmiştir.");
        }
        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı."));
        if (user.isEnabled()) {
            throw new RuntimeException("Hesap zaten aktif. Tekrar aktivasyon gerekli değil. ");
        }

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());

        tokenRepository.save(savedToken);
    }

}
