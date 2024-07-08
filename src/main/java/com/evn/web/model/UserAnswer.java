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
@Table(name = "USER_ANSWER")
public class UserAnswer extends BaseModel implements UserOwned {


	@ManyToOne
	@JsonIgnore
	private Quiz quiz;



	@JsonIgnore
	@OneToOne
	private Question question;
	@JsonIgnore
	@OneToOne
	private Answer answer;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@Column(name = "additional_answer")
	private String additionalAnswer;

	public Calendar getCreatedDate() {
		return createdDate;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	public Answer getAnswer() {
		return answer;
	}


	public void setAnswer(Answer answer) {
		this.answer = answer;
	}


	public String getAdditionalAnswer() {
		return additionalAnswer;
	}


	public void setAdditionalAnswer(String additionalAnswer) {
		this.additionalAnswer = additionalAnswer;
	}


	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}


	@Override
	@JsonIgnore
	public User getUser() {
		return quiz.getUser();
	}

}
