package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.exception.LessonNotFoundException;
import com.example.model.Lesson;
import com.example.repository.LessonRepository;
import com.example.service.impl.LessonServiceImpl;

@ExtendWith(SpringExtension.class)
public class LessonServiceMockTest {

	@Mock
	private LessonRepository lessonRepository;
	
	@InjectMocks
	private LessonService lessonService = new LessonServiceImpl();
	
	@Test
	public void getAllLessons() {
		List<Lesson> lessons = Arrays.asList(
				new Lesson(10001, "pocky", "Learn Java"),
				new Lesson(10002, "pocky", "Learn Spring Boot"));
		
		when(lessonRepository.findAll()).thenReturn(lessons);
		assertEquals(lessons, lessonService.getAllLessons("in28minutes"));
	}
	
	@Test
	public void getLesson() {
		Lesson lesson = new Lesson(10001, "pocky", "Learn Java");
		
		when(lessonRepository.findById(Long.valueOf(10001))).thenReturn(Optional.of(lesson));
		
		assertEquals(lesson, lessonService.getLesson("pocky", Long.valueOf(10001)));
	}
	
	@Test
	public void getLessonNotFound() {
		
		LessonNotFoundException exception = assertThrows(
				LessonNotFoundException.class,
				() -> lessonService.getLesson("pocky", Long.valueOf(10001)),
				"Lesson id not found : 10001");
		
		assertEquals("Lesson id not found : 10001", exception.getMessage());
	}
	
	@Test
	public void deleteLesson() {
		Lesson lesson = new Lesson(10001, "pocky", "Learn Java");
		
		when(lessonRepository.findById(Long.valueOf(10001))).thenReturn(Optional.of(lesson));
		lessonService.deleteLesson("pocky", Long.valueOf(10001));
		
		verify(lessonRepository, times(1)).deleteById(Long.valueOf(10001));
	}
	
	@Test
	public void updatedLesson() {
		Lesson lesson = new Lesson(10001, "in28minutes", "Learn Java");
		
		when(lessonRepository.save(lesson)).thenReturn(lesson);
		
		assertEquals(lesson, lessonService.updateLesson("pocky", Long.valueOf(10001), lesson));
	}
	
	@Test
	public void createLesson() {
		Lesson lesson = new Lesson(10001, "pocky", "Learn Java");
		
		when(lessonRepository.save(lesson)).thenReturn(lesson);
		assertEquals(lesson, lessonService.createLesson("pocky", lesson));
	}
}
