package org.abonehatirlatici.neoduyorum.controller;

import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.Settings;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.abonehatirlatici.neoduyorum.repo.SettingsRepository;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsRepository settingsRepository;
    private final UserRepository userRepository;

    @PostMapping("/{userId}/notification-days")
    public ResponseEntity<String> updateNotificationDays(@PathVariable Long userId, @RequestParam int days) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı.");
        }
        User user = userOpt.get();
        Optional<Settings> settingsOpt = settingsRepository.findByUserId(userId);

        Settings settings = settingsOpt.orElse(Settings.builder().user(user).build());
        settings.setNotificationDays(days);
        settingsRepository.save(settings);

        return ResponseEntity.ok("Bildirim süresi " + days + " gün olarak ayarlandı.");
    }

}
