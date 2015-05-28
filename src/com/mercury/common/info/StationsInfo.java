package com.mercury.common.info;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mercury.demand.persistence.model.Station;

@XmlRootElement
public class StationsInfo {
	private List<Station> stations;	
	
	@XmlElement(name="stations")
	public List<Station> getStations() {
			return stations;
	}
	public void setStations(List<Station> stations) {
			this.stations = stations;
	}
}
