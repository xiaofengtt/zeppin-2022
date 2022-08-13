package com.whaty.platform.entity.dao.recruit;

import java.util.List;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

public interface PeRecStudentDAO extends AbstractEntityDao<PeRecStudent, String>{
	
	/**
	 *根据学生id，状态更新学生信息，学生录取。
	 */
	public int updateRecStudent(final String id,final String status,final String studyMajroMark,final String regNo)throws EntityException;


	List getCount(String sql);
	
	/**
	 * 根据站点id，录取状态获得 考试学生列表，总分大于等于站点分数，单科成绩大于等于单科分数线
	 */
	public Page getExamRecStudentList(String siteId,String status,int pageSize,int start,String sort);


}
