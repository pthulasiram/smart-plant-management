package com.archius.cosmos.util;

import java.util.List;
import java.util.Map;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

/**
 * NLPConversation Util class used to communicate watson to make continuous
 * conversations
 * 
 * @version v1-experimental
 */
/**
 * @author Thulasiram
 *
 */
public class NLPAPIClient {
	private Map context;

	private static NLPAPIClient instance;

	private NLPAPIClient() {

	}

	public static NLPAPIClient getInstance() {
		if (instance == null) {
			instance = new NLPAPIClient();
		}
		return instance;
	}

	/**
	 * Communicate with watson api using message and returns response
	 * 
	 * @param message
	 * @return
	 */
	public MessageResponse nlpExchange(String message, Map<String, String> nlpProps) throws Exception {

		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_09_20);
		service.setUsernameAndPassword(nlpProps.get("username"), nlpProps.get("password"));
		MessageRequest msg = getMessage(message);
		MessageResponse response = service.message(nlpProps.get("workspaceId"), msg).execute();
		context = response.getContext();
		
		// System.out.println("watson "+response.toString());
		List<String> outputList = response.getText();

		return response;

	}

	/**
	 * Preparing Message Request using message and context
	 * 
	 * @param conversationMsg
	 * @return
	 */
	private MessageRequest getMessage(String conversationMsg) {
		MessageRequest msg = null;
		if (context != null && conversationMsg != null) {
			msg = new MessageRequest.Builder().inputText(conversationMsg).context(context).build();
		} else {
			msg = new MessageRequest.Builder().inputText(conversationMsg).build();
		}
		return msg;

	}

}