package jorge.rv.quizzz.model;

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

	@Size(min = 2, max =1000, message = "The question should be between 2 and 1000 characters")
	@NotNull(message = "Question text not provided")
	private String text;

	@ManyToOne
	@JsonIgnore
	private Quiz quiz;


	//@JsonIgnore
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Answer> answers;

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

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	@JsonIgnore
	public User getUser() {
		return quiz.getUser();
	}

}
