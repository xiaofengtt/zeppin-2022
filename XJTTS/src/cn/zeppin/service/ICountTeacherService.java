package cn.zeppin.service;

import java.awt.Stroke;
import java.util.List;

public interface ICountTeacherService {
	/**
	 * 按照在岗教师性别统计
	 */
	public List<Object[]> countBySex(String scode, String currentYear);

	/**
	 * 按照在岗教师年龄段统计
	 */
	public List countByAge(String scode, String currentYear);

	/**
	 * 按照在岗教师教龄段统计
	 */
	public List countByTeachingAge(String scode, String currentYear);

	/**
	 * 按照在岗教师的学校举办者类型统计(包括“学校类型”、“学校数”、“教师数”)
	 */
	public List<Object[]> countBySchoolType(String scode, String currentYear);

	/**
	 * 按照在岗教师职称统计
	 */
	public List<Object[]> countByJopTitle(String scode, String currentYear);

	/**
	 * 按照在岗教师政治面貌统计
	 */
	public List<Object[]> countByPolitice(String scode, String currentYear);

	/**
	 * 按照在岗双语教师统计
	 */
	public List<String> countByIsMutiLanguage(String scode, String currentYear);

	/**
	 * 按照在岗教师主要教学语言统计
	 */
	public List<Object[]> countByTeachingLanguage(String scode, String currentYear);

	/**
	 * 按照在岗教师主要教学学段
	 */
	public List<Object[]> countByTeachingGrade(String scode, String currentYear);

	/**
	 * 按照在岗教师主要教学学科
	 */
	public List<Object[]> countByTeachingSubject(String scode, String currentYear);

	// /////////////////////所在地区统计/////////////////////

	/**
	 * 在岗教师所属地区类型(下属单位)
	 */
	public List<Object[]> countByAttribute(String scode, String currentYear);

	/**
	 * * 教师所在地区
	 * @return
	 */
	public List<Object[]> countByTeacherAddress(String scode, String currentYear);

	// ///////////////////////////////////////////////////
	/**
	 * 在岗总教师人数
	 */
	public List<Object[]> countByLastYear(String scode, int year);
	

	/**
	 * 今年在岗总教师人数
	 */
	public List<Object[]> countByCurrentYear(String scode, int year);

}
