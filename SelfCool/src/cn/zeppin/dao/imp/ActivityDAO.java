/** 
 * Project Name:Self_Cool 
 * File Name:ActivityDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IActivityDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Activity;

/** 
 * ClassName: RoleDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:47:47 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class ActivityDAO extends HibernateTemplateDAO<Activity, Integer> implements IActivityDAO{
	
	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public int getActivityCountByParams(HashMap<String, Object> params) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Activity a where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and a.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and a.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and a.status=").append(params.get("status"));
		}
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
	}

	/**
	 * 获取分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Activity> getActivityListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {

		StringBuilder hql = new StringBuilder();
		hql.append(" from Activity a where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and a.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and a.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("sysUser.id") != null && !params.get("sysUser.id").equals("")){
			hql.append(" and a.sysUser.id=").append(params.get("sysUser.id"));
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and a.status=").append(params.get("status"));
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				hql.append(comma);
				hql.append("a.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

}
