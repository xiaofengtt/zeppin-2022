/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public class ElectiveStatus {
	
	public static String SELECTED="SELECED";
	public static String UNDERCHECK="UNDERCHECK";
	public static String UNSELECTED="UNSELECTED";
	
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(SELECTED))
			return "已选课";
		else if(type!=null && type.equals(UNDERCHECK))
			return "选课确认中";
		else if(type!=null && type.equals(UNSELECTED))
			return "未选可";
		else
			throw new PlatformException("ElectiveStatus error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(SELECTED);
		list.add(UNDERCHECK);
		list.add(UNSELECTED);
		return list;
	}
	
	

}
