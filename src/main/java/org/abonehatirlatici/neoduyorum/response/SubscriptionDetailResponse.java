package org.abonehatirlatici.neoduyorum.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionDetailResponse {

    private Long id;
    private String sFrequency;
    private String paketAdi;
    private String sOdemeBirimi;
    private Double sOdemeMiktari;



}
