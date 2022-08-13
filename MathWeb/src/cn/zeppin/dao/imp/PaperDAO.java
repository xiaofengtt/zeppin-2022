/** 
 * Project Name:CETV_TEST 
 * File Name:PaperDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IPaperDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Paper;

/**
 * ClassName: PaperDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:42:01 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class PaperDAO extends HibernateTemplateDAO<Paper, Integer> implements IPaperDAO {

	@Override
	public void updatePaper(Paper paper) {
		this.merge(paper);
	}

	/**
	 * 试卷个数
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param searchMap
	 * @return
	 */
	public int searchPaperCount(Map<String, Object> searchMap) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) ").append(" from Paper p where 1=1 ");

		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			sb.append(" and p.id=").append(searchMap.get("id"));
		}

		// type
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")) {

			String type = searchMap.get("type").toString();
			if (type.indexOf(",") > -1) {
				sb.append(" and p.type in (").append(type).append(")");
			} else {
				sb.append(" and p.type=").append(type);
			}
		} else {
			sb.append(" and p.type!=5");
		}

		// name
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
			sb.append(" and p.name like '%").append(searchMap.get("name")).append("%'");
		}

		// source
		if (searchMap.get("source") != null && !searchMap.get("source").equals("")) {
			sb.append(" and p.source like'%").append(searchMap.get("source")).append("%'");
		}

		// 试卷时长
		if (searchMap.get("answerTime") != null && !searchMap.get("answerTime").equals("")) {
			sb.append(" and p.answerTime=").append(searchMap.get("answerTime"));
		}

		// 总分
		if (searchMap.get("totalScore") != null && !searchMap.get("totalScore").equals("")) {
			sb.append(" and p.totalScore=").append(searchMap.get("totalScore"));
		}

		// 封面
		if (searchMap.get("cover") != null && !searchMap.get("cover").equals("")) {
			sb.append(" and p.cover like'%").append(searchMap.get("cover")).append("%'");
		}

		// 状态
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and p.status=").append(searchMap.get("status"));
		} else {
			sb.append(" and p.status!=0");
		}

		// 学科
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")) {
			sb.append(" and p.subject.id=").append(searchMap.get("subject.id"));
		}

		// 学段
		if (searchMap.get("grade.id") != null && !searchMap.get("grade.id").equals("")) {
			sb.append(" and p.grade.id=").append(searchMap.get("grade.id"));
		}
		// 学段scode
		if (searchMap.get("grade.scode") != null && !searchMap.get("grade.scode").equals("")) {
			sb.append(" and p.grade.scode like'").append(searchMap.get("grade.scode")).append("%'");
		}

		// 地区
		if (searchMap.get("area.scode") != null && !searchMap.get("area.scode").equals("")) {
			sb.append(" and p.area.scode like '%").append(searchMap.get("area.scode")).append("%'");
		}

		// 年度
		if (searchMap.get("year") != null && !searchMap.get("year").equals("")) {
			sb.append(" and p.year=").append(searchMap.get("year"));
		}

		if (searchMap.get("sysUser.organization.id") != null && !searchMap.get("sysUser.organization.id").equals("")) {
			sb.append(" and p.sysUser.organization.id=").append(searchMap.get("sysUser.organization.id"));
		}

		return ((Long) this.getResultByHQL(sb.toString())).intValue();

	}

	/**
	 * 试卷平均分
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param paperId
	 * @return
	 */
	public float getPaperAverage(int paperId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select AVG(score) from UserTest ut where ut.status=1 ");
		sb.append(" and ut.paper=").append(paperId);
		Object obj = this.getResultByHQL(sb.toString());
		obj = obj == null ? 0 : obj;
		float avg = Float.valueOf(obj.toString());
		return avg;
	}

	/**
	 * 试卷列表
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:53 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<Paper> searchPaper(Map<String, Object> searchMap, String sorts, int offset, int pagesize) {

		StringBuilder sb = new StringBuilder();
		sb.append(" from Paper p where 1=1 ");

		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			sb.append(" and p.id=").append(searchMap.get("id"));
		}

		// type
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")) {

			String type = searchMap.get("type").toString();
			if (type.indexOf(",") > -1) {
				sb.append(" and p.type in (").append(type).append(")");
			} else {
				sb.append(" and p.type=").append(type);
			}
		}else {
			sb.append(" and p.type!=5");
		}

		// name
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
			sb.append(" and p.name like '%").append(searchMap.get("name")).append("%'");
		}

		// source
		if (searchMap.get("source") != null && !searchMap.get("source").equals("")) {
			sb.append(" and p.source like'%").append(searchMap.get("source")).append("%'");
		}

		// 试卷时长
		if (searchMap.get("answerTime") != null && !searchMap.get("answerTime").equals("")) {
			sb.append(" and p.answerTime=").append(searchMap.get("answerTime"));
		}

		// 总分
		if (searchMap.get("totalScore") != null && !searchMap.get("totalScore").equals("")) {
			sb.append(" and p.totalScore=").append(searchMap.get("totalScore"));
		}

		// 封面
		if (searchMap.get("cover") != null && !searchMap.get("cover").equals("")) {
			sb.append(" and p.cover like'%").append(searchMap.get("cover")).append("%'");
		}

		// 状态
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and p.status=").append(searchMap.get("status"));
		} else {
			sb.append(" and p.status!=0");
		}

		// 学科
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")) {
			sb.append(" and p.subject.id=").append(searchMap.get("subject.id"));
		}

		// 学段
		if (searchMap.get("grade.id") != null && !searchMap.get("grade.id").equals("")) {
			sb.append(" and p.grade.id=").append(searchMap.get("grade.id"));
		}
		// 学段scode
		if (searchMap.get("grade.scode") != null && !searchMap.get("grade.scode").equals("")) {
			sb.append(" and p.grade.scode like'").append(searchMap.get("grade.scode")).append("%'");
		}

		// 地区
		if (searchMap.get("area.scode") != null && !searchMap.get("area.scode").equals("")) {
			sb.append(" and p.area.scode like '%").append(searchMap.get("area.scode")).append("%'");
		}

		// 年度
		if (searchMap.get("year") != null && !searchMap.get("year").equals("")) {
			sb.append(" and p.year=").append(searchMap.get("year"));
		}

		if (searchMap.get("sysUser.organization.id") != null && !searchMap.get("sysUser.organization.id").equals("")) {
			sb.append(" and p.sysUser.organization.id=").append(searchMap.get("sysUser.organization.id"));
		}

		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("p.").append(sort);
				comma = ",";
			}
		}

		return this.getByHQL(sb.toString(), offset, pagesize);

	}

}
