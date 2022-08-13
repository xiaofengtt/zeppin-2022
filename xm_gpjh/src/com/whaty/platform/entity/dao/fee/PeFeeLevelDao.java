package com.whaty.platform.entity.dao.fee;

import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PeFeeLevelDao extends AbstractEntityDao<PeFeeLevel,String> {
	
	/**
	 * 获得默认fee水平
	 */
	public PeFeeLevel getDefaultFeeLevel();
}
