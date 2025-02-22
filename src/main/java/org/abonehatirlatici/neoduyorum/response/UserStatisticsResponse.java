package org.abonehatirlatici.neoduyorum.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class UserStatisticsResponse {

    private BigDecimal monthlySpending;
    private BigDecimal yearlySpending;
    private Integer year;

}
