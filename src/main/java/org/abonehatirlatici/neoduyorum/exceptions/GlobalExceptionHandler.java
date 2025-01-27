package org.abonehatirlatici.neoduyorum.exceptions;

import jakarta.mail.MessagingException;
import org.abonehatirlatici.neoduyorum.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .hataKodu(GenelHataKodlari.ACCOUNT_LOCKED.getCode())
                                .hataKoduAciklama(GenelHataKodlari.ACCOUNT_LOCKED.getDescription())
                                .hata(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .hataKodu(GenelHataKodlari.ACCOUNT_DISABLED.getCode())
                                .hataKoduAciklama(GenelHataKodlari.ACCOUNT_DISABLED.getDescription())
                                .hata(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .hataKodu(GenelHataKodlari.BAD_CREDENTIALS.getCode())
                                .hataKoduAciklama(GenelHataKodlari.BAD_CREDENTIALS.getDescription())
                                .hata(GenelHataKodlari.BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .hata(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(GenelHataKodlariExc.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(GenelHataKodlariExc exp) {
        GenelHataKodlari errorCodes = exp.getGenelHataKodlari();

        return ResponseEntity
                .status(errorCodes.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .hataKodu(errorCodes.getCode())
                                .hataKoduAciklama(errorCodes.getDescription())
                                .hata(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .hataKoduAciklama("Internal error, contact the admin.")
                                .hata(exp.getMessage())
                                .build()
                );
    }

}
