package com.mercury.demand.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mercury.demand.persistence.dao.HistoryDao;
import com.mercury.demand.persistence.model.Transaction;

public class HistoryDaoImpl implements HistoryDao{

	private HibernateTemplate template;
	
	@Autowired
	@Qualifier("tableSessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.template = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getAllHistory() {
		// TODO Auto-generated method stub
		String hql = "From Transaction";
		return template.find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getUserTransaction(String username) {
		// TODO Auto-generated method stub
		String hql = "From Transaction where user_id = ?";
		return template.find(hql,username);
	}

	@Override
	public void addAHistory(String user_id, String ticket_id, String seat_no, Date date) {
		// TODO Auto-generated method stub
		Transaction transaction = new Transaction();
		transaction.setUser_id(user_id);
		transaction.setTicket_id(ticket_id);
		transaction.setSeat_no(seat_no);
		transaction.setTran_time(date);
		template.save(transaction);
		
		List<Transaction> list = this.getAllHistory();
		for(Transaction tran : list) {
			System.out.println(tran.getTicket_id());
		}
		System.out.println("--------");
	}
}
