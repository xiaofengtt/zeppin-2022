/** 
 * Project Name:CETV_TEST 
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

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Subject s where 1=1 ");

		if (params.containsKey("name")) {
			String name = (String) params.get("name");
			if (name != null) {
				sb.append(" and s.name like '%").append(name).append("%'");
			}
		}

		if (params.containsKey("gradeId")) {
			Object grade = params.get("gradeId");
			if (grade != null && !grade.toString().trim().equals("")) {
				int gradeId = Integer.valueOf(grade.toString());
				if (gradeId > 0) {
					sb.append(" and s.grade=").append(gradeId);
				}
			}
		}
		
		if(params.containsKey("gradescode")&&params.get("gradescode")!=null){
			sb.append(" and s.grade.scode like '").append(params.get("gradescode")).append("%'");
		}

		if (params.containsKey("gradeName")) {
			Object gradeName = params.get("gradeName");
			if (gradeName != null) {
				sb.append(" and s.grade.name like '%").append(gradeName).append("%' ");
			}
		}

		if (params.containsKey("categoryId")) {
			Object category = params.get("categoryId");
			if (category != null && !category.toString().trim().equals("")) {
				int categoryId = Integer.valueOf(category.toString());
				if (categoryId > 0) {
					sb.append(" and s.category=").append(categoryId);
				}
			}
		}

		if (params.containsKey("categoryName")) {
			Object categoryName = params.get("categoryName");
			if (categoryName != null) {
				sb.append(" and s.category.name like '%").append(categoryName).append("%' ");
			}
		}

		if (params.containsKey("status")) {
			Object status = params.get("status");
			if (status != null) {
				int istatus = Integer.valueOf(status.toString());
				sb.append(" and s.status=").append(istatus);
			}
		}

		Object result = this.getResultByHQL(sb.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}
		return 0;
	}

	@Override
	public List<Subject> getSubjectByParams(HashMap<String, Object> params, int offset, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append("from Subject s where 1=1 ");

		if (params.containsKey("name")) {
			String name = (String) params.get("name");
			if (name != null) {
				sb.append(" and s.name like '%").append(name).append("%'");
			}
		}

		if (params.containsKey("gradeId")) {
			Object grade = params.get("gradeId");
			if (grade != null && !grade.toString().trim().equals("")) {
				int gradeId = Integer.valueOf(grade.toString());
				if (gradeId > 0) {
					sb.append(" and s.grade=").append(gradeId);
				}
			}
		}
		
		if(params.containsKey("gradescode")&&params.get("gradescode")!=null){
			sb.append(" and s.grade.scode like '").append(params.get("gradescode")).append("%'");
		}
		
		if (params.containsKey("gradeName")) {
			Object gradeName = params.get("gradeName");
			if (gradeName != null) {
				sb.append(" and s.grade.name like '%").append(gradeName).append("%' ");
			}
		}

		if (params.containsKey("categoryId")) {
			Object category = params.get("categoryId");
			if (category != null && !category.toString().trim().equals("")) {
				int categoryId = Integer.valueOf(category.toString());
				if (categoryId > 0) {
					sb.append(" and s.category=").append(categoryId);
				}
			}
		}

		if (params.containsKey("categoryName")) {
			Object categoryName = params.get("categoryName");
			if (categoryName != null) {
				sb.append(" and s.category.name like '%").append(categoryName).append("%' ");
			}
		}

		if (params.containsKey("status")) {
			Object status = params.get("status");
			if (status != null) {
				int istatus = Integer.valueOf(status.toString());
				sb.append(" and s.status=").append(istatus);
			}
		}

		if (params.containsKey("sorts")) {
			String sorts = params.get("sorts").toString();
			// 排序
			if (sorts != null && sorts.length() > 0) {
				String[] sortArray = sorts.split(",");
				sb.append(" order by ");
				String comma = "";
				for (String sort : sortArray) {
					sb.append(comma);
					sb.append("s.").append(sort);
					comma = ",";
				}
			}

		}

		return this.getByHQL(sb.toString(), offset, length);
	}

}
