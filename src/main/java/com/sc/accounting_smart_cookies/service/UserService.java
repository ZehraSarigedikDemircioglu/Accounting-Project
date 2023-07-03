package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.UserDTO;

import java.util.List;
public interface UserService {

    List<UserDTO>getAllUsers();

    boolean findByUsername1(String username);

    UserDTO findByUsername(String username);

    UserDTO findById(Long id);

    UserDTO updateUser(UserDTO dto);

    void save(UserDTO dto);

    void delete(Long id);
}
