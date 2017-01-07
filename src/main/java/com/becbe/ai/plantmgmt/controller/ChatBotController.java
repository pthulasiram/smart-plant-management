package com.archius.cosmos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.archius.cosmos.service.HanaOdataService;
/**
 * @author Thulasiram Ponnam
 *
 */
@Controller
@EnableScheduling
class ChatBotController {
	int count = 0;
	int i=100;
	@Autowired
	SimpMessagingTemplate template;

	@Autowired
	private HanaOdataService hanaOdataService;

	/*@CrossOrigin
	//@Scheduled(fixedDelay = 20000L)
	@SendTo("/topic/chatbot")
	public void sendDashboardEvents() {

		try {
			i++;
			//template.convertAndSend("/topic/chatbot", hanaOdataService.fetchEngineOdata("Engine"));
			template.convertAndSend("/topic/chatbot", i);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@CrossOrigin
	//@Scheduled(fixedDelay = 20000L)
	@SendTo("/topic/count")
	public void sendCounts() {
		count ++;
		try {
			template.convertAndSend("/topic/count", "Scoket No Request Made "+count);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}