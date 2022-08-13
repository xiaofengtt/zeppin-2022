package com.whaty.platform.standard.aicc.model;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.util.AiccLog;

public class AiccData {

	private Core core;
	private StudentData studentData;
	private SuspendData suspendData;
	private Objectives objectives;
	private Evaluation evaluation;
	private LaunchData launchData;
	private Paths paths;
	private CommentsLMS commentsLMS;
	private CommentsLearner commentsLearner;
	private StudentDemographics stuDemo;
	private StudentPreferences stuPre;
	private Interactions interactions;

	public AiccData() {
		this.core=new Core();
		this.launchData=new LaunchData();
		this.suspendData=new SuspendData();
		this.objectives=new Objectives();
		this.studentData=new StudentData();
	}
	public AiccData(String aiccDataStr) throws AiccException
	{
		AiccLog.setDebug("parseAiccData_________");
		this.core=new Core();
		this.launchData=new LaunchData();
		this.suspendData=new SuspendData();
		this.objectives=new Objectives();
		this.studentData=new StudentData();
		parserAiccData(aiccDataStr);
	}
	public CommentsLMS getCommentsLMS() {
		return commentsLMS;
	}
	public void setCommentsLMS(CommentsLMS commentsLMS) {
		this.commentsLMS = commentsLMS;
	}
	public Core getCore() {
		return core;
	}
	public void setCore(Core core) {
		this.core = core;
	}
	public Evaluation getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	public Interactions getInteractions() {
		return interactions;
	}
	public void setInteractions(Interactions interactions) {
		this.interactions = interactions;
	}
	public LaunchData getLaunchData() {
		return launchData;
	}
	public void setLaunchData(LaunchData launchData) {
		this.launchData = launchData;
	}
	public Objectives getObjectives() {
		return objectives;
	}
	public void setObjectives(Objectives objectives) {
		this.objectives = objectives;
	}
	public Paths getPaths() {
		return paths;
	}
	public void setPaths(Paths paths) {
		this.paths = paths;
	}
	public StudentDemographics getStuDemo() {
		return stuDemo;
	}
	public void setStuDemo(StudentDemographics stuDemo) {
		this.stuDemo = stuDemo;
	}
	public StudentData getStudentData() {
		return studentData;
	}
	public void setStudentData(StudentData studentData) {
		this.studentData = studentData;
	}
	public StudentPreferences getStuPre() {
		return stuPre;
	}
	public void setStuPre(StudentPreferences stuPre) {
		this.stuPre = stuPre;
	}
	public SuspendData getSuspendData() {
		return suspendData;
	}
	public void setSuspendData(SuspendData suspendData) {
		this.suspendData = suspendData;
	}
	private void parserAiccData(String aiccDataStr) throws AiccException
	{
		aiccDataStr=aiccDataStr.replace("[","&");
		String[] groups=aiccDataStr.split("&");
		for(int i=1;i<groups.length;i++)
		{
			String groupstr=groups[i];
			String groups_key=groupstr.substring(0,groupstr.indexOf("]")).trim();
			String groups_content=groupstr.substring(groupstr.indexOf("]")+1,groupstr.length()).trim();
			if(groups_key.equalsIgnoreCase("core"))
			{
				this.setCore(new Core(groups_content)); 
			}
			else if(groups_key.equalsIgnoreCase("core_lesson"))
			{
				this.setSuspendData(new SuspendData(groups_content));
			}
			else if(groups_key.equalsIgnoreCase("core_vendor"))
			{
				this.setLaunchData(new LaunchData(groups_content));
			}
			else if(groups_key.equalsIgnoreCase("objectives_status"))
			{
				this.setObjectives(Objectives.parseObjectives(groups_content));
				
			}
		}
	}
	
	
}
