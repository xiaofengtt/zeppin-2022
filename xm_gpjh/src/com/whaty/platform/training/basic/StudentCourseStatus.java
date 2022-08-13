/**
 * 
 */
package com.whaty.platform.training.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public class StudentCourseStatus {
	
	public static String UNSELECTED="UNSELECTED";
	
	public static String SELECTED="SELECTED";
	
	public static String UNDERCHECK="UNDERCHECK";
	
	
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(UNSELECTED))
			return "δѡ��";
		else if(type!=null && type.equals(SELECTED))
			return "��ѡ��";
		else if(type!=null && type.equals(UNDERCHECK))
			return "�ȴ�ѡ�����";
		else
			throw new PlatformException("StudentCourseStatus error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(UNSELECTED);
		list.add(SELECTED);
		list.add(UNDERCHECK);
		return list;
	}
}
