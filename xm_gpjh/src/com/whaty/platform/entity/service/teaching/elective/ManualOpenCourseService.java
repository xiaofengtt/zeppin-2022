package com.whaty.platform.entity.service.teaching.elective;

import java.util.List;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.exception.EntityException;

public interface ManualOpenCourseService {

	public int updateOpenCourse(List ids) throws EntityException;
	
	/**
	 * 批量开课
	 * @param peSiteList
	 * @param peEdutypeList
	 * @param peMajorList
	 * @param peGradeList
	 * @return
	 * @throws EntityException
	 */
	public String saveOpenCourseBatch(List<String> peSiteList,List<String> peEdutypeList,List<String> peMajorList,List<String> peGradeList )throws EntityException;
}
