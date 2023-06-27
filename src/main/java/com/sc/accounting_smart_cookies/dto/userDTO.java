package com.sc.accounting_smart_cookies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class userDTO {

    private Long id;
    private String userName;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phone;
//    private RoleDTO role;
//    private CompanyDTO company;
    private boolean isOnlyAdmin;
}
