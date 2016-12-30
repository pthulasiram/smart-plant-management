/**
 * 
 */
package com.archius.cosmos;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.archius.cosmos.common.AppConstants;
import com.archius.cosmos.common.OdataProperties;
import com.archius.cosmos.model.Engine;
import com.archius.cosmos.util.AppFileUtil;
import com.archius.cosmos.util.ClassUtil;
import com.archius.cosmos.util.JSONToObjectUtil;
import com.archius.cosmos.util.NLPAPIClient;
import com.archius.cosmos.util.RegExUtil;
import com.archius.cosmos.util.RestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Thulasiram
 *
 */
public class EngineDAO {

	private static final Logger logger = LoggerFactory.getLogger(EngineDAO.class);

	NLPAPIClient nlpApiClient = NLPAPIClient.getInstance();
	AppFileUtil fileUtil = AppFileUtil.getInstance();
	@Autowired
	OdataProperties odataProps;
	
	/*
	 * private static EngineDAO instance;
	 * 
	 * private EngineDAO() {
	 * 
	 * }
	 * 
	 * public static EngineDAO getInstance() { if (instance == null) { instance
	 * = new EngineDAO(); } return instance; }
	 */

	public String fetchEngineOdata(String queryParams) {
		String env = odataProps.getAppEnv().toUpperCase();
		String uri = odataProps.getHanaServerIp() + ":" + odataProps.getHanaServerPort();
		String username = odataProps.getHanaUserName();
		String password = odataProps.getHanaPassword();
		String response = "";
		String productApi = odataProps.getOdataEngine();
		productApi = productApi.replace("$_param", "Engine") + queryParams;
		RestClient restClient = RestClient.getRestClient();
		String url = "http://" + uri + productApi;
		logger.info("product order API Url " + url);
		String json = "{}";
		if (env.equals(AppConstants.APP_ENV_PROD)) {
			ResponseEntity<Object> res = restClient.invoke(url, username, password, HttpMethod.GET, null, String.class);
			logger.info("Hana Engine Odata" + res.getBody().toString());
			json = res.getBody().toString();
		} else {
			json = fileUtil.getFile("sim");
		}

		if (json != null) {
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject;

			try {
				jsonObject = jsonParser.parse(json).getAsJsonObject();
				JsonArray arrObj = jsonParser.parse(json).getAsJsonObject().getAsJsonObject("d")
						.getAsJsonArray("results");
				response = arrObj.toString();
			} catch (Exception e) {
				logger.error("Error while parsing events data " + e.getMessage());
			}
		}

		return response;
	}

	public String updateEngine(String message, String key, String intent, String queryParams,
			Map<String, Engine> engineMap) {
		String env = odataProps.getAppEnv().toUpperCase();
		String uri = odataProps.getHanaServerIp() + ":" + odataProps.getHanaServerPort();
		String username = odataProps.getHanaUserName();
		String password = odataProps.getHanaPassword();
		RegExUtil instance = RegExUtil.getInstance();
		String response = "";
		Map<String, String> dataMap = instance.fetchKeyValuePair(message);
		String engine_no = (dataMap != null) ? dataMap.get("engine_no") : "";
		Engine engine = engineMap.get(engine_no);
		ClassUtil util = ClassUtil.getInstance();
		try {
			List<String> fields = util.listInstanceFieldsByClass(engine);
			fields.forEach((String field) -> {
				String setter = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
				switch (field) {
				case "Pressure":
					if (dataMap.get(field) == null) {
						break;
					}
					engine.setPressure(dataMap.get(field));
					break;
				case "Speed_RPM":
					if (dataMap.get(field) == null) {
						break;
					}
					engine.setSpeed_RPM(dataMap.get(field));
					break;
				case "test_rf":
					if (dataMap.get(field) == null) {
						break;
					}
					engine.setTest_rf(Double.parseDouble(dataMap.get(field)));
					break;
				case "Flowrate":
					if (dataMap.get(field) == null) {
						break;
					}
					engine.setFlowrate(dataMap.get(field));
					break;

				default:
					break;
				}
			});
			String URL = odataProps.getOdataEngineUpdateApi() + " ?ID='" + engine.getEngine_no() + "'&PRESSURE='"
					+ engine.getPressure() + "'&FLOWRATE='" + engine.getFlowrate() + "'&SPEED='" + engine.getSpeed_RPM()
					+ "'";
			RestClient client = RestClient.getRestClient();
			if (env.equals(AppConstants.APP_ENV_PROD)) {
				ResponseEntity<Object> res = client.invoke(URL, username, password, HttpMethod.GET, null, String.class);
				if (res.getStatusCode().equals(200)) {

					/*
					 * String queryParam =
					 * nlpOdataMap.get("re-simulate").replace("$_filter",
					 * ENGINE_RSIM_QUERY);
					 */
					String jsonData = fetchEngineOdata(queryParams);
					JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
					List<Engine> listEngine = obj.convertJsonToObject(jsonData, Engine.class);
					response = "RUL	for Pump 14 is";
				}
			} else {
				response = "RUL	for Pump 14 is";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Operating conditions reset operation failed " + e.getMessage());
		}
		return response;
	}

}
