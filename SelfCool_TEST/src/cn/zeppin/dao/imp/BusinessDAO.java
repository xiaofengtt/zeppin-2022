/** 
 * Project Name:Self_Cool 
 * File Name:BusinessDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IBusinessDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Business;

/**
 * ClassName: BusinessDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午8:33:58 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class BusinessDAO extends HibernateTemplateDAO<Business, Integer> implements IBusinessDAO {

	/**
	 * 根据 业务Name来获取 业务
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:13:41 <br/>
	 * @param Name
	 * @return
	 */
	public Business getBusinessByName(String Name) {

		if (Name == null || Name.trim().length() == 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from Business b where 1=1 ");
		sb.append(" and b.name='" + Name + "'");

		List<Business> liT = this.getByHQL(sb.toString());
		if (liT != null && liT.size() > 0) {
			return liT.get(0);
		}
		return null;
	}
	
	/**
	 * 获取业务个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public Integer getBusinessCountByParams(HashMap<String, Object> params) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Business b where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and b.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and b.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and b.status=").append(params.get("status"));
		}
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
	}

	/**
	 * 获取业务分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Business> getBusinessListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {

		StringBuilder hql = new StringBuilder();
		hql.append(" from Business b where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and b.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and b.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and b.status=").append(params.get("status"));
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				hql.append(comma);
				hql.append("b.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

}