/** 
 * Project Name:CETV_TEST 
 * File Name:TestPaperSectionDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITestPaperSectionDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.TestPaperSection;

/**
 * ClassName: TestPaperSectionDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:59:23 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class TestPaperSectionDAO extends HibernateTemplateDAO<TestPaperSection, Integer> implements ITestPaperSectionDAO {

	/**
	 * 计算试卷目录个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperSectionsCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();

		sb.append("select count(*) from TestPaperSection tpc where 1=1 ");

		// 试卷
		if (map.get("paper.id") != null) {
			sb.append(" and tpc.paper=").append(map.get("paper.id"));
		}

		// 父目录
		if (map.get("testPaperSection.id") != null) {
			sb.append(" and tpc.testPaperSection=").append(map.get("testPaperSection.id"));
		} else {
			// 一级目录
			sb.append(" and tpc.testPaperSection is null");
		}

		if (map.get("name") != null) {
			sb.append(" and tpc.name like'%").append(map.get("name")).append("%'");
		}

		return ((Long) this.getResultByHQL(sb.toString())).intValue();
	}

	/**
	 * 计算试卷目录列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperSection> searchTestPaperSections(Map<String, Object> map, String sorts, int offset, int pagesize) {

		StringBuilder sb = new StringBuilder();

		sb.append("from TestPaperSection tpc where 1=1 ");

		// 试卷
		if (map.get("paper.id") != null) {
			sb.append(" and tpc.paper=").append(map.get("paper.id"));
		}

		// 父目录
		if (map.get("testPaperSection.id") != null) {
			sb.append(" and tpc.testPaperSection=").append(map.get("testPaperSection.id"));
		} else {
			// 一级目录
			sb.append(" and tpc.testPaperSection is null");
		}

		if (map.get("name") != null) {
			sb.append(" and tpc.name like'%").append(map.get("name")).append("%'");
		}

		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("tpc.").append(sort);
				comma = ",";
			}
		}

		return this.getByHQL(sb.toString(), offset, pagesize);
	}

}
