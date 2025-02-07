package org.abonehatirlatici.neoduyorum.service;

import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.Notification;
import org.abonehatirlatici.neoduyorum.repo.NotificationRepository;
import org.abonehatirlatici.neoduyorum.entity.PaymentPlan;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.PaymentRepository;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.abonehatirlatici.neoduyorum.request.NotificationRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String ONESIGNAL_APP_ID = "ee503461-204c-48c7-9cef-6260c5a847dd";
    private static final String ONESIGNAL_API_KEY = "os_v2_app_5zidiyjajremphhpmjqmlkch3wmnsstriliehanzqw3vttycol6rh3xq3agdpwdcynkwoum7pozalykoa2rjqzq5gsj6g5ovh6kxatq";
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final NotificationRepository notificationRepository;
    private final RestTemplate restTemplate;

    public void sendNotification(String playerId, String message) {
        String url = "https://onesignal.com/api/v1/notifications";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Basic " + ONESIGNAL_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{"
                + "\"app_id\": \"" + ONESIGNAL_APP_ID + "\","
                + "\"include_player_ids\": [\"" + playerId + "\"],"
                + "\"contents\": {\"en\": \"" + message + "\"},"
                + "\"headings\": {\"en\": \"Yeni Bildirim\"}"
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public void createNotification(NotificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new RuntimeException("Kullanıcı Bulunamadı."));
        PaymentPlan paymentPlan = paymentRepository.findById(request.getPaymentPlanId())
                .orElseThrow(()-> new RuntimeException("Ödeme planı bulunamadı."));

        Notification notification = Notification.builder()
                .user(user)
                .paymentPlan(paymentPlan)
                .notificationDate(request.getNotificationDate())
                .status(request.getStatus())
                .build();
        notificationRepository.save(notification);
    }
}
