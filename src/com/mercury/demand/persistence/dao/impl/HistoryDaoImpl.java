package com.mercury.demand.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mercury.demand.persistence.dao.HistoryDao;
import com.mercury.demand.persistence.model.Transaction;
import com.redis.entity.RedisTransaction;

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
		String hql = "From Transaction where user_id = ? order by tran_time desc";
		return template.find(hql,username);
	}

	@Override
	public void addAHistory(List<RedisTransaction> trans) {
		// TODO Auto-generated method stub
		Session session = template.getSessionFactory().openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		   
		for ( int i = 0 ; i < trans.size() ; i++ ) {
			RedisTransaction record = trans.get(i);
		    Transaction oracleRecord = new Transaction();
		    oracleRecord.setUser_id(record.getUserId());
		    oracleRecord.setTicket_id(record.getTicketId());
		    oracleRecord.setSeat_no(record.getSeatNo());
		    oracleRecord.setTran_time(record.getDate());
		    session.save(oracleRecord);
		    if ( i % 20 == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		        session.flush();
		        session.clear();
		    }
		}
		   
		tx.commit();
		session.close();
	}
}
