package com.example.demo.jwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.utils.Validate;
import com.google.gson.Gson;

/**
 * JWT exception filter to to catch exceptions in HttpServletRequest requests<br>
 * <h4>Created On: 16th-Jan-2019</h4>
 * @author Anup Nair
 */
@ComponentScan
public class JwtExceptionHandlingFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtExceptionHandlingFilter.class);
	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			log.info("Inside JwtExceptionHandlingFilter");
			log.info("Request with Authorization " + request.getHeader("Authorization"));
			filterChain.doFilter(request, response);
		} catch (RuntimeException e) {
			// custom error response class used across my project
			log.error("Global filter exception caught inside doFilterInternal() JwtExceptionHandlingFilter with message: "+e);
			// JSONArray errorResponse = new JSONArray();
			String errorResponse = "Unauthorized resource access request";
	        /*if (Validate.isEmpty(request.getHeader("Authorization"))) {
	        	String header = request.getHeader("Authorization");
	        	if(header == null || !header.startsWith("Bearer ")) {
	        		errorResponse.setResponseMessage(ResponseMessageConstant.ACCESS_DENIED);
		        	log.error("Exception caught in doFilterInternal() JwtExceptionHandlingFilter since no token was passed with exception message: "+e);	
	        	}
	        } else {
	        	errorResponse.setResponseMessage(ResponseMessageConstant.INVALID_TOKEN);
	        	log.error("Exception caught in doFilterInternal() JwtExceptionHandlingFilter due to invalid token with exception message: "+e);
	        }*/
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.getWriter().write(new String(new Gson().toJson((errorResponse))));

		}
	}
	
}