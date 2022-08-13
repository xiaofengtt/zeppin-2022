/**
 * 
 */
package com.whaty.platform.logmanage;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author wq
 *
 */
public class LogType {
	public final static String MANAGER = "管理员";
	public final static String SITEMANAGER = "分站管理员";
	public final static String TEACHER = "教师";
	public final static String STUDENT = "学生";
	public final static String USER = "USER";
	
	public static List getTypeList() {
		ArrayList list = new ArrayList();
		list.add(MANAGER);
		list.add(SITEMANAGER);
		list.add(TEACHER);
		list.add(STUDENT);
//		list.add(USER);
		return list;
	}
	
	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(MANAGER))
			return "管理员";
		else if (type != null && type.equals(SITEMANAGER))
			return "分站管理员";
		else if (type != null && type.equals(TEACHER))
			return "教师";
		else if (type != null && type.equals(STUDENT))
			return "学生";
		else if (type != null && type.equals(USER))
			return "用户";
		else
			throw new TypeErrorException("LogType Type error!");
	}
}
