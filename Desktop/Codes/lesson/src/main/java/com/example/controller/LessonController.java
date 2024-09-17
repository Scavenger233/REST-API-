package com.example.controller;
import com.example.model.Lesson;
import com.example.service.LessonService;
import java.net.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class LessonController {
	
	@Autowired
	private LessonService lessonService;
	
	//url后面会加入GetMapping中的后缀链接
	@GetMapping("/instructors/{username}/lessons")
	public List<Lesson> getAllLessons(@PathVariable String username) {
		
		List<Lesson> lessons = lessonService.getAllLessons(username);
		
		return lessons;
	}
	
	@GetMapping("/instructors/{username}/lessons/{id}")
	public Lesson getLesson(@PathVariable String username, @PathVariable long id) {
		Lesson lesson = lessonService.getLesson(username, id);
		return lesson;
	}
	
	@DeleteMapping("/instructors/{username}/lessons/{id}")
	public ResponseEntity<Void> deleteLesson(@PathVariable String username, @PathVariable long id) {
		lessonService.deleteLesson(username, id);
		
		ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
		return responseEntity;
	}
	
	@PutMapping("/instructors/{username}/lessons/{id}")
	public ResponseEntity<Lesson> updateLesson(@PathVariable String username, @PathVariable long id,
			@RequestBody Lesson lesson) {
		lesson.setUsername(username);
		
		Lesson lessonUpdated = lessonService.updateLesson(username, id, lesson);
		
		ResponseEntity<Lesson> responseEntity = new ResponseEntity<Lesson>(lessonUpdated, HttpStatus.OK);
		
		return responseEntity;
	}
	
	@PostMapping("/instructors/{username}/lessons")
	public ResponseEntity<Void> createLesson(@PathVariable String username, @RequestBody Lesson lesson) {
		lesson.setUsername(username);
		
		Lesson createdLesson = lessonService.createLesson(username, lesson);
		
		if(createdLesson == null)
			return ResponseEntity.noContent().build();
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdLesson.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
}
