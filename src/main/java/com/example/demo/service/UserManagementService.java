package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.demo.dto.input.UserEntry;
import com.google.gson.JsonElement;

@Service
public interface UserManagementService {

	public JSONObject signIn(UserEntry userEntry);

	public JSONObject signUp(UserEntry userEntry);

	public JSONObject getResource(HttpServletRequest request);

}
