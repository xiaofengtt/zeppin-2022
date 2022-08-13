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
			return "ѧ��";
		else if (type != null && type.equals(TEACHER))
			return "��ʦ";
		else if (type != null && type.equals(MANAGER))
			return "����Ա"; 
		else if (type != null && type.equals(SITETEACHER))
			return "ѧϰ���Ľ�ʦ";
		else if (type != null && type.equals(SITEMANAGER))
			return "ѧϰ���Ĺ���Ա";
		//else if (type != null && type.equals(SUPERADMIN))
		//	return "��������Ա";
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
