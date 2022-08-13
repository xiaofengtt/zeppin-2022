package com.whaty.platform.entity.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.util.Page;

public interface EnumConstService {

	public List getList(DetachedCriteria detachedCriteria);

	public int deleteByIds(List ids);

	public EnumConst save(EnumConst instance);

	public int updateColumnByIds(List ids, String column, String value);
	
	public Page getByPage(DetachedCriteria detachedCriteria, int pageSize, int startIndex);
	
	public EnumConst getDefaultByNamespace(String namespace);
}
