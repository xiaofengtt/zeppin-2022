package com.whaty.platform.standard.scorm.offline;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class OfflineComments{
	
	private String lastStudyTime;
	private List<InnerTest> quiz;
	
	public OfflineComments(){
		this.lastStudyTime="";
		this.quiz = new ArrayList<InnerTest>();
	}
	
	public String getLastStudyTime() {
		return lastStudyTime;
	}
	public void setLastStudyTime(String lastStudyTime) {
		this.lastStudyTime = lastStudyTime;
	}
	public List<InnerTest> getQuiz() {
		return quiz;
	}
	public void setQuiz(List<InnerTest> quiz) {
		this.quiz = quiz;
	}
	
	public static void main(String[] args){
		
		String str = "{\"comment\":{\"quiz\":[],\"lastStudyTime\":\"\"}}";
		System.out.println(JSONObject.fromObject(str));
	
	}
	
}

