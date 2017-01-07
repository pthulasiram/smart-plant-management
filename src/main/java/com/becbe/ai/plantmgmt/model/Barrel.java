package com.archius.cosmos.model;
/**
 * @author Thulasiram
 *
 */
public class Barrel {

	public EngMetatData __metadata;

	public String Plant;
	public int Days;
	public int Barrels;

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

	/**
	 * @return the days
	 */
	public int getDays() {
		return Days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(int days) {
		Days = days;
	}

	/**
	 * @return the barrels
	 */
	public int getBarrels() {
		return Barrels;
	}

	/**
	 * @param barrels
	 *            the barrels to set
	 */
	public void setBarrels(int barrels) {
		Barrels = barrels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Barrel [__metadata=" + __metadata + ", Plant=" + Plant + ", Days=" + Days + ", Barrels=" + Barrels
				+ "]";
	}

}
