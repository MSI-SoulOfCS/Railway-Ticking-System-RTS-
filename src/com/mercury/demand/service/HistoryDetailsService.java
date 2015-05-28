package com.mercury.demand.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.common.info.HistoryInfo;
import com.mercury.demand.persistence.dao.HistoryDao;

public class HistoryDetailsService {
	@Autowired
	private HistoryDao hd;
	
	private static HistoryDetailsService instance;
	
	private HistoryDetailsService() {}
	
	public static HistoryDetailsService getInstance() {
		if(instance == null) {
			synchronized(HistoryDetailsService.class) {
				if(instance == null) instance = new HistoryDetailsService();
			}
		}
		return instance;
	}
	
	public HistoryDao getHd() {
		return hd;
	}

	public void setHd(HistoryDao hd) {
		this.hd = hd;
	}
	
	public HistoryInfo getAllHistory(){
		HistoryInfo rs = new HistoryInfo();
		rs.setHistory(hd.getAllHistory());
		return rs;
	}
}
