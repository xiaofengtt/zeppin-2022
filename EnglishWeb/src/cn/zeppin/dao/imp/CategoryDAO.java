/** 
 * Project Name:CETV_TEST 
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
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) ");
		sb.append(" from Category c where 1=1 ");
		// parentId
		if (params.get("pid") != null && !params.get("pid").equals("")) {
			sb.append(" and c.category=").append(params.get("pid"));
		} else {
			sb.append(" and c.level=1");
		}
		// name
		if (params.get("name") != null && !params.get("name").equals("")) {
			sb.append(" and c.name like'%" + params.get("name") + "%'");
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
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

		StringBuilder sb = new StringBuilder();
		sb.append(" from Category c where 1=1 ");
		// parentId
		if (params.get("pid") != null && !params.get("pid").equals("")) {
			sb.append(" and c.category=").append(params.get("pid"));
		} else {
			sb.append(" and c.level=1");
		}
		// name
		if (params.get("name") != null && !params.get("name").equals("")) {
			sb.append(" and c.name like'%" + params.get("name") + "%'");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("c.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(sb.toString(), offset, length);
	}

}