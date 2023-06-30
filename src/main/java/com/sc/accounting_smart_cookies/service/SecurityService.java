package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService extends UserDetailsService {

    UserDTO getLoggedInUser();
}
