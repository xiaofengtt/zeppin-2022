/**
 * 
 */
package com.whaty.platform.entity.activity.score;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;


/**
 * @author Administrator
 *
 */
public class ScoreLevelType {

	public static String ADVANCE="ADVANCE";
	public static String GOOD="GOOD";
	public static String NORMAL="NORMAL";
	public static String PASSED="PASSED";
	public static String NOTPASSED="NOTPASSED";
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(ADVANCE))
			return "优秀";
		else if(type!=null && type.equals(GOOD))
			return "良好";
		else if(type!=null && type.equals(NORMAL))
			return "普通";
		else if(type!=null && type.equals(PASSED))
			return "及格";
		else if(type!=null && type.equals(NOTPASSED))
			return "不及格";
		else
			throw new TypeErrorException("Cashout Type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(ADVANCE);
		list.add(GOOD);
		list.add(NORMAL);
		list.add(PASSED);
		list.add(NOTPASSED);
		return list;
	}
	
	public static List  simpleTypes()
	{
		ArrayList list=new ArrayList();
		list.add(PASSED);
		list.add(NOTPASSED);
		return list;
	}
	
}
