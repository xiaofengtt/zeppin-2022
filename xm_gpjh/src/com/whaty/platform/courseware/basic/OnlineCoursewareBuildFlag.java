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
			return "δ����";
		else if(type!=null && type.equals(NOTCOMPLETE))
			return "δ��ȫ����";
		else if(type!=null && type.equals(COMPLETED))
			return "ȫ������";
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
