package org.abonehatirlatici.neoduyorum.controller;

import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.request.NotificationRequest;
import org.abonehatirlatici.neoduyorum.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam String playerId, @RequestParam String message) {
        notificationService.sendNotification(playerId,message);
        return ResponseEntity.ok("Bildirim Başarıyla Gönderildi.");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequest request) {
        notificationService.createNotification(request);
        return ResponseEntity.ok("Bildirim Başarıyla Oluşturuldu.");
    }

}
