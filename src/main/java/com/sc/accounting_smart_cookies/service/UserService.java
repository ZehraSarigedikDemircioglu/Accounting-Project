package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.UserDTO;

public interface UserService {

    UserDTO findByUsername(String username);
}
