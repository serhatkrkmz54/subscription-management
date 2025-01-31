package org.abonehatirlatici.neoduyorum.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionGroupResponse {

    private String abonelikAdi;
    private List<SubscriptionDetailResponse> paketler;

}
