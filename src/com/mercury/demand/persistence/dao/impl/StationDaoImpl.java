package com.mercury.demand.persistence.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mercury.demand.persistence.dao.StationDao;
import com.mercury.demand.persistence.model.Station;

public class StationDaoImpl implements StationDao {

	private HibernateTemplate template;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.template = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Station> getAllStations() {
		// TODO Auto-generated method stub
		String hql = "From Station";
		return template.find(hql);
	}
	
}
