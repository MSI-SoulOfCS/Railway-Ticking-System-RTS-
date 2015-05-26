package com.mercury.common.info;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mercury.demand.persistence.model.User1;

@XmlRootElement
public class ResultInfo {
	private List<User1> users;	
	
	@XmlElement(name="user")
	public List<User1> getUsers() {
			return users;
	}
	public void setUsers(List<User1> users) {
			this.users = users;
	}
}
