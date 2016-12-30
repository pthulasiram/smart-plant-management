package com.archius.cosmos.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
/**
 * @author Thulasiram
 *
 */
public class RestClient {

	private static RestClient restClient;

	private RestClient() {

	}

	static {
		SSHHandler.disableSslVerification();
	}

	public static RestClient getRestClient() {
		if (restClient == null) {
			restClient = new RestClient();
		}
		return restClient;
	}

	/**
	 * It creates request object with basic auth information header
	 * 
	 * @param userName
	 * @param password
	 * @return HttpEntity ( Request object with basic auth header)
	 */
	private HttpEntity<String> getBasicAuthEntity(final String userName, final String password) {
		String credentails = userName + ":" + password;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + new String(Base64.encode(credentails.getBytes())));
		return new HttpEntity<String>(headers);
	}

	/**
	 * This method returns default spring rest client
	 * 
	 * @return
	 */
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * This method returns default spring rest client
	 * 
	 * @return
	 */
	/*
	 * public OAuth2RestTemplate getRestTemplateWithOauth2(String userName,
	 * String password, String accessTokenUri, String clientId, String secret,
	 * String grantType) { ResourceOwnerPasswordResourceDetails resource = new
	 * ResourceOwnerPasswordResourceDetails(); resource.setUsername(userName);
	 * resource.setPassword(password);
	 * resource.setAccessTokenUri(accessTokenUri);
	 * resource.setClientId(clientId); resource.setClientSecret(secret);
	 * resource.setGrantType(grantType); DefaultOAuth2ClientContext
	 * clientContext = new DefaultOAuth2ClientContext(); return new
	 * OAuth2RestTemplate(resource, clientContext); }
	 */

	public ResponseEntity<Object> invoke(String uri, String username, String password, HttpMethod method,
			String mimeType, Class className) {
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> request = getBasicAuthEntity(username, password);
		ResponseEntity<Object> response = restTemplate.exchange(uri, method, request, className);
		return response;
	}

	public void post() {
		RestTemplate rest = getRestTemplate();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
		HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
		List<HttpMessageConverter<?>> messageConverterList = new ArrayList<HttpMessageConverter<?>>();
		messageConverterList.add(formHttpMessageConverter);
		messageConverterList.add(stringHttpMessageConverternew);
		rest.setMessageConverters(messageConverterList);
		map.add("username", "5bc2b981-c169-40e3-ab3c-c9815d28e4fc");
		map.add("password", "6PkzoMJxxSWI");
		String result = rest.postForObject("http://localhost:8080/soa-server/user/", map, String.class);
		System.out.println(result);
	}

	/*public static void main(String args[]) throws Exception {
		String url = "";
		url = "http://54.173.130.64:8000/sap/opu/odata/sap/ZPMORDEER_SRV/PMOrder(Orderid='500120')";
		RestClient client = RestClient.getRestClient();

		ResponseEntity<Object> res = client.invoke(url, "idadmin", "ides", HttpMethod.GET, null, String.class);
		System.out.println(res.getBody().toString());
	}*/
}
