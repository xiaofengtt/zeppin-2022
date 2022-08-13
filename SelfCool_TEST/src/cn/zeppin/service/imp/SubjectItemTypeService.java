/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.imp
 * SubjectItemTypeService
 */
package cn.zeppin.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemDAO;
import cn.zeppin.dao.api.ISubjectItemTypeDAO;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.service.api.ISubjectItemTypeService;
import cn.zeppin.task.TaskJobDAO;
import cn.zeppin.utility.Dictionary;

/**
 * @author Clark 下午3:03:11
 */
public class SubjectItemTypeService implements ISubjectItemTypeService {

	private ISubjectItemTypeDAO subjectItemTypeDAO;
	private IItemDAO itemDAO;
	private TaskJobDAO taskJobDAO;

	public ISubjectItemTypeDAO getSubjectItemTypeDAO() {
		return subjectItemTypeDAO;
	}

	public void setSubjectItemTypeDAO(ISubjectItemTypeDAO subjectItemTypeDAO) {
		this.subjectItemTypeDAO = subjectItemTypeDAO;
	}
	
	public IItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(IItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public TaskJobDAO getTaskJobDAO() {
		return taskJobDAO;
	}

	public void setTaskJobDAO(TaskJobDAO taskJobDAO) {
		this.taskJobDAO = taskJobDAO;
	}

	/**
	 * 更新学科题型表（先删除后添加）
	 * 
	 * @author Clark
	 * @date 2014年7月30日下午3:03:33
	 * @param subject
	 * @param itemTypeList
	 */
	@Override
	public List<SubjectItemType> updateSubjectItemType(Subject subject, String[] proportions, List<ItemType> itemTypeList) {
		this.getSubjectItemTypeDAO().deleteBySubject(subject);
		List<SubjectItemType> result = new ArrayList<>();
		for (int i = 0; i < itemTypeList.size(); i++) {
			SubjectItemType subjectItemType = new SubjectItemType();
			subjectItemType.setSubject(subject);
			subjectItemType.setItemType(itemTypeList.get(i));
			subjectItemType.setInx(i + 1);
			subjectItemType.setProportion(Float.valueOf(proportions[i]));
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("subject.id", subject.getId());
			searchMap.put("itemType.id", itemTypeList.get(i).getId());
			searchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
			Integer itemCount =  this.getItemDAO().searchItemCount(searchMap);
			subjectItemType.setReleasedItemcount(itemCount);
			subjectItemType = this.getSubjectItemTypeDAO().save(subjectItemType);
			result.add(subjectItemType);
		}
		this.getTaskJobDAO().calculateSubjectItemTypeData();
		return result;
	}

	/**
	 * 查询学科题型
	 * 
	 * @author Clark
	 * @date 2014年7月30日下午5:37:25
	 * @param searchMap
	 */
	@Override
	public List<SubjectItemType> searchSubjectItemType(Map<String, Object> searchMap) {
		return this.getSubjectItemTypeDAO().searchSubjectItemType(searchMap);
	}

	
	/**
	 * 通过唯一索引获取SubjectItemType
	 * @param subjectId
	 * @param itemTypeId
	 * @return
	 */
	@Override
	public SubjectItemType getByKey(Integer subjectId, Integer itemTypeId) {
		// TODO Auto-generated method stub
		return this.getSubjectItemTypeDAO().getByKey(subjectId, itemTypeId);
	}



}
