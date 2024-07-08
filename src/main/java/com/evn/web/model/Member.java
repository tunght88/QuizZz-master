package com.evn.web.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "member")
public class Member extends BaseModel implements UserOwned {

	@Size(min = 1, max = 50, message = "The answer should be less than 50 characters")
	@NotNull(message = "No answer text provided.")
	private String text;


	@ManyToOne
	@JsonIgnore
	private Idea idea;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Override
	public User getUser() {
		return null;
	}

}
