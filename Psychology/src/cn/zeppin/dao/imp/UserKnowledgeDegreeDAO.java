/** 
 * Project Name:CETV_TEST 
 * File Name:UserKnowledgeDegreeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserKnowledgeDegreeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserKnowledgeDegree;

/**
 * ClassName: UserKnowledgeDegreeDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月14日 下午2:10:11 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserKnowledgeDegreeDAO extends HibernateTemplateDAO<UserKnowledgeDegree, Long> implements IUserKnowledgeDegreeDAO {

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年10月26日 下午5:18:20 <br/>
	 * @param ukld
	 */
	public void addUserKnowledgeDegree(UserKnowledgeDegree ukld) {
		this.save(ukld);
	}

	/**
	 * 计算count
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:06 <br/>
	 * @param map
	 * @return
	 */
	public int getUserKnowledgeDegreeCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserKnowledgeDegree uld where 1=1 ");
		if (map.get("user.id") != null) {
			sb.append(" and uld.user=").append(map.get("user.id"));
		}

		if (map.get("subject.id") != null) {
			sb.append(" and uld.knowledge.subject=").append(map.get("subject.id"));
		}

		return ((Long) this.getResultByHQL(sb.toString())).intValue();
	}

	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:44 <br/>
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserKnowledgeDegree> getUserKnowledgeDegree(Map<String, Object> map, String sorts, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from UserKnowledgeDegree uld where 1=1 ");
		if (map.get("user.id") != null) {
			sb.append(" and uld.user=").append(map.get("user.id"));
		}

		if (map.get("subject.id") != null) {
			sb.append(" and uld.knowledge.subject=").append(map.get("subject.id"));
		}

		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("uld.").append(sort);
				comma = ",";
			}
		}

		return this.getByHQL(sb.toString(), offset, length);

	}

}
