package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Teacher;

public interface ITeacherDao extends IBaseDao<Teacher, Integer> {

    /**
     * @author Clark 2014.05.27
     * 
     * 通过搜索条件取得教师信息
     * @param paramsMap
     * @param offset
     * @param length
     * @return List<Teacher> 符合条件的教师信息列表
     */
	public List getTeachers(Map<String, Object> paramsMap, int offset, int length);

	/**
	 * @param paramsMap
	 * @return
	 */
	public int getTeacherCount(Map<String, Object> paramsMap);

	/**
	 * @param string
	 * @return
	 */
	public double getCount(String string);
	
	/**
	 * 通过邮箱查找教师
	 * @param email
	 * @return
	 */
	public List<Teacher> getTeacherByEmail(String email);
	
	/**
	 * 登录验证
	 */
	public List getTeacherForLogin(Object[] pars);

}
