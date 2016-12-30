package com.archius.cosmos.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
/**
 * @author Thulasiram
 *
 */
@Configuration
@PropertySource(value = "classpath:api_odata.properties")
public class OdataProperties {
	@Autowired
	private Environment env;

	@Value("${odata.service.ip}")
	private String oDataIp;

	@Value("${odata.service.port}")
	private String oDataPort;

	@Value("${odata.service.username}")
	private String oDataUserName;

	@Value("${odata.service.password}")
	private String oDataPassword;

	@Value("${odata.service.api.pmorder}")
	private String oDataPmOderApi;

	@Value("${odata.service.api.fistype}")
	private String oDatafisTypeApi;

	@Value("${hana.service.ip}")
	private String hanaServerIp;

	@Value("${hana.service.port}")
	private String hanaServerPort;

	@Value("${hana.service.api.sales}")
	private String hanaSalesApi;

	@Value("${hana.service.username}")
	private String hanaUserName;

	@Value("${hana.service.password}")
	private String hanaPassword;

	@Value("${hana.odata.service.api.barrel}")
	private String odataBarrel;

	@Value("${hana.odata.service.api.utilization}")
	private String odataUtilization;

	@Value("${hana.odata.service.api.headcount}")
	private String odataHeadCount;

	@Value("${hana.odata.service.api.downtime}")
	private String downTime;

	@Value("${hana.odata.service.api.engine}")
	private String odataEngine;

	@Value("${hana.odata.service.api.classification}")
	private String odataEngineClassification;

	@Value("${app.environment}")
	private String appEnv;

	@Value("${hana.odata.service.api.engine.update}")
	private String odataEngineUpdateApi;

	/**
	 * @return the odataEngineUpdateApi
	 */
	public String getOdataEngineUpdateApi() {
		return odataEngineUpdateApi;
	}

	/**
	 * @param odataEngineUpdateApi
	 *            the odataEngineUpdateApi to set
	 */
	public void setOdataEngineUpdateApi(String odataEngineUpdateApi) {
		this.odataEngineUpdateApi = odataEngineUpdateApi;
	}

	public String getoDataIp() {
		return oDataIp;
	}

	public void setoDataIp(String oDataIp) {
		this.oDataIp = oDataIp;
	}

	public String getoDataPort() {
		return oDataPort;
	}

	public void setoDataPort(String oDataPort) {
		this.oDataPort = oDataPort;
	}

	public String getoDataUserName() {
		return oDataUserName;
	}

	public void setoDataUserName(String oDataUserName) {
		this.oDataUserName = oDataUserName;
	}

	public String getoDataPassword() {
		return oDataPassword;
	}

	public void setoDataPassword(String oDataPassword) {
		this.oDataPassword = oDataPassword;
	}

	public String getoDataPmOderApi() {
		return oDataPmOderApi;
	}

	public void setoDataPmOderApi(String oDataPmOderApi) {
		this.oDataPmOderApi = oDataPmOderApi;
	}

	public String getoDatafisTypeApi() {
		return oDatafisTypeApi;
	}

	public void setoDatafisTypeApi(String oDatafisTypeApi) {
		this.oDatafisTypeApi = oDatafisTypeApi;
	}

	/**
	 * @return the hanaServerIp
	 */
	public String getHanaServerIp() {
		return hanaServerIp;
	}

	/**
	 * @param hanaServerIp
	 *            the hanaServerIp to set
	 */
	public void setHanaServerIp(String hanaServerIp) {
		this.hanaServerIp = hanaServerIp;
	}

	/**
	 * @return the hanaServerPort
	 */
	public String getHanaServerPort() {
		return hanaServerPort;
	}

	/**
	 * @param hanaServerPort
	 *            the hanaServerPort to set
	 */
	public void setHanaServerPort(String hanaServerPort) {
		this.hanaServerPort = hanaServerPort;
	}

	/**
	 * @return the hanaSalesApi
	 */
	public String getHanaSalesApi() {
		return hanaSalesApi;
	}

	/**
	 * @param hanaSalesApi
	 *            the hanaSalesApi to set
	 */
	public void setHanaSalesApi(String hanaSalesApi) {
		this.hanaSalesApi = hanaSalesApi;
	}

	/**
	 * @return the hanaUserName
	 */
	public String getHanaUserName() {
		return hanaUserName;
	}

	/**
	 * @param hanaUserName
	 *            the hanaUserName to set
	 */
	public void setHanaUserName(String hanaUserName) {
		this.hanaUserName = hanaUserName;
	}

	/**
	 * @return the hanaPassword
	 */
	public String getHanaPassword() {
		return hanaPassword;
	}

	/**
	 * @param hanaPassword
	 *            the hanaPassword to set
	 */
	public void setHanaPassword(String hanaPassword) {
		this.hanaPassword = hanaPassword;
	}

	/**
	 * @return the odataBarrel
	 */
	public String getOdataBarrel() {
		return odataBarrel;
	}

	/**
	 * @param odataBarrel
	 *            the odataBarrel to set
	 */
	public void setOdataBarrel(String odataBarrel) {
		this.odataBarrel = odataBarrel;
	}

	/**
	 * @return the odataUtilization
	 */
	public String getOdataUtilization() {
		return odataUtilization;
	}

	/**
	 * @param odataUtilization
	 *            the odataUtilization to set
	 */
	public void setOdataUtilization(String odataUtilization) {
		this.odataUtilization = odataUtilization;
	}

	/**
	 * @return the odataHeadCount
	 */
	public String getOdataHeadCount() {
		return odataHeadCount;
	}

	/**
	 * @param odataHeadCount
	 *            the odataHeadCount to set
	 */
	public void setOdataHeadCount(String odataHeadCount) {
		this.odataHeadCount = odataHeadCount;
	}

	/**
	 * @return the odataEngine
	 */
	public String getOdataEngine() {
		return odataEngine;
	}

	/**
	 * @param odataEngine
	 *            the odataEngine to set
	 */
	public void setOdataEngine(String odataEngine) {
		this.odataEngine = odataEngine;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getValue(String key) {
		return env.getProperty(key);
	}

	/**
	 * @return the downTime
	 */
	public String getDownTime() {
		return downTime;
	}

	/**
	 * @param downTime
	 *            the downTime to set
	 */
	public void setDownTime(String downTime) {
		this.downTime = downTime;
	}

	/**
	 * @return the env
	 */
	public Environment getEnv() {
		return env;
	}

	/**
	 * @param env
	 *            the env to set
	 */
	public void setEnv(Environment env) {
		this.env = env;
	}

	/**
	 * @return the appEnv
	 */
	public String getAppEnv() {
		return appEnv;
	}

	/**
	 * @param appEnv
	 *            the appEnv to set
	 */
	public void setAppEnv(String appEnv) {
		this.appEnv = appEnv;
	}

	/**
	 * @return the odataEngineClassification
	 */
	public String getOdataEngineClassification() {
		return odataEngineClassification;
	}

	/**
	 * @param odataEngineClassification
	 *            the odataEngineClassification to set
	 */
	public void setOdataEngineClassification(String odataEngineClassification) {
		this.odataEngineClassification = odataEngineClassification;
	}

}
