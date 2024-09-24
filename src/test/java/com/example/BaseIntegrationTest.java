package com.example;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class BaseIntegrationTest {

	public static String basicToken = "Basic Ynl0ZWNhcHRhaW46YnlOZWNhcHRhaW4=";
	
	public HttpHeaders getHttpHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, basicToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return headers;
	}
}
