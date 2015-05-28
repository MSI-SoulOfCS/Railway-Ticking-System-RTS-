package com.mercury.demand.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mercury.demand.persistence.dao.TicketDao;
import com.mercury.demand.persistence.model.Ticket;

public class TicketDaoImpl implements TicketDao {

	private HibernateTemplate template;
	
	@Autowired
	@Qualifier("tableSessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.template = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> getAllTickets() {
		// TODO Auto-generated method stub
		String hql = "From Ticket";
		return template.find(hql);
	}

	@Override
	public List<Ticket> getTicketsWithCondition(String fromLocation, String toLocation, Date date) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
