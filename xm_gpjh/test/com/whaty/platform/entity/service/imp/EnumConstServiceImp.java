package com.whaty.platform.entity.service.imp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.Page;

public class EnumConstServiceImp implements EnumConstService {
	
	private EnumConstDAO enumConstDao;
	
	public EnumConstDAO getEnumConstDao() {
		return enumConstDao;
	}

	public void setEnumConstDao(EnumConstDAO enumConstDao) {
		this.enumConstDao = enumConstDao;
	}

	public int deleteByIds(List ids) {
		return enumConstDao.deleteByIds(ids);
	}

	public List getList(DetachedCriteria detachedCriteria) {
		return enumConstDao.getList(detachedCriteria);
	}

	public EnumConst save(EnumConst instance) {
		return enumConstDao.save(instance);
	}

	public int updateColumnByIds(List ids, String column, String value) {
		return enumConstDao.updateColumnByIds(ids, column, value);
	}

	public Page getByPage(DetachedCriteria detachedCriteria, int pageSize, int startIndex) {
		return enumConstDao.getByPage(detachedCriteria, pageSize, startIndex);
	}
	
	public EnumConst getById(String id) {
		return enumConstDao.getById(id);
	}
	
	public EnumConst getDefaultByNamespace(String namespace) {
		return enumConstDao.getDefaultByNamespace(namespace);
	}
}
