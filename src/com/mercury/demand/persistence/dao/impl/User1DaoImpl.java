package com.mercury.demand.persistence.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.mercury.demand.persistence.dao.User1Dao;
import com.mercury.demand.persistence.model.User1;

public class User1DaoImpl implements User1Dao{
	
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public HibernateTemplate getHibernateTemplate(){
		return new HibernateTemplate(this.sessionFactory);
	}
	@SuppressWarnings("unchecked")
	@Override 
	public List<User1> getUser1(){
		String sql="from User1";
    	
    	return this.getHibernateTemplate().find(sql);
    }
}
