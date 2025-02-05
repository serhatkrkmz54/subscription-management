package org.abonehatirlatici.neoduyorum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.StaticSubscription;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.PaymentRepository;
import org.abonehatirlatici.neoduyorum.repo.StaticSubscriptionRepo;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.request.PaymentPlanAddRequest;
import org.abonehatirlatici.neoduyorum.response.SubscriptionDetailResponse;
import org.abonehatirlatici.neoduyorum.response.SubscriptionGroupResponse;
import org.abonehatirlatici.neoduyorum.response.PaymentPlanAddResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentPlanService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final StaticSubscriptionRepo staticSubscriptionRepo;

    public List<SubscriptionGroupResponse> getGroupedSubs() {
        List<StaticSubscription> subscriptions = staticSubscriptionRepo.findAllOrderedByName();
        Map<String, List<SubscriptionDetailResponse>> groupedSubs = subscriptions.stream()
                .collect(Collectors.groupingBy(
                        StaticSubscription::getSAbonelikAdi,
                        Collectors.mapping(sub -> new SubscriptionDetailResponse(
                                sub.getId(),
                                sub.getSFrequency(),
                                sub.getSKategori(),
                                sub.getSOdemeBirimi(),
                                sub.getSOdemeMiktari()
                        ), Collectors.toList())
                ));
        return groupedSubs.entrySet().stream()
                .map(entry -> new SubscriptionGroupResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public PaymentPlanAddResponse createPaymentPlanFromStatic(Long staticSubscriptionId, Long userId, Integer last4Digits, LocalDate bitisTarihi, String cardName) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı."));

        StaticSubscription staticSubscription = staticSubscriptionRepo.findById(staticSubscriptionId)
                .orElseThrow(()-> new UsernameNotFoundException("Statik abonelik bulunamadı."));

        var subscription = PaymentPlan.builder()
                .user(user)
                .abonelikAdi(staticSubscription.getSAbonelikAdi())
                .odemeMiktari(staticSubscription.getSOdemeMiktari())
                .odemeBirimi(staticSubscription.getSOdemeBirimi())
                .frequency(staticSubscription.getSFrequency())
                .last4Digits(last4Digits)
                .cardName(cardName)
                .bitisTarihi(bitisTarihi)
                .baslangicTarihi(LocalDate.now())
                .createdDate(LocalDateTime.now())
                .build();
        paymentRepository.save(subscription);
        return mapToResponse(subscription);
    }

    public PaymentPlanAddResponse createPaymentPlan(PaymentPlanAddRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı."));

        var subscription = PaymentPlan.builder()
                .user(user)
                .abonelikAdi(request.getAbonelikAdi())
                .odemeMiktari(request.getOdemeMiktari())
                .odemeBirimi(request.getOdemeBirimi())
                .baslangicTarihi(request.getBaslangicTarihi())
                .bitisTarihi(request.getBitisTarihi())
                .frequency(request.getFrequency())
                .last4Digits(request.getLast4Digits())
                .createdDate(LocalDateTime.now())
                .cardName(request.getCardName())
                .build();
        paymentRepository.save(subscription);
        return mapToResponse(subscription);
    }

    @Transactional
    public PaymentPlanAddResponse updatePaymentPlan(Long paymentPlanId, Long userId, PaymentPlanAddRequest request) {
        PaymentPlan paymentPlan = paymentRepository.findByIdAndUserId(paymentPlanId, userId)
                .orElseThrow(()-> new RuntimeException("Abonelik bulunamadı."));

        paymentPlan.setAbonelikAdi(request.getAbonelikAdi());
        paymentPlan.setOdemeMiktari(request.getOdemeMiktari());
        paymentPlan.setOdemeBirimi(request.getOdemeBirimi());
        paymentPlan.setBitisTarihi(request.getBitisTarihi());
        paymentPlan.setBaslangicTarihi(request.getBaslangicTarihi());
        paymentPlan.setFrequency(request.getFrequency());
        paymentPlan.setLast4Digits(request.getLast4Digits());
        paymentPlan.setCardName(request.getCardName());
        paymentPlan.setUpdatedDate(LocalDateTime.now());
        paymentRepository.save(paymentPlan);
        return mapToResponse(paymentPlan);
    }

    @Transactional
    public boolean abonelikSil(Long paymentPlanId, Long userId) {
        try {
            paymentRepository.deleteByIdAndUserId(paymentPlanId,userId);
            return true;
        }catch (Exception e) {
            return false;
        }
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
                .baslangicTarihi(subscription.getBaslangicTarihi())
                .bitisTarihi(subscription.getBitisTarihi())
                .frequency(subscription.getFrequency())
                .last4Digits(subscription.getLast4Digits())
                .cardName(subscription.getCardName())
                .build();
    }
}
