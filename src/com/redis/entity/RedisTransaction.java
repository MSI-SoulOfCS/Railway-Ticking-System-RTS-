package com.redis.entity;

import java.util.Date;

public class RedisTransaction 
{
	public static final String TRANSACTION_POOL_NAME = "transactionPool";
	public static final String TRANSACTION_INFO_SPLITTER = "&";
	
	private String userId;
	private String ticketId;
	private String seatNo; 
	private Date date;
	
	public RedisTransaction(){}
	
	public RedisTransaction(String userId, String ticketId, String seatNo, Date date) {
		super();
		this.userId = userId;
		this.ticketId = ticketId;
		this.seatNo = seatNo;
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
