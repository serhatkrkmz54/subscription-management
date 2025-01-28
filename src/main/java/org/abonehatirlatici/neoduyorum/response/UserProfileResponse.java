package org.abonehatirlatici.neoduyorum.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Builder
public class UserProfileResponse {

    private Long userId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private List<PaymentPlanAddResponse> paymentPlans;

}
