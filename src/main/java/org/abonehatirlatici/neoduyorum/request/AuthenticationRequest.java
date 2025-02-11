package org.abonehatirlatici.neoduyorum.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email formatı")
    @NotEmpty(message = "Email girmek zorunludur")
    @NotBlank(message = "Email girmek zorunludur")
    private String email;
    @Size(min = 3, message = "Parola en az 3 karakter uzunluğunda olmalı")
    @NotEmpty(message = "Parola girmek zorunludur")
    @NotBlank(message = "Parola girmek zorunludur")
    private String password;

    private String expoPushToken;

}
