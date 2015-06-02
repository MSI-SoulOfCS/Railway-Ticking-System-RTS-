package com.redis.entity;

import java.util.Date;

public class RedisTicket 
{
	/*
	 * ticket pool name
	 */
	public static final String TICKET_POOL_NAME = "ticketPool";
	public static final String SEAT_SUFFIX = "-seats";
	
	/*
	 * user the ticket splitter to generate ticket hash key
	 */
	public static final String TICKET_SPLITTER = "#";
	public static final String ACTIVE = "active";
	public static final String PRICE = "price";
	public static final String AMOUNT = "amount";
	
	private String start;
	private String destination;
	private Date date;
	private String active;
	private String price;
	private String[] seats;
	private boolean available;
	private int amount;
	private long avaiNumber;
	
	public RedisTicket(){}
	
	public RedisTicket(String start, String destination, Date date, String active,
			String price, String[] seats) {
		super();
		this.start = start;
		this.destination = destination;
		this.date = date;
		this.active = active;
		this.price = price;
		this.seats = seats;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String[] getSeats() {
		return seats;
	}
	public void setSeats(String[] seats) {
		this.seats = seats;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public long getAvaiNumber() {
		return avaiNumber;
	}

	public void setAvaiNumber(long avaiNumber) {
		this.avaiNumber = avaiNumber;
	}

}
