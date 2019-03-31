package com.example.demo.jwt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.model.UserModel;
import com.example.demo.redis.dto.UserCache;
import com.example.demo.redis.repository.UserRepository;
import com.example.demo.utils.Validate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * JWT utility class which manages parsing and generation of JWT tokens for privileged and non privileged users<br>  
 * <h4>Created On: 15th-Jan-2019</h4><br>
 * @author Anup Nair
 */
@Component
public class JwtUtil {

	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
	
	@Value("${jwt.secret}")
    private String secret;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), throws a RunTimeException.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or throws a RunTimeException if a token is invalid.
     */
    public UserModel parseToken(String token) {
        try {
            log.info("Inside parseToken() in JwtUtil");
        	Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            UserModel userModel = new UserModel();
            Integer userId = Integer.parseInt((String) body.get("userId"));
            UserCache userCache = new UserCache();
            if(0 != userId) {
            	userModel.setUserId(userId);
            	try {
            		userCache = userRepository.getUserByHashKey(userModel.getUserId());	
            	} catch(JedisConnectionException ex) {
            		log.error("Please check redis server connectivity inside parseToken() in JwtUtil");
            	} catch(Exception ex) {
            		log.error("Please check redis server connectivity inside parseToken() in JwtUtil");
            	}
            }
            String tokenTimeStamp = ((String) body.get("timeStamp")).trim();
            // UserCache userCache = userRepository.getUserByHashKey(userModal.getUserId()+":"+userModal.getDeviceId());
            if(!Validate.isEmpty(userCache)) {
            	String revisedToken = null;
            	if(tokenTimeStamp.equals(userCache.getTokenTimeStampInMillis())) {
            		
            		revisedToken =  generatePrivilegedToken(userModel);
                	System.out.println("Revised Token: "+revisedToken);
                	userModel.setAuthToken(revisedToken); // New Token
                	
                } else if(!Validate.isEmpty(tokenTimeStamp) && !Validate.isEmpty(userCache.getTokenTimeStampInMillis())) {
                	
            		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                	Date requestedDate,cacheDate;
            		try {
            			requestedDate = format.parse(tokenTimeStamp);
            			cacheDate = format.parse(userCache.getTokenTimeStampInMillis());
            	    	long difference = cacheDate.getTime() - requestedDate.getTime();
            	    	// 3 second buffer allowed so as to handle concurrent request scenario
            	    	if(difference < 300000) {
            	    		
                            	revisedToken = generatePrivilegedToken(userModel);
            	    	}
            		} catch (ParseException e) {
            			
            			throw new RuntimeException();
            		}
                	log.info("Revised Token: "+revisedToken);
                	userModel.setAuthToken(revisedToken); // New Token
                } else {
                	throw new RuntimeException();
                }
            } else {
            	log.error("userCache object has not been returned from Redis Cache Server. Please check redis server connectivity");
            	throw new RuntimeException();
            }
            return userModel;

        } catch (JwtException e) {
        	throw new RuntimeException();
        }
    }

    /**
     * Generates a JWT token with userId and timeStamp as claims.
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generatePrivilegedToken(UserModel userModel) {
    	log.info("Inside generatePrivilegedToken() in JwtUtil");
    	String jwtToken = null;
    	if(!Validate.isEmpty(userModel)) {
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	String tokenTimeStamp = simpleDateFormat.format(new Date(System.currentTimeMillis())).replaceAll("[^0-9]", "").trim();
            Claims claims = Jwts.claims();
            // = Jwts.claims().setSubject(userModal.getUserName())
            claims.put("userId", userModel.getUserId() + "");
            claims.put("timeStamp", tokenTimeStamp + "");
            jwtToken = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, secret )
                    // .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                    // .setExpiration(new Date(System.currentTimeMillis() + 5000))
                    .compact();
			UserCache userCache = new UserCache();
        	userCache.setTokenTimeStampInMillis(tokenTimeStamp);
            userCache.setUserId(userModel.getUserId());
            try {
            	userRepository.saveUser(userCache);	
            } catch(JedisConnectionException ex) {
            	log.error("Please check redis server connectivity inside generatePrivilegedToken() in JwtUtil");
            	jwtToken= null ;
        	} catch(Exception ex) {
        		log.error("Please check redis server connectivity inside generatePrivilegedToken() in JwtUtil");
        		jwtToken= null ;
        	}
    	} else {
    		log.error("null object userModal passed to generatePrivilegedToken() in JwtUtil");
    		throw new RuntimeException();
    	}
    	
        return jwtToken;
        
    }
    
}
