package com.sc.accounting_smart_cookies.aspect;

import com.sc.accounting_smart_cookies.entity.common.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private UserPrincipal getCompany() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserPrincipal) authentication.getPrincipal());
    }

    private String getUserFirstNameLastNameAndUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserPrincipal) authentication.getPrincipal()).getFullNameForProfile() + " " + authentication.getName();
    }

    @Pointcut("execution(* com.sc.accounting_smart_cookies.controller.CompanyController.activateCompany(..))")
    public void loggingCompanyActivation() {
    }

    @Pointcut("execution(* com.sc.accounting_smart_cookies.controller.CompanyController.deactivateCompany(..))")
    public void loggingCompanyDeactivation() {
    }

    @AfterReturning(pointcut = "loggingCompanyActivation()", returning = "results")
    public void afterLoggingCompanyActivation(JoinPoint joinPoint, Object results) {
        log.info("After Returning -> Method name: {}, Company Name: {}, First Name, Last Name and username of logged-in user: {}",
                joinPoint.getSignature().toShortString()
                , getCompany().getCompanyTitleForProfile()
                , getUserFirstNameLastNameAndUserName()
                , results.toString());
    }

    @AfterReturning(pointcut = "loggingCompanyDeactivation()", returning = "results")
    public void afterLoggingCompanyDeactivation(JoinPoint joinPoint, Object results) {
        log.info("After Returning -> Method name: {}, Company Name: {}, First Name, Last Name and username of logged-in user: {}",
                joinPoint.getSignature().toShortString()
                , getCompany().getCompanyTitleForProfile()
                , getUserFirstNameLastNameAndUserName()
                , results.toString());
    }

    @Pointcut("@annotation(com.sc.accounting_smart_cookies.annotation.LoggingAnnotation)")
    public void loggingAnnotationPC() {
    }

    @Around("loggingAnnotationPC()")
    // @Around contains code which is executed before and after the matched method (JoinPoint)
    public Object anyLoggingAnnotationOperation(ProceedingJoinPoint proceedingJoinPoint) {

        long beforeTime = System.currentTimeMillis();
        Object result = null;
        log.info("Execution starts:");

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long afterTime = System.currentTimeMillis();

        log.info("Time taken to execute: {} ms - Method: {}"
                , (afterTime - beforeTime), proceedingJoinPoint.getSignature().toShortString());
        return result;
    }
}
