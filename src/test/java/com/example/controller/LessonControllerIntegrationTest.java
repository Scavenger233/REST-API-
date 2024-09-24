package com.example.controller;
import org.junit.jupiter.api.MethodOrderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.BaseIntegrationTest;
import com.example.SpringBootCrudApplication;
import com.example.exception.LessonNotFoundException;
import com.example.model.Lesson;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootCrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//这行代码有问题
//原代码为
//@TestMethodOrder(value = OrderAnnotation.class)
//@ActiveProfiles("test")
public class LessonControllerIntegrationTest extends BaseIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@Order(1)
	public void addLesson() {
		Lesson lesson = new Lesson(10001, "pocky", "Spring Boot Introduction");
		
		HttpEntity<Lesson> entity = new HttpEntity<>(lesson, getHttpHeader());
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/instructors/pocky/lessons"),
				HttpMethod.POST, entity, String.class);
		
		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
		assertTrue(actual.contains("/instructors/pocky/lessons"));
	}
	
	@Test
	@Order(2)
	public void updateLessons() throws JSONException {
		Lesson lesson = new Lesson(1, "pocky", "Spring Boot Introduction updated");
		
		HttpEntity<Lesson> entity = new HttpEntity<>(lesson, getHttpHeader());
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("api/instructors/pocky/lessons/1"),
				HttpMethod.PUT, entity, String.class);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
		
		String expected = "{\"id\": 1, \"username\": \'pocky\", \"description\": \"Spring Boot Introduction updated\"}";
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(3)
	public void testGetLesson() throws JSONException, JsonProcessingException {
		HttpEntity<String> entity = new HttpEntity<>(null, getHttpHeader());
		
		ResponseEntity<String> response1 = restTemplate.exchange(
				createURLWithPort("/api/instructors/pocky/lessons/1"),
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"id\": 1, \"username\": \'pocky\", \"description\": \"Spring Boot Introduction updated\"}";

		JSONAssert.assertEquals(expected, response1.getBody(), false);
	}
	
	@Test
	@Order(4)
	public void testDeleteLesson() {
		
		Lesson lesson = restTemplate.getForObject(createURLWithPort("/api/instructors/pocky/lessons/1"), Lesson.class);
		assertNotNull(lesson);
		
		HttpEntity<String> entity = new HttpEntity<>(null, getHttpHeader());
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/instructors/pocky/lessons/1"),
				HttpMethod.DELETE, entity, String.class);
		
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
		
		try {
			lesson = restTemplate.getForObject("/api/instructors/pocky/lessons/1", Lesson.class);
		} catch (LessonNotFoundException e){
			assertEquals("Lesson id not found : 1", e.getMessage());
		}
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	
	
	
	
	
}
