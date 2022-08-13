package com.whaty.platform.standard.aicc.operation;


import com.whaty.platform.standard.aicc.ResponseData;
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.model.AiccData;
import com.whaty.platform.standard.aicc.util.AiccLog;

public abstract class AiccDataManage {
	private AiccURLParam aiccURL;


	public AiccURLParam getAiccURLParam() {
		return aiccURL;
	}

	public void setAiccURLParam(AiccURLParam aiccURL) {
		this.aiccURL = aiccURL;
	}
	
	public ResponseData respToReq() throws AiccException
	{
		AiccLog.setDebug("begin respToReq!!!!");
		ResponseData responseData=null;
		String command=this.aiccURL.getCommand();
		AiccLog.setDebug("command="+command);
		
		String session_id=this.aiccURL.getSessionId();
		AiccLog.setDebug("session_id="+session_id);
		String[] values=session_id.split("---");
		String user_id=values[0];
		AiccLog.setDebug("user_id="+user_id);
		String course_id=values[1];
		AiccLog.setDebug("course_id="+course_id);
		String ausytem_id=values[2];
		AiccLog.setDebug("ausytem_id="+ausytem_id);
		if(command.equalsIgnoreCase("GetParam"))
		{
			AiccLog.setDebug("GetParam begin!!!!");
			AiccData aiccData=getValueFromDB(user_id,course_id,ausytem_id,session_id);
			responseData=new ResponseData();
			responseData.setError(0);
			responseData.setCore(aiccData.getCore());
			responseData.setLaunchData(aiccData.getLaunchData());
			responseData.setSuspendData(aiccData.getSuspendData());
			responseData.setObjectives(aiccData.getObjectives());
			AiccLog.setDebug("after getparam responseData="+responseData.toString());
			return responseData;
			
		}
		else if(command.equalsIgnoreCase("PutParam"))
		{
			AiccLog.setDebug("PutParam begin!!!!");
			AiccData aiccData=new AiccData(this.getAiccURLParam().getAiccData());
			AiccLog.setDebug("prepare to putValue to DB!!!!!");
			putValueToDB(aiccData,user_id,course_id,ausytem_id,session_id);
			responseData=new ResponseData();
			responseData.setError(0);
			return responseData;
		}
		else if(command.equalsIgnoreCase("ExitAU"))
		{
			AiccLog.setDebug("ExitAU begin!!!!");
			exitAU(user_id,course_id,ausytem_id,session_id);
			responseData=new ResponseData();
			responseData.setError(0);
			return responseData;
		}
		else
		{
			responseData=new ResponseData();
			responseData.setError(1);
			return responseData;
		}

	}
	
	public abstract void putValueToDB(AiccData aiccData,String user_id,String course_id,String ausystem_id,String session_id) throws AiccException;
	public abstract AiccData getValueFromDB(String user_id,String course_id,String ausystem_id,String session_id) throws AiccException;
	public abstract void exitAU(String user_id,String course_id,String ausystem_id,String session_id) throws AiccException;
	public abstract void updatePlatformDB(String user_id,String course_id,Float completedPercent) throws AiccException;
}
