package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.service.basic.PrBzzTchOpenCourseService;

public class PrBzzTchOpenCourseServiceImp implements PrBzzTchOpenCourseService {

	private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public void deleteByIds(List idList) {
		this.generalDao.deleteByIds(idList);
	}

	public PeBzzBatch getPeBzzBatch(DetachedCriteria criteria) {
		return generalDao.getPeBzzBatch(criteria);
	}
}
