package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Lesson {
	
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String description;
	
	public Lesson() {
		
	}
	
	public Lesson(long id, String username, String description) {
		super();
		this.id = id;
		this.username = username;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
