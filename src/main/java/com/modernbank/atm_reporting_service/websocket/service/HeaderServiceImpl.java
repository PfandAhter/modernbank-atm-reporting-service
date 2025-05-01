package com.modernbank.atm_reporting_service.websocket.service;

import com.modernbank.atm_reporting_service.constants.HeaderKey;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IHeaderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HeaderServiceImpl implements IHeaderService {

    private final HttpServletRequest request;

    //TODO: Token Client cagirimi ve tokenden userid extraction islemi

    public String extractToken(){
        String token = null;
        try{
            token = request.getHeader(HeaderKey.AUTHORIZATION_TOKEN);
        }catch (Exception E){
            log.error("test");
        }
        return token;
    }

    public String extractUserId(){
        String userId = null;
        try{
            String token = extractToken();
            userId = token.split(" ")[1]; //TODO: BURADA TOKEN SERVICE CAGIRIMI YAPILICAKTIR...
        }catch (Exception E){
            log.error("test");
        }
        return userId;
    }
}