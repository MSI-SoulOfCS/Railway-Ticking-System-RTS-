package com.mercury.result;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mercury.demand.persistence.dao.User1Dao;
import com.mercury.demand.persistence.dao.impl.User1DaoImpl;
import com.mercury.demand.persistence.model.User1;

public class Result {
	//ApplicationContext actx=new ClassPathXmlApplicationContext("config.xml");
	private static Result instance;
	public static Result getInstance(){
		if(instance==null){
			synchronized(Result.class){
				if(instance==null)instance=new Result();
			}
		}
		return instance;
	}
	@Autowired
	User1Dao hd;
	
	public User1Dao getHd() {
		return hd;
	}

	public void setHd(User1Dao hd) {
		this.hd = hd;
	}

	public List<User1> getResult(){
		return Result.getInstance().hd.getUser1();
	}
}
