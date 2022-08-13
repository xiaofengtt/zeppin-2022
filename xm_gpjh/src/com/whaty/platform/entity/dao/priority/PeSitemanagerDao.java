package com.whaty.platform.entity.dao.priority;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PeSitemanagerDao extends AbstractEntityDao<PeSitemanager,String> {

	public Integer getTotalCount(DetachedCriteria detachedCriteria);
	public List listInfo(String siteid, String sitename, String exampici, String sort, String dir);
}
