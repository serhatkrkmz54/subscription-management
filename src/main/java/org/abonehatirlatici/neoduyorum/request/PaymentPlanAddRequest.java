package org.abonehatirlatici.neoduyorum.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PaymentPlanAddRequest {

    private String abonelikAdi;
    private Double odemeMiktari;
    private String odemeBirimi;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;
    private String frequency;
    private Integer last4Digits;
    private String cardName;

}
