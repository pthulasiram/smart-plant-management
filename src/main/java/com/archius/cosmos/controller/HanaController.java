package com.archius.cosmos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archius.cosmos.exception.RestApiException;
import com.archius.cosmos.service.HanaOdataService;
import com.archius.cosmos.service.OdataService;

/**
 * @author Thulasiram Ponnam
 *
 */
@RestController
public class HanaController {
	@Autowired
	HanaOdataService hanaOdataService;

	@CrossOrigin
	@RequestMapping(value = "/hana/product/sales", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchHanaProductSales() throws RestApiException {
		return hanaOdataService.fetchProductSales();
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/barrel", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchBarrelData() throws RestApiException {
		return hanaOdataService.fetchBarrelData("");
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/barrel/{paramId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchBarrelData(@PathVariable("paramId") String paramId) throws RestApiException {
		return hanaOdataService.fetchBarrelData(paramId);
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/engine", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchEngineData() throws RestApiException {
		return hanaOdataService.fetchEngineOdata("");
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/engine/{paramId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchEngineData(@PathVariable("paramId") String paramId) throws RestApiException {
		return hanaOdataService.fetchEngineOdata(paramId);
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/headcount", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchHeadCountData() throws RestApiException {
		return hanaOdataService.fetchHeadCountData("");
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/headcount/{paramId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchHeadCountData(@PathVariable("paramId") String paramId) throws RestApiException {
		return hanaOdataService.fetchHeadCountData(paramId);
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/utilization", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchUtilizationData() throws RestApiException {
		return hanaOdataService.fetchUtilizationData("");
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/utilization/{paramId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchUtilizationData(@PathVariable("paramId") String paramId) throws RestApiException {
		return hanaOdataService.fetchUtilizationData(paramId);
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/utilization/barchart", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	String fetchUtilizationBarChartData() throws RestApiException {
		return hanaOdataService.fetchUtilizationBarChartData();
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/downtime", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchDownTimeData() throws RestApiException {
		return hanaOdataService.fetchDownTimeData("");
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/downtime/{paramId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchDownTimeData(@PathVariable("paramId") String paramId) throws RestApiException {
		return hanaOdataService.fetchDownTimeData(paramId);
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/kpi", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchKPIData() throws RestApiException {
		return hanaOdataService.fetchKPIData();
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/engine/classification", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchEngineClassificationData() throws RestApiException {
		return hanaOdataService.fetchEngineClassificationOdata("");
	}

	@CrossOrigin
	@RequestMapping(value = "/hana/odata/engine/classification/{paramId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	String fetchEngineClassificationData(@PathVariable("paramId") String paramId) throws RestApiException {
		return hanaOdataService.fetchEngineClassificationOdata(paramId);
	}

}
