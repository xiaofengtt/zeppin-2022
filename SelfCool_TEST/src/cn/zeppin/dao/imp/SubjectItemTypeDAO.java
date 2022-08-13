/** 
 * Project Name:Self_Cool 
 * File Name:SubjectItemTypeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectItemTypeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.utility.Dictionary;

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
		StringBuilder hql = new StringBuilder();
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
		StringBuilder hql = new StringBuilder();
		hql.append(" delete from SubjectItemType sit ");
		hql.append(" where sit.subject=").append(subject.getId());
		this.executeHQL(hql.toString());
	}

	/**
	 * 查询学科题型
	 * @author Clark
	 * @date 2014年7月30日下午5:37:25
	 * @param searchMap
	 */
	@Override
	public List<SubjectItemType> searchSubjectItemType(Map<String, Object> searchMap) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select sit from SubjectItemType sit where 1=1 ");
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")){
			hql.append(" and sit.subject.id=").append(searchMap.get("subject.id"));
		}
		hql.append(" order by sit.inx");
		return this.getByHQL(hql.toString());
	}
	
	/**
	 * 获取用户该学科相关题型信息
	 * @param ssoUser
	 * @param subject
	 * @param isStandard
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getSubjectItemType(SsoUser ssoUser,
			Subject subject, Integer isStandard) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" select sit.id, sit.subject, it.id as itemtypeid, it.name as itemtypename, sit.inx, sit.released_itemcount,");
		sql.append(" (select count(*) from sso_user_test_item_count sutic, item i");
		sql.append("    where sutic.itemid = i.id");
		sql.append("    and i.type = it.id");
		sql.append("    and i.subject = sit.subject");
		sql.append("    and sutic.last_test_item_complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_CORRECT);
		//当前必须是已发布状态，下线的试题不作为统计内容
		sql.append("    and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		//只统计非组合题和组合题中的子题（组合题中level=1的存储着材料，故不计算）
		sql.append("    and ((i.level=1 and i.is_group=0) or (i.level=2 and i.is_group=1))");
		sql.append("    and sutic.userid=").append(ssoUser.getId());
		sql.append(" ) as last_right_itemcount");
		sql.append(" from subject_item_type sit, item_type it ");
		sql.append(" where sit.item_type=it.id");
		sql.append(" and sit.subject=").append(subject.getId());
		sql.append(" and it.is_standard=").append(isStandard);
		sql.append(" order by sit.inx");
		
		List<Object[]> rst = this.getBySQL(sql.toString());
		List<Map<String,Object>> result = new ArrayList<>();
		for (Object[] obj : rst) {
			Map<String, Object> itemTypeMap = new HashMap<>();
			int i = 0;
			itemTypeMap.put("id", obj[i++]);
			itemTypeMap.put("subjectId", obj[i++]);
			itemTypeMap.put("itemTypeId", obj[i++]);
			itemTypeMap.put("itemTypeName", obj[i++]);
			itemTypeMap.put("inx", obj[i++]);
			itemTypeMap.put("releasedItemCount", obj[i++]);
			itemTypeMap.put("lastRightItemCount", obj[i++]);
			result.add(itemTypeMap);
		}
		
		return result;
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
		StringBuilder hql = new StringBuilder();
		hql.append(" from SubjectItemType sit where 1=1 ");
		hql.append(" and sit.itemType=").append(itemTypeId);
		hql.append(" and sit.subject=").append(subjectId);
		return (SubjectItemType) this.getResultByHQL(hql.toString());
	}


}
