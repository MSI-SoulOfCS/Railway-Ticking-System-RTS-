package com.mercury.demand.persistence.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mercury.demand.persistence.dao.PersonDao;
import com.mercury.demand.persistence.model.Person;

@Repository
public class PersonDaoImpl implements PersonDao {
	@Autowired 
	@Qualifier("loginSessionFactory")
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
	@Override
	public Person getPersonById(int personId) {
		// TODO Auto-generated method stub
		return (Person)this.getCurrentSession().get(Person.class, personId);
	}

	@Override
	public Person getPersonByUsername(String username) {
		// TODO Auto-generated method stub
		Criteria ct = this.getCurrentSession().createCriteria(Person.class);
		return (Person)ct.add(Restrictions.eq("username", username)).uniqueResult();
	}

}
