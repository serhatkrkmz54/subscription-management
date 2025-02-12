package org.abonehatirlatici.neoduyorum.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.abonehatirlatici.neoduyorum.entity.enums.Gender;

@Setter
@Getter
@Builder
public class RegistrationRequest {

    @Email(message = "Email formatına uygun olmalıdır.")
    @NotEmpty(message = "Email Girmek zorunludur.")
    @NotBlank(message = "Email girmek zorunludur.")
    private String email;

    @NotEmpty(message = "Telefon numarası girmek zorunludur.")
    @NotBlank(message = "Telefon numarası girmek zorunludur")
    private String phoneNumber;

    @Size(min = 4, message = "Parola en az 4 karakter uzunluğunda olmalıdır.")
    @NotEmpty(message = "Parola girmek zorunludur.")
    @NotBlank(message = "Parola Girmek zorunludur.")
    private String password;
    private String fullName;
    private Gender gender;
    private String expoPushToken;

}
