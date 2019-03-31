package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.input.UserEntry;
import com.example.demo.jwt.service.JwtUtil;
import com.example.demo.model.UserModel;
import com.example.demo.utils.Validate;
import com.google.gson.Gson;

@Service("UserManagementService")
@Scope("prototype")
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	JwtUtil jwtUtil;

	private static Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

	@Override
	public JSONObject signIn(UserEntry userEntry) {
		JSONObject json = new JSONObject();
		try {
			if (("admin").equals(userEntry.getUsername()) && ("admin").equals(userEntry.getPassword())) {
				// GET Authentic user from RDBMS(optional) => eg. userId is 10
				UserModel userModel = new UserModel();
				userModel.setUserId(10);
				String token = jwtUtil.generatePrivilegedToken(userModel);

				if (!Validate.isEmpty(token)) {
					json.put("token", token);
					json.put("responseMessage", "Sign In Successfull");
				}

			} else {
				return json.put("responseMessage", "Invalid credentials");
			}
		} catch (Exception e) {
			logger.error("Exception in UserManagementServiceImpl -> signIn()" + e);
		}

		return json;
	}

	@Override
	public JSONObject signUp(UserEntry userEntry) {

		JSONObject json = new JSONObject();
		try {
			// SAVE user to RDMS and get userId => eg. userId is 20
			UserModel userModel = new UserModel();
			userModel.setUserId(20);
			String token = jwtUtil.generatePrivilegedToken(userModel);
			if (!Validate.isEmpty(token)) {
				json.put("token", token);
				json.put("responseMessage", "Sign In Successfull");
			}
			
		} catch (Exception e) {
			logger.error("Exception in UserManagementServiceImpl -> signIn()" + e);
		}

		return json;

	}

	@Override
	public JSONObject getResource(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			if (!Validate.isEmpty(request.getAttribute("authToken"))) {
				json.put("token", request.getAttribute("authToken").toString().trim());
				json.put("resource", "Valuable Resource");
			} else {
				logger.error("Token Empty -> from UserManagementServiceImpl getReource()");
			}
		} catch (Exception e) {
			logger.error("Exception in UserManagementServiceImpl -> getReource()" + e);
		}
		return json;

	}

}
