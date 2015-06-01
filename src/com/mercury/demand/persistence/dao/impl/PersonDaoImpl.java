package com.mercury.demand.persistence.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mercury.demand.mail.MailAppBean;
import com.mercury.demand.persistence.dao.PersonDao;
import com.mercury.demand.persistence.model.Person;
import com.mercury.demand.security.MagicCrypt;

@Repository
public class PersonDaoImpl implements PersonDao {
	@Autowired 
	@Qualifier("tableSessionFactory")
    private SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("mailApp")
	private MailAppBean mailApp;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
	@Override
	public Person getPersonByUsername(String username) {
		// TODO Auto-generated method stub
		Criteria ct = this.getCurrentSession().createCriteria(Person.class);		
		return (Person)ct.add(Restrictions.eq("username", username)).uniqueResult();
	}

	@Override
	public String registerNewUser(String username, String password,
			String email, String lastname, String firstname) {
		Person existUser = this.getPersonByUsername(username);
		if(existUser == null) {
			Person newUser = new Person();
			newUser.setUsername(username);
			newUser.setPassword(MagicCrypt.getInstance().MD5(password));
			newUser.setEmail(email);
			newUser.setFirstname(firstname);
			newUser.setLastname(lastname);
			newUser.setAuthority("ROLE_USER");
			newUser.setEnabled(false);
			sessionFactory.getCurrentSession().save(newUser);
			
			String encryptStr = MagicCrypt.getInstance().encrypt(username);
			encryptStr = MagicCrypt.getInstance().httpGetStringConvert(encryptStr);
			mailApp.sendMail(firstname + " " + lastname, "http://localhost:8080/Demand1/restful/UserActivate.html?id="+encryptStr, email);
			
			return "yes";
		}
		return "no";
	}

	@Override
	public String activateUser(String encryptId) {
		// TODO Auto-generated method stub
		String username = MagicCrypt.getInstance().decrypt(encryptId);
		Person activateUser = this.getPersonByUsername(username);
		
		
		if(activateUser == null)
			return "This account is invalid. Please register an account.";
		else if(activateUser.isEnabled())
			return "This account had been activated";
		
		activateUser.setEnabled(true);
		sessionFactory.getCurrentSession().update(activateUser);
		return "This account is invalid. Please register an account.";
		//return "Your account is activated";
	}

	@Override
	public Person updateUserProfile(String username, String password,
			String email, String lastname, String firstname) {
		// TODO Auto-generated method stub
		Person updateUser = this.getPersonByUsername(username);
		updateUser.setUsername(username);
		updateUser.setPassword(password);
		updateUser.setEmail(email);
		updateUser.setFirstname(firstname);
		updateUser.setLastname(lastname);
		sessionFactory.getCurrentSession().update(updateUser);
		return updateUser;
	}
	


}
