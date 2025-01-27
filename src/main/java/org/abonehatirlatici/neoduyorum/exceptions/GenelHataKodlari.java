package org.abonehatirlatici.neoduyorum.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum GenelHataKodlari {

    NO_CODE(0,HttpStatus.NOT_IMPLEMENTED, "No Code"),
    INCORRECTED_CURRENT_PASSWORD(300,HttpStatus.BAD_REQUEST, "Parola Geçersiz."),
    NEW_PASSWORD_DOES_NOT_MUCH(301, HttpStatus.BAD_REQUEST, "Yeni Parola Eşleşmiyor."),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "Kullanıcı hesabı kilitli."),
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "Kullanıcı hesabı devre dışı"),
    BAD_CREDENTIALS(304,HttpStatus.FORBIDDEN, "Giriş yapılan parola veya email geçersiz."),
    EMAIL_ALREADY_EXISTS(305, HttpStatus.BAD_REQUEST, "Aynı mail adresi ile kayıt olamazsınız.");


    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;


    GenelHataKodlari(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
