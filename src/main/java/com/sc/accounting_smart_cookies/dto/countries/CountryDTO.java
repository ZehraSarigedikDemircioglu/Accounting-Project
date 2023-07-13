package com.sc.accounting_smart_cookies.dto.countries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDTO {
//
//    @JsonProperty("country_name")
//    public String countryName;

    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("country_short_name")
    private String countryShortName;
    @JsonProperty("country_phone_code")
    private int countryPhoneCode;

}
