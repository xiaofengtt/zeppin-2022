/**
 * Project Name:ItemDatabase File Name:IGradeService.java Package
 * Name:cn.zeppin.service.api Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Grade;

/**
 * ClassName: IGradeService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午7:30:21 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IGradeService
{
	
	/**
	 * 递归得到学段全称
	 * 
	 * @author Clark
	 * @date: 2014年7月14日 下午7:32:20 <br/>
	 * @param grade
	 * @return
	 */
	public String getGradeFullName(Grade grade);
	
	/**
	 * 获取所有学段
	 * 
	 * @author sj
	 * @date: 2014年7月15日 下午3:05:42 <br/>
	 * @return
	 */
	public List<Grade> getAllGrades();
	
	/**
	 * @category 通过级别获取学段
	 * @author sj
	 * @date: 2014年7月15日 下午4:32:36 <br/>
	 * @param level
	 * @return
	 */
	
	public List<Grade> getAllGradesByLevel(int level);
	
	
	/**
	 * @category 通过参数获取学段
	 * @author sj
	 * @date: 2014年7月15日 下午4:32:36 <br/>
	 * @param searchMap
	 * @return
	 */	
	public List<Grade> getGradeByParam(Map<String,Object> searchMap);
	
	/**
	 * @category 获取某级别的学段分页
	 * @author sj
	 * @date: 2014年7月17日 下午3:38:59 <br/>
	 * @param i
	 * @param pageStart
	 * @param pageSize
	 * @return
	 */
	
	public List<Grade> getAllGradesByLevel(int level, int pageStart, int pageSize);
	
	/**
	 * 新增学段
	 * 
	 * @author sj
	 * @date: 2014年7月17日 下午7:21:42 <br/>
	 * @param grade
	 */
	public Grade add(Grade grade);
	
	/**
	 * 更新学段
	 * 
	 * @author sj
	 * @date: 2014年7月17日 下午7:32:26 <br/>
	 * @param grade
	 */
	public void update(Grade grade);
	
	public void delete(int id);
	
	public int getChildCount(int id);
	
	/**
	 * @category 通过id获取学段
	 * @param id
	 * @return
	 * @author suijing 2014年7月21日 下午7:13:08
	 */
	public Grade getById(int id);
	
	/**
	 * @category获取某级别的学段分页并排序
	 * @param level
	 * @param offset
	 * @param pagesize
	 * @param sorts
	 * @return
	 * @author suijing 2014年7月22日 下午1:58:05
	 */
	public java.util.List<Grade> getAllGradesByLevel(int level, int offset, int pagesize, String sorts);
	
	/**
	 * 获取某一级别的学段数目
	 * 
	 * @param level
	 * @return
	 * @author suijing 2014年7月24日 下午1:05:43
	 */
	public int getCountByLevel(int level);
	
	/**
	 * @param pid
	 * @param offset
	 * @param pagesize
	 * @param sorts
	 * @return
	 * @author suijing 2014年7月24日 下午4:54:13
	 */
	public List<Grade> getChildes(int pid, int offset, int pagesize, String sorts);
	
	/**
	 * @param string
	 * @return
	 * @author suijing 2014年7月28日 上午11:36:44
	 */
	public boolean checkNameExist(String string);
	
	/**
	 * @param grade
	 * @author suijing 2014年7月28日 下午12:48:08
	 */
	public void delete(Grade grade);
	
	/**
	 * 获取所有学段
	 * 
	 * @param pid
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @return
	 * @author suijing 2014年7月29日 下午4:04:08
	 */
	public List<Grade> getAllGradesByAdmin(int offset, int pageSize, String sorts, HashMap<String, Object> paras);
	
	/**
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param paras
	 * @return
	 * @author suijing 2014年7月29日 下午4:47:41
	 */
	public List<Grade> getAllGradesByUser(int offset, int pageSize, String sorts, HashMap<String, Object> paras);
	
	/**
	 * @param paras
	 * @return
	 * @author suijing 2014年7月29日 下午5:21:18
	 */
	public int getCountByParas(HashMap<String, Object> paras);
	
	/**
	 * @param paras
	 * @param b
	 * @return
	 * @author suijing 2014年7月29日 下午5:26:29
	 */
	public int getCountByParas(HashMap<String, Object> paras, boolean b);
	
	/**
	 * @param pid
	 * @return
	 * @author suijing 2014年7月31日 下午3:26:32
	 */
	public int getLevelByPid(int pid);
	
	/**
	 * @param id
	 * @return
	 * @author suijing 2014年7月31日 下午4:37:41
	 */
	public boolean hasChild(Integer id);
	
}
