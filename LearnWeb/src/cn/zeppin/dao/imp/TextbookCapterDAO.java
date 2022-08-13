/** 
 * Project Name:CETV_TEST 
 * File Name:TextbookCapterDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ITextbookCapterDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.TextbookCapter;

/**
 * ClassName: TextbookCapterDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午11:01:38 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class TextbookCapterDAO extends HibernateTemplateDAO<TextbookCapter, Integer> implements ITextbookCapterDAO
{
	
	@Override
	public TextbookCapter save(TextbookCapter textbookCapter)
	{
		TextbookCapter result = super.save(textbookCapter);
		String format = "0000000000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = df.format(result.getId());
		if (result.getTextbookCapter() != null)
		{
			scode = result.getTextbookCapter().getScode() + scode;
		}
		result.setScode(scode);
		result = this.update(result);
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.ITextbookCapterDAO#getAllTextbookCapter(int, int,
	 * java.lang.String, java.util.HashMap)
	 */
	@Override
	public List<TextbookCapter> getAllTextbookCapter(int offset, int pageSize, String sorts, HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		List<TextbookCapter> lstTextbookCapters = new ArrayList<TextbookCapter>();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("from TextbookCapter where 1=1");
		if (paras.containsKey("id"))
		{
			sBuilder.append(" and id=" + paras.get("id"));
		}
		if (paras.containsKey("name"))
		{
			sBuilder.append(" and name like '%" + paras.get("name") + "%'");
		}
		if (paras.containsKey("level"))
		{
			sBuilder.append(" and level=" + paras.get("level"));
		}
		if (paras.containsKey("number"))
		{
			sBuilder.append(" and number like '%" + paras.get("number") + "%'");
		}
		if (paras.containsKey("textbookCapter"))
		{
			sBuilder.append(" and textbookCapter.id=" + paras.get("textbookCapter"));
		}
		if (paras.containsKey("textbook"))
		{
			sBuilder.append(" and textbook.id=" + paras.get("textbook"));
		}
		if (!sorts.equals(""))
		{
			sBuilder.append(" order by " + sorts);
		}
		
		lstTextbookCapters = getByHQL(sBuilder.toString(), offset, pageSize);
		return lstTextbookCapters;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.api.ITextbookCapterDAO#getChild(java.lang.Integer)
	 */
	@Override
	public int getChild(Integer id)
	{
		// TODO Auto-generated method stub
		String hqlsString = "select count(*) from TextbookCapter where textbookCapter =" + id;
		int result = Integer.parseInt(getResultByHQL(hqlsString).toString());
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.api.ITextbookCapterDAO#getCountByParas(java.util.HashMap)
	 */
	@Override
	public int getCountByParas(HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select count(*) from TextbookCapter where 1=1");
		if (paras.containsKey("id"))
		{
			sBuilder.append(" and id=" + paras.get("id"));
		}
		if (paras.containsKey("name"))
		{
			sBuilder.append(" and name like '%" + paras.get("name") + "%'");
		}
		if (paras.containsKey("level"))
		{
			sBuilder.append(" and level=" + paras.get("level"));
		}
		if (paras.containsKey("number"))
		{
			sBuilder.append(" and number like '%" + paras.get("number") + "%'");
		}
		if (paras.containsKey("textbookCapter"))
		{
			sBuilder.append(" and textbookCapter.id=" + paras.get("textbookCapter"));
		}
		if (paras.containsKey("textbook"))
		{
			sBuilder.append(" and textbook.id=" + paras.get("textbook"));
		}
		int result = Integer.parseInt(getResultByHQL(sBuilder.toString()).toString());
		return result;
	}
}
