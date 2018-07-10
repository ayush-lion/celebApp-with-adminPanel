package com.ctn.celebApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity(name="quizquestion")
public class QuizQuestion {

	@Id
	@GeneratedValue
	
	private Integer quizId;
	
	private String quizQuestion;
	
	private String optionA;
	
	private String optionB;
	
	private String optionC;
	
	private String optionD;
	
	private String rightAnswer;

	public Integer getQuizId() {
		return quizId;
	}

	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}

	public String getQuizQuestion() {
		return quizQuestion;
	}

	public void setQuizQuestion(String quizQuestion) {
		this.quizQuestion = quizQuestion;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
}
