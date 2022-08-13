/** 
 * Project Name:Self_Cool 
 * File Name:RoleDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IRetrieveDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Retrieve;

/** 
 * ClassName: RoleDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:47:47 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class RetrieveDAO extends HibernateTemplateDAO<Retrieve, Integer> implements IRetrieveDAO{

	@Override
	public int searchRetrieveCount(Map<String, Object> searchMap) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from Retrieve r where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and r.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			hql.append(" and r.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("retrieveType.id") != null && !searchMap.get("retrieveType.id").equals("")){
			hql.append(" and r.retrieveType.id=").append(searchMap.get("retrieveType.id"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and r.status=").append(searchMap.get("status"));
		}
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
	}

	@Override
	public List<Retrieve> searchRetrieves(Map<String, Object> searchMap,String sorts, int offset, int length) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from Retrieve r where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and r.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			hql.append(" and r.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("retrieveType.id") != null && !searchMap.get("retrieveType.id").equals("")){
			hql.append(" and r.retrieveType.id=").append(searchMap.get("retrieveType.id"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and r.status=").append(searchMap.get("status"));
		}
		//排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				hql.append(comma);
				hql.append("r.").append(sort);
				comma = ",";
			}
			
		}
		return this.getByHQL(hql.toString(), offset, length);
	}
	
	

}
