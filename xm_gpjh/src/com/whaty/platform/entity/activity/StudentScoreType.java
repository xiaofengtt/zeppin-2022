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
public class StudentScoreType {
	public static String GRADUATE="GRADUATE";
	public static String PAPER="PAPER";
	public static String LEVEL4ENGLISH="LEVEL4ENGLISH";
	public static String LEVEL6ENGLISH="LEVEL6ENGLISH";
	public static String DEGREEENGLISH="DEGREEENGLISH";
	
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(PAPER))
			return "毕业论文（设计）成绩";
		else if(type!=null && type.equals(GRADUATE))
			return "毕业成绩";
		else if(type!=null && type.equals(LEVEL4ENGLISH))
			return "四级英语成绩";
		else if(type!=null && type.equals(LEVEL6ENGLISH))
			return "六级英语成绩";
		else if(type!=null && type.equals(DEGREEENGLISH))
			return "学位英语成绩";
		else
			throw new TypeErrorException("Cashout Type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(GRADUATE);
		list.add(PAPER);
		list.add(LEVEL4ENGLISH);
		list.add(LEVEL6ENGLISH);
		list.add(DEGREEENGLISH);
		return list;
	}
}
