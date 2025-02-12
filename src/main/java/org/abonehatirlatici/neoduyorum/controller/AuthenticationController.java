package org.abonehatirlatici.neoduyorum.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.request.AuthenticationRequest;
import org.abonehatirlatici.neoduyorum.request.PlayerIdRequest;
import org.abonehatirlatici.neoduyorum.request.RegistrationRequest;
import org.abonehatirlatici.neoduyorum.request.UpdateProfileRequest;
import org.abonehatirlatici.neoduyorum.response.AuthenticationResponse;
import org.abonehatirlatici.neoduyorum.response.UserProfileResponse;
import org.abonehatirlatici.neoduyorum.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateProfile(Authentication authentication,
                                                @Valid @RequestBody UpdateProfileRequest request) {
        String email = authentication.getName();
        authenticationService.updateProfile(email,request);
        return ResponseEntity.ok("Profil başarıyla güncellendi.");
    }

    @GetMapping("/profil")
    public ResponseEntity<UserProfileResponse> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        UserProfileResponse userProfile = authenticationService.getUserByProfile(user);
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        authenticationService.activeAccount(token);
    }

    @PostMapping("/re-send-activation-code")
    public ResponseEntity<String> resendActivationToken(@RequestBody AuthenticationRequest request) {
        try {
            authenticationService.resendActivationToken(request.getEmail());
            return ResponseEntity.ok("Yeni aktivasyon kodu gönderildi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hata:"+e.getMessage());
        }
    }
}
