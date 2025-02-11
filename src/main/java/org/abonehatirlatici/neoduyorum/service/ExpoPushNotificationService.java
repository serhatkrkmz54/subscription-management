package org.abonehatirlatici.neoduyorum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpoPushNotificationService {

    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    public void sendPushNotification(String expoPushToken, String title, String message) {
        if (!expoPushToken.startsWith("ExponentPushToken")) {
            System.out.println("Geçersiz Expo Push Token: " + expoPushToken);return;
        }

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = Map.of(
                "to", expoPushToken,
                "title", title,
                "body", message
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String ,Object>> request = new HttpEntity<>(requestBody,headers);

        ResponseEntity<String> response = restTemplate.exchange(EXPO_PUSH_URL, HttpMethod.POST,request,String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Bildirim başarıyla gönderildi." + message);
        }else {
            System.out.println("Bildirim gönderilemedi. Hata Kodu: " + response.getStatusCode());
        }
    }

}
