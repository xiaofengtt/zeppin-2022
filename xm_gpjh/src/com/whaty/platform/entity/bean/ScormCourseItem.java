package com.whaty.platform.entity.bean;

/**
 * ScormCourseItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ScormCourseItem extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private ScormCourseInfo scormCourseInfo;
	private String type;
	private String title;
	private String launch;
	private String parameterstring;
	private String datafromlms;
	private String prerequisites;
	private String masteryscore;
	private String maxtimeallowed;
	private String timelimitaction;
	private Long sequence;
	private Long thelevel;
	private String itemId;

	// Constructors

	/** default constructor */
	public ScormCourseItem() {
	}

	/** full constructor */
	public ScormCourseItem(ScormCourseInfo scormCourseInfo, String type,
			String title, String launch, String parameterstring,
			String datafromlms, String prerequisites, String masteryscore,
			String maxtimeallowed, String timelimitaction, Long sequence,
			Long thelevel, String itemId) {
		this.scormCourseInfo = scormCourseInfo;
		this.type = type;
		this.title = title;
		this.launch = launch;
		this.parameterstring = parameterstring;
		this.datafromlms = datafromlms;
		this.prerequisites = prerequisites;
		this.masteryscore = masteryscore;
		this.maxtimeallowed = maxtimeallowed;
		this.timelimitaction = timelimitaction;
		this.sequence = sequence;
		this.thelevel = thelevel;
		this.itemId = itemId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ScormCourseInfo getScormCourseInfo() {
		return this.scormCourseInfo;
	}

	public void setScormCourseInfo(ScormCourseInfo scormCourseInfo) {
		this.scormCourseInfo = scormCourseInfo;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLaunch() {
		return this.launch;
	}

	public void setLaunch(String launch) {
		this.launch = launch;
	}

	public String getParameterstring() {
		return this.parameterstring;
	}

	public void setParameterstring(String parameterstring) {
		this.parameterstring = parameterstring;
	}

	public String getDatafromlms() {
		return this.datafromlms;
	}

	public void setDatafromlms(String datafromlms) {
		this.datafromlms = datafromlms;
	}

	public String getPrerequisites() {
		return this.prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getMasteryscore() {
		return this.masteryscore;
	}

	public void setMasteryscore(String masteryscore) {
		this.masteryscore = masteryscore;
	}

	public String getMaxtimeallowed() {
		return this.maxtimeallowed;
	}

	public void setMaxtimeallowed(String maxtimeallowed) {
		this.maxtimeallowed = maxtimeallowed;
	}

	public String getTimelimitaction() {
		return this.timelimitaction;
	}

	public void setTimelimitaction(String timelimitaction) {
		this.timelimitaction = timelimitaction;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Long getThelevel() {
		return this.thelevel;
	}

	public void setThelevel(Long thelevel) {
		this.thelevel = thelevel;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}