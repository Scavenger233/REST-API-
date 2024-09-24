package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	List<Lesson> findByUsername(String username);
}
