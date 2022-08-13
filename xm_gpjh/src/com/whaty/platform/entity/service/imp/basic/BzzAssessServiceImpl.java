package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.service.basic.BzzAssessService;

public class BzzAssessServiceImpl implements BzzAssessService{
	
	private GeneralDao generalDao;

	public void updatePeBzzAssess(PeBzzAssess assess) {
		this.getGeneralDao().updatePeBzzAssess(assess);
		
	}
	
	public void updatepeBzzSuggestion(PeBzzSuggestion peBzzSuggestion) {
		this.generalDao.updatepeBzzSuggestion(peBzzSuggestion);
		
	}
	
	public  PeBzzSuggestion getPeBzzSuggestion(String sugid) {
		return this.getGeneralDao().getPeBzzSuggestion(sugid);
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
}
