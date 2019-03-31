package com.example.demo.jwt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.jwt.bean.AuthenticatedUser;
import com.example.demo.jwt.bean.JwtAuthenticationToken;
import com.example.demo.model.UserModel;

/**
 * Authentication provider class used to parse and validate JWT tokens 
 * <h4>Created On: 15th-Jan-2019</h4><br>
 * @author Anup Nair
 */
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
    		
	
	@Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
    	log.info("Inside retrieveUser() in JwtAuthenticationProvider");
    	JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();

        UserModel parsedUser = jwtUtil.parseToken(token);

        /*To be used for throwing an exception for invalid token and parsed user*/
        /*if (parsedUser == null) {
            try {
				throw new Exception("JWT token is not valid");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/

        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("");
        return new AuthenticatedUser(parsedUser.getAuthToken(), authorityList);
    }

}
