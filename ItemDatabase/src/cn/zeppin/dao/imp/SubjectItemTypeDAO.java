/** 
 * Project Name:ItemDatabase 
 * File Name:SubjectItemTypeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;


import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectItemTypeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;

/** 
 * ClassName: SubjectItemTypeDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月2日 下午1:36:53 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class SubjectItemTypeDAO extends HibernateTemplateDAO<SubjectItemType, Integer> implements ISubjectItemTypeDAO {
	
	/**
	 * 删除学科题型关联数据
	 * @author Clark
	 * @date 2014年7月29日上午10:00:46
	 * @param itemType
	 */
	@Override
	public void deleteByItemType(ItemType itemType) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" delete from SubjectItemType sit ");
		hql.append(" where sit.itemType=").append(itemType.getId());
		this.executeHQL(hql.toString());
	}

	/**
	 * 删除学科题型关联数据
	 * @author Clark
	 * @date 2014年7月29日上午10:00:46
	 * @param itemType
	 */
	@Override
	public void deleteBySubject(Subject subject) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" delete from SubjectItemType sit ");
		hql.append(" where sit.subject=").append(subject.getId());
		this.executeHQL(hql.toString());
	}

	@Override
	public List<SubjectItemType> searchSubjectItemType(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" select sit from SubjectItemType sit where 1=1 ");
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")){
			hql.append(" and sit.subject.id=").append(searchMap.get("subject.id"));
		}
		hql.append(" order by sit.inx");
		return this.getByHQL(hql.toString());
	}


}
