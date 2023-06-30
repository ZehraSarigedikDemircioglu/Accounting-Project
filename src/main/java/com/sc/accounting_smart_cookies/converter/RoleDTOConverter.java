package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.RoleDTO;
import com.sc.accounting_smart_cookies.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleDTOConverter implements Converter<String, RoleDTO> {

    @Autowired
    RoleService roleService;
    @Override
    public RoleDTO convert(String source) {
        if (source==null||source.equals("")){
        return null;
        }
        return roleService.findById(Long.parseLong(source));
    }
}
