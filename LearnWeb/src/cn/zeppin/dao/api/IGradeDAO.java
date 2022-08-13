/**
 * Project Name:CETV_TEST File Name:IGradeDAO.java Package
 * Name:cn.zeppin.dao.api Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Grade;

/**
 * ClassName: IGradeDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:15:40 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IGradeDAO extends IBaseDAO<Grade, Integer>
{
	
	List<Grade> getAllGradesByLevel(int level);
	
	List<Grade> getAllGradesByLevel(int level, int pageStart, int pageSize);
	
	/**
	 * @param level
	 * @param offset
	 * @param pagesize
	 * @param sorts
	 * @return
	 * @author suijing 2014年7月22日 下午2:02:28
	 */
	List<Grade> getAllGradesByLevel(int level, int offset, int pagesize, String sorts);
	
	/**
	 * @param level
	 * @return
	 * @author suijing 2014年7月24日 下午1:11:35
	 */
	int getCountByLevel(int level);
	
	/**
	 * @category 通过参数获取学段
	 * @author sj
	 * @date: 2014年7月15日 下午4:32:36 <br/>
	 * @param searchMap
	 * @return
	 */	
	public List<Grade> getGradeByParam(Map<String,Object> searchMap);
	
	/**
	 * @param pid
	 * @param offset
	 * @param pagesize
	 * @param sorts
	 * @return
	 * @author suijing 2014年7月24日 下午4:55:44
	 */
	List<Grade> getChildes(int pid, int offset, int pagesize, String sorts);
	
	/**
	 * @param string
	 * @return
	 * @author suijing 2014年7月28日 上午11:38:12
	 */
	boolean checkNameExist(String string);
	
	/**
	 * @param id
	 * @return
	 * @author suijing 2014年7月28日 下午12:54:33
	 */
	int getChildCount(int id);
	
	/**
	 * @param pid
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param isAdmin
	 * @author suijing 2014年7月29日 下午4:08:52
	 * @return
	 */
	List<Grade> getAllGrades(Integer pid, int offset, int pageSize, String sorts, boolean isAdmin);
	
	/**
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param isAdmin
	 * @param paras
	 * @return
	 * @author suijing 2014年7月29日 下午4:49:50
	 */
	List<Grade> getAllGrades(int offset, int pageSize, String sorts, boolean isAdmin, HashMap<String, Object> paras);
	
	/**
	 * @param paras
	 * @return
	 * @author suijing 2014年7月29日 下午5:22:33
	 */
	int getChildCount(HashMap<String, Object> paras);
	
	/**
	 * @param paras
	 * @param isAdmin
	 * @return
	 * @author suijing 2014年7月29日 下午5:24:17
	 */
	int getChildCount(HashMap<String, Object> paras, boolean isAdmin);
	
	/**
	 * @param pid
	 * @return
	 * @author suijing 2014年7月31日 下午3:27:28
	 */
	int getLevelByPid(int pid);
	
}
