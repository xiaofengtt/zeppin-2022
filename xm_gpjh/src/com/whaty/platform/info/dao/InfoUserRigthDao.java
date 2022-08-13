package com.whaty.platform.info.dao;

import java.util.List;

import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.info.bean.InfoUserRight;

public interface InfoUserRigthDao extends AbstractEntityDao<InfoUserRight,String> {
	
	/**
	 * 根据newtype 列表删除记录
	 */
	public int deleteByNewsTypeIds(List newsTypeIds);
}
