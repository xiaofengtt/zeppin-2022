/**
 * 
 */
package com.whaty.platform.entity.activity.score;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author chenjian
 *
 */
public class ElectiveScoreType {
	
	public static String USUAL="NORMAL";
	public static String EXAM="EXAM";
	public static String TOTAL="TOTAL";
	public static String EXPEND="EXPEND";
	public static String RENEW="RENEW";
	public static String HOMEWORK="HOMEWORK";
	public static String SELFTEST="SELFTEST";
	public static String EXPERIMENT="EXPERIMENT";
	public static String TEST="TEST";
	public static String TOTAL_EXPEND_SCORE="TOTAL_EXPEND_SCORE";
	
		public static String typeShow(String type) throws TypeErrorException
	{
		if(type!=null && type.equals(USUAL))
			return "ƽʱ�ɼ�";
		else if(type!=null && type.equals(EXAM))
			return "���Գɼ�";
		else if(type!=null && type.equals(TOTAL))
			return "�����ɼ�";
		else if(type!=null && type.equals(EXPEND))
			return "�����ɼ�";
		else if(type!=null && type.equals(RENEW))
			return "���޳ɼ�";
		else if(type!=null && type.equals(HOMEWORK))
			return "��ҵ�ɼ�";
		else if(type!=null && type.equals(SELFTEST))
			return "�Բ�ɼ�";
		else if(type!=null && type.equals(EXPERIMENT))
			return "����ɼ�";
		else if(type!=null && type.equals(TOTAL_EXPEND_SCORE))
			return "���������ɼ�";
		else
			throw new TypeErrorException("Cashout Type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(USUAL);
		list.add(EXAM);
		list.add(TOTAL);
		list.add(EXPEND);
		list.add(HOMEWORK);
		list.add(SELFTEST);
		list.add(EXPERIMENT);
		list.add(TOTAL_EXPEND_SCORE);
		return list;
	}
	

}
