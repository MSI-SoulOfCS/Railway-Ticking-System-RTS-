package com.redis.entity;

import java.util.Date;

public class RedisRequest 
{
	public static final String REQUEST_LIST_NAME = "requestList";
	public static final String REQUEST_SPLITER = "%";
	
	private String userId;
	private String ticketKey;
	private Date date;
	private String seatNo;
	
	public RedisRequest(){}
	
	public RedisRequest(String userId, String ticketKey, Date date, String seatNo) {
		super();
		this.userId = userId;
		this.ticketKey = ticketKey;
		this.date = date;
		this.seatNo = seatNo;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTicketKey() {
		return ticketKey;
	}
	public void setTicketKey(String ticketKey) {
		this.ticketKey = ticketKey;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

}
