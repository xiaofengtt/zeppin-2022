package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

/**
 * 教师基本信息大数据统计
 * 
 * @author geng
 *
 */
public interface ICountTeachterDao {
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

	// //////////////////////////////////////////////////

	/**
	 * 按照在岗是双语教师统计
	 */
	public List<Object[]> countByMutiLanguage(String scode, String currentYear);

	/**
	 * 按照在岗教师单语主要教学语言统计
	 */
	public List<Object[]> countByChinese(String scode, String currentYear);

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
	 * * 教师所在地区(下属单位)
	 * 
	 * @param id
	 *            选择地区id
	 * @param currentYear
	 *            只筛选当前年的数据
	 * @return
	 */
	public List<Object[]> countByTeacherAddress(String scode, String currentYear);

	// /////////////////////////////////////////////

	/**
	 * 去年在岗总教师人数
	 */
	public List<Object[]> countByLastYear(String scode, int year);

	/**
	 * 今年在岗总教师人数
	 */
	public List<Object[]> countByCurrentYear(String scode, int year);

}
