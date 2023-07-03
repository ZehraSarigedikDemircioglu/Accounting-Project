package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.RoleDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;

import java.util.List;

public interface RoleService {


    RoleDTO findById(Long id);
    List<RoleDTO>getAllRoles();
    List<RoleDTO>getAllRolesForCurrentUser();
}
