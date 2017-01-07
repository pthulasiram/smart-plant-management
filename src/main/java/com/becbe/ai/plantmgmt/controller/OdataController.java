package com.archius.cosmos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archius.cosmos.exception.RestApiException;
import com.archius.cosmos.service.OdataService;
/**
 * @author Thulasiram
 *
 */
@RestController
public class OdataController {
	@Autowired
	OdataService odataService;
	@CrossOrigin
	@RequestMapping(value= "/odata/product/{orderid}", method=RequestMethod.GET , produces=MediaType.APPLICATION_JSON_VALUE)
	String getProductOrder(@PathVariable String orderid) throws RestApiException{
		return odataService.getProductOrder(orderid);		
	}
	@CrossOrigin
	@RequestMapping(value="/odata/product/fis/{perivId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	String getProductFisType(@PathVariable("perivId") String perivId) throws RestApiException{
		return odataService.getProductOrder(perivId);		
	}
}
