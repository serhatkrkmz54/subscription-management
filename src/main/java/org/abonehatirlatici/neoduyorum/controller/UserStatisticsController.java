package org.abonehatirlatici.neoduyorum.controller;

import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.UserStatistics;
import org.abonehatirlatici.neoduyorum.response.UserStatisticsResponse;
import org.abonehatirlatici.neoduyorum.service.UserStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserStatisticsResponse> getUserStatistics(@PathVariable Long userId) {
        UserStatisticsResponse userStatistics = userStatisticsService.getUserStatisticsByUserId(userId);
        return ResponseEntity.ok(userStatistics);
    }

}
