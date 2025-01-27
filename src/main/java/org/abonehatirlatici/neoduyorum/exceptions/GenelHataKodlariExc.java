package org.abonehatirlatici.neoduyorum.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenelHataKodlariExc extends RuntimeException{

    private final GenelHataKodlari genelHataKodlari;

    public GenelHataKodlariExc(GenelHataKodlari genelHataKodlari) {
        super(genelHataKodlari.getDescription());
        this.genelHataKodlari = genelHataKodlari;
    }
    public HttpStatus getHttpStatus() {
        return genelHataKodlari.getHttpStatus();
    }
    public int getCode() {
        return genelHataKodlari.getCode();
    }
}
