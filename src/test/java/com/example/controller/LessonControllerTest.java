package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.model.Lesson;
import com.example.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = LessonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")//only with this annotation, this class would run test properties instead of the other file
class LessonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private LessonService lessonService;
	
	private static final ObjectMapper om = new ObjectMapper();
	
	Lesson mockLesson = new Lesson(10001, "pocky", "Spring boot introduction");
	
	String exampleLessonJson = "{\"id\": 10001, \"username\": \'bytecaptain\", \'description\": \"Spring Boot Introduction\"}";
	
	@Test
	public void getLesson() throws Exception {
		
		Mockito.when(lessonService.getLesson("bytecaptain",10001)).thenReturn(mockLesson);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/instructors/bytecaptain/lessons/10001").accept (
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		JSONAssert.assertEquals(exampleLessonJson, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void createLesson() throws Exception {
		
		Lesson lesson = new Lesson(10001, "pocky", "Spring boot introduction");
		
		Mockito.when(lessonService.createLesson(Mockito.anyString(), Mockito.any(Lesson.class))).thenReturn(lesson);
		
		String lessonString = om.writeValueAsString(lesson);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/instructors/pocky/lessons/10001").content(lessonString)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		assertEquals("http://localhost/instructors/pocky/lessons/10001",
				response.getHeader(HttpHeaders.LOCATION));
	}
	
	@Test
	public void updateLesson() throws Exception{
		
		Lesson lesson = new Lesson(10001, "pocky", "Spring Boot Introduction");
		
		Mockito.when(lessonService.updateLesson(Mockito.anyString(), Mockito.anyLong(), Mockito.any(Lesson.class))).thenReturn(lesson);
		
		String lessonString = om.writeValueAsString(lesson);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("instructors/pocky/lessons.10001")
				.contentType(MediaType.APPLICATION_JSON).content(lessonString);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		JSONAssert.assertEquals(exampleLessonJson, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void deleteLesson() throws Exception {
		
		doNothing().when(lessonService).deleteLesson("pocky", Long.valueOf(10001));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/instructors/pocky/lessons/10001");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}
	
}
