package com.mercury.result;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.common.info.ResultInfo;
import com.mercury.demand.persistence.dao.User1Dao;

public class Result {
	@Autowired
	private User1Dao hd;
	
	private static Result instance;
	
	private Result() {}
	
	public static Result getInstance() {
		if(instance == null) {
			synchronized(Result.class) {
				if(instance == null) instance = new Result();
			}
		}
		return instance;
	}
	
	public User1Dao getHd() {
		return hd;
	}

	public void setHd(User1Dao hd) {
		this.hd = hd;
	}
	
	public ResultInfo getResult(){
		ResultInfo rs = new ResultInfo();
		rs.setUsers(hd.getUser1());
		return rs;
	}
}
