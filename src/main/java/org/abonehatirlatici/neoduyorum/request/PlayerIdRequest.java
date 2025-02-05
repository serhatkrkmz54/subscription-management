package org.abonehatirlatici.neoduyorum.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlayerIdRequest {

    private Long userId;
    private String playerId;


}
