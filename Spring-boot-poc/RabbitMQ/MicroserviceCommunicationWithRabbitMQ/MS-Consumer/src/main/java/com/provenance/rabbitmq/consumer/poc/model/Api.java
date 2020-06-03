package com.provenance.rabbitmq.consumer.poc.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Api {

	private HashMap<String, Object> api_unique_identifier = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getApi_unique_identifier() {
		return api_unique_identifier;
	}

	@JsonAnySetter
	public void setApi_unique_identifier(HashMap<String, Object> api_unique_identifier) {
		this.api_unique_identifier = api_unique_identifier;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class API {

		private String path;
		private String method;
		private Boolean is_requestbody;
		private Map<String, Param> params = new HashMap<>();

		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		public class Param {
			private Boolean is_mandatory;
			private String type;
			private String data_type;
		}

	}

}
