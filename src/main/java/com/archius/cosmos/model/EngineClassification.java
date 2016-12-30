package com.archius.cosmos.model;

import java.util.Map;
/**
 * @author Thulasiram
 *
 */
public class EngineClassification {
	/*
	 * "engine_no": "100", "cycle_no": "197", "Pressure": "-0.0016", "Flowrate":
	 * "-0.0005", "Equipment_No": "M-3000-N059", "engine_id":
	 * "Motor for electric pump,250kw", "Pred": "0.991", "test_rf": "3.534",
	 * "Cost": "3969", "Pred_Class": "99.1"
	 */
	public EngMetatData __metadata;
	public String engine_no;// = 91;
	public String cycle_no;// = 128;
	public String Pressure; // =-0.0011000000000000001,
	public String Flowrate;// = 0;
	public String Equipment_No;// =M-1000-N062,
	public String engine_id; // =Electric pump motor,
	public double Pred;// = 200;
	public double test_rf;// =3.624;
	public String Cost;// =4188
	public double Pred_Class;

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
	 * @return the flowrate
	 */
	public String getFlowrate() {
		return Flowrate;
	}

	/**
	 * @param flowrate
	 *            the flowrate to set
	 */
	public void setFlowrate(String flowrate) {
		Flowrate = flowrate;
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
	 * @return the pred
	 */
	public double getPred() {
		return Pred;
	}

	/**
	 * @param pred
	 *            the pred to set
	 */
	public void setPred(double pred) {
		Pred = pred;
	}

	/**
	 * @return the test_rf
	 */
	public double getTest_rf() {
		return test_rf;
	}

	/**
	 * @param test_rf
	 *            the test_rf to set
	 */
	public void setTest_rf(double test_rf) {
		this.test_rf = test_rf;
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

	/**
	 * @return the pred_Class
	 */
	public double getPred_Class() {
		return Pred_Class;
	}

	/**
	 * @param pred_Class
	 *            the pred_Class to set
	 */
	public void setPred_Class(double pred_Class) {
		Pred_Class = pred_Class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EngineClassification [__metadata=" + __metadata + ", engine_no=" + engine_no + ", cycle_no=" + cycle_no
				+ ", Pressure=" + Pressure + ", Flowrate=" + Flowrate + ", Equipment_No=" + Equipment_No
				+ ", engine_id=" + engine_id + ", Pred=" + Pred + ", test_rf=" + test_rf + ", Cost=" + Cost
				+ ", Pred_Class=" + Pred_Class + "]";
	}

}
