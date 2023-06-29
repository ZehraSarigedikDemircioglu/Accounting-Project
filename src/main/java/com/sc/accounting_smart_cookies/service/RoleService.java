package com.sc.accounting_smart_cookies.service;

import com.sc.accounting_smart_cookies.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO>getAllRoles();
    RoleDTO findById(Long id);
}
