package com.evn.web.model.support;

import lombok.Builder;

@Builder
public class AssessmentView {
	private long id;
	private String name;
	private String description;
	private String ideaName;
	private String level;
	private String councilName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIdeaName() {
		return ideaName;
	}
	public void setIdeaName(String ideaName) {
		this.ideaName = ideaName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCouncilName() {
		return councilName;
	}
	public void setCouncilName(String councilName) {
		this.councilName = councilName;
	}
	
	
}
