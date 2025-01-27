package org.abonehatirlatici.neoduyorum.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.request.PaymentPlanAddRequest;
import org.abonehatirlatici.neoduyorum.response.PaymentPlanAddResponse;
import org.abonehatirlatici.neoduyorum.service.PaymentPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-plan")
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;
    private final UserRepository userRepository;

    public PaymentPlanController(PaymentPlanService paymentPlanService, UserRepository userRepository) {
        this.paymentPlanService = paymentPlanService;
        this.userRepository = userRepository;
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

    @GetMapping("/all-plan/{userId}")
    public ResponseEntity<List<PaymentPlanAddResponse>> getPlanByUser(@PathVariable Long userId) {
        List<PaymentPlanAddResponse> plans = paymentPlanService.getPlanByUser(userId);
        return ResponseEntity.ok(plans);
    }
}
