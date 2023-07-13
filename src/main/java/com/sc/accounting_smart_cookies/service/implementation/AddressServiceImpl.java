package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.client.CountryClient;
import com.sc.accounting_smart_cookies.dto.countries.AuthorizationTokenDTO;
import com.sc.accounting_smart_cookies.dto.countries.CountryDTO;
import com.sc.accounting_smart_cookies.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
public class AddressServiceImpl implements AddressService {

    private String apiToken;

    private String userEmail;

//    @Value("$auth_token")
//    private String authToken;

    private final CountryClient countryClient;

    public AddressServiceImpl(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    private String getAuthToken(){
        AuthorizationTokenDTO tokenDTO = countryClient.getToken(apiToken,userEmail);
        if(tokenDTO != null){
            String token = "Bearer" + tokenDTO.getAuthToken();
            return token;
        }
        throw new NoSuchElementException("Auth token not found");

    }


    @Override
    public List<String> retrieveCountyList() {

        List<CountryDTO> countries = countryClient.getCountries(getAuthToken());

        return countries.stream().map(CountryDTO::getCountryName).collect(Collectors.toList());

    }
}
