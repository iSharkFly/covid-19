package com.ossez.covid19.service.rets;

/**
 * Class to store a town,state,county association. This class is used as
 * part of a larger collection to map a town id to the three aforementioned
 * values.
 * 
 * @author YUCHENG
 *
 */
public class MaineLocation {
	private String townName = null;
	private String stateName = null;
	private String countyName = null;

	/**
	 * 
	 * 
	 * @param townName
	 * @param stateName
	 * @param countyName
	 */
	public MaineLocation(String townName, String stateName, String countyName) {
		this.townName = townName;
		this.stateName = stateName;
		this.countyName = countyName;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public String getTownName() {
		return townName;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * 
	 * @return
	 */
	public String getCountyName() {
		return countyName;
	}
}
