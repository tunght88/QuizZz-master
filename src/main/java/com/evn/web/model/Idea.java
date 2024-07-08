package com.evn.web.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "idea")
public class Idea extends BaseModel implements UserOwned {

	@Size(min = 1, max = 200, message = "The answer should be less than 200 characters")
	@NotNull(message = "No answer text provided.")
	private String text;


	@OneToOne
	@JsonIgnore
	private Assessment assessment;
	@OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Member> members;
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

	public List<Member> getMembers() {
		return members;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
	public User getUser() {
		return null;
	}

}
