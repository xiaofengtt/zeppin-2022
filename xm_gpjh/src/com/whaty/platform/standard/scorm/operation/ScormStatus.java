package com.whaty.platform.standard.scorm.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;

public class ScormStatus {
	public static String PASSED="passed";
	public static String COMPLETED="completed";
	public static String FAILED="failed";
	public static String INCOMPLETE="incomplete";
	public static String BROWSED="browsed";
	public static String NOTATTEMPTED="not attempted";
	public static String typeShow(String type) throws ScormException
	{
		if(type!=null && type.equals(PASSED))
			return "passed";
		else if(type!=null && type.equals(COMPLETED))
			return "completed";
		else if(type!=null && type.equals(FAILED))
			return "failed";
		else if(type!=null && type.equals(INCOMPLETE))
			return "incomplete";
		else if(type!=null && type.equals(BROWSED))
			return "browsed";
		else if(type!=null && type.equals(NOTATTEMPTED))
			return "not attempted";
		else
			throw new ScormException("Aicc Status error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(PASSED);
		list.add(COMPLETED);
		list.add(FAILED);
		list.add(INCOMPLETE);
		list.add(BROWSED);
		list.add(NOTATTEMPTED);
		return list;
	}
	
	public static String standard(String status){
		if(status!=null && status.trim().length()>0 && status.trim().substring(0,1).equalsIgnoreCase("p"))
    		return ScormStatus.PASSED;
    	else if(status!=null && status.trim().length()>0 && status.trim().substring(0,1).equalsIgnoreCase("f"))
    		return ScormStatus.FAILED;
    	else if(status!=null && status.trim().length()>0 && status.trim().substring(0,1).equalsIgnoreCase("c"))
    		return ScormStatus.COMPLETED;
    	else if(status!=null && status.trim().length()>0 && status.trim().substring(0,1).equalsIgnoreCase("i"))
    		return ScormStatus.INCOMPLETE;
    	else if(status!=null && status.trim().length()>0 && status.trim().substring(0,1).equalsIgnoreCase("b"))
    		return ScormStatus.BROWSED;
    	else
    		return ScormStatus.NOTATTEMPTED;
	}
}
