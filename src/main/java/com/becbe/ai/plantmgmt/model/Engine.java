package com.archius.cosmos.model;

import java.util.Map;
/**
 * @author Thulasiram
 *
 */
public class Engine {

	/*<d:Pressure m:type="Edm.String">-0.0012</d:Pressure>
	<d:Speed_RPM m:type="Edm.String">100</d:Speed_RPM>
	<d:Flowrate m:type="Edm.String">-0.0004</d:Flowrate>
	<d:cycle_no m:type="Edm.String">134</d:cycle_no>
	<d:engine_no m:type="Edm.String">70</d:engine_no>
	<d:test_rf m:type="Edm.Decimal">3.959</d:test_rf>
	<d:engine_id m:type="Edm.String">Circulation pump 01</d:engine_id>
	<d:Equipment_No m:type="Edm.String">10006757</d:Equipment_No>
	<d:Source m:type="Edm.String">Sim</d:Source>
	<d:Cost m:type="Edm.Decimal">4081</d:Cost>*/
	public EngMetatData __metadata;
	public String Pressure; // =-0.0011000000000000001,
	public String Speed_RPM;// = 200;
	public String Flowrate;// = 0;
	public String cycle_no;// = 128;
	public String engine_no;// = 91;
	public double test_rf;// =3.624;
	public String engine_id; // =Electric pump motor,
	public String Equipment_No;// =M-1000-N062,
	public String Cost;// =4188
	public String Source;

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
	 * @return the pressure
	 */
	public String getPressure() {
		return Pressure;
	}

	/**
	 * @param pressure
	 *            the pressure to set
	 */
	public void setPressure(String pressure) {
		Pressure = pressure;
	}

	/**
	 * @return the speed_RPM
	 */
	public String getSpeed_RPM() {
		return Speed_RPM;
	}

	/**
	 * @param speed_RPM
	 *            the speed_RPM to set
	 */
	public void setSpeed_RPM(String speed_RPM) {
		Speed_RPM = speed_RPM;
	}

	

	/**
	 * @return the cycle_no
	 */
	public String getCycle_no() {
		return cycle_no;
	}

	/**
	 * @param cycle_no
	 *            the cycle_no to set
	 */
	public void setCycle_no(String cycle_no) {
		this.cycle_no = cycle_no;
	}

	/**
	 * @return the engine_no
	 */
	public String getEngine_no() {
		return engine_no;
	}

	/**
	 * @param engine_no
	 *            the engine_no to set
	 */
	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}

	
	/**
	 * @return the test_rf
	 */
	public double getTest_rf() {
		return test_rf;
	}

	/**
	 * @param test_rf the test_rf to set
	 */
	public void setTest_rf(double test_rf) {
		this.test_rf = test_rf;
	}

	/**
	 * @return the engine_id
	 */
	public String getEngine_id() {
		return engine_id;
	}

	/**
	 * @param engine_id
	 *            the engine_id to set
	 */
	public void setEngine_id(String engine_id) {
		this.engine_id = engine_id;
	}

	/**
	 * @return the equipment_No
	 */
	public String getEquipment_No() {
		return Equipment_No;
	}

	/**
	 * @param equipment_No
	 *            the equipment_No to set
	 */
	public void setEquipment_No(String equipment_No) {
		Equipment_No = equipment_No;
	}

	/**
	 * @return the cost
	 */
	public String getCost() {
		return Cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(String cost) {
		Cost = cost;
	}

	public String getFlowrate() {
		return Flowrate;
	}

	public void setFlowrate(String flowrate) {
		Flowrate = flowrate;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	@Override
	public String toString() {
		return "Engine [Pressure=" + Pressure + ", Speed_RPM=" + Speed_RPM + ", Flowrate=" + Flowrate + ", cycle_no="
				+ cycle_no + ", engine_no=" + engine_no + ", test_rf=" + test_rf + ", engine_id=" + engine_id
				+ ", Equipment_No=" + Equipment_No + ", Cost=" + Cost + ", Source=" + Source + "]";
	}
	
	

}
