/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author chenjian
 *
 */
public class DegreeType {

	public static String BACHELOR="BACHELOR";
	public static String SUBJECT="SUBJECT";
	public static String MASTER="MASTER";
	public static String DOCTOR="DOCTOR";
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(BACHELOR))
			return "本科";
		else if(type!=null && type.equals(SUBJECT))
			return "专科";
		else if(type!=null && type.equals(MASTER))
			return "硕士";
		else if(type!=null && type.equals(DOCTOR))
			return "博士";
		else
			throw new TypeErrorException("DegreeType error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(BACHELOR);
		list.add(SUBJECT);
		list.add(MASTER);
		list.add(DOCTOR);
		return list;
	}
}
