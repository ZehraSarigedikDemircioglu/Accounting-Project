package com.sc.accounting_smart_cookies.service.impl;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO findByUsername(String username) {
        return null;
    }
}
