package com.mercury.common.info;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mercury.demand.persistence.model.History;

@XmlRootElement
public class HistoryInfo {
	private List<History> history;	
	
	@XmlElement(name="history")
	public List<History> getHistory() {
			return history;
	}
	public void setHistory(List<History> history) {
			this.history = history;
	}
}
