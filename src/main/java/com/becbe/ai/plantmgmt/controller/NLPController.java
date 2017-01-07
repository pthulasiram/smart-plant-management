package com.archius.cosmos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archius.cosmos.exception.RestApiException;
import com.archius.cosmos.service.NLPService;
import com.archius.cosmos.service.OdataService;
/**
 * @author Thulasiram
 *
 */
@RestController
public class NLPController {
	@Autowired
	NLPService nlpService;
	@CrossOrigin
	@RequestMapping(value= "/nlp/chatbot/{query}", method=RequestMethod.GET)
	String chatResponse(@PathVariable String query) throws RestApiException{
		return nlpService.exchangeChat(query);
	}
	
}
