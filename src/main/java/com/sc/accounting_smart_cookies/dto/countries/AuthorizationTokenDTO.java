package com.sc.accounting_smart_cookies.dto.countries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationTokenDTO {

    @JsonProperty("auth_token")
    private String authToken;


}
