package com.mercury.demand.persistence.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mercury.demand.persistence.dao.TicketDao;
import com.mercury.demand.persistence.model.History;
import com.mercury.demand.persistence.model.Person;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> getTicketsInPeroidOfTime(String fromLocation,
			String toLocation, Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		String[] fromLocations = fromLocation.split(",");
		String[] toLocations = toLocation.split(",");
		if(fromLocations.length == 3 && toLocations.length == 3) {
			String hql = "From Ticket where from_loc.station = ? and to_loc.station = ? and activate = ? and date between ? and ?"; 
			return template.find(hql, new Object[] {fromLocations[2], toLocations[2], true, beginDate, endDate});
		}
		else {
			return this.getAllTickets();
		}
	}
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<Ticket> getTicketsByUser(Person user){
		String hql="From History where person=?";
		List<History> historyList=template.find(hql,user);
		List<Ticket> ticketList=new ArrayList<Ticket>();
		for(int i=0;i<historyList.size();i++){
			ticketList.add(historyList.get(i).getTicket());
		}
		return ticketList;
	}
}
