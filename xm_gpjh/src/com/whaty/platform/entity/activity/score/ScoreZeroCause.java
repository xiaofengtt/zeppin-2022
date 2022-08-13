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
public class ScoreZeroCause {

	public static String QUEKAO="QUEKAO";
	public static String HUANKAO="HUANKAO";
	public static String ZUOBI="ZUOBI";
	public static String NORMAL="NORMAL";
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(QUEKAO))
			return "缺考";
		else if(type!=null && type.equals(HUANKAO))
			return "缓考";
		else if(type!=null && type.equals(ZUOBI))
			return "作弊";
		else if(type!=null && type.equals(NORMAL))
			return "正常成绩";
		else
			throw new TypeErrorException("Cashout Type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(QUEKAO);
		list.add(HUANKAO);
		list.add(ZUOBI);
		list.add(NORMAL);
		return list;
	}
}
