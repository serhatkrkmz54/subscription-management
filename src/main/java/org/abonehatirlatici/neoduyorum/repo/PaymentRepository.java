package org.abonehatirlatici.neoduyorum.repo;

import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentPlan, Long> {
    List<PaymentPlan>findByUser(User user);
    void deleteByIdAndUserId(Long paymentPlanId, Long userId);
    Optional<PaymentPlan> findByIdAndUserId(Long id, Long userId);
}
