package com.whaty.platform.entity.service.teaching.elective;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.Const;

public interface SemesterOpenCourseService {
	
	public PrTchOpencourse savePrTchOpencourse(PrTchOpencourse instance) throws EntityException;
	
	public int saveSemesterOpenCourse() throws EntityException;
	
	/**
	 * 批量导入学期开课（建议考试场次或上课次数）
	 * @param file
	 * @param type 操作类型 examNo 建议考试场次  ，  courseTime  上课次数
	 * @return
	 * @throws EntityException
	 */
	public int saveOpenCourseBatch(File file,String type) throws EntityException;
}
