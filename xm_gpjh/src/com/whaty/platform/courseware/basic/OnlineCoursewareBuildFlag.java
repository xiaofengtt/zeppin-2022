package com.whaty.platform.courseware.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public class OnlineCoursewareBuildFlag {

	public static String NOTBULID="NOTBULID";
	public static String NOTCOMPLETE="NOTCOMPLETE";
	public static String COMPLETED="COMPLETED";
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(NOTBULID))
			return "未建立";
		else if(type!=null && type.equals(NOTCOMPLETE))
			return "未完全建好";
		else if(type!=null && type.equals(COMPLETED))
			return "全部建好";
		else
			throw new PlatformException("support database type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(NOTBULID);
		list.add(NOTCOMPLETE);
		list.add(COMPLETED);
		return list;
	}
}
