package com.whaty.platform.standard.scorm.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;

public class ScormControlType {
	public static String FLOW="flow";
	public static String CHOICE="choice";
	public static String typeShow(String type) throws ScormException
	{
		if(type!=null && type.equals(FLOW))
			return "flow";
		else if(type!=null && type.equals(CHOICE))
			return "choice";
		else
			throw new ScormException("scorm Status error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(FLOW);
		list.add(CHOICE);
		return list;
	}
	
}
