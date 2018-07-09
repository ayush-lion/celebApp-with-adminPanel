package com.ctn.celebApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity(name="fixures")
public class Fixures {
	
	@Id
	@GeneratedValue
	
	private Integer id;
	
	private String fixures;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFixures() {
		return fixures;
	}

	public void setFixures(String fixures) {
		this.fixures = fixures;
	}
}
