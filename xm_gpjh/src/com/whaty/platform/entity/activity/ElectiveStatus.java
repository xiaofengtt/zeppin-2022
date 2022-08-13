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
			return "��ѡ��";
		else if(type!=null && type.equals(UNDERCHECK))
			return "ѡ��ȷ����";
		else if(type!=null && type.equals(UNSELECTED))
			return "δѡ��";
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
