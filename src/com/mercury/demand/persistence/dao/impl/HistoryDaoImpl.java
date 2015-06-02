package com.mercury.demand.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mercury.demand.persistence.dao.HistoryDao;
import com.mercury.demand.persistence.model.History;
import com.mercury.demand.persistence.model.Person;
import com.mercury.demand.persistence.model.Ticket;

public class HistoryDaoImpl implements HistoryDao{

	private HibernateTemplate template;
	
	@Autowired
	@Qualifier("tableSessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.template = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<History> getAllHistory() {
		// TODO Auto-generated method stub
		String hql = "From History";
		return template.find(hql);
	}
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<Ticket> getTicketsHistoryByUser(Person user){
		String hql="From History where person=?";
		List<History> historyList=template.find(hql,user);
		List<Ticket> ticketList=new ArrayList<Ticket>();
		for(int i=0;i<historyList.size();i++){
			ticketList.add(historyList.get(i).getTicket());
		}
		return ticketList;
	}
}
