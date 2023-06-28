package com.sc.accounting_smart_cookies;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AccountingSmartCookiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingSmartCookiesApplication.class, args);
    }

    @Bean
    public ModelMapper mapper(){

        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
