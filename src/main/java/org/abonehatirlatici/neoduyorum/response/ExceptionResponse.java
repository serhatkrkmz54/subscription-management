package org.abonehatirlatici.neoduyorum.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private Integer hataKodu;
    private String hataKoduAciklama;
    private String hata;
    private Set<String> validationErrors;
    private Map<String,String> errors;

}
