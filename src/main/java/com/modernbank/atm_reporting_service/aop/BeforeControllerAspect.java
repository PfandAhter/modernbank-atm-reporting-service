package com.modernbank.atm_reporting_service.aop;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.BaseRequest;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IHeaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@RequiredArgsConstructor

public class BeforeControllerAspect {

    private final IHeaderService headerService;

    @Before(value = "execution(* com.modernbank.atm_reporting_service.websocket.controller.ATMServiceController*(..))")
    public void setTokenBeforeController(JoinPoint joinPoint){
        Object[] parameters = joinPoint.getArgs();
        for(Object param : parameters){
            if(param instanceof BaseRequest baseRequest){
                String token = headerService.extractToken();
                String userId = headerService.extractUserId();
                baseRequest.setToken(token);
                baseRequest.setUserId(userId);
            }
        }
    }
}