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
			return "����";
		else if(type!=null && type.equals(SUBJECT))
			return "ר��";
		else if(type!=null && type.equals(MASTER))
			return "˶ʿ";
		else if(type!=null && type.equals(DOCTOR))
			return "��ʿ";
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
