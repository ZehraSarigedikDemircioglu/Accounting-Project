package com.sc.accounting_smart_cookies.client;

import com.sc.accounting_smart_cookies.dto.countries.AuthorizationTokenDTO;
import com.sc.accounting_smart_cookies.dto.countries.CountryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "https://www.universal-tutorial.com/api", name ="COUNTRY-CLIENT" )
public interface CountryClient {
    @GetMapping( value ="/getaccesstoken", consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthorizationTokenDTO getToken(@RequestHeader("user-email") String email,
                                   @RequestHeader("api-token") String token
                                   );
    @GetMapping(value = "/countries/", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<CountryDTO> getCountries(@RequestHeader("Authorization") String authToken);
}
