/**
 * 
 */
package com.archius.cosmos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.archius.cosmos.model.Engine;
import com.archius.cosmos.model.UtilizationVo;

/**
 * @author Thulasiram Ponnam
 *
 */
public class ChartJSONUtil {

	private static final Logger logger = LoggerFactory.getLogger(ChartJSONUtil.class);

	private static ChartJSONUtil instance;

	private ChartJSONUtil() {

	}

	public static ChartJSONUtil getInstance() {
		if (instance == null) {
			instance = new ChartJSONUtil();
		}
		return instance;
	}

	public String convertJsonToObject(List<Engine> engineDataList) {
		JSONArray chartJSON = new JSONArray();
			//Collections.sort(engineDataList);
		String[] colors = { "#999", "red" };
		Collections.sort(engineDataList,new BeanComparator("test_rf"));
		int i = 0;
		for (Engine engine : engineDataList) {
			JSONObject valueObj = new JSONObject();
			JSONArray chartValues = new JSONArray();
			JSONObject chartObj = new JSONObject();
			valueObj.put("x",engine.getTest_rf());
			valueObj.put("y", Integer.parseInt(engine.getCost()));
			valueObj.put("engine_no", engine.getEngine_no());
			valueObj.put("name", engine.getEngine_id());
			valueObj.put("size", Integer.parseInt(engine.getCost()));
			chartValues.add(valueObj);
			chartObj.put("values", chartValues);
			chartObj.put("key", engine.getEquipment_No());
			if(i >=5){
				valueObj.put("color", colors[0]);
			} else {
				valueObj.put("color", colors[1]);
			}
			chartJSON.add(chartObj);
			i++;
		}

		JSONObject dataJSON = new JSONObject();
		dataJSON.put("title", "Risk Identification chart");
		dataJSON.put("placeHolder", "dashboard");
		dataJSON.put("type", "BubbleChart");
		dataJSON.put("x_max", getMaxRulByEngine(engineDataList) );
		dataJSON.put("y_max", getMaxCostByEngine(engineDataList));
		dataJSON.put("data", chartJSON);
		// System.out.print(chartJSON.toJSONString());
		return dataJSON.toJSONString();

	}
	
	private String getMaxCostByEngine(List<Engine> engineDataList){
		Collections.sort(engineDataList,new BeanComparator("Cost"));
		int size = engineDataList.size();
		Engine eng = engineDataList.get(size-1);
		return eng.getCost();
	}
	
	private double getMaxRulByEngine(List<Engine> engineDataList){
		Collections.sort(engineDataList,new BeanComparator("test_rf"));
		int size = engineDataList.size();
		Engine eng = engineDataList.get(size-1);
		return eng.getTest_rf();
	}
	public String oprFailureAnalysisData(List<Engine> engineDataList) {
		JSONArray chartJSON = new JSONArray();

		for (Engine engine : engineDataList) {
			JSONArray records = new JSONArray();
			records.add(engine.getEngine_no());
			records.add(engine.getEngine_id());
			records.add(engine.getTest_rf());
			records.add(engine.getPressure());
			records.add(engine.getFlowrate());
			records.add(engine.getSpeed_RPM());
			chartJSON.add(records);
		}
		JSONObject dataJSON = new JSONObject();
		dataJSON.put("title", "Operation Conditions Details");
		dataJSON.put("type", "Table");
		dataJSON.put("placeHolder", "dashboard");
		dataJSON.put("data", chartJSON);

		return dataJSON.toJSONString();
	}

	public String fetchUtilizationData(List<UtilizationVo> utilizationList) {
		JSONObject chartDataObj = new JSONObject();
		JSONArray chartValues = new JSONArray();
		JSONArray chartJSON = new JSONArray();
		String plant = "";
		String[] colors = { "#999", "#ddd" };
		int i = 0;
		Collections.sort(utilizationList,BeanComparator.utilizationMonthCompare);
		for (UtilizationVo utilization : utilizationList) {
			JSONObject valueObj = new JSONObject();
			valueObj.put("Months", utilization.getMonths());
			valueObj.put("Utilization", utilization.getUtilization());
			i = (i == 0) ? 1 : 0;
			valueObj.put("color", colors[i]);
			plant = utilization.getPlant();
			chartValues.add(valueObj);
		}
		chartDataObj.put("values", chartValues);
		chartDataObj.put("key", plant);
		chartJSON.add(chartDataObj);
		return chartJSON.toJSONString();
	}
}
