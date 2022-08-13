package com.whaty.platform.entity.dao.recruit;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrRecExamCourseTime;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PrRecExamCourseTimeDAo extends AbstractEntityDao<PrRecExamCourseTime,String> {
	public Integer getTotalCount(DetachedCriteria detachedCriteria);

}
