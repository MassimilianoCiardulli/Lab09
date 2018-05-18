package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

public class CountryIdMap {
	
	private Map<Integer, Country> map ;
	
	public CountryIdMap() {
		map = new HashMap<>();
	}
	
	public Country get(int ccode) {
		return map.get(ccode);
	}
	
	public Country get(Country country) {
		Country old = map.get(country.getCcode());
		if(old == null) {
			map.put(country.getCcode(), country);
			return country ;
		} else {
			return old ;
		}
	}
	
	public Country get(String country) {
		for(Country c:map.values()) {
			if(c.getStateName().equals(country))
				return c ;
		}
		return null ;
	}
	
	public void put(int ccode, Country object) {
		map.put(ccode, object);
	}
}
