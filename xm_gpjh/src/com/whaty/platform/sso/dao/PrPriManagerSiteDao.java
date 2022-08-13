package com.whaty.platform.sso.dao;

import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.sso.bean.PrPriManagerSite;
/**
 * @author lwx 2008-7-28
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public interface PrPriManagerSiteDao extends AbstractEntityDao<PrPriManagerSite,String> {
	
	/**
	 * 根据用户id 删除该用户的站点范围
	 */
	public void deleteByUserId(String ssouserId);
}
