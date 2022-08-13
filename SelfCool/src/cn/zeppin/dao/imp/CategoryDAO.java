/** 
 * Project Name:Self_Cool 
 * File Name:CategoryDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ICategoryDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Category;

/**
 * ClassName: CategoryDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午8:33:58 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class CategoryDAO extends HibernateTemplateDAO<Category, Integer> implements ICategoryDAO {

	/**
	 * 根据 分类Name来获取 分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:13:41 <br/>
	 * @param Name
	 * @return
	 */
	public Category getCategoryByName(String Name) {

		if (Name == null || Name.trim().length() == 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from Category c where 1=1 ");
		sb.append(" and c.name='" + Name + "'");

		List<Category> liT = this.getByHQL(sb.toString());
		if (liT != null && liT.size() > 0) {
			return liT.get(0);
		}
		return null;
	}

	/**
	 * 获取分类个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public int getCategoryCountByParams(HashMap<String, Object> params) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Category c where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and c.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and c.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("shortname") != null && !params.get("shortname").equals("")){
			hql.append(" and c.shortname like '%").append(params.get("shortname")).append("%' ");
		}
		if (params.get("level") != null && !params.get("level").equals("")){
			hql.append(" and c.level=").append(params.get("level"));
		}
		if (params.get("scode") != null && !params.get("scode").equals("")){
			hql.append(" and c.scode like '%").append(params.get("scode")).append("%' ");
		}
		if (params.get("category.id") != null && !params.get("category.id").equals("")){
			hql.append(" and c.category.id=").append(params.get("category.id"));
		}
		if (params.get("sysUser.id") != null && !params.get("sysUser.id").equals("")){
			hql.append(" and c.sysUser.id=").append(params.get("sysUser.id"));
		}
		if (params.get("category.name") != null && !params.get("category.name").equals("")){
			hql.append(" and c.category.name like '%").append(params.get("category.name")).append("%' ");
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and c.status=").append(params.get("status"));
		}
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
	}

	/**
	 * 获取分类分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Category> getCategoryListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {

		StringBuilder hql = new StringBuilder();
		hql.append(" from Category c where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and c.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and c.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("shortname") != null && !params.get("shortname").equals("")){
			hql.append(" and c.shortname like '%").append(params.get("shortname")).append("%' ");
		}
		if (params.get("level") != null && !params.get("level").equals("")){
			hql.append(" and c.level=").append(params.get("level"));
		}
		if (params.get("scode") != null && !params.get("scode").equals("")){
			hql.append(" and c.scode like '%").append(params.get("scode")).append("%' ");
		}
		if (params.get("category.id") != null && !params.get("category.id").equals("")){
			hql.append(" and c.category.id=").append(params.get("category.id"));
		}
		if (params.get("sysUser.id") != null && !params.get("sysUser.id").equals("")){
			hql.append(" and c.sysUser.id=").append(params.get("sysUser.id"));
		}
		if (params.get("category.name") != null && !params.get("category.name").equals("")){
			hql.append(" and c.category.name like '%").append(params.get("category.name")).append("%' ");
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and c.status=").append(params.get("status"));
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				hql.append(comma);
				hql.append("c.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

}