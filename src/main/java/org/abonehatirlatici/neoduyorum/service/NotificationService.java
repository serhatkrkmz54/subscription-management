package org.abonehatirlatici.neoduyorum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.Notification;
import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.Settings;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.NotificationRepository;
import org.abonehatirlatici.neoduyorum.repo.PaymentRepository;
import org.abonehatirlatici.neoduyorum.repo.SettingsRepository;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PaymentRepository paymentRepository;
    private final ExpoPushNotificationService expoPushNotificationService;
    private final SettingsRepository settingsRepository;

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void checkAndSendNotification() {
        LocalDate today = LocalDate.now();
//        LocalDate notificationDate = today.plusDays(5);
//        List<PaymentPlan> upcomingPayments = paymentRepository.findByBitisTarihiBetween(LocalDate.now(), notificationDate);

        List<PaymentPlan> upcomingPayments = paymentRepository.findAll();

        for (PaymentPlan plan : upcomingPayments) {
            User user = plan.getUser();
            long daysLeft = ChronoUnit.DAYS.between(today,plan.getBitisTarihi());

            Optional<Settings> settingsOptional = settingsRepository.findByUserId(user.getId());
            int notificationDays = settingsOptional.map(Settings::getNotificationDays).orElse(5);

            if (daysLeft>0 && daysLeft<=notificationDays && user.getExpoPushToken() != null) {
                String message = "Abonelik adı " + plan.getAbonelikAdi() +
                        " olan aboneliğinizin ödeme süresine " + daysLeft + " gün kalmıştır!";
                expoPushNotificationService.sendPushNotification(user.getExpoPushToken(), "Aboneliğin Ödeme Süresi Doluyor!", message);

                Notification notification = Notification.builder()
                        .user(user)
                        .paymentPlan(plan)
                        .notificationDate(LocalDate.now())
                        .createdDate(LocalDateTime.now())
                        .message(message)
                        .status("SENT")
                        .build();
                notificationRepository.save(notification);
            }
        }
    }
}
