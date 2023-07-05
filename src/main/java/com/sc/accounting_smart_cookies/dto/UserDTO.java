package com.sc.accounting_smart_cookies.dto;

import com.sc.accounting_smart_cookies.annotation.UniqueEmail;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UniqueEmail
@Builder
public class UserDTO {

    private Long id;
    @NotBlank()
    @NotNull
    @Email(message = "A user with this email already exists. Please try with different email.")
    private String username;
    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String password;
    @NotNull
    private String confirmPassword;
    @NotBlank
    @Size(max = 50, min = 2)
    private String firstname;
    @NotBlank
    @Size(max = 50, min = 2)
    private String lastname;

    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // +111 (202) 555-0125  +1 (202) 555-0125
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"                                  // +111 123 456 789
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")                     // +111 123 45 67 89
    private String phone;
    @NotNull
    private RoleDTO role;
    @NotNull
    private CompanyDTO company;
    private boolean isOnlyAdmin;
    private boolean enabled;

    public void setPassword(String password) {
        this.password = password;
        checkConfirmPassword();
    }

    public void setConfirmPassword(String confirmPassWord) {
        this.confirmPassword = confirmPassWord;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if (this.password == null || this.confirmPassword == null) {
            return;
        } else if (!this.password.equals(this.confirmPassword)) {
            this.confirmPassword = null;
        }
    }


}
