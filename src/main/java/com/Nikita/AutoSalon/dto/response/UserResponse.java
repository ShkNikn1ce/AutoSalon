package com.Nikita.AutoSalon.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long UserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
