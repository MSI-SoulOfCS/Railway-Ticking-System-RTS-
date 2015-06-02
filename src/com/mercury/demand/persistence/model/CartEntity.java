package com.mercury.demand.persistence.model;

import java.io.Serializable;

public class CartEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	private String time;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
