package com.mercury.demand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.demand.persistence.dao.StationDao;
import com.mercury.demand.persistence.model.Station;

@Service
public class StationDetailsService {
	@Autowired
	private StationDao hd;
	
	public StationDao getHd() {
		return hd;
	}

	public void setHd(StationDao hd) {
		this.hd = hd;
	}
	
	public List<Station> getAllStations(){
		return hd.getAllStations();
	}
}
