package com.example.service.impl;

import com.example.exception.LessonNotFoundException;
import com.example.model.Lesson;
import com.example.repository.LessonRepository;
import com.example.service.LessonService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {
	
	Logger logger = LoggerFactory.getLogger(LessonServiceImpl.class);

	@Autowired
	private LessonRepository lessonRepository;
	
	@Override
	public List<Lesson> getAllLessons(String username) {
		logger.trace("Entered getAlllessons method");
		
		List<Lesson> lessons = lessonRepository.findAll();
		
		return lessons;
	}
	
	@Override
	public Lesson getLesson(String username, long id) {
		return lessonRepository.findById(id).orElseThrow(() -> new LessonNotFoundException(id));
	}
	
	@Override
	public void deleteLesson(String username, long id) {
		Optional<Lesson> lesson = lessonRepository.findById(id);
		if(lesson.isPresent()) {
			lessonRepository.deleteById(id);
		} else {
			throw new LessonNotFoundException(id);
		}
	}
	
	@Override
	public Lesson updateLesson(String username, long id, Lesson lesson) { 
		Lesson lessonUpdated = lessonRepository.save(lesson);
		return lessonUpdated;
	}
	
	@Override
	public Lesson createLesson(String username, Lesson lesson) {
		Lesson createdLesson = lessonRepository.save(lesson);
		return createdLesson;
	}
}
