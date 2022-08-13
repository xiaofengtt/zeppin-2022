/**
 * 
 */
package com.whaty.platform.config;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;


/**描述系统可能使用的数据库
 * @author chenjian
 *
 */
public class DatabaseType {

	public static String ORACLE9="ORACLE9";
	public static String ORACLE8="ORACLE8";
	public static String MYSQL5="MYSQL5";
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(ORACLE9))
			return "Oracle 9 数据库";
		else if(type!=null && type.equals(ORACLE8))
			return "Oracle 8 数据库";
		else if(type!=null && type.equals(MYSQL5))
			return "MYSQL 5.0.x数据库";
		else
			throw new PlatformException("support database type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(ORACLE9);
		list.add(ORACLE8);
		list.add(MYSQL5);
		return list;
	}
	
}
