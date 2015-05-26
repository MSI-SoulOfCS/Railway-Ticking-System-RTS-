package com.mercury.demand.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.mercury.demand.mail.MailAppBean;



public class PostSuccessfulAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	@Qualifier("mailApp")
	private MailAppBean mailApp;
	
    public MailAppBean getMailApp() {
		return mailApp;
	}
	public void setMailApp(MailAppBean mailApp) {
		this.mailApp = mailApp;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        //mailApp.sendMail("Peter", "This is a welcome email");
		super.onAuthenticationSuccess(request, response, authentication);
   }

}