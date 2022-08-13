/** 
 * Project Name:ItemDatabase 
 * File Name:ISubjectItemTypeDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;

/**
 * ClassName: ISubjectItemTypeDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月2日 下午1:34:35 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface ISubjectItemTypeDAO extends IBaseDAO<SubjectItemType, Integer> {

	/**
	 * 删除学科题型关联数据
	 * @author Clark
	 * @date 2014年7月29日上午10:00:46
	 * @param itemType
	 */
	public void deleteByItemType(ItemType itemType);

	/**
	 * 删除学科题型关联数据
	 * @author Clark
	 * @date 2014年7月29日上午10:00:46
	 * @param subject
	 */
	public void deleteBySubject(Subject subject);

	/**
	 * 查询学科题型
	 * @author Clark
	 * @date 2014年7月30日下午5:37:25
	 * @param searchMap
	 */
	public List<SubjectItemType> searchSubjectItemType(Map<String, Object> searchMap);

}
