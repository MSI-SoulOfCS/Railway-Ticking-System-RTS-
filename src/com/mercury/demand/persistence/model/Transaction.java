package com.mercury.demand.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="transaction")
public class Transaction implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String user_id;
	private String ticket_id;
	private String seat_no;
	private Date tran_time;
	
	
    @Id
	@SequenceGenerator(name="TID_SEQ", sequenceName="HISTORY_ID_SEQ", initialValue=1, allocationSize=1)  // default allocationSize=50
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TID_SEQ")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="user_id", nullable = false)
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Column(name="ticket_id", nullable = false)
	public String getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}
	@Column(name="seat_no", nullable = false)
	public String getSeat_no() {
		return seat_no;
	}
	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}
	@Column(name="tran_time", nullable = false)
	public Date getTran_time() {
		return tran_time;
	}
	public void setTran_time(Date tran_time) {
		this.tran_time = tran_time;
	}
	
	
}
