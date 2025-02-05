package org.abonehatirlatici.neoduyorum.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {

    private String fullName;

    private String phoneNumber;

    private String gender;

    private String oldPassword;

    private String newPassword;

}
