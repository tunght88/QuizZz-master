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
@Table(name = "council")
public class Council extends BaseModel implements UserOwned {

	@Size(min = 1, max = 50, message = "The answer should be less than 50 characters")
	@NotNull(message = "No answer text provided.")
	private String text;


	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@Column(name = "secretary")
	private String secretary;
	@Column(name = "president")
	private String president;

	@Column(name = "start_date")
	private Calendar startDate;

	@Column(name = "end_date")
	private Calendar endDate;
	@Column(name = "decision_number")
	private String decisionNumber;
	@Column(name = "unit")
	private String unit;
	@Column(name = "founding_date")
	private Calendar foundingDate;
	@Column(name = "meeting_loc")
	private String meetingLoc;
	@Column(name = "meeting_type")
	private String meetingType;
	
	
	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getMeetingLoc() {
		return meetingLoc;
	}

	public void setMeetingLoc(String meetingLoc) {
		this.meetingLoc = meetingLoc;
	}

	public Calendar getStartDate() {
		if(startDate == null)
			startDate = Calendar.getInstance();
		return startDate;
	}

	public Calendar getFoundingDate() {
		if(foundingDate == null)
			foundingDate = Calendar.getInstance();
		return foundingDate;
	}

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String president) {
		this.president = president;
	}

	public void setFoundingDate(Calendar foundingDate) {
		this.foundingDate = foundingDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public User getUser() {
		return null;
	}

}
