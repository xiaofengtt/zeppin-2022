package com.whaty.platform.entity.dao.priority;

import java.util.List;

import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PeManagerDao extends AbstractEntityDao<PeManager,String> {
	
	
	 /**
     * 批量上传手机号码
     */
    public int updateBatch(List mangerList);
}
