package cn.zeppin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Teacher;

public interface ITeacherService extends IBaseService<Teacher, Integer>
{
    public boolean existIdCard(String idCard);
    
    public Teacher getTeacherByIdCard(String idCard);

    public boolean existMobile(String mobile);

    public boolean existEmail(String email);

    public int getTotalPage(int pageLength);

    /**
     * @author Clark 2014.05.27
     * 
     *         通过搜索条件取得教师信息列表
     * @param paramsMap
     * @param offset
     * @param length
     * @return List<Teacher> 符合条件的教师信息列表
     */
    public List<Teacher> getTeachers(Map<String, Object> paramsMap, int offset,
	    int length);

    /**
     * @author Clark 2014.05.27
     * 
     *         通过搜索条件取得教师信息数量
     * @param paramsMap
     * @return int 符合条件的教师信息数量
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
     * @return
     */
    public List getTeacherForLogin(Object[] pars);
    
    /**
     * 根据条件查询教师
     * @return
     */
    public List<Teacher> getTeacherListByParams(HashMap<String,String> map);

	/**
	 * 获取教师信息
	 * @param projectId
	 * @param sbujectId
	 * @param trainingUnitId
	 * @param classes
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<Object[]> getListForPageBySql(Integer projectId,
			Short sbujectId, Integer trainingUnitId, int classes, int start,
			int limit, String sort);
}
