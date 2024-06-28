package jorge.rv.quizzz.model.support;

public class Response {
	private Long question;
	private Long selectedAnswer;
	private String additionalAnswer;

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	public Long getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(Long selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public String getAdditionalAnswer() {
		return additionalAnswer;
	}

	public void setAdditionalAnswer(String additionalAnswer) {
		this.additionalAnswer = additionalAnswer;
	}

}
