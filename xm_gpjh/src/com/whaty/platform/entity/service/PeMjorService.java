package com.whaty.platform.entity.service;

import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;

public class PeMjorService {
	private GeneralDao<PeMajor> generalDao;

	public GeneralDao<PeMajor> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeMajor> generalDao) {
		this.generalDao = generalDao;
	}
	
	public PeMajor save(PeMajor transientInstance) throws EntityException {
		PeMajor instance = null;
		try{
			instance = this.generalDao.save(transientInstance);
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return instance;
	}

}
