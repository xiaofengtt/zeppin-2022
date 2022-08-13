
package com.whaty.platform.standard.aicc.model;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.util.AiccLog;

public class Core implements DataModel {

	/*
	GetParam(response) : CMI Mandatory
	PutComments: Optional
	PutInteractions: Optional
	PutObjectives Status: Optional
	PutPath: Optional
	*/
	private String studentId;  
	
	/*GetParam(response) : Mandatory*/
    private String studentName;

    /*
    GetParam(Response) : CMI Mandatory
    PutParam : CMI Mandatory
    */
    private String lessonLocation;

   /* GetParam(response) : CMI Mandatory
    PutParam : AU Mandatory
    passed,completed,failed,incomplete,browsed,not attempted
    *
    */
    private String lessonStatus;
    
    /*PutParam : CMI Mandatory*/
    private String exit;
    
    /*GetParam (response): CMI Mandatory*/
    private String credit;  
    
    /*GetParam (response) : CMI Mandatory*/
    private String entry;
    
    /*GetParam, PutParam:
	Core.Score.Raw: CMI Mandatory, AU Mandatory
	Core.Score.Max: If Core.Score.Min exists, then CMI Mandatory and AU Mandatory, otherwise optional.
	Core.Score.Min: Optional (CMI and AU)*/
    private AiccScore score;

    /*PutParam : AU Mandatory
    PutParam : CMI Mandatory*/
    private String sessionTime;

    /*GetParam (response) : CMI optional
    GetParam (response) : AU optional*/
    private String lessonMode;
    
    /*GetParam (response) : CMI Mandatory*/
    private String totalTime;

    
    
    public Core() {
		
	}
    public Core(String str) throws AiccException {
    	AiccLog.setDebug("parseCore="+str);
    	parseCore(str);
	}

	
    public String getCredit() {
        if(credit == null){
           return "";     
        }
        return credit.trim();
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLessonLocation() {
        if(lessonLocation == null){
            return "";    
        }
        return lessonLocation.trim();
    }

    public void setLessonLocation(String lessonLocation) {
        this.lessonLocation = lessonLocation;
    }
    
    public String getLessonStatus() {
        if(lessonStatus == null){
            return "";    
        }
        return lessonStatus.trim();
    }
    
    public void setLessonStatus(String lessonStatus) {
    	this.lessonStatus =AiccStatus.standard(lessonStatus);
    }
    
    public String getLessonMode() {
        if(lessonMode == null){
            return "";    
        }
        return lessonMode.trim();
    }

    public void setLessonMode(String lessonMode) {
        this.lessonMode = lessonMode;
    }

    public AiccScore getScore() {
    	if(score == null){
            score=new AiccScore();    
        }
    	return score;
    }

    public void setScore(AiccScore score) {
    	if(score == null){
            this.score=new AiccScore();    
        }
    	this.score = score;
    }

    public String getStudentId() {
        if(studentId == null){
            return "";    
        }
        return studentId.trim();
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        if(studentName == null){
           return "";    
        }
        return studentName.trim();
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String toStrData() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Core]").append("\r\n");
       
            sb.append("Student_ID=").append(getStudentId()).append("\r\n");
            sb.append("Student_Name=").append(getStudentName()).append("\r\n");
            sb.append("Lesson_Location=").append(getLessonLocation()).append("\r\n");
            sb.append("Credit=").append(getCredit()).append("\r\n");
            sb.append("Lesson_Status=").append(getLessonStatus()).append("\r\n");
            sb.append("Score=").append(getScore().toStrData()).append("\r\n");
            sb.append("Time=").append(getTotalTime()).append("\r\n");
            sb.append("Lesson_Mode=").append(this.getLessonMode()).append("\r\n"); 
       
        return sb.toString();
    }

	public String getTotalTime() {
		if(totalTime!=null)
			return totalTime;
		else
			return "";
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public String getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}
	
	private void parseCore(String str) throws AiccException
	{
		String[] contents = str.split("\r\n");
        for(int i = 0 ; i<contents.length;i++){
            String currLine = contents[i];
            String[] keyValue = currLine.split("=");
            String key = keyValue[0];
            String value = "";
            if(keyValue.length > 1){
            	value = keyValue[1];
            }
            if(key.equalsIgnoreCase("lesson_status")){
                this.setLessonStatus(value);    
            }else if(key.equalsIgnoreCase("lesson_location")){
                this.setLessonLocation(value);    
            }else if(key.equalsIgnoreCase("score")){
                this.setScore(new AiccScore(value));
            }else if(key.equalsIgnoreCase("time")){
                this.setSessionTime(value);
            }
        }
        
	}
}