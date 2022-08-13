/**
 * 
 */
package com.whaty.platform.config;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**���������˱�ƽ̨��ȫ������ϵͳ
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
			return "�����ѧϵͳ";
		else if(type!=null && type.equals(SSO))
			return "�����¼ϵͳ";
		else if(type!=null && type.equals(TRAINING))
			return "��ѵϵͳ";
		else if(type!=null && type.equals(RSS))
			return "����RSS�Ż�ϵͳ";
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
