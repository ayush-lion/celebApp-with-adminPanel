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
	
	private String timestamp;
	
	private String stadium;
	
	private String year;
	
	private String format;
	
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}	
}
