/** 
 * Project Name:Self_Cool 
 * File Name:ItemTypeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemTypeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ItemType;

/** 
 * ClassName: ItemTypeDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:28:54 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class ItemTypeDAO extends HibernateTemplateDAO<ItemType, Integer> implements IItemTypeDAO{
	
	/**
	 * 通过名称获取题型（主要用于对重名做判断）
	 * @author Clark
	 * @date 2014年7月29日上午9:16:01
	 * @param name
	 * @return itemType
	 */
	@Override
	public ItemType getItemTypeByName(String name) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" from ItemType where name='").append(name).append("'");
		return (ItemType) this.getResultByHQL(hql.toString());
	}

	/**
	 * 通过条件查询题型列表
	 * @author Clark
	 * @date 2014年7月29日上午11:09:14
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<ItemType>
	 */
	@Override
	public List<ItemType> searchItemType(Map<String, Object> searchMap,
			String sorts, int offset, int pagesize) {
		StringBuffer hql = new StringBuffer();
		hql.append("select it from ItemType it ");
		
		hql.append(" where 1=1 ");
		
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")){
			hql.append(" and it.id=").append(searchMap.get("id"));
		}
		
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			hql.append(" and it.name like '%").append(searchMap.get("name")).append("%'");
		}
		
//		if (searchMap.get("isGroup") != null && !searchMap.get("isGroup").equals("")){
//			hql.append(" and it.isGroup is ").append(searchMap.get("isGroup"));
//		}
		
		if (searchMap.get("isStandard") != null && !searchMap.get("isStandard").equals("")){
			hql.append(" and it.isStandard is ").append(searchMap.get("isStandard"));
		}
		if (searchMap.get("sysUser") != null && !searchMap.get("sysUser").equals("")){
			hql.append(" and it.sysUser=").append(searchMap.get("sysUser"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and it.status=").append(searchMap.get("status"));
		}
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				hql.append(comma);
				hql.append("it.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(hql.toString(), offset, pagesize);
	}

	/**
	 * 通过条件查询题型数量
	 * @author Clark
	 * @date 2014年7月29日上午11:09:55
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchItemTypeCount(Map<String, Object> searchMap) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from ItemType it ");
		
		hql.append(" where 1=1 ");
			
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")){
			hql.append(" and it.id=").append(searchMap.get("id"));
		}
		
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			hql.append(" and it.name like '%").append(searchMap.get("name")).append("%'");
		}
		
//		if (searchMap.get("isGroup") != null && !searchMap.get("isGroup").equals("")){
//			hql.append(" and it.isGroup is ").append(searchMap.get("isGroup"));
//		}
		
		if (searchMap.get("isStandard") != null && !searchMap.get("isStandard").equals("")){
			hql.append(" and it.isStandard is ").append(searchMap.get("isStandard"));
		}
		
		if (searchMap.get("sysUser") != null && !searchMap.get("sysUser").equals("")){
			hql.append(" and it.sysUser=").append(searchMap.get("sysUser"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and it.status=").append(searchMap.get("status"));
		}
		return Integer.valueOf(this.getResultByHQL(hql.toString()).toString());
	}

}
