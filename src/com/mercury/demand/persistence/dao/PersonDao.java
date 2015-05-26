package com.mercury.demand.persistence.dao;

import com.mercury.demand.persistence.model.Person;

public interface PersonDao {
	public Person getPersonById(int personId);
	public Person getPersonByUsername(String username);
}
