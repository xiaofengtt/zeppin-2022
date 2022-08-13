package com.whaty.platform.standard.aicc.model;

import java.util.List;
import java.util.Map;

public class StudentData implements DataModel{

	public static final String LESSON_STATUS_PREFIX = "Lesson_Status.";
	
	public static final String SCORE_PREFIX = "Score.";
	
	/*GetParam (response) : CMI Optional, AU Optional*/
	private String attempNumber="0";
	
	/*PutParam: CMI Optional, AU Optional*/
	private List Tries;
	
	/*GetParam (response): CMI Optional, AU Optional*/
	private String masteryScore;
	
	/*GetParam (response): CMI Optional, AU Optional*/
	private String maxTimeAllowed;
	
	/*GetParam (response): CMI Optional, AU Optional*/
	private String timeLimitAction;
	
	/*PutParam: CMI Optional, AU Optional*/
	private int triesDuringLesson;
	
	/*GetParam (response): CMI Optional, AU Optional*/
	private Map lessonStatusMap;
	
	/*GetParam (response): CMI Optional, AU Optional*/
	private Map scoreMap;

	public String getAttempNumber() {
		return attempNumber;
	}

	public void setAttempNumber(String attempNumber) {
		this.attempNumber = attempNumber;
	}

	public Map getLessonStatusMap() {
		return lessonStatusMap;
	}

	public void setLessonStatusMap(Map lessonStatusMap) {
		this.lessonStatusMap = lessonStatusMap;
	}

	public String getMasteryScore() {
		return masteryScore;
	}

	public void setMasteryScore(String masteryScore) {
		this.masteryScore = masteryScore;
	}

	public String getMaxTimeAllowed() {
		return maxTimeAllowed;
	}

	public void setMaxTimeAllowed(String maxTimeAllowed) {
		this.maxTimeAllowed = maxTimeAllowed;
	}

	public Map getScoreMap() {
		return scoreMap;
	}

	public void setScoreMap(Map scoreMap) {
		this.scoreMap = scoreMap;
	}

	public String getTimeLimitAction() {
		return timeLimitAction;
	}

	public void setTimeLimitAction(String timeLimitAction) {
		this.timeLimitAction = timeLimitAction;
	}
	
	public String toStrData(){
		//to implement
		return null;
	}

	public List getTries() {
		return Tries;
	}

	public void setTries(List tries) {
		Tries = tries;
	}

	public int getTriesDuringLesson() {
		return triesDuringLesson;
	}

	public void setTriesDuringLesson(int triesDuringLesson) {
		this.triesDuringLesson = triesDuringLesson;
	}
}
