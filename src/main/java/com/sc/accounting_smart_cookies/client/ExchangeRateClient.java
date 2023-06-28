package com.sc.accounting_smart_cookies.client;

import com.sc.accounting_smart_cookies.dto.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json", name ="EXCHANGERATES-CLIENT")
public interface ExchangeRateClient {

    @GetMapping("/exchange-rates")
    ExchangeRates getExchangeRates();

}
