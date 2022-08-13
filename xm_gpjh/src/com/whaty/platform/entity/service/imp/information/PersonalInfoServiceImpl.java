package com.whaty.platform.entity.service.imp.information;

import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.dao.GeneralDao;

public class PersonalInfoServiceImpl implements PersonalInfoService {
	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public void updatePersonalInfo(PeEnterpriseManager enterpriseManager) {
		this.getGeneralDao().updatePeEnterpriseManager(enterpriseManager);
	}

}
