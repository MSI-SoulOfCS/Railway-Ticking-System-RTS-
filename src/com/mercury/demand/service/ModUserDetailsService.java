package com.mercury.demand.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.demand.persistence.dao.PersonDao;
import com.mercury.demand.persistence.model.Person;

@Service
@Transactional(readOnly = false)
public class ModUserDetailsService {
	@Autowired
	private PersonDao pd;
		
	public PersonDao getPd() {
		return pd;
	}
	public void setPd(PersonDao pd) {
		this.pd = pd;
	}
	
	public Person getUserByUsername(String username) {
		return pd.getPersonByUsername(username);
	}
}

