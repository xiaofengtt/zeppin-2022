/**
 * 
 */
package com.whaty.platform.training.skill;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public class StudentSkillStatus {
	public static String TARGET="TARGET";
	public static String OBTAINED="OBTAINED";
	public static String UNOBTAINED="UNOBTAINED";
	public static String APPLYED="APPLYED";
	public static String CHECKAPPLY="CHECKAPPLY";
	public static String UNAPPLYED="UNAPPLED";
	
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(TARGET))
			return "目标";
		else if(type!=null && type.equals(OBTAINED))
			return "已获得";
		else if(type!=null && type.equals(UNOBTAINED))
			return "未获得";
		else if(type!=null && type.equals(APPLYED))
			return "已申请";
		else if(type!=null && type.equals(CHECKAPPLY))
			return "审核申请中";
		else if(type!=null && type.equals(UNAPPLYED))
			return "未申请";
		else
			throw new PlatformException("TrainingCourseWareType error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(TARGET);
		list.add(OBTAINED);
		list.add(UNOBTAINED);
		list.add(APPLYED);
		list.add(UNAPPLYED);
		list.add(CHECKAPPLY);
		return list;
	}
}
