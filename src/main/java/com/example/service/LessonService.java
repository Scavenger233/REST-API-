package com.example.service;

import java.util.List;

import com.example.model.Lesson;

public interface LessonService {
	
	List<Lesson> getAllLessons(String username);
	
	Lesson getLesson(String username, long id);
	
	void deleteLesson(String username, long id);
	
	Lesson updateLesson(String username, long id, Lesson lesson);
	
	Lesson createLesson(String username, Lesson lesson);

}
