package com.whaty.platform.sso.dao;

import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.sso.bean.PrPriManagerGrade;
/**
 * @author lwx 2008-7-28
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public interface PrPriManagerGradeDao extends AbstractEntityDao<PrPriManagerGrade,String> {
	
	/**
	 * 根据用户id 删除该用户的年级范围
	 */
	public void deleteByUserId(String ssouserId);
}
