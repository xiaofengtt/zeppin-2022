/**
 * 
 */
package com.whaty.platform.config;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**该类描述了本平台下全部的子系统
 * @author chenjian
 *
 */
public class SubSystemType {

	public static String SSO="SSO";
	public static String ENTITY="ENTITY";
	public static String TRAINING="TRAINING";
	public static String RSS="RSS";
	
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(ENTITY))
			return "教务教学系统";
		else if(type!=null && type.equals(SSO))
			return "单点登录系统";
		else if(type!=null && type.equals(TRAINING))
			return "培训系统";
		else if(type!=null && type.equals(RSS))
			return "个人RSS门户系统";
		else
			throw new PlatformException("entity user type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(SSO);
		list.add(ENTITY);
		list.add(TRAINING);
		list.add(RSS);
		return list;
	}
}
