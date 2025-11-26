package com.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.APILogs;
import com.api.repository.APILogsRepository;

@Service
public class APILogsServiceImpl {

	@Autowired
	private APILogsRepository apiLogsRepository;

	public void saveLogs(String transactionId, String conversationId, String contract, int notification, String request,
			String response) {
		APILogs apiLogs = new APILogs();
		apiLogs.setTransactionId(transactionId);
		apiLogs.setConversationId(conversationId);
		apiLogs.setContract(contract);
		apiLogs.setNotification(String.valueOf(notification));

		// Truncate request and response to fit in VARCHAR(255)
		apiLogs.setRequest(truncateString(request, 255));
		apiLogs.setResponse(truncateString(response, 255));

		apiLogsRepository.save(apiLogs);
	}

	/**
	 * Truncates a string to the specified maximum length.
	 * If the string is longer, it will be truncated and "..." will be appended.
	 * 
	 * @param value     The string to truncate
	 * @param maxLength The maximum length
	 * @return Truncated string
	 */
	private String truncateString(String value, int maxLength) {
		if (value == null) {
			return null;
		}
		if (value.length() <= maxLength) {
			return value;
		}
		// Reserve 3 characters for "..."
		return value.substring(0, maxLength - 3) + "...";
	}
}
