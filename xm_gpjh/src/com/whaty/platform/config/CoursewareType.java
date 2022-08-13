/**
 * 
 */
package com.whaty.platform.config;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public class CoursewareType {
	public static String AICC="AICC";
	public static String SCORM12="SCORM12";
	public static String SCORM13="SCORM13";
	public static String WHATYONLINE="WHATYONLINE";
	public static String WHATYBASIC="WHATYBASIC";
	public static String WHATYPRO="WHATYPRO";
	public static String NORMALHTTP="NORMALHTTP";
	public static String UPLOADHTTP="UPLOADHTTP";
	
	
	public static String typeShow(String type) throws PlatformException
	{
		if(type!=null && type.equals(AICC))
			return "AICC�μ�";
		else if(type!=null && type.equals(SCORM12))
			return "SCORM1.2�μ�";
		else if(type!=null && type.equals(SCORM13))
			return "SCORM1.3�μ�";
		else if(type!=null && type.equals(WHATYONLINE))
			return "���ݹ�˾���������Ŀμ�";
		else if(type!=null && type.equals(WHATYBASIC))
			return "���ݹ�˾������μ�";
		else if(type!=null && type.equals(WHATYPRO))
			return "���ݹ�˾רҵ��μ�";
		else if(type!=null && type.equals(NORMALHTTP))
			return "ֱ�����ӵ�HTTP�μ�";
		else if(type!=null && type.equals(UPLOADHTTP))
			return "�������ص�HTTP�μ�";
		else
			throw new PlatformException("TrainingCourseWareType error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(AICC);
		list.add(SCORM12);
		list.add(SCORM13);
		list.add(WHATYBASIC);
		list.add(WHATYPRO);
		list.add(NORMALHTTP);
		list.add(UPLOADHTTP);
		return list;
	}
}
