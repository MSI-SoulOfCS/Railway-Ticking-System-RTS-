package com.mercury.demand.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mercury.demand.persistence.dao.PersonDao;
import com.mercury.demand.persistence.model.CartEntity;
import com.mercury.demand.persistence.model.History;
import com.mercury.demand.persistence.model.Person;
import com.mercury.demand.persistence.model.Station;
import com.mercury.demand.persistence.model.Ticket;
import com.mercury.demand.service.HistoryDetailsService;
import com.mercury.demand.service.ModUserDetailsService;
import com.mercury.demand.service.StationDetailsService;
import com.mercury.demand.service.TicketDetailsService;
import com.redis.entity.CartItem;
import com.redis.entity.RedisRequest;
import com.redis.entity.RedisTicket;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
import com.redis.util.RelationConverter;

@Controller
public class RestfulController {
	@Autowired
	private StationDetailsService stationDetailsService;
	@Autowired
	private HistoryDetailsService historyDetailsService;
	@Autowired
	private TicketDetailsService ticketDetailsService;
	@Autowired
	private ModUserDetailsService userDetailsService;
	
	private TicketService ticketService;
	
	public RestfulController() {
		ticketService = new TicketServiceImpl();
	}
	
	

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
	public @ResponseBody List<RedisTicket> getTicketsByUser() {
		return ticketService.getAllTicket()	;
	}
	
	@RequestMapping(value="/restful/history.html", method = RequestMethod.GET)
	public @ResponseBody List<Ticket> getTicketsByUserID() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person person=userDetailsService.getUserByUsername(user.getUsername());
		return historyDetailsService.getTicketsHistoryByUser(person);
	}
	
	//Get tickets during a period of time
	@RequestMapping(value="/restful/PeroidTickets.html", method = RequestMethod.POST)
	public @ResponseBody List<Ticket> getTicketsDuringPeriodTime(@RequestParam("From") String from, 
																 @RequestParam("To") String to,
															 @RequestParam("Time") String time) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String[] DateAndTime = time.split("/");
		String[] HourTime = DateAndTime[1].split("-");
		Date firstDate;
		Date secondDate;
		if(!DateAndTime[0].equals("")) {
			if(DateAndTime[1].equals("Anytime")) {
				try {
					String timestamp = DateAndTime[0] + " 00:00:00.0";
					firstDate = dateFormat.parse(timestamp);
					timestamp = DateAndTime[0] + " 23:59:59.0";
					secondDate = dateFormat.parse(timestamp);
					return ticketDetailsService.getPeroidTimeOfTikcets(from, to, firstDate, secondDate);
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
					return ticketDetailsService.getPeroidTimeOfTikcets(from, to, firstDate, secondDate);			
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
					return ticketDetailsService.getPeroidTimeOfTikcets(from, to, firstDate, secondDate);			
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
		return ticketDetailsService.getAllTickets();
	}
	
	//Add new ticket
	@RequestMapping(value="/admin/NewTicket.html", method = RequestMethod.GET)
	public @ResponseBody List<RedisTicket> addNewTicket(@RequestParam("From") String from,
														@RequestParam("To") String to,
														@RequestParam("Time") String time,
														@RequestParam("Amount") int amount,
														@RequestParam("Price") double price,
														@RequestParam("Seat") String seatType){
		System.out.println(price + " " + amount);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try 
		{
			Date date = dateFormat.parse(time);
			
			RedisTicket ticket = new RedisTicket();
			ticket.setStart(from);
			ticket.setDestination(to);
			ticket.setDate(date);
			ticket.setPrice(Double.valueOf(price).toString());
			ticket.setAmount(amount);
			ticket.setAvailable(true);
			ticket.setActive("true");
			
			ticket.setSeats(RelationConverter.seatGenerator(seatType, amount));
			
			ticketService.adminAddTicket(ticket);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.ticketService.getAllTicket();
	}

	//****************************************
	//***************History******************
	//****************************************	
	//Get all history
	@RequestMapping(value="/auth/History.html", method = RequestMethod.GET)
	public @ResponseBody List<History> getAllHistory() {
		return historyDetailsService.getAllHistory();
	}
	
	@RequestMapping(value="/auth/UserHistory.html", method = RequestMethod.GET)
	public @ResponseBody List<Ticket> getUserHistory() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return historyDetailsService.getTicketsHistoryByUser(userDetailsService.getUserByUsername(user.getUsername()));
	}
	
	//****************************************
	//***************Cart*********************
	//****************************************		
	@RequestMapping(value="/auth/AddCart.html", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	public @ResponseBody List<RedisTicket> addTicketToUser(@RequestBody CartEntity[] data) {

	    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<RedisTicket> unCartList = new ArrayList<RedisTicket>();
		for(CartEntity entity : data) {
			try 
			{
				Date date = dateFormat.parse(entity.getTime());
				String from = entity.getFrom();
				String to = entity.getTo();
				
				RedisTicket ticket = new RedisTicket();
				ticket.setStart(from);
				ticket.setDestination(to);
				ticket.setDate(date);
				
				String ticketKey = RelationConverter.ticketKeyGenerator(ticket);
				
				RedisRequest request = new RedisRequest(); 
				
				request.setUserId(username);
				RedisRequest returnedRequest = ticketService.buyTicket(request, ticketKey);
				if(returnedRequest == null) {
					RedisTicket unpurchaseTicket = new RedisTicket();
					unpurchaseTicket.setStart(from);
					unpurchaseTicket.setDestination(to);
					unpurchaseTicket.setDate(date);
					unCartList.add(unpurchaseTicket);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return unCartList;
	}
	
	@RequestMapping(value="/auth/GetCartItemByUser.html", method = RequestMethod.GET)
	public @ResponseBody List<CartItem> getCartItemByUser() 
	{
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ticketService.getCartItem(user.getUsername());
	}
	
	@RequestMapping(value="/auth/RemoveCartItem.html", method = RequestMethod.POST)
	public @ResponseBody List<CartItem> removeCartItem(@RequestParam("CartItem") String cartItem) {
		
		ticketService.removeCartItem(cartItem);
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<CartItem> result = ticketService.getCartItem(user.getUsername());
		if(result == null)
			result = new ArrayList<CartItem>();
		return result;
	}
	
	@RequestMapping(value="/auth/CheckOut.html", method = RequestMethod.POST)
	public @ResponseBody String checkOut(@RequestBody CartItem[] data) {
		
		return null;
	}
	
	//****************************************
	//***************Users*******************
	//****************************************
	//Get a certain user by username
	@RequestMapping(value="/auth/GetUser.html", method = RequestMethod.POST)
	public @ResponseBody Person getCertainUserByUsername(@RequestParam("username") String username) {
		return userDetailsService.getUserByUsername(username);
	}
		
	//Register a user
	@RequestMapping(value="/restful/RegisterUser.html", method = RequestMethod.POST)
	public @ResponseBody String registerUser(@RequestParam("r_username") String username,
											 @RequestParam("r_password") String password,
											 @RequestParam("r_email") String email,
											 @RequestParam("r_lastname") String lastname,
											 @RequestParam("r_firstname") String firstname) {
		String result = userDetailsService.registerNewUser(username, password, email, lastname, firstname);
		result = "[{\"result\":\"" + result + "\"}]";
		return result;
		
	}
	
	//Activate a user
	@RequestMapping(value="/restful/UserActivate.html", method = RequestMethod.GET)
	public ModelAndView activateUser(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("content/activation");
		mav.addObject("Result", userDetailsService.activateUser(id));
		return mav;
	}
	
	//Update the user data
	@RequestMapping(value="/auth/UpdateUser.html", method = RequestMethod.POST)
	public @ResponseBody Person updateUser(@RequestParam("firstname") String firstname,
								   @RequestParam("lastname") String lastname,
								   @RequestParam("email") String email,
								   @RequestParam("password") String password) {
		//get current login user
	    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetailsService.updateUserProfile(user.getUsername(), password, email, lastname, firstname);
		
	}
	
}