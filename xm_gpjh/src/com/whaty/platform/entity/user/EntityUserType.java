/**
 * 
 */
package com.whaty.platform.entity.user;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 * 
 */
public class EntityUserType {

	public static String STUDENT = "STUDENT";

	public static String TEACHER = "TEACHER";

	public static String MANAGER = "MANAGER";

	public static String SITETEACHER = "SITETEACHER";

	public static String SITEMANAGER = "SITEMANAGER";

	//public static String SUPERADMIN = "SUPERADMIN";

	public static String typeShow(String type) throws PlatformException {
		if (type != null && type.equals(STUDENT))
			return "学生";
		else if (type != null && type.equals(TEACHER))
			return "教师";
		else if (type != null && type.equals(MANAGER))
			return "管理员"; 
		else if (type != null && type.equals(SITETEACHER))
			return "学习中心教师";
		else if (type != null && type.equals(SITEMANAGER))
			return "学习中心管理员";
		//else if (type != null && type.equals(SUPERADMIN))
		//	return "超级管理员";
		else
			throw new PlatformException("entity user type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(STUDENT);
		list.add(TEACHER);
		list.add(MANAGER);
		list.add(SITETEACHER);
		list.add(SITEMANAGER);
		//list.add(SUPERADMIN);
		return list;
	}
}
