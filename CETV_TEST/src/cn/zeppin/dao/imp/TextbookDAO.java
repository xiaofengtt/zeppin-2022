/** 
 * Project Name:CETV_TEST 
 * File Name:TextbookDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITextbookDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Textbook;

/**
 * ClassName: TextbookDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午11:04:04 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class TextbookDAO extends HibernateTemplateDAO<Textbook, Integer> implements ITextbookDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.ITextbookDAO#getAllTextBooks(int, int,
	 * java.lang.String, boolean, java.util.HashMap)
	 */
	@Override
	public List<Textbook> getAllTextBooks(int offset, int pageSize, String sorts, boolean isAdmin, HashMap<String, Object> paras) {
		// TODO Auto-generated method stub
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("from Textbook where 1=1");
		if (paras.containsKey("id")) {
			sBuilder.append(" and id=" + paras.get("id"));
		}
		if (paras.containsKey("name")) {
			sBuilder.append(" and name like '%" + paras.get("name") + "%'");
		}
		if (paras.containsKey("grade")) {
			sBuilder.append(" and grade=" + paras.get("grade"));
		}
		if (paras.containsKey("grade.scode")) {
			sBuilder.append(" and grade.scode like '" + paras.get("grade.scode")).append("%'");
		}
		if (paras.containsKey("subject")) {
			sBuilder.append(" and subject=" + paras.get("subject"));
		}
		if (paras.containsKey("publisher")) {
			sBuilder.append(" and publisher like '%" + paras.get("publisher") + "%'");
		}
		if (paras.containsKey("version")) {
			sBuilder.append(" and version like '%" + paras.get("version") + "%'");
		}
		if (!isAdmin) {
			sBuilder.append(" and status=1");
		}
		if (!sorts.equals("")) {
			sBuilder.append(" order by " + sorts);
		}

		return getByHQL(sBuilder.toString(), offset, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.ITextbookDAO#getCountByParas(java.util.HashMap,
	 * boolean)
	 */
	@Override
	public int getCountByParas(HashMap<String, Object> paras, boolean isAdmin) {
		// TODO Auto-generated method stub
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select count(*) from Textbook where 1=1");
		if (paras.containsKey("id")) {
			sBuilder.append(" and id=" + paras.get("id"));
		}
		if (paras.containsKey("name")) {
			sBuilder.append(" and name like '%" + paras.get("name") + "%'");
		}
		if (paras.containsKey("grade")) {
			sBuilder.append(" and grade=" + paras.get("grade"));
		}
		if (paras.containsKey("grade.scode")) {
			sBuilder.append(" and grade.scode like '" + paras.get("grade.scode")).append("%'");
		}
		if (paras.containsKey("subject")) {
			sBuilder.append(" and subject=" + paras.get("subject"));
		}
		if (paras.containsKey("publisher")) {
			sBuilder.append(" and publisher like '%" + paras.get("publisher") + "%'");
		}
		if (paras.containsKey("version")) {
			sBuilder.append(" and version like '%" + paras.get("version") + "%'");
		}
		if (!isAdmin) {
			sBuilder.append(" and status=1");
		}

		int result = Integer.parseInt(getResultByHQL(sBuilder.toString()).toString());

		return result;
	}

	@Override
	public List<Textbook> getTextbookByParam(Map<String, Object> searchMap) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("from Textbook where 1=1");
		if (searchMap.containsKey("id")) {
			sBuilder.append(" and id=" + searchMap.get("id"));
		}
		if (searchMap.containsKey("name")) {
			sBuilder.append(" and name like '%" + searchMap.get("name") + "%'");
		}
		if (searchMap.containsKey("grade")) {
			sBuilder.append(" and grade=" + searchMap.get("grade"));
		}
		if (searchMap.containsKey("subject")) {
			sBuilder.append(" and subject=" + searchMap.get("subject"));
		}
		if (searchMap.containsKey("publisher")) {
			sBuilder.append(" and publisher like '%" + searchMap.get("publisher") + "%'");
		}
		if (searchMap.containsKey("version")) {
			sBuilder.append(" and version like '%" + searchMap.get("version") + "%'");
		}
		return getByHQL(sBuilder.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.ITextbookDAO#getCountByName(java.lang.String)
	 */
	@Override
	public int getCountByName(String name, Integer id) {
		// TODO Auto-generated method stub
		String hqlString = "select count(*) from Textbook where name='" + name + "'";
		if (id != null) {
			hqlString += " and id<>" + id;
		}
		int count = Integer.parseInt(getResultByHQL(hqlString).toString());
		return count;
	}

}
