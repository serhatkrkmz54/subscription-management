package org.abonehatirlatici.neoduyorum.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentPlanAddResponse {

    private Long id;
    private String abonelikAdi;
    private Double odemeMiktari;
    private String odemeBirimi;
    private LocalDate odemeTarihi;
    private LocalDate bitisTarihi;
    private String frequency;
    private Integer last4Digits;
    private String cardName;
    private Long userId;

}
