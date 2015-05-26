package com.mercury.demand.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.demand.persistence.dao.PersonDao;
import com.mercury.demand.persistence.model.Person;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService  implements UserDetailsService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private PersonDao pd;
		
	public PersonDao getPd() {
		return pd;
	}
	public void setPd(PersonDao pd) {
		this.pd = pd;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = null;  
		try {
			Person person = pd.getPersonByUsername(username);
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(person.getAuthority()));
			user = new User(
					person.getUsername(),
					person.getPassword(),
					true,
					true,
					true,
					true,
					authorities 
			);
		} catch (Exception e) {
			logger.error("Error in retrieving user" + e.getMessage());
			throw new UsernameNotFoundException("Error in retrieving user");
		}
		return user;
	}		  
}
