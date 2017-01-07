/**
 * 
 */
package com.archius.cosmos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.archius.cosmos.common.OdataProperties;
import com.archius.cosmos.util.RestClient;
/**
 * @author Thulasiram Ponnam
 *
 */
@Service
public class OdataServiceImpl implements OdataService{
	private static final Logger logger = LoggerFactory.getLogger(OdataServiceImpl.class);
	
	RestClient restClient = RestClient.getRestClient();
	@Autowired
	OdataProperties odataProperties;
	
	public String getProductOrder(String orderId) {
		String uri = odataProperties.getoDataIp()+":"+odataProperties.getoDataPort();
		String username = odataProperties.getoDataUserName();
		String password = odataProperties.getoDataPassword();
		String productApi = odataProperties.getoDataPmOderApi();
		productApi = productApi.replace("$_orderid", orderId);
		uri = "http://"+uri+productApi;
		logger.info("product order API Url" + uri);
		ResponseEntity<Object> res  = restClient.invoke(uri, username, password, HttpMethod.GET, null, String.class);
		logger.info("product order API response" + res.getBody().toString());
		return res.getBody().toString();
	}

	public String getProductFisType(String perivId) {
		String uri = odataProperties.getoDataIp()+":"+odataProperties.getoDataPort();
		String username = odataProperties.getoDataUserName();
		String password = odataProperties.getoDataPassword();
		String fisTypeApi = odataProperties.getoDatafisTypeApi();
		fisTypeApi = fisTypeApi.replace("$_perivid", perivId);
		uri = "http://"+uri+fisTypeApi;
		logger.info("Fis Type API Url" + uri);
		ResponseEntity<Object> res  = restClient.invoke(uri, username, password, HttpMethod.GET, null, String.class);
		logger.info("Fis Type API response" + res.getBody().toString());
		return res.getBody().toString();
	}
}
