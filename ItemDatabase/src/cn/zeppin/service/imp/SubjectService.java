/** 
 * Project Name:ItemDatabase 
 * File Name:SubjectService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectDAO;
import cn.zeppin.dao.api.ISubjectItemTypeDAO;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.service.api.ISubjectService;

/**
 * ClassName: ISubjectService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月20日 下午5:04:08 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class SubjectService implements ISubjectService {

	private ISubjectDAO subjectDAO;
	private ISubjectItemTypeDAO subjectItemTypeDAO;

	public ISubjectDAO getSubjectDAO() {
		return subjectDAO;
	}

	public void setSubjectDAO(ISubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}

	public ISubjectItemTypeDAO getSubjectItemTypeDAO() {
		return subjectItemTypeDAO;
	}

	public void setSubjectItemTypeDAO(ISubjectItemTypeDAO subjectItemTypeDAO) {
		this.subjectItemTypeDAO = subjectItemTypeDAO;
	}

	/**
	 * 获取全部Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @return
	 */
	public List<Subject> getAllSubject(){
		return this.getSubjectDAO().getAllSubject();
	}
	
	/**
	 * 根据id来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Subject getSubjectById(int id) {
		return this.getSubjectDAO().get(id);
	}

	/**
	 * 根据Name来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:05:22 <br/>
	 * @param name
	 * @return
	 */
	@Override
	public Subject getSubjectByName(String name) {
		// TODO Auto-generated method stub
		return this.getSubjectDAO().getSubjectByName(name);
	}

	/**
	 * 添加学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:11:15 <br/>
	 * @param subject
	 */
	@Override
	public void addSubject(Subject subject) {
		this.getSubjectDAO().save(subject);
	}

	/**
	 * 跟新 学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:09:46 <br/>
	 * @param subject
	 */
	@Override
	public void updateSubject(Subject subject) {
		this.getSubjectDAO().update(subject);
	}

	/**
	 * 获取学科Count
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getSubjectCountByParams(HashMap<String, Object> params) {
		return this.getSubjectDAO().getSubjectCountByParams(params);
	}

	/**
	 * 获取学科列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public List<Subject> getSubjectByParams(HashMap<String, Object> params, int offset, int length) {
		return this.getSubjectDAO().getSubjectByParams(params, offset, length);
	}

	/**
	 * 获取学科Count
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getSubjectCountByCategory(int category, int status) {
		HashMap<String, Object> ht = new HashMap<>();
		ht.put("categoryId", category);
		ht.put("status", status);
		return this.getSubjectCountByParams(ht);
	}

	/**
	 * 获取学科列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public List<Subject> getSubjectByCategory(int category, int status, int offset, int length) {
		HashMap<String, Object> ht = new HashMap<>();
		ht.put("categoryId", category);
		ht.put("status", status);
		return this.getSubjectByParams(ht, offset, length);
	}

	/**
	 * 学科下是否存在题型
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:33:26 <br/>
	 * @param subject
	 * @param itemtype
	 * @return
	 */
	@Override
	public boolean isCanItemType(Subject subject, ItemType itemtype) {

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject.id", subject.getId());
		List<SubjectItemType> subjectItemTypes = this.getSubjectItemTypeDAO().searchSubjectItemType(searchMap);

		if (subjectItemTypes == null || subjectItemTypes.size() == 0) {
			return true;
		} else {
			for (SubjectItemType sit : subjectItemTypes) {
				if (sit.getItemType().getId() == itemtype.getId()) {
					return true;
				}
			}
		}

		return false;
	}

}
