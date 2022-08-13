package com.whaty.platform.entity.dao.recruit;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrRecCourseMajorEdutype;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PrRecCourseMajorEdutypeDAO extends AbstractEntityDao<PrRecCourseMajorEdutype,String> {
	public Integer getTotalCount(DetachedCriteria detachedCriteria);
}
