package com.archius.cosmos.service;
/**
 * @author Thulasiram
 *
 */
public interface HanaOdataService {

	public String fetchProductSales();

	public String fetchBarrelData(String barrel);

	public String fetchHeadCountData(String headCount);

	public String fetchUtilizationData(String utilization);

	public String fetchUtilizationBarChartData();

	public String fetchEngineOdata(String engineOdata);

	public String fetchDownTimeData(String utilization);
	
	public String fetchEngineClassificationOdata(String engineOdata);
	
	
	public String fetchKPIData();

}
