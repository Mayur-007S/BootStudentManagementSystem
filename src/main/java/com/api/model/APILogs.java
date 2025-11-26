package com.api.model;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Entity
public class APILogs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq_no;
	private String transactionId;
	private String conversationId;
	private String timestamp;
	private String contract;
	private String notification;

	@Column(length = 255)
	private String request;

	@Column(length = 255)
	private String response;

	public APILogs() {
		LocalTime now = LocalTime.now();
		this.timestamp = now.toString();
	}

	public long getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(long seq_no) {
		this.seq_no = seq_no;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
