package org.abonehatirlatici.neoduyorum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.entity.UserStatistics;
import org.abonehatirlatici.neoduyorum.repo.PaymentRepository;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.repo.UserStatisticsRepository;
import org.abonehatirlatici.neoduyorum.response.UserStatisticsResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final UserStatisticsRepository userStatisticsRepository;


    @Transactional
    public UserStatisticsResponse getUserStatisticsByUserId(Long userId) {
        UserStatistics userStatistics = userStatisticsRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Kullanıcı istatistikleri bulunamadı."));

        return UserStatisticsResponse.builder()
                .monthlySpending(userStatistics.getMonthlySpending())
                .yearlySpending(userStatistics.getYearlySpending())
                .year(userStatistics.getYear())
                .build();
    }

    @Transactional
    public void updateUserStatistics(User user) {
        List<PaymentPlan> paymentPlans = paymentRepository.findByUser(user);

        if (paymentPlans.isEmpty()) {
            System.out.println("User ID: " + user.getId() + " için ödeme planı bulunamadı!");
            return;
        }

        BigDecimal totalMonthly = BigDecimal.ZERO;
        BigDecimal totalYearly = BigDecimal.ZERO;
        int currentYear = LocalDate.now().getYear();

        for (PaymentPlan plan : paymentPlans) {
            BigDecimal amount = plan.getOdemeMiktari() != null ? BigDecimal.valueOf(plan.getOdemeMiktari()) : BigDecimal.ZERO;
            if ("AYLIK".equalsIgnoreCase(plan.getFrequency()) && plan.getBitisTarihi().isAfter(LocalDate.now())){
                totalMonthly = totalMonthly.add(amount);
                totalYearly = totalYearly.add(amount.multiply(BigDecimal.valueOf(12)));
            } else if ("YILLIK".equalsIgnoreCase(plan.getFrequency()) && plan.getBitisTarihi().isAfter(LocalDate.now())) {
                totalYearly = totalYearly.add(amount);
                totalMonthly = totalMonthly.add(amount.divide(BigDecimal.valueOf(12),2,BigDecimal.ROUND_HALF_UP));
            }
            if (plan.getBitisTarihi().isBefore(LocalDate.now())) {
                totalMonthly = totalMonthly.add(amount);
            }
        }

        UserStatistics userStatistics = userStatisticsRepository.findByUserId(user.getId())
                        .orElse(UserStatistics.builder()
                                .user(user)
                                .monthlySpending(BigDecimal.ZERO)
                                .yearlySpending(BigDecimal.ZERO)
                                .year(currentYear)
                                .build());

        userStatistics.setMonthlySpending(totalMonthly);
        userStatistics.setYearlySpending(totalYearly);
        userStatistics.setYear(currentYear);
        userStatisticsRepository.save(userStatistics);

    }

    @Scheduled(cron = "0 0 3 1 * ?")
    public void updateAllUsersStatistics() {
        List<User> users = userRepository.findAll();
        users.forEach(this::updateUserStatistics);
    }

}
