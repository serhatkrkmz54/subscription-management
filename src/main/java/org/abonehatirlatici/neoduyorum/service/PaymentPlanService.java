package org.abonehatirlatici.neoduyorum.service;

import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.PaymentRepository;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.request.PaymentPlanAddRequest;
import org.abonehatirlatici.neoduyorum.response.PaymentPlanAddResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentPlanService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentPlanAddResponse createPaymentPlan(PaymentPlanAddRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı."));

        var subscription = PaymentPlan.builder()
                .user(user)
                .abonelikAdi(request.getAbonelikAdi())
                .odemeMiktari(request.getOdemeMiktari())
                .odemeBirimi(request.getOdemeBirimi())
                .odemeTarihi(request.getOdemeTarihi())
                .bitisTarihi(request.getBitisTarihi())
                .frequency(request.getFrequency())
                .last4Digits(request.getLast4Digits())
                .createdDate(LocalDateTime.now())
                .cardName(request.getCardName())
                .build();
        paymentRepository.save(subscription);
        return mapToResponse(subscription);
    }

    public List<PaymentPlanAddResponse> getPlanByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı."));
        return paymentRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentPlanAddResponse mapToResponse(PaymentPlan subscription) {
        return PaymentPlanAddResponse.builder()
                .id(subscription.getId())
                .userId(subscription.getUser().getId())
                .abonelikAdi(subscription.getAbonelikAdi())
                .odemeMiktari(subscription.getOdemeMiktari())
                .odemeBirimi(subscription.getOdemeBirimi())
                .odemeTarihi(subscription.getOdemeTarihi())
                .bitisTarihi(subscription.getBitisTarihi())
                .frequency(subscription.getFrequency())
                .last4Digits(subscription.getLast4Digits())
                .cardName(subscription.getCardName())
                .build();
    }

}
