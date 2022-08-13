package com.whaty.platform.standard.aicc.operation;

import java.io.Serializable;

import com.whaty.platform.standard.aicc.Exception.AiccException;

/**
 * ����������Aicc�μ���LMS���͵����
 * @author CHENJIAN
 *
 */
public class AiccURLParam implements Serializable {

	private String command = "";
	
	//sessionid��"userid---courseid---systemid---ϵͳ����4��sessionId"���
	private String sessionId = "";
	
	private String version = "";
	private String auPassword = "";
	private String aiccData = "";

	
	public AiccURLParam() {
		 
	}
	public AiccURLParam(String urlParam) {
		 try
		 {
			 parseString(urlParam);
		 }
		 catch(AiccException e)
		 {
			 
		 }
	}
	public AiccURLParam(String command,String session_id,String version,String au_password,String aicc_data) {
		 this.setCommand(command);
		 this.setSessionId(session_id);
		 this.setVersion(version);
		 this.setAuPassword(au_password);
		 this.setAiccData(aicc_data);
	}
	public String getAiccData() {
		return aiccData;
	}

	public void setAiccData(String aiccData) {
		this.aiccData = aiccData;
	}

	public String getAuPassword() {
		return auPassword;
	}

	public void setAuPassword(String auPassword) {
		this.auPassword = auPassword;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
    
	private void parseString(String urlParam) throws AiccException
	{
		String[] values=urlParam.split("&");
		for(int i=0;i<values.length;i++)
		{
			String[] params=values[i].split("=");
			if(params[0].equalsIgnoreCase("command")) this.setCommand(params[1]);
			if(params[0].equalsIgnoreCase("session_id")) this.setSessionId(params[1]);
			if(params[0].equalsIgnoreCase("version")) this.setVersion(params[1]);
			if(params[0].equalsIgnoreCase("au_password")) this.setAuPassword(params[1]);
			if(params[0].equalsIgnoreCase("aicc_data")) this.setAiccData(params[1]);
		}
	}
	
	public String toString()
	{
		String url="command="+this.getCommand()+"&session_id="+this.getSessionId()+"&version="+this.getVersion() +
				   "&au_password="+this.getAuPassword()+"&aicc_data="+this.getAiccData();
		return url;
	}
}
