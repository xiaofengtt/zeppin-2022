package com.whaty.platform.entity.service.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.exception.EntityException;

public interface PrEduMajorSiteFeeLevelService {
	/**
	 * 添加站点时候添加层次-专业-站点-收费标准关系表
	 * 
	 * @param peSite
	 */
	public void saveSite(PeSite peSite) throws EntityException;

	/**
	 * 添加层次时候添加层次-专业-站点-收费标准关系表
	 * 
	 * @param peEdutype
	 */
	public void saveEdutype(PeEdutype peEdutype) throws EntityException;

	/**
	 * 添加专业时候添加层次-专业-站点-收费标准关系表
	 * 
	 * @param peMajor
	 */
	public void saveMajor(PeMajor peMajor) throws EntityException;

}
