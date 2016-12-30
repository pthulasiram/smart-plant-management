package com.archius.cosmos.model;

import java.util.Comparator;
/**
 * @author Thulasiram
 *
 */
public class UtilizationVo  {
	public EngMetatData __metadata;
	public String Months;
	public String Plant;
	public String Utilization;
	/**
	 * @return the __metadata
	 */
	public EngMetatData get__metadata() {
		return __metadata;
	}
	/**
	 * @param __metadata the __metadata to set
	 */
	public void set__metadata(EngMetatData __metadata) {
		this.__metadata = __metadata;
	}
	/**
	 * @return the months
	 */
	public String getMonths() {
		return Months;
	}
	/**
	 * @param months the months to set
	 */
	public void setMonths(String months) {
		Months = months;
	}
	/**
	 * @return the plant
	 */
	public String getPlant() {
		return Plant;
	}
	/**
	 * @param plant the plant to set
	 */
	public void setPlant(String plant) {
		Plant = plant;
	}
	/**
	 * @return the utilization
	 */
	public String getUtilization() {
		return Utilization;
	}
	/**
	 * @param utilization the utilization to set
	 */
	public void setUtilization(String utilization) {
		Utilization = utilization;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UtilizationVo [__metadata=" + __metadata + ", Months=" + Months + ", Plant=" + Plant + ", Utilization="
				+ Utilization + "]";
	}
	
	public static Comparator<UtilizationVo> utilizationMonthsComparator = new Comparator<UtilizationVo>() {

		@Override
		public int compare(UtilizationVo o1, UtilizationVo o2) {
			
			return o1.getMonths().toUpperCase().compareTo(o2.getMonths().toUpperCase());
		}};
}

