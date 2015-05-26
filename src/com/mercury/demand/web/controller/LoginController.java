package com.mercury.demand.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value="/security/login.htm", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "security/login";
	}
	
	@RequestMapping(value="/content/main.htm", method = RequestMethod.GET)
	public ModelAndView mainPage() {	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("content/hello");
		mav.addObject("title", "Hello, welcome to Customized Spring Security");
		return mav;
	}
}
