package com.provenance.rabbitmq.consumer.topic.exchange.poc.synoma.plugin;

import lombok.Data;
import lombok.NoArgsConstructor;

public class Profile {
	private String profileName;
	private boolean shouldHandleRetries;
	private String[] queues;

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public boolean getShouldHandleRetries() {
		return shouldHandleRetries;
	}

	public void setShouldHandleRetries(boolean shouldHandleRetries) {
		this.shouldHandleRetries = shouldHandleRetries;
	}

	public String[] getQueues() {
		return queues;
	}

	public void setQueues(String[] queues) {
		this.queues = queues;
	}

}
