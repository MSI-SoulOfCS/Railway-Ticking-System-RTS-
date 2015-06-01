package com.mercury.demand.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value="/index.html", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request, ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("welcome/welcome");
		return mav;
	}	
	
	/*@RequestMapping(value="/payment/payment.html",method=RequestMethod.POST)
	public ModelAndView payment(@RequestParam("From") String from,
								@RequestParam("To") String to,
								@RequestParam("Time") String time,
								@RequestParam("Amount") String amount,
								@RequestParam("Price") String price){
		List<Ticket> result;
		ModelAndView mav = new ModelAndView();
		mav.setViewName("payment/payment");
		mav.addObject("purchasedTickets", result);

		return mav;
	}*/
	
	@RequestMapping(value="/content/ticket.html", method = RequestMethod.POST)
	public ModelAndView ticket(@RequestParam("From") String from, 
			   				   @RequestParam("To") String to,
			   				   @RequestParam("Time") String time, 
			 				   ModelMap model) {
		System.out.println("From:"+from+" To:"+to+" Date:"+time);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String[] DateAndTime = time.split("/");
		String[] HourTime = DateAndTime[1].split("-");
		Date firstDate;
		Date secondDate;
		
		List<Ticket> result = ticketDetailsService.getAllTickets();
		if(!DateAndTime[0].equals("")) {
			if(DateAndTime[1].equals("Anytime")) {
				try {
					String timestamp = DateAndTime[0] + " 00:00:00.0";
					firstDate = dateFormat.parse(timestamp);
					timestamp = DateAndTime[0] + " 23:59:59.0";
					secondDate = dateFormat.parse(timestamp);
					result = ticketDetailsService.getPeroidTimeOfTikcets(from, to, firstDate, secondDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			else if(HourTime.length == 2) {
				try {
					String timestamp = DateAndTime[0] + " " + HourTime[0] + ":00:00.0";
					firstDate = dateFormat.parse(timestamp);

					if(HourTime[1] != "24")
						timestamp = DateAndTime[0] + " " + HourTime[1] + ":00:00.0";
					else
						timestamp = DateAndTime[0] + " 23:59:59.0";

					secondDate = dateFormat.parse(timestamp);
					result = ticketDetailsService.getPeroidTimeOfTikcets(from, to, firstDate, secondDate);			
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					String timestamp = DateAndTime[0] + " " + HourTime[0] + ":00:00.0";
					firstDate = dateFormat.parse(timestamp);

					timestamp = DateAndTime[0] + " " + HourTime[0] + ":59:59.0";
					secondDate = dateFormat.parse(timestamp);
					result = ticketDetailsService.getPeroidTimeOfTikcets(from, to, firstDate, secondDate);			
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		};
		ModelAndView mav = new ModelAndView();
		mav.setViewName("content/ticket");
		mav.addObject("resultTickets", result);

		return mav;
	}
	
	@RequestMapping(value="/payment/shoppingcart.html", method = RequestMethod.GET)
	public ModelAndView shoppingcart(HttpServletRequest request, ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("payment/shoppingcart");
		return mav;
	}	
	
	@RequestMapping(value="/payment/successCheckOut.html", method = RequestMethod.GET)
	public ModelAndView successCheckOut(HttpServletRequest request, ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("payment/successCheckOut");
		return mav;
	}	
	
	
	@RequestMapping(value="/auth/user.html", method = RequestMethod.GET)
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
}