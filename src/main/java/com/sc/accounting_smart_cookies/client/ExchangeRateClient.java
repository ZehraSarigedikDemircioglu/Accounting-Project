package com.sc.accounting_smart_cookies.client;

import com.sc.accounting_smart_cookies.dto.currency.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json", name ="EXCHANGERATES-CLIENT")
public interface ExchangeRateClient {

    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    ExchangeResponse getExchangeRates();


}
