package com.mercury.demand.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.common.info.StationsInfo;
import com.mercury.demand.persistence.dao.StationDao;

public class StationDetailsService {
	@Autowired
	private StationDao hd;
	
	private static StationDetailsService instance;
	
	private StationDetailsService() {}
	
	public static StationDetailsService getInstance() {
		if(instance == null) {
			synchronized(StationDetailsService.class) {
				if(instance == null) instance = new StationDetailsService();
			}
		}
		return instance;
	}
	
	public StationDao getHd() {
		return hd;
	}

	public void setHd(StationDao hd) {
		this.hd = hd;
	}
	
	public StationsInfo getAllStations(){
		StationsInfo rs = new StationsInfo();
		rs.setStations(hd.getAllStations());
		return rs;
	}
}
