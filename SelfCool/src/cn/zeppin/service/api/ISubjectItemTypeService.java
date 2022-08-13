/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.api
 * ISubjectItemTypeService
 */
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;

/**
 * @author Clark
 * 下午3:00:39
 */
public interface ISubjectItemTypeService {

	/**
	 * 更新学科题型表（先删除后添加）
	 * @author Clark
	 * @date 2014年7月30日下午3:03:33
	 * @param subject
	 * @param itemTypeList
	 */
	public List<SubjectItemType> updateSubjectItemType(Subject subject, String[] proportions, List<ItemType> itemTypeList);

	/**
	 * 查询学科题型
	 * @author Clark
	 * @date 2014年7月30日下午5:37:25
	 * @param searchMap
	 */
	public List<SubjectItemType> searchSubjectItemType(Map<String, Object> searchMap);

	/**
	 * 通过唯一索引获取SubjectItemType
	 * @param subjectId
	 * @param itemTypeId
	 * @return
	 */
	public SubjectItemType getByKey(Integer subjectId, Integer itemTypeId);

}
