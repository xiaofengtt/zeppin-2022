package com.whaty.platform.standard.aicc.file;

import java.util.Map;


public class AUData implements AiccFileModel{

    private String systemId;

    private String type;

    private String commandLine;

    
    private String maxTimeAllowed;

    private String timeLimitAction;

    private String fileName;

    private String maxScore;

    private String masteryScore;

    private String systemVendor;

    private String coreVendor;

    private String webLaunch;

    private String auPassword;
    
    public AUData()
    {
    	
    }
    
    public AUData(Map map) {
        this.systemId = (String)map.get("system_id");
        this.type = (String)map.get("type");
        this.commandLine = (String)map.get("command_line");
        this.maxTimeAllowed = (String)map.get("max_time_allowed");
        this.fileName = (String)map.get("file_name");
        this.maxScore = (String)map.get("max_score");
        this.masteryScore = (String)map.get("mastery_score");
        this.systemVendor = (String)map.get("system_vendor");
        this.coreVendor = (String)map.get("core_vendor");
        this.webLaunch = (String)map.get("web_launch");
        this.auPassword = (String)map.get("au_password");
    }
    
    

	public String getAuPassword() {
		return auPassword;
	}

	public void setAuPassword(String auPassword) {
		this.auPassword = auPassword;
	}

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	public String getCoreVendor() {
		return coreVendor;
	}

	public void setCoreVendor(String coreVendor) {
		this.coreVendor = coreVendor;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMasteryScore() {
		return masteryScore;
	}

	public void setMasteryScore(String masteryScore) {
		this.masteryScore = masteryScore;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public String getMaxTimeAllowed() {
		return maxTimeAllowed;
	}

	public void setMaxTimeAllowed(String maxTimeAllowed) {
		this.maxTimeAllowed = maxTimeAllowed;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemVendor() {
		return systemVendor;
	}

	public void setSystemVendor(String systemVendor) {
		this.systemVendor = systemVendor;
	}

	public String getTimeLimitAction() {
		return timeLimitAction;
	}

	public void setTimeLimitAction(String timeLimitAction) {
		this.timeLimitAction = timeLimitAction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWebLaunch() {
		return webLaunch;
	}

	public void setWebLaunch(String webLaunch) {
		this.webLaunch = webLaunch;
	}

	public String toStrData() {
		/*
		 "system_id", "type", "command_line", "Max_Time_Allowed", "time_limit_action", "file_name",
		 "max_score", "mastery_score", "system_vendor", "core_vendor", "web_launch", "AU_password"
		 */
		StringBuffer sb=new StringBuffer();
		sb.append("\"");
		sb.append(this.getSystemId());
		sb.append("\",\"");
		sb.append(this.getType());
		sb.append("\",\"");
		sb.append(this.getCommandLine());
		sb.append("\",\"");
		sb.append(this.getMaxTimeAllowed());
		sb.append("\",\"");
		sb.append(this.getTimeLimitAction());
		sb.append("\",\"");
		sb.append(this.getMaxScore());
		sb.append("\",\"");
		sb.append(this.getCoreVendor());
		sb.append("\",\"");
		sb.append(this.getSystemVendor());
		sb.append("\",\"");
		sb.append(this.getFileName());
		sb.append("\",\"");
		sb.append(this.getMasteryScore());
		sb.append("\",\"");
		sb.append(this.getWebLaunch());
		sb.append("\",\"");
		sb.append(this.getAuPassword());
		sb.append("\"");
		return sb.toString();
	}
   
}