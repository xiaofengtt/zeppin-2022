/**
 * 
 */
package com.whaty.platform.entity.user;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author chenjian
 *
 */
public class StudyStatusType {

	public static String XIUXUE="XIUXUE";
	public static String ZAIJI="ZAIJI";
	public static String TUIXUE="TUIXUE";
	
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(XIUXUE))
			return "ÐÝÑ§";
		else if(type!=null && type.equals(ZAIJI))
			return "ÔÚ¼®";
		else if(type!=null && type.equals(TUIXUE))
			return "ÍËÑ§";
		else
			throw new TypeErrorException("Cashout Type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(XIUXUE);
		list.add(ZAIJI);
		list.add(TUIXUE);
		return list;
	}
}
