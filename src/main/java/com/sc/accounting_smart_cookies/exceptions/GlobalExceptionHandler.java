package com.sc.accounting_smart_cookies.exceptions;

import com.sc.accounting_smart_cookies.annotation.DefaultExceptionMessage;
import com.sc.accounting_smart_cookies.dto.DefaultExceptionMessageDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import java.lang.reflect.Method;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({AccountingProjectException.class})
    public String genericException(Throwable e, HandlerMethod handlerMethod, Model model) {
        String message = "Something went wrong!";
        Optional<DefaultExceptionMessageDto> defaultMessage = getMessageFromAnnotation(handlerMethod.getMethod());
        if (defaultMessage.isPresent() && !ObjectUtils.isEmpty(defaultMessage.get().getMessage())) {
            message = defaultMessage.get().getMessage();
        } else if (e.getMessage() != null) {
            message = e.getMessage();
        }
        model.addAttribute("message", message);
     return "";
    }


    private Optional<DefaultExceptionMessageDto> getMessageFromAnnotation(Method method) {
        DefaultExceptionMessage defaultExceptionMessage = method.getAnnotation(DefaultExceptionMessage.class);
        if (defaultExceptionMessage != null&& !defaultExceptionMessage.defaultMessage().equals("Something went wrong!")) {
            DefaultExceptionMessageDto defaultExceptionMessageDto = DefaultExceptionMessageDto
                    .builder()
                    .message(defaultExceptionMessage.defaultMessage())
                    .build();
            return Optional.of(defaultExceptionMessageDto);
        }
        return Optional.empty();
    }
}