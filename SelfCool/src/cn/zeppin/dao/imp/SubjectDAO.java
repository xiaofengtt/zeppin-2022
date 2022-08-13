/** 
 * Project Name:Self_Cool 
 * File Name:SubjectDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ISubjectDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Subject;

/**
 * ClassName: SubjectDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:06:19 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class SubjectDAO extends HibernateTemplateDAO<Subject, Integer> implements ISubjectDAO {

	/**
	 * 获取全部Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @return
	 */
	public List<Subject> getAllSubject(){
		StringBuilder sb = new StringBuilder();
		sb.append("from Subject s");
		return this.getByHQL(sb.toString());
	}
	
	/**
	 * 根据Name来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:05:22 <br/>
	 * @param name
	 * @return
	 */
	@Override
	public Subject getSubjectByName(String name) {

		StringBuilder sb = new StringBuilder();
		sb.append("from Subject s where 1=1").append(" and s.name='").append(name).append("'");

		List<Subject> liT = this.getByHQL(sb.toString());

		if (liT != null && liT.size() > 0) {
			return liT.get(0);
		}

		return null;
	}

	@Override
	public int getSubjectCountByParams(HashMap<String, Object> params) {

		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Subject s where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and s.id=").append(params.get("id"));
		}
		if (params.get("name") != null && !params.get("name").equals("")){
			hql.append(" and s.name like '%").append(params.get("name")).append("%' ");
		}
		if (params.get("shortname") != null && !params.get("shortname").equals("")){
			hql.append(" and s.shortname like '%").append(params.get("shortname")).append("%' ");
		}
		if (params.get("category.id") != null && !params.get("category.id").equals("")){
			hql.append(" and s.category.id=").append(params.get("category.id"));
		}
		if (params.get("grade.id") != null && !params.get("grade.id").equals("")){
			hql.append(" and s.grade.id=").append(params.get("grade.id"));
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and s.status=").append(params.get("status"));
		}
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
	}

	@Override
	public List<Subject> getSubjectByParams(HashMap<String, Object> params, String sorts , int offset, int length) {
		StringBuilder hql = new StringBuilder();

		/**
		 * if内容逻辑可能有点问题，需要修改retrieve.id部分  ——rongjingfeng
		 */
		if (params.containsKey("retrieve.id") && params.get("retrieve.id") != null) {

			hql.append("select distinct s from Subject s, SubjectRetrieve sr where 1=1 and s.id= sr.subject ");
			if (params.get("category.id") != null && !params.get("category.id").equals("")) {
				hql.append(" and s.category.id=").append(params.get("category.id"));
			}

			String[] retrieves = (String[]) params.get("retrieve.id");
			StringBuilder retCondion = new StringBuilder();

			for (String retrieve : retrieves) {
				if (retrieve != null && retrieve.length() > 0 && !retrieve.equals("0")) {
					retCondion.append(" sr.retrieve.id=").append(retrieve).append(" or ");
				}
			}
			if (retCondion.length() > 0) {
				retCondion.delete(retCondion.length() - 3, retCondion.length());
				hql.append(" and (");
				hql.append(retCondion.toString());
				hql.append(")");
			}

		} else {
			hql.append("from Subject s where 1=1 ");
			if (params.get("id") != null && !params.get("id").equals("")) {
				hql.append(" and s.id=").append(params.get("id"));
			}
			if (params.get("name") != null && !params.get("name").equals("")) {
				hql.append(" and s.name like '%").append(params.get("name")).append("%' ");
			}
			if (params.get("shortname") != null && !params.get("shortname").equals("")) {
				hql.append(" and s.shortname like '%").append(params.get("shortname")).append("%' ");
			}
			if (params.get("category.id") != null && !params.get("category.id").equals("")) {
				hql.append(" and s.category.id=").append(params.get("category.id"));
			}
			if (params.get("grade.id") != null && !params.get("grade.id").equals("")) {
				hql.append(" and s.grade.id=").append(params.get("grade.id"));
			}
			if (params.get("status") != null && !params.get("status").equals("")) {
				hql.append(" and s.status=").append(params.get("status"));
			}
			if (sorts != null && sorts.length() > 0) {
				String[] sortArray = sorts.split(",");
				hql.append(" order by ");
				String comma = "";
				for (String sort : sortArray) {
					hql.append(comma);
					hql.append("s.").append(sort);
					comma = ",";
				}
			}
		}

		return this.getByHQL(hql.toString(), offset, length);
	}

}
