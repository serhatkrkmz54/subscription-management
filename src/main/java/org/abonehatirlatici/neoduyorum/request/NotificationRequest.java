package org.abonehatirlatici.neoduyorum.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class NotificationRequest {

    private Long userId;
    private Long paymentPlanId;
    private LocalDate notificationDate;
    private String status;

}
