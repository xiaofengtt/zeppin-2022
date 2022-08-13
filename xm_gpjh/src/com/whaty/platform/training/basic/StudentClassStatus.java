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
public class StudentClassStatus {
	
	public static String UNSELECTED="UNSELECTED";
	
	public static String SELECTED="SELECTED";
	
	public static String UNDERCHECK="UNDERCHECK";
	
	public static String GRADUATED="GRADUATED";
	
	
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(UNSELECTED))
			return "未加入";
		else if(type!=null && type.equals(SELECTED))
			return "已加入";
		else if(type!=null && type.equals(UNDERCHECK))
			return "等待审核";
		else if(type!=null && type.equals(GRADUATED))
			return "已结业";
		else
			throw new PlatformException("StudentCourseStatus error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(UNSELECTED);
		list.add(SELECTED);
		list.add(UNDERCHECK);
		list.add(GRADUATED);
		return list;
	}
}
