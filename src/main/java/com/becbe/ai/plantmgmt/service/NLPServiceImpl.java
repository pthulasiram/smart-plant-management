/**
 * 
 */
package com.archius.cosmos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.archius.cosmos.common.AppConstants;
import com.archius.cosmos.common.NLPProperties;
import com.archius.cosmos.common.OdataProperties;
import com.archius.cosmos.model.Engine;
import com.archius.cosmos.util.AppFileUtil;
import com.archius.cosmos.util.BeanComparator;
import com.archius.cosmos.util.ChartJSONUtil;
import com.archius.cosmos.util.ClassUtil;
import com.archius.cosmos.util.JSONToObjectUtil;
import com.archius.cosmos.util.NLPAPIClient;
import com.archius.cosmos.util.RegExUtil;
import com.archius.cosmos.util.RestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.conversation.v1.model.Intent;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
/**
 * @author Thulasiram
 *
 */
@Service
public class NLPServiceImpl implements NLPService {
	private static final Logger logger = LoggerFactory.getLogger(NLPServiceImpl.class);

	NLPAPIClient nlpApiClient = NLPAPIClient.getInstance();
	AppFileUtil fileUtil = AppFileUtil.getInstance();

	private final String ENGINE_SIM_QUERY = "test_rf lt 5 and Source eq 'Sim'";
	private final String ENGINE_RSIM_QUERY = "test_rf lt 4 and Source eq 'Reim'";
	private String welcomeMsg = null;
	private boolean isWelcomeMsg = true;
	@Autowired
	NLPProperties nlpProperties;

	@Autowired
	OdataProperties odataProps;

	@Autowired
	SimpMessagingTemplate template;

	JSONToObjectUtil jsonObjUtil = JSONToObjectUtil.getInstance();

	Map<String, String> nlpProps;
	Map<String, Engine> engineMap;
	Map<String, String> funIntentMap;

	Map<String, String> nlpOdataMap;

	String uri;
	String username;
	String password;
	String env;

	@PostConstruct
	public void initialize() {
		engineMap = new HashMap<String, Engine>();
		uri = odataProps.getHanaServerIp() + ":" + odataProps.getHanaServerPort();
		username = odataProps.getHanaUserName();
		password = odataProps.getHanaPassword();
		nlpProps = new HashMap<String, String>();
		nlpOdataMap = new HashMap<String, String>();
		funIntentMap = new HashMap<String, String>();
		updateNLPProps();
		processConversation("Hello");
		// failure_analysis_sensory_data=?$filter=$_filter
		nlpOdataMap.put("failure_analysis_sensory_data", "&$filter=$_filter");
		nlpOdataMap.put("re-simulate", "&$filter=$_filter");
		nlpOdataMap.put("pump_inclusion_reason", "&$filter=$_filter");
		env = odataProps.getAppEnv().toUpperCase();
		updateFunctionKeyList();
	}

	@Override
	public String exchangeChat(String message) {
		return processConversation(message);
	}

	private void updateNLPProps() {

		nlpProps.put("username", nlpProperties.getUsername());
		nlpProps.put("password", nlpProperties.getPassword());
		nlpProps.put("workspaceId", nlpProperties.getWorkspaceId());
	}

	private void updateFunctionKeyList() {
		funIntentMap.put("failure_analysis_sensory_data", "yes");
		funIntentMap.put("operating_conditions_failure_analysis", "operating_conditions");
		funIntentMap.put("re-simulate_flowrate", "re-simulate");
		funIntentMap.put("re-simulate_pressure", "re-simulate");
		funIntentMap.put("re-simulate_SpeedRPM", "re-simulate");
		funIntentMap.put("pump_inclusion_reason", "pump_inclusion");

	}

	@Override
	public Map<String, String> getNlpOdataMap() {
		// TODO Auto-generated method stub
		return nlpOdataMap;
	}

	private String processConversation(String message) {

		if (message.equals("chat-init")) {
			try {
				String queryParam = nlpOdataMap.get("failure_analysis_sensory_data").replace("$_filter",
						ENGINE_SIM_QUERY);
				String jsonData = fetchEngineOdata(queryParam);

				JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
				List<Engine> listEngine = obj.convertJsonToObject(jsonData, Engine.class);
				updateEngineDataMap(listEngine);
			} catch (Exception e) {
				logger.error("Failed to fetch engine simulation data in initialization" + e.getMessage());
			}

			return welcomeMsg != null ? welcomeMsg : "Hi, How I can Help you";
		}

		try {

			MessageResponse response = nlpApiClient.nlpExchange(message, nlpProps);
			logger.info(" NLP watson" + response.toString());
			final Map context = response.getContext();
			Intent intent = response.getIntents().get(0);
			List<String> outputList = response.getText();
			if (context != null && context.size() > 0) {
				String functionKey = (String) context.get("function");
				// if (functionKey != null &&
				// nlpOdataMap.containsKey(functionKey) &&
				// intent.getIntent().equals("yes")) {
				if (funIntentMap.containsKey(functionKey) && funIntentMap.get(functionKey).equals(intent.getIntent())) {
					
					String botResponse = "Yes, Absolutely";
					if (functionKey.equals(AppConstants.RE_SIMULATE_FLOWRATE) || functionKey.equals(AppConstants.RE_SIMULATE_PRESSURE) || functionKey.equals(AppConstants.RE_SIMULATE_SPEEDRPM)) {

						String queryParam = nlpOdataMap.get("re-simulate").replace("$_filter", ENGINE_RSIM_QUERY);
						new Thread(new Runnable() {

							@Override
							public void run() {
								pushEngineOdataToChatBot(functionKey, context);

							}
						}).start();
						return botResponse;
					}
					if (functionKey.equals("failure_analysis_sensory_data")) {
						String queryParam = nlpOdataMap.get(functionKey).replace("$_filter", ENGINE_SIM_QUERY);
						new Thread(new Runnable() {

							@Override
							public void run() {
								pushEngineOdataToDashWs(queryParam, functionKey, intent.getIntent());

							}
						}).start();
					}
					if (functionKey.equals("operating_conditions_failure_analysis")) {
						String queryParam = nlpOdataMap.get("failure_analysis_sensory_data").replace("$_filter",
								ENGINE_SIM_QUERY);
						new Thread(new Runnable() {

							@Override
							public void run() {
								pushEngineOdataToDashWs(queryParam, functionKey, intent.getIntent());

							}
						}).start();
					}
				}
					if (intent.getIntent().equals("operating_conditions") && engineMap.size() > 0) {
						return "ok";
					}
					// }
					// }
					if (intent.getIntent().equals("alert_recommendations") && engineMap.size() > 0) {
						StringBuffer msg = new StringBuffer();
						msg.append(
								"Plant utilization likely to\ndrop in the next week.\nPreventive Maintenance needed on\npumps");
						List<Engine> engineList = engineMap.entrySet().stream().map(x -> x.getValue())
								.collect(Collectors.toList());
						Collections.sort(engineList, new BeanComparator("test_rf"));
						msg.append("<b>");
						for (int i = 0; i < 5; i++) {
							msg.append(engineList.get(i).getEngine_no());
							if (i < engineList.size() - 1) {
								msg.append(",");
							}
						}
						msg.append("</b>");
						return msg.toString();

					}
				}
			if (isWelcomeMsg) {
				welcomeMsg = outputList.get(0).toString();
				isWelcomeMsg = false;
			}
			return outputList.get(0).toString();
		} catch (Exception e) {
			return "Failed to connect watson server";
		}
	}

	private void pushEngineOdataToDashWs(String queryParams, String key, String intent) {
		String jsonData = null;
		String dataResponse = null;
		try {

			switch (key) {
			case AppConstants.FAILURE_ANALYSIS_SENSORY_DATA:
				if (funIntentMap.get(key).equals(intent)) {
					jsonData = fetchEngineOdata(queryParams);
					JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
					List<Engine> listEngine = obj.convertJsonToObject(jsonData, Engine.class);
					updateEngineDataMap(listEngine);
					List<Engine> list = new ArrayList<Engine>(engineMap.values());
					dataResponse = ChartJSONUtil.getInstance().convertJsonToObject(list);
				}
				break;
			case AppConstants.OPR_CON_FAILURE_ANALYSIS:
				if (funIntentMap.get(key).equals(intent)) {
					jsonData = fetchEngineOdata(queryParams);
					JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
					List<Engine> listEngine = obj.convertJsonToObject(jsonData, Engine.class);
					updateEngineDataMap(listEngine);
					List<Engine> list = new ArrayList<Engine>(engineMap.values());
					dataResponse = ChartJSONUtil.getInstance().oprFailureAnalysisData(listEngine);
				}
				break;
			case AppConstants.RE_SIMULATE:
				String engineNo = null;
				String pressure = null;
				if (funIntentMap.get(key).equals(AppConstants.RE_SIMULATE_PRESSURE)) {
					jsonData = fetchEngineOdata(queryParams);
					JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
					List<Engine> listEngine = obj.convertJsonToObject(jsonData, Engine.class);
					updateEngineDataMap(listEngine);
					List<Engine> list = new ArrayList<Engine>(engineMap.values());
					dataResponse = ChartJSONUtil.getInstance().oprFailureAnalysisData(listEngine);
				}
				break;

			default:
				break;
			}

			template.convertAndSend("/topic/chatbot", dataResponse);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void pushEngineOdataToChatBot(String key, Map<String, String> contextMap) {
		String jsonData = null;
		String dataResponse = "{}";
		JSONObject dataJSON = new JSONObject();
		dataJSON.put("type", "Message");
		dataJSON.put("placeHolder", "chatbot");
		try {

			//switch (key) {
			//case AppConstants.RE_SIMULATE:
				String engineNo = null;
				String pressure = null;
				if (key.equals(AppConstants.RE_SIMULATE_PRESSURE)) {
					dataJSON.put("data", updateEngine(contextMap, true, false, false));
				}

				if (key.equals(AppConstants.RE_SIMULATE_FLOWRATE)) {
					dataJSON.put("data", updateEngine(contextMap, false, false, true));
				}

				if (key.equals(AppConstants.RE_SIMULATE_SPEEDRPM)) {
					dataJSON.put("data", updateEngine(contextMap, true, true, false));
				}
				/*break;

			default:
				break;
			}*/
			dataResponse = dataJSON.toJSONString();
		template.convertAndSend("/topic/chatbot", dataResponse);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateEngineDataMap(List<Engine> listEngine) {
		engineMap = new HashMap<String, Engine>();
		for (Engine enginge : listEngine) {
			engineMap.put(enginge.getEngine_no() + "", enginge);
		}
	}

	private String updateEngine(Map dataMap, boolean isPressure, boolean isSpeedRPM,
			boolean isFlowRate) {
		RegExUtil instance = RegExUtil.getInstance();
		StringBuilder response = new StringBuilder();
		// Map<String, String> dataMap =
		// instance.fetchKeyValuePair(message);
		Double engine_No = (Double) dataMap.get("Pump");
		String engine_no = (dataMap != null) ? engine_No.intValue()+"" : "";
		Engine engine = engineMap.get(engine_no);
		ClassUtil util = ClassUtil.getInstance();
		try {
			List<String> fields = util.listInstanceFieldsByClass(engine);
			fields.remove(0);
			fields.forEach((String field) -> {
				String setter = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
				switch (field) {
				case "Pressure":
					/*if (dataMap.get(field) == null) {
						break;
					}*/
					if (isPressure) {
						engine.setPressure((String) dataMap.get(field));
					}
					break;
				case "Speed_RPM":
					/*if (dataMap.get(field) == null) {
						break;
					}*/
					if (isSpeedRPM) {
						engine.setSpeed_RPM((String) dataMap.get(field));
					}
					break;
				case "test_rf":
					if (dataMap.get(field) == null) {
						break;
					}
					engine.setTest_rf(Double.parseDouble((String) dataMap.get(field)));
					break;
				case "Flowrate":
					/*if (dataMap.get(field) == null) {
						break;
					}*/
					if (isFlowRate) {
						Double flowRate = (Double) dataMap.get(field);
						
						engine.setFlowrate(flowRate.toString());
					}
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

					String queryParam = nlpOdataMap.get("re-simulate").replace("$_filter", ENGINE_RSIM_QUERY);
					String jsonData = fetchEngineOdata(queryParam);
					JSONToObjectUtil obj = JSONToObjectUtil.getInstance();
					List<Engine> listEngine = obj.convertJsonToObject(jsonData, Engine.class);
					updateEngineDataMap(listEngine);
					Engine tmp_engine = engineMap.get(engine_no);
					// Based on new temp, the RUL for Equi 14 is < >
					response.append("Based	on	new ");
					// Pressure
					if (isPressure) {
						response.append("Pressure = " + tmp_engine.getPressure());
					}
					// speed RPM
					if (isSpeedRPM) {
						response.append("Speed RPM = " + tmp_engine.getSpeed_RPM());
					}
					// flow rate
					if (isFlowRate) {
						response.append("Flow Rate = " + tmp_engine.getFlowrate());
					}

					response.append(", The RUL for Pump " + engine_no + " is " + tmp_engine.getTest_rf());
				}
			} else {
				response.append("D, The RUL for Pump " + engine_no + " is " + engine.getTest_rf());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Operating conditions reset operation failed " + e.getMessage());
		}
		return response.toString();
	}

	private String fetchEngineOdata(String queryParams) {
		String productApi = odataProps.getOdataEngine();
		productApi = productApi.replace("$_param", "Engine") + queryParams;
		// +queryParams;
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
				return arrObj.toString();
			} catch (Exception e) {
				logger.error("Error while parsing events data " + e.getMessage());
				e.printStackTrace();
			}
		}

		return null;
	}

}
