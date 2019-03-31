package com.example.demo.jwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.jwt.bean.AuthenticatedUser;

/**
 * Final step after successful authentication of a JWT token<br> 
 * <h4>Created On: 15th-Jan-2019</h4><br>
 * @author Anup Nair
 */
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("API request authentication successfull. Inside onAuthenticationSuccess() in JwtAuthenticationSuccessHandler");
    	// We do not need to do anything extra on REST authentication success, because there is no page to redirect to
    	AuthenticatedUser user = new AuthenticatedUser();
    	user = (AuthenticatedUser) authentication.getPrincipal();
    	// request.setAttribute("authToken",authentication.getPrincipal());
    	request.setAttribute("authToken",user.getToken());
    }

}	