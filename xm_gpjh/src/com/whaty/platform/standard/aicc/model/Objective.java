package com.whaty.platform.standard.aicc.model;

import com.whaty.platform.standard.aicc.file.DESData;

public class Objective extends DESData{

	/*GetParam(response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional
	PutObjectives: CMI Optional, AU Optional*/
	private String objectiveId;
	
	/*GetParam(response) : CMI Optional, AU optional
	PutParam : CMI Optional, AU optional*/
	private String objectiveStatus;
	
	/*GetParam(response): CMI Optional, AU Optional
	PutParam: CMI Mandatory, AU Optional
	PutObjectives: CMI Optional, AU Optional*/
	private AiccScore objectiveScore;
	
	/*PutObjectives: CMI Optional, AU optional*/
	private String objectiveDate;
	
	/*PutObjectives: CMI Optional, AU optional*/
	private String objectiveTime;
	
	/*PutObjectives: CMI Optional, AU optional*/
	private String objectiveMasteryTime;
	

	public String getObjectiveDate() {
		return objectiveDate;
	}

	public void setObjectiveDate(String objectiveDate) {
		this.objectiveDate = objectiveDate;
	}

	public String getObjectiveId() {
		return objectiveId;
	}

	public void setObjectiveId(String objectiveId) {
		this.objectiveId = objectiveId;
	}

	public String getObjectiveMasteryTime() {
		return objectiveMasteryTime;
	}

	public void setObjectiveMasteryTime(String objectiveMasteryTime) {
		this.objectiveMasteryTime = objectiveMasteryTime;
	}

	public AiccScore getObjectiveScore() {
		return objectiveScore;
	}

	public void setObjectiveScore(AiccScore objectiveScore) {
		this.objectiveScore = objectiveScore;
	}

	public String getObjectiveStatus() {
		return objectiveStatus;
	}

	public void setObjectiveStatus(String objectiveStatus) {
		this.objectiveStatus = objectiveStatus;
	}

	public String getObjectiveTime() {
		return objectiveTime;
	}

	public void setObjectiveTime(String objectiveTime) {
		this.objectiveTime = objectiveTime;
	}
	
}
