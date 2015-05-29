package com.mercury.demand.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mercury.demand.persistence.model.History;
import com.mercury.demand.persistence.model.Person;
import com.mercury.demand.persistence.model.Station;
import com.mercury.demand.persistence.model.Ticket;
import com.mercury.demand.service.HistoryDetailsService;
import com.mercury.demand.service.ModUserDetailsService;
import com.mercury.demand.service.StationDetailsService;
import com.mercury.demand.service.TicketDetailsService;

@Controller
public class LoginController {
	@Autowired
	private StationDetailsService stationDetailsService;
	@Autowired
	private HistoryDetailsService historyDetailsService;
	@Autowired
	private TicketDetailsService ticketDetailsService;
	@Autowired
	private ModUserDetailsService userDetailsService;

	public StationDetailsService getStationDetailsService() {
		return stationDetailsService;
	}

	public void setStationDetailsService(StationDetailsService stationDetailsService) {
		this.stationDetailsService = stationDetailsService;
	}

	public HistoryDetailsService getHistoryDetailsService() {
		return historyDetailsService;
	}

	public void setHistoryDetailsService(HistoryDetailsService historyDetailsService) {
		this.historyDetailsService = historyDetailsService;
	}

	public TicketDetailsService getTicketDetailsService() {
		return ticketDetailsService;
	}

	public void setTicketDetailsService(TicketDetailsService ticketDetailsService) {
		this.ticketDetailsService = ticketDetailsService;
	}
	
	@RequestMapping(value="/security/login.html", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "security/login";
	}
	
	@RequestMapping(value="/welcome/welcome.html", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request, ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("welcome/welcome");
		return mav;
	}	
	
	@RequestMapping(value="/account/account.html", method = RequestMethod.GET)
	public ModelAndView account(HttpServletRequest request, ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("account/dashboard");
		return mav;
	}	
	@RequestMapping(value="/content/main.html", method = RequestMethod.GET)
	public ModelAndView mainPage() {	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("content/hello");
		mav.addObject("title", "Hello, welcome to Customized Spring Security");
		return mav;
	}
	
	
	//****************************************
	//************RESTFUL PART API************
	//****************************************
	
	//****************************************
	//***************Station******************
	//****************************************
	//Get all stations
	@RequestMapping(value="/restful/Stations.html", method = RequestMethod.GET)
	public @ResponseBody List<Station> getAllStations() {
		return stationDetailsService.getAllStations();
	}
	
	//****************************************
	//***************Ticket*******************
	//****************************************
	//Get all tickets
	@RequestMapping(value="/restful/Tickets.html", method = RequestMethod.GET)
	public @ResponseBody List<Ticket> getAllTickets() {
		return ticketDetailsService.getAllTickets();
	}
	
	//Get tickets during a period of time
	@RequestMapping(value="/restful/PeroidTickets.html", method = RequestMethod.POST)
	public @ResponseBody List<Ticket> getTicketsDuringPeriodTime(@RequestParam("From") String from, 
																 @RequestParam("To") String to,
																 @RequestParam("Time") String time) {
		System.out.println("From:"+from+" To:"+to+" Date:"+time);
		return ticketDetailsService.getAllTickets();
	}

	//****************************************
	//***************History******************
	//****************************************	
	//Get all history
	@RequestMapping(value="/auth/History.html", method = RequestMethod.GET)
	public @ResponseBody List<History> getAllHistory() {
		return historyDetailsService.getAllHistory();
	}
	
	//****************************************
	//***************Users*******************
	//****************************************
	//Get a certain user by username
	@RequestMapping(value="/auth/GetUser.html", method = RequestMethod.POST)
	public @ResponseBody Person getCertainUserByUsername(@RequestParam("username") String username) {
		//get current login user
	    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    System.out.println(user.getUsername()); //get logged in username
		return userDetailsService.getUserByUsername(username);
	}
}
