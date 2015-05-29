package com.mercury.demand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.demand.persistence.dao.HistoryDao;
import com.mercury.demand.persistence.model.History;

@Service
public class HistoryDetailsService {
	@Autowired
	private HistoryDao hd;
		
	public HistoryDao getHd() {
		return hd;
	}

	public void setHd(HistoryDao hd) {
		this.hd = hd;
	}
	
	public List<History> getAllHistory(){
		return hd.getAllHistory();
	}
}
