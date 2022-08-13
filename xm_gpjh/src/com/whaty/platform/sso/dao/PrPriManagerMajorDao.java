package com.whaty.platform.sso.dao;

import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.sso.bean.PrPriManagerMajor;
/**
 * @author lwx 2008-7-28
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public interface PrPriManagerMajorDao extends AbstractEntityDao<PrPriManagerMajor,String> {
	
	/**
	 * 根据用户id 删除该用户的专业范围
	 */
	public void deleteByUserId(String ssouserId);
}
