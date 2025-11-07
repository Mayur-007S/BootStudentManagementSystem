package com.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.APILogs;
import com.api.repository.APILogsRepository;

@Service
public class APILogsServiceImpl {

	@Autowired
	private APILogsRepository apiLogsRepository;
	
	public void saveLogs(String transactionId,String conversationId,String contract,int notification,String request,String response) {
		APILogs apiLogs = new APILogs();
		apiLogs.setTransactionId(transactionId);
		apiLogs.setConversationId(conversationId);
		apiLogs.setContract(contract);
		apiLogs.setNotification(String.valueOf(notification));
		apiLogs.setRequest(request);
		apiLogs.setResponse(response);
		
		apiLogsRepository.save(apiLogs);
	}
}
