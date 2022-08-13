/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.imp
 * SubjectItemTypeService
 */
package cn.zeppin.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectItemTypeDAO;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.service.api.ISubjectItemTypeService;

/**
 * @author Clark 下午3:03:11
 */
public class SubjectItemTypeService implements ISubjectItemTypeService {

	private ISubjectItemTypeDAO subjectItemTypeDAO;

	public ISubjectItemTypeDAO getSubjectItemTypeDAO() {
		return subjectItemTypeDAO;
	}

	public void setSubjectItemTypeDAO(ISubjectItemTypeDAO subjectItemTypeDAO) {
		this.subjectItemTypeDAO = subjectItemTypeDAO;
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
	public List<SubjectItemType> updateSubjectItemType(Subject subject, List<ItemType> itemTypeList) {
		// TODO Auto-generated method stub
		this.getSubjectItemTypeDAO().deleteBySubject(subject);
		List<SubjectItemType> result = new ArrayList<>();
		for (int i = 0; i < itemTypeList.size(); i++) {
			SubjectItemType subjectItemType = new SubjectItemType();
			subjectItemType.setSubject(subject);
			subjectItemType.setItemType(itemTypeList.get(i));
			subjectItemType.setInx(i + 1);
			subjectItemType = this.getSubjectItemTypeDAO().save(subjectItemType);
			result.add(subjectItemType);
		}
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
		// TODO Auto-generated method stub
		return this.getSubjectItemTypeDAO().searchSubjectItemType(searchMap);
	}

}
