package com.whaty.platform.sso.service.admin;

import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;

public interface PrPriRangeRightService extends GeneralService {
	
	public boolean updateRangeRight(String[] major, String[] grade, String[] edutype, String[] site, SsoUser ssoUser) throws EntityException;

}
