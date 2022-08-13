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
public class TrainingCoursewareViewType {

	public static String POPWIN="POPWIN";
	
	public static String INWIN="INWIN";
	
	
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(POPWIN))
			return "弹出式";
		else if(type!=null && type.equals(INWIN))
			return "嵌入式";
		else
			throw new PlatformException("TrainingCoursewareViewType error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(POPWIN);
		list.add(INWIN);
		return list;
	}
}
