package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.client.CountryClient;
import com.sc.accounting_smart_cookies.dto.countries.AuthorizationTokenDTO;
import com.sc.accounting_smart_cookies.dto.countries.CountryDTO;
import com.sc.accounting_smart_cookies.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplUnitTest {

    @Mock
    private CountryClient countryClient;
    @InjectMocks
    AddressServiceImpl addressService;

    @Test
    void should_retrieve_countryList() {
        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setCountryName("Netherlands");
        CountryDTO countryDTO2 = new CountryDTO();
        countryDTO2.setCountryName("Germany");
        List<CountryDTO> countryDTOList = new ArrayList<>();
        countryDTOList.add(countryDTO1);
        countryDTOList.add(countryDTO2);

        AuthorizationTokenDTO authorizationTokenDTO = new AuthorizationTokenDTO();
        authorizationTokenDTO.setAuthToken("TokenZehra");

        when(countryClient.getToken(any(), any())).thenReturn(authorizationTokenDTO);
        when(countryClient.getCountries("Bearer " + authorizationTokenDTO.getAuthToken())).thenReturn(countryDTOList);

        List<String> countryList = addressService.retrieveCountyList();
        assertEquals(2, countryList.size());
    }
    @Test
    void should_throw_exception_when_token_is_null() {
        when(countryClient.getToken(any(), any())).thenReturn(null);
        Throwable throwable = catchThrowable(() ->
                addressService.retrieveCountyList());
        assertThat(throwable).isInstanceOf(RuntimeException.class);
    }
}
