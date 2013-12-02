package controller;

import java.util.ArrayList;

import model.Question;

import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameCtrl extends InputListener {
	private ArrayList<Question> questions;
	private int currentQuestion;
	private boolean changeQuestion;
	
	public GameCtrl() {
		questions = new ArrayList<Question>();
		changeQuestion = true;
		
		Question q1 = new Question("Le panda est en voie ");
		q1.addAnswer("d'expulsion");
		q1.addAnswer("d'extinction", true);
		q1.addAnswer("d'exclusion");
		questions.add(q1);
		
		Question q2 = new Question("Le tapir est très ");
		q2.addAnswer("lait");
		q2.addAnswer("laid", true);
		q2.addAnswer("les");
		questions.add(q2);
		
		Question q3 = new Question("Les ordinateurs sont très ");
		q3.addAnswer("pratique", true);
		q3.addAnswer("pratiquant");
		q3.addAnswer("pathethique");
		questions.add(q3);
		
		currentQuestion = -1;
	}
	
	public Question getNextQuestion(){
		changeQuestion = false;
		return questions.get(++currentQuestion);
	}
	
	public Question getCurQuestion(){
		System.out.println(questions.get(currentQuestion).getQuestion());
		return questions.get(currentQuestion);
	}
	
	public boolean changeQuestion(){
		return changeQuestion;
	}
	
	public int checkAnswer(String answer){
		if (answer.equals(questions.get(currentQuestion).getRightAnswer())) {
			changeQuestion = true;
			return 1;
		} else {
			return 0;
		}
	}
}
