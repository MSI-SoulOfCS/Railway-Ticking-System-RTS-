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
import com.mercury.demand.persistence.model.Transaction;
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
	
	//Get tickets during a period of time
	@RequestMapping(value="/restful/PeroidTickets.html", method = RequestMethod.POST)
	public @ResponseBody List<RedisTicket> getTicketsDuringPeriodTime(@RequestParam("ticketItem") String ticketItem) {
		ticketItem=ticketItem.replace('_', ' ');
		String[] str=ticketItem.split("\\+");
		//This is from and to!!!
		String from = str[0];
		String to = str[1];
		//This is date!!!
		String date=str[2];
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date ticketDate;
		try {
			ticketDate = dateFormat.parse(date);
			System.out.println(ticketDate);
			List<RedisTicket> result = this.ticketService.searchTicket(from, to, ticketDate, ticketDate);
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<RedisTicket>();
	}
	
	//Add new ticket
	@RequestMapping(value="/admin/NewTicket.html", method = RequestMethod.GET)
	public @ResponseBody List<RedisTicket> addNewTicket(@RequestParam("From") String from,
														@RequestParam("To") String to,
														@RequestParam("Time") String time,
														@RequestParam("Amount") int amount,
														@RequestParam("Price") double price,
														@RequestParam("Seat") String seatType){
		
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
	public @ResponseBody List<Transaction> getAllHistory() {
		return historyDetailsService.getAllHistory();
	}
	
	@RequestMapping(value="/auth/UserHistory.html", method = RequestMethod.GET)
	public @ResponseBody List<Transaction> getUserHistory() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return historyDetailsService.getTransactionByUsername(user.getUsername());
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
	
	@RequestMapping(value="/auth/FindTicket.html",method=RequestMethod.POST)
	public @ResponseBody RedisTicket getTicket(@RequestParam("ticketItem") String ticketItem){
		ticketItem=ticketItem.replace('_', ' ');
		System.out.println(ticketItem);
		String[] str=ticketItem.split("\\+");
		
		for(int i=0;i<str.length;i++)System.out.println(str[i]);
		
		return new RedisTicket();
		
	}
	
	@RequestMapping(value="/auth/CheckOut.html", method = RequestMethod.POST)
	public @ResponseBody String checkOut(@RequestBody CartItem[] data) {
	    
		for(CartItem item : data) 
		{
			ticketService.transactionCompleteWithPool(item.getItemId());
		}
		
		return "[{\"result\":\"" + "yes" + "\"}]";
	}
	
	//****************************************
	//***************Users*******************
	//****************************************
	//Get a certain user by username
	@RequestMapping(value="/auth/GetUser.html", method = RequestMethod.POST)
	public @ResponseBody Person getCertainUserByUsername(@RequestParam("username") String username) {
		return userDetailsService.getUserByUsername(username);
	}
	
	//Get current user
	@RequestMapping(value="/auth/GetCurrentUser.html", method = RequestMethod.GET)
	public @ResponseBody Person getCurrentUser() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetailsService.getUserByUsername(user.getUsername());
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
								   		   @RequestParam("email") String email) {
		//get current login user
	    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetailsService.updateUserProfile(user.getUsername(), email, lastname, firstname);
		
	}
	
	//Reset Password for the user
	@RequestMapping(value="/restful/ResetPwd.html", method = RequestMethod.POST)
	public @ResponseBody String resetUserPwd(@RequestParam("email") String email) {
		return "[{\"result\":\"" + userDetailsService.resetUserPwd(email) + "\"}]";
	}
	
	//Update the user Password
	@RequestMapping(value="/auth/UpdatePassword.html", method = RequestMethod.POST)
	public @ResponseBody String updatePassword(@RequestParam("password") String password) {
	    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "[{\"result\":\"" + userDetailsService.updateUserPassword(user.getUsername(), password) + "\"}]";
	}
	
	//****************************************
	//***TEST MULTI-THREAD BUY TICKET*********
	//****************************************
	@RequestMapping(value="/restful/TestTicket.html", method = RequestMethod.GET)
	public void testTicket() 
	{
		System.out.println("test");
/*		RedisRequest request = new RedisRequest();
		
		request.setUserId(userid);
		
		//System.out.println(Thread.currentThread().getName());
		RedisRequest r = 
				ticketService.buyTicket(request, ticketid);
		
		if(r != null)
		{
			//Thread.sleep(1000*random.nextInt(10));
			ticketService.transactionCompleteWithPool(RelationConverter.requestKeyGenerator(r));
			System.out.println(r.getUserId() + "---" + 
					r.getTicketKey() + "---" + 
					r.getSeatNo() + "---" + 
					r.getDate());
		}*/
		return;
	}
	
}