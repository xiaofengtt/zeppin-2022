package com.whaty.platform.standard.aicc.model;

public class StudentDataTry {
	
	/*PutParam: CMI Optional, AU Optional*/
	private AiccScore tryScore;
	
	/*PutParam: CMI Optional, AU Optional*/
	private String tryStatus;
	
	/*PutParam: CMI Optional, AU Optional*/
	private String tryTime;

	public AiccScore getTryScore() {
		return tryScore;
	}

	public void setTryScore(AiccScore tryScore) {
		this.tryScore = tryScore;
	}

	public String getTryStatus() {
		return tryStatus;
	}

	public void setTryStatus(String tryStatus) {
		this.tryStatus = tryStatus;
	}

	public String getTryTime() {
		return tryTime;
	}

	public void setTryTime(String tryTime) {
		this.tryTime = tryTime;
	}

}
