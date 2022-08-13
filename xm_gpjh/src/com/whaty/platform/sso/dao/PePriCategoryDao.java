package com.whaty.platform.sso.dao;

import java.util.List;

import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.sso.bean.PePriCategory;

public interface PePriCategoryDao extends AbstractEntityDao<PePriCategory,String> {
	
	/**
 	 * 批量删除，级联删除其下的子分类;
 	 */
 	public void deleteByBatch(List<PePriCategory> list);
}
