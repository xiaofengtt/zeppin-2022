package com.whaty.platform.entity.dao.recruit;

import com.whaty.platform.entity.bean.PrStudentInfo;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PrStudentInfoDao extends AbstractEntityDao<PrStudentInfo,String> {
	
	/**
	 * 根据学号查找学生
	 */
	public PrStudentInfo getByRegNo(String regNo);
	
	/**
 	 *  获得当前最大学号（除8和9开头）
 	 */
 	public String getMaxStuRegNo();
 	/**
	 * 根据招生表中学生id
	 */
	public PrStudentInfo getByRecId(String id);
}
