package org.abonehatirlatici.neoduyorum.controller;

import jakarta.validation.Valid;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.StaticSubscriptionRepo;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.request.PaymentPlanAddRequest;
import org.abonehatirlatici.neoduyorum.response.SubscriptionGroupResponse;
import org.abonehatirlatici.neoduyorum.response.PaymentPlanAddResponse;
import org.abonehatirlatici.neoduyorum.service.PaymentPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-plan")
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;
    private final UserRepository userRepository;
    private final StaticSubscriptionRepo staticSubscriptionRepo;

    public PaymentPlanController(PaymentPlanService paymentPlanService, UserRepository userRepository, StaticSubscriptionRepo staticSubscriptionRepo) {
        this.paymentPlanService = paymentPlanService;
        this.userRepository = userRepository;
        this.staticSubscriptionRepo = staticSubscriptionRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<PaymentPlanAddResponse> addPaymentPlan(@RequestBody @Valid PaymentPlanAddRequest paymentPlanAddRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı."));
        PaymentPlanAddResponse createdPlan = paymentPlanService.createPaymentPlan(paymentPlanAddRequest,user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
    }

    @PutMapping("/duzenle/{paymentPlanId}")
    public ResponseEntity<PaymentPlanAddResponse> updatePaymentPlan(
            @PathVariable Long paymentPlanId,
            @RequestBody @Valid PaymentPlanAddRequest request,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        Long userId = ((User) userDetails).getId();
        PaymentPlanAddResponse updatedPlan = paymentPlanService.updatePaymentPlan(paymentPlanId,userId,request);
        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/delete/{paymentPlanId}")
    public ResponseEntity<String> deletePaymentPlan(@PathVariable Long paymentPlanId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı."));

        boolean isDeleted = paymentPlanService.abonelikSil(paymentPlanId,user.getId());
        if (isDeleted) {
            return ResponseEntity.ok("Ödeme Planı Başarıyla Silindi.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Silme İşlemi Sırasında Hata Oluştu.");
        }
    }

    @GetMapping("/all-plan/{userId}")
    public ResponseEntity<List<PaymentPlanAddResponse>> getPlanByUser(@PathVariable Long userId) {
        List<PaymentPlanAddResponse> plans = paymentPlanService.getPlanByUser(userId);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/static-subscriptions")
    public ResponseEntity<List<SubscriptionGroupResponse>> getGroupedSubs() {
        return ResponseEntity.ok(paymentPlanService.getGroupedSubs());
    }

    @PostMapping("/add/{subsId}")
    public ResponseEntity<PaymentPlanAddResponse> addPaymentPlanFromStaticSubscription(
            @PathVariable Long subsId,
            @RequestBody PaymentPlanAddRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı."));
        PaymentPlanAddResponse response = paymentPlanService.createPaymentPlanFromStatic(
                subsId,
                user.getId(),
                request.getLast4Digits(),
                request.getBitisTarihi(),
                request.getCardName()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
