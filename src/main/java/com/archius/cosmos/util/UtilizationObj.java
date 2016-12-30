package com.archius.cosmos.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.archius.cosmos.model.EngMetatData;
/**
 * @author Thulasiram
 *
 */
public class UtilizationObj  {
	EngMetatData __metadata;
	String Plant;
	String Months;
	int Utilization;

   
    
	public UtilizationObj(EngMetatData __metadata, String plant, String months, int utilization) {
		super();
		this.__metadata = __metadata;
		Plant = plant;
		Months = months;
		Utilization = utilization;
	}
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
	 * @return the utilization
	 */
	public int getUtilization() {
		return Utilization;
	}
	/**
	 * @param utilization the utilization to set
	 */
	public void setUtilization(int utilization) {
		Utilization = utilization;
	}
	/*Comparator for sorting the list by Student Name*/
    public static Comparator<UtilizationObj> utilizationMonthsComparator = new Comparator<UtilizationObj>() {

	public int compare(UtilizationObj s1, UtilizationObj s2) {
	   String StudentName1 = s1.getMonths().toUpperCase();
	   String StudentName2 = s2.getMonths().toUpperCase();

	   //ascending order
	   return StudentName1.compareTo(StudentName2);

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
    }};

   
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Student [__metadata=" + __metadata + ", Plant=" + Plant + ", Months=" + Months + ", Utilization="
				+ Utilization + "]";
	}
	
}

