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
			return "Ŀ��";
		else if(type!=null && type.equals(OBTAINED))
			return "�ѻ��";
		else if(type!=null && type.equals(UNOBTAINED))
			return "δ���";
		else if(type!=null && type.equals(APPLYED))
			return "������";
		else if(type!=null && type.equals(CHECKAPPLY))
			return "���������";
		else if(type!=null && type.equals(UNAPPLYED))
			return "δ����";
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
