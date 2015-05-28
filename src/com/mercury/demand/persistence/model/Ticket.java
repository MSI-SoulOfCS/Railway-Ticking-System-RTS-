package com.mercury.demand.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ticket")
public class Ticket implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private double price;
	private Date date;
	private Station from_loc;
	private Station to_loc;
	private int amount;
	private int activate;
	
    @Id
    @Column(name="ID", nullable = false)	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    @Column(name="PRICE", nullable = false)	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
    @Column(name="DAY", nullable = false)	
	public Date getDate() {
		return date;
	}
    public void setDate(Date date) {
		this.date = date;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FROM_LOC")
	public Station getFrom_loc() {
		return from_loc;
	}
	public void setFrom_loc(Station from_loc) {
		this.from_loc = from_loc;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TO_LOC")
	public Station getTo_loc() {
		return to_loc;
	}
	public void setTo_loc(Station to_loc) {
		this.to_loc = to_loc;
	}
    @Column(name="AMOUNT", nullable = false)		
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
    @Column(name="ACTIVATE", nullable = false)	
	public int getActivate() {
		return activate;
	}
	public void setActivate(int activate) {
		this.activate = activate;
	}

}