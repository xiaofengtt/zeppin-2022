package com.whaty.platform.entity.service.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeBzzBatch;

public interface PrBzzTchOpenCourseService {

	public void deleteByIds(List idList);

	public PeBzzBatch getPeBzzBatch(DetachedCriteria criteria);

	
}
