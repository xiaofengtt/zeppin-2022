/**
 * 
 */
package com.whaty.platform.training.user;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public class TrainingUserType{
	
	public static String STUDENT="STUDENT";
	public static String CLASSMANAGER="CLASSMANAGER";
	public static String MANAGER="MANAGER";
	public static String SUPERADMIN="SUPERADMIN";
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(STUDENT))
			return "学生";
		else if(type!=null && type.equals(CLASSMANAGER))
			return "训练班主管";
		else if(type!=null && type.equals(MANAGER))
			return "管理员";
		else if(type!=null && type.equals(SUPERADMIN))
			return "超级管理员";
		else
			throw new PlatformException("entity user type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(STUDENT);
		list.add(CLASSMANAGER);
		list.add(MANAGER);
		list.add(SUPERADMIN);
		return list;
	}

}
