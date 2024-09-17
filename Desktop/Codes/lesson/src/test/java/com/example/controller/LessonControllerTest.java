package com.example.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
@ActiveProfiles("test")
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
	}
	
}
