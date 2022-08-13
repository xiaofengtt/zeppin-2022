/** 
 * Project Name:ItemDatabase 
 * File Name:PublisherDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IPublisherDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Publisher;

/**
 * ClassName: PublisherDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午11:04:04 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class PublisherDAO extends HibernateTemplateDAO<Publisher, Integer> implements IPublisherDAO {
	
	public boolean isExist(String name ,String fcode , Integer id){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Publisher where status=1 ");
		sb.append(" and (name='").append(name).append("'");
		sb.append(" or fcode='").append(fcode).append("' )");
		if (id != null) {
			sb.append(" and id<>").append(id);
		}
		int count = Integer.valueOf(getResultByHQL(sb.toString()).toString());
		if (count > 0){
			return true;
		}else{
			return false;
		}
	}
	

	public List<Publisher> getPublisherList(Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from Publisher p ");

		sb.append(" where p.status=1  ");
		
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")){
			sb.append(" and p.id=").append(searchMap.get("id"));
		}
		
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and p.name like '%").append(searchMap.get("name")).append("%'");
		}
		
		if (searchMap.get("fcode") != null && !searchMap.get("fcode").equals("")){
			sb.append(" and p.fcode = '").append(searchMap.get("fcode")).append("'");
		}
		
		if (searchMap.get("sysUser") != null && !searchMap.get("sysUser").equals("")){
			sb.append(" and p.sysUser=").append(searchMap.get("sysUser"));
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append("p.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(sb.toString(), offset, pagesize);
	}

	public Integer getPublisherCount(Map<String, Object> searchMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Publisher p ");

		sb.append(" where p.status=1 ");

		if (searchMap.get("id") != null && !searchMap.get("id").equals("")){
			sb.append(" and p.id=").append(searchMap.get("id"));
		}
		
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and p.name like '%").append(searchMap.get("name")).append("%'");
		}
		
		if (searchMap.get("fcode") != null && !searchMap.get("fcode").equals("")){
			sb.append(" and p.fcode = '").append(searchMap.get("fcode")).append("'");
		}
		
		if (searchMap.get("sysUser") != null && !searchMap.get("sysUser").equals("")){
			sb.append(" and p.sysUser=").append(searchMap.get("sysUser"));
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
}
