package com.sc.accounting_smart_cookies.annotation;//package com.sc.accounting_smart_cookies.annotation;
//
//
//import com.sc.accounting_smart_cookies.dto.UserDTO;
//import com.sc.accounting_smart_cookies.entity.User;
//import com.sc.accounting_smart_cookies.repository.UserRepository;
//import com.sc.accounting_smart_cookies.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//
//@Component
//public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    UserService userService;
//
//    public UniqueEmailValidator(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
////    @Override
////    public void initialize(UniqueEmail constraintAnnotation) {
////        ConstraintValidator.super.initialize(constraintAnnotation);
////    }
//
//    @Override
//    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
////        User user=userRepository.findByUsername(username);
////        if (emailExist(username)){
////          return true;
////        }
//
//        return username != null && !userRepository.existsByUsername(username);
//
//    }
//
//    private boolean emailExist(String email) {
//        UserDTO userDTO1 = userService.findByUsername(email);
//        return userDTO1.getId() != null;
//    }
//}


import com.sc.accounting_smart_cookies.annotation.UniqueEmail;
import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.User;
import com.sc.accounting_smart_cookies.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDTO> {
    private final UserRepository userRepository;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext context) {
        if (user.getUsername() == null) {
            return true; // Skip validation if email is not provided
        }

        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            return true; // Email is unique
        }

        // Check if the existing user has the same ID as the current user
        return existingUser.getId().equals(user.getId());
    }
}
