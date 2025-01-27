package org.abonehatirlatici.neoduyorum.repo;

import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentPlan, Long> {
    List<PaymentPlan>findByUser(User user);

}
