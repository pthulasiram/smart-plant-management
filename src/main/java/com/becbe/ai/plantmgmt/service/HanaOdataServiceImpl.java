/**
 * 
 */
package com.archius.cosmos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.archius.cosmos.common.AppConstants;
import com.archius.cosmos.common.OdataProperties;
import com.archius.cosmos.model.Barrel;
import com.archius.cosmos.model.DownTime;
import com.archius.cosmos.model.HeadCount;
import com.archius.cosmos.model.UtilizationVo;
import com.archius.cosmos.util.AppFileUtil;
import com.archius.cosmos.util.BeanComparator;
import com.archius.cosmos.util.ChartJSONUtil;
import com.archius.cosmos.util.JSONToObjectUtil;
import com.archius.cosmos.util.RestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Thulasiram Ponnam
 *
 */
@Service
public class HanaOdataServiceImpl implements HanaOdataService {
	private static final Logger logger = LoggerFactory.getLogger(HanaOdataServiceImpl.class);

	RestClient restClient = RestClient.getRestClient();
	AppFileUtil fileUtil = AppFileUtil.getInstance();
	@Autowired
	OdataProperties odataProperties;
	String uri;
	String username;
	String password;
	List<UtilizationVo> utilizationList;
	List<Barrel> barrelList;
	List<HeadCount> headCountList;
	List<DownTime> downTimeList;
	String env;

	@PostConstruct
	public void initialize() {
		uri = odataProperties.getHanaServerIp() + ":" + odataProperties.getHanaServerPort();
		username = odataProperties.getHanaUserName();
		password = odataProperties.getHanaPassword();
		utilizationList = new ArrayList<UtilizationVo>();
		barrelList = new ArrayList<Barrel>();
		headCountList = new ArrayList<HeadCount>();
		downTimeList = new ArrayList<DownTime>();
		env = odataProperties.getAppEnv().toUpperCase();
	}

	@Override
	public String fetchProductSales() {

		String productApi = odataProperties.getHanaSalesApi();
		// productApi = productApi.replace("$_orderid", orderId);
		String url = "http://" + uri + productApi;
		logger.info("product order API Url" + url);
		String response = "{error: nodata}";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Hana Product sales report" + res.getBody().toString());
			response = res.getBody().toString();
		} else {
			response = "";
		}
		return response;
	}

	@Override
	public String fetchBarrelData(String barrel) {
		String productApi = odataProperties.getOdataBarrel();
		productApi = productApi.replace("$_param", barrel);
		if (barrel.equals("")) {
			productApi = productApi.substring(0, productApi.length() - 1);
		}
		String url = "http://" + uri + productApi;
		logger.info("barrel API Url" + url);
		String response = "{error: nodata}";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Hana Product sales report" + res.getBody().toString());
			response = res.getBody().toString();
		} else {
			response = fileUtil.getFile(barrel);
		}
		return response;
	}

	@Override
	public String fetchHeadCountData(String headCount) {
		String productApi = odataProperties.getOdataHeadCount();
		productApi = productApi.replace("$_param", headCount);
		if (headCount.equals("")) {
			productApi = productApi.substring(0, productApi.length() - 1);
		}
		String url = "http://" + uri + productApi;
		logger.info("product order API Url" + url);

		String response = "{error: nodata}";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Head count data report" + res.getBody().toString());
			response = res.getBody().toString();
		} else {
			response = fileUtil.getFile(headCount);
		}
		return response;
	}

	@Override
	public String fetchUtilizationData(String utilization) {
		String productApi = odataProperties.getOdataUtilization();
		productApi = productApi.replace("$_param", utilization);
		if (utilization.equals("")) {
			productApi = productApi.substring(0, productApi.length() - 1);
		}
		String url = "http://" + uri + productApi;
		logger.info("product order API Url " + url);
		String response = "{error: nodata}";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Hana utilization report" + res.getBody().toString());
			response = res.getBody().toString();
		} else {
			response = fileUtil.getFile(utilization);
		}
		return response;
	}

	@Override
	public String fetchDownTimeData(String downTime) {
		String productApi = odataProperties.getDownTime();
		productApi = productApi.replace("$_param", downTime);
		if (downTime.equals("")) {
			productApi = productApi.substring(0, productApi.length() - 1);
		}
		String url = "http://" + uri + productApi;
		logger.info("product order API Url " + url);
		String response = "{error: nodata}";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Hana downtime report" + res.getBody().toString());
			response = res.getBody().toString();
		} else {
			response = fileUtil.getFile(downTime);
		}
		return response;
	}

	@Override
	public String fetchEngineOdata(String engineOdata) {
		String productApi = odataProperties.getOdataEngine();
		productApi = productApi.replace("$_param", engineOdata);
		if (engineOdata.equals("")) {
			productApi = productApi.substring(0, productApi.length() - 1);
		}
		String url = "http://" + uri + productApi;
		logger.info("product order API Url " + url);
		String json = "";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Hana Engine Odata" + res.getBody().toString());
			json = res.getBody().toString();
		} else {
			json = fileUtil.getFile(engineOdata);
		}
		return json;
	}

	@Override
	public String fetchUtilizationBarChartData() {
		String jsonData = null;
		String chartData = null;
		try {
			String json = fetchUtilizationData("Utilization");
			if (json != null) {
				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObject;
				jsonObject = jsonParser.parse(json).getAsJsonObject();
				JsonArray arrObj = jsonParser.parse(json).getAsJsonObject().getAsJsonObject("d")
						.getAsJsonArray("results");
				jsonData = arrObj.toString();

			}

			JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
			utilizationList = obj.convertJsonToObject(jsonData, UtilizationVo.class);
			chartData = ChartJSONUtil.getInstance().fetchUtilizationData(utilizationList);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("Failed to Prepare Utilization Bar Chart Data :" + e.getMessage());
		}

		logger.info("Hana utilization report" + chartData);
		return chartData;
	}

	@Override
	public String fetchKPIData() {
		JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
		String jsonData = null;
		JSONObject responseJSON = new JSONObject();
		JSONArray valueObj = new JSONArray();
		try {
			jsonData = fetchBarrelData(KPIConstants.BARREL);

			if (jsonData != null) { // convert rsponse in to barrel
				String json = parseJSON(jsonData);
				barrelList = obj.convertJsonToObject(json, Barrel.class);
			}
			jsonData = fetchUtilizationData(KPIConstants.UTILIZATION);
			if (jsonData != null) { // convert rsponse in to utilization
				String json = parseJSON(jsonData);
				utilizationList = obj.convertJsonToObject(json, UtilizationVo.class);
				Collections.sort(utilizationList,BeanComparator.utilizationMonthCompare);
			}
			jsonData = fetchDownTimeData(KPIConstants.DOWN_TIME);
			if (jsonData != null) { // convert rsponse in to DOWN_TIME
				String json = parseJSON(jsonData);
				downTimeList = obj.convertJsonToObject(json, DownTime.class);
				Collections.sort(downTimeList,BeanComparator.downTimeMonthCompare);
			}

			jsonData = fetchHeadCountData(KPIConstants.HEAD_COUNT);
			if (jsonData != null) { // convert rsponse in to HeadCount
				String json = parseJSON(jsonData);
				headCountList = obj.convertJsonToObject(json, HeadCount.class);
			}
			JSONObject dataJson = new JSONObject();

			dataJson.put("utilization", utilizationList != null && utilizationList.size() > 0
					? utilizationList.get(utilizationList.size() -1).getUtilization() : "");
			dataJson.put("production",
					barrelList != null && barrelList.size() > 0 ? barrelList.get(0).getBarrels() : "");
			dataJson.put("downtime",
					downTimeList != null && downTimeList.size() > 0 ? downTimeList.get(downTimeList.size()-1).getDowntime() : "");
			dataJson.put("headcount",
					headCountList != null && headCountList.size() > 0 ? headCountList.get(0).getHeadCount() : "");
			responseJSON.put("data", dataJson);

		} catch (Exception e) {
			logger.error("Failed to fetch KPI Data due to :" + e.getMessage());
		}
		return responseJSON.toString();
	}

	private String parseJSON(String json) {
		if (json != null) {
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject;
			jsonObject = jsonParser.parse(json).getAsJsonObject();
			JsonArray arrObj = jsonParser.parse(json).getAsJsonObject().getAsJsonObject("d").getAsJsonArray("results");
			json = arrObj.toString();

		}
		return json;
	}

	@Override
	public String fetchEngineClassificationOdata(String engineOdata) {
		String productApi = odataProperties.getOdataEngineClassification();
		productApi = productApi.replace("$_param", engineOdata);
		if (engineOdata.equals("")) {
			productApi = productApi.substring(0, productApi.length() - 1);
		}
		String url = "http://" + uri + productApi;
		logger.info("product order API Url " + url);
		String json = "";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Hana Engine Odata" + res.getBody().toString());
			json = res.getBody().toString();
		} else {
			json = fileUtil.getFile("EngineClassif");
		}
		return json;
	}

}
