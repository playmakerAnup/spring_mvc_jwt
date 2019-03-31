package com.example.demo;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.input.UserEntry;
import com.example.demo.service.UserManagementService;
import com.example.demo.utils.Validate;
import com.google.gson.Gson;

/**
 * @author Anup Nair
 * Created on 30th-March-2019
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserManagementService userManagementService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/signIn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String signIn(@RequestBody UserEntry userEntry) {
		logger.info("signIn attempt");
		return new String(new Gson().toJson(userManagementService.signIn(userEntry))) ;
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String signUp(UserEntry userEntry) {
		logger.info("signUp attempt");
		return new String(new Gson().toJson(userManagementService.signUp(userEntry)));
	}
	
	@RequestMapping(value = "/resource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String resource(HttpServletRequest request) {
		return new String(new Gson().toJson(userManagementService.getResource(request))) ;
	}
}
