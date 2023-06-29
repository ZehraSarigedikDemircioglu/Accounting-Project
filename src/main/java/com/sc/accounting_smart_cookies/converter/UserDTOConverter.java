package com.sc.accounting_smart_cookies.converter;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter implements Converter<String, UserDTO> {
    @Autowired
    UserService userService;
    @Override
    public UserDTO convert(String source) {
        if (source==null||source.equals("")){
            return null;
        }
        return userService.findByUsername(source);
    }
}
