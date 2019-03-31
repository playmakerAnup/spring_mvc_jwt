package com.example.demo.jwt.bean;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Bean class for parsing JWT token from HttpServlet request<br>
 * <h4>Created On: 18th-Jan-2019</h4><br>
 * 
 * @author Anup Nair
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

    private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}

