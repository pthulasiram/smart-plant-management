package com.archius.cosmos.model;

import java.util.Comparator;
/**
 * @author Thulasiram
 *
 */
public class DownTime {

	public EngMetatData __metadata;
	public String Plant;
	public String Months;
	public int Downtime;

	/**
	 * @return the __metadata
	 */
	public EngMetatData get__metadata() {
		return __metadata;
	}

	/**
	 * @param __metadata
	 *            the __metadata to set
	 */
	public void set__metadata(EngMetatData __metadata) {
		this.__metadata = __metadata;
	}

	/**
	 * @return the plant
	 */
	public String getPlant() {
		return Plant;
	}

	/**
	 * @param plant
	 *            the plant to set
	 */
	public void setPlant(String plant) {
		Plant = plant;
	}

	

	public String getMonths() {
		return Months;
	}

	public void setMonths(String months) {
		Months = months;
	}

	/**
	 * @return the downtime
	 */
	public int getDowntime() {
		return Downtime;
	}

	/**
	 * @param downtime the downtime to set
	 */
	public void setDowntime(int downtime) {
		Downtime = downtime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DownTime [__metadata=" + __metadata + ", Plant=" + Plant + ", Months=" + Months + ", Downtime="
				+ Downtime + "]";
	}

}
