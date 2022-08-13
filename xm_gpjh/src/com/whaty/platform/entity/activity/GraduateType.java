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
public class GraduateType {
	public static String BENKE="BENKE";
	public static String ZHUANKE="ZHUANKE";
	public static String BENKEYIYE="BENKEYIYE";
	public static String ZHUANKEYIYE="ZHUANKEYIYE";
	public static String YANJIUSHENG="YANJIUSHENG";
	public static String YANJIUSHENGYIYE="YANJIUSHENGYIYE";
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(BENKE))
			return "���Ʊ�ҵ";
		else if(type!=null && type.equals(ZHUANKE))
			return "ר�Ʊ�ҵ";
		else if(type!=null && type.equals(BENKEYIYE))
			return "������ҵ";
		else if(type!=null && type.equals(ZHUANKEYIYE))
			return "ר����ҵ";
		else if(type!=null && type.equals(YANJIUSHENG))
			return "�о�����ҵ";
		else if(type!=null && type.equals(YANJIUSHENGYIYE))
			return "�о�����ҵ";
		else
			throw new TypeErrorException("Cashout Type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(BENKE);
		list.add(ZHUANKE);
		list.add(BENKEYIYE);
		list.add(ZHUANKEYIYE);
		list.add(YANJIUSHENG);
		list.add(YANJIUSHENGYIYE);
		return list;
	}
}
