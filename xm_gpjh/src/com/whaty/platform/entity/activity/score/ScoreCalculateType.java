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
public class ScoreCalculateType {

	public static String BILI="BILI";
	public static String KAIFANG="KAIFANG";
	
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(BILI))
			return "��������";
		else if(type!=null && type.equals(KAIFANG))
			return "��������";
		
		else
			throw new TypeErrorException("Cashout Type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(BILI);
		list.add(KAIFANG);
		;
		return list;
	}
	

	
}
