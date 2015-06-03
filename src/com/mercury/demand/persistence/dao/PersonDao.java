package com.mercury.demand.persistence.dao;

import com.mercury.demand.persistence.model.Person;

public interface PersonDao {
	public Person getPersonByUsername(String username);
	public String registerNewUser(String username, String password, String email, String lastname, String firstname);
	public String activateUser(String encryptId);
	public Person updateUserProfile(String username, String email, String lastname, String firstname);
	public String resetUserPwd(String email);
	public String updateUserPassword(String username, String password);
}
