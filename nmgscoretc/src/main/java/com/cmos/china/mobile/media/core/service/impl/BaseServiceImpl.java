package com.cmos.china.mobile.media.core.service.impl;

import com.cmos.china.mobile.media.core.base.CMOSBaseServiceImpl;
import com.cmos.china.mobile.media.core.dao.impl.BaseDaoImpl;

/**
 * 服务基类
 */
public class BaseServiceImpl extends CMOSBaseServiceImpl{
	private BaseDaoImpl baseDao;

	public BaseDaoImpl getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDaoImpl baseDao) {
		this.baseDao = baseDao;
	}
}
