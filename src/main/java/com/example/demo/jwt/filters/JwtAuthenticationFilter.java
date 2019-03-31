package com.example.demo.jwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.demo.jwt.bean.JwtAuthenticationToken;
import com.example.demo.utils.Validate;

import io.jsonwebtoken.JwtException;

/**
 * JWT filter to authenticate token passed with HttpServletRequest requests<br>
 * <h4>Created On: 16th-Jan-2019</h4>
 * @author Anup Nair
 */
@ComponentScan
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtExceptionHandlingFilter.class);
	
	public JwtAuthenticationFilter() {
        super("/**");   
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
    	log.info("Attempting authentication inside attemptAuthentication() in JwtAuthenticationFilter");
    	String header = null;
    	if(!Validate.isEmpty(request.getHeader("Authorization"))) {
        	header = request.getHeader("Authorization");        	
        }

        if (header == null || !header.startsWith("Bearer ")) {
        	log.error("Unauthorized API request caught inside attemptAuthentication() in JwtAuthenticationFilter for remote address: "+request.getRemoteAddr());
        	throw new JwtException("No JWT token found in request headers");
        }
        String authToken = header.substring(7);
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
        Authentication authentication = null;
        authentication = getAuthenticationManager().authenticate(authRequest);
        // authentication.setAuthenticated(true);
        /*try {
        	authentication = getAuthenticationManager().authenticate(authRequest);
        } catch(Exception ex) {
        	System.out.println("Invalid Token caught");
        	return (Authentication) new RuntimeException("Invalid Token caught");
        }*/
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        log.info("Inside successfulAuthentication() in JwtAuthenticationFilter");
        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }
    
}
