package model;

import java.util.ArrayList;

public class Question {
	private String question;
	private ArrayList<String> answers;
	private String goodAnswer;

	public Question(String question) {
		super();
		answers = new ArrayList<String>();
		this.question = question;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer(int index) {
		return answers.get(index);
	}

	public boolean addAnswer(String e) {
		return answers.add(e);
	}
	
	public boolean addAnswer(String e, boolean isRight) {
		if (isRight)
			goodAnswer = e;
		return answers.add(e);
	}
	
	public String getRightAnswer() {
		return goodAnswer;
	}
}
