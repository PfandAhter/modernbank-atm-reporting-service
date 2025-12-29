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

    @Override
    public String extractToken(){
        String token = null;
        try{
            token = request.getHeader(HeaderKey.AUTHORIZATION_TOKEN);
        }catch (Exception E){
            log.error("Error while extracting token from header: {}", E.getMessage());
        }
        return token;
    }

    @Override
    public String extractUserId(){
        String userId = null;
        try{
            String token = extractToken();
            //userId = securityServiceClient.extractUserIdFromToken();
            userId = token.split(" ")[1]; //TODO: BURADA TOKEN SERVICE CAGIRIMI YAPILICAKTIR...
        }catch (Exception E){
            log.error("Error while extracting userId from token: {}", E.getMessage());
        }
        return userId;
    }

    @Override
    public String extractUserRole(){
        String userRole = null;
        try{
            userRole = request.getHeader(HeaderKey.USER_ROLE);
        }catch (Exception e){
            log.error("");
        }

        return userRole;
    }
}