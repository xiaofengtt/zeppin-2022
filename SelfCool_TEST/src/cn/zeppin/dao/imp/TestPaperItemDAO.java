/** 
 * Project Name:Self_Cool 
 * File Name:TestPaperItemDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITestPaperItemDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.TestPaperItem;

/**
 * ClassName: TestPaperItemDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:17:28 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class TestPaperItemDAO extends HibernateTemplateDAO<TestPaperItem, Integer> implements ITestPaperItemDAO {

	/**
	 * 计算试卷题数个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperItemsCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from TestPaperItem tpi where 1=1 ");

		if (map.containsKey("paper.id")) {
			sb.append(" and tpi.paper=").append(map.get("paper.id"));
		}

		if (map.containsKey("testPaperSection.id")) {
			sb.append(" and tpi.testPaperSection=").append(map.get("testPaperSection.id"));
		}

		return ((Long) this.getResultByHQL(sb.toString())).intValue();

	}

	/**
	 * 计算试卷题数列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperItem> searchTestPaperItems(Map<String, Object> map, String sorts, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from TestPaperItem tpi where 1=1 ");

		if (map.containsKey("paper.id")) {
			sb.append(" and tpi.paper=").append(map.get("paper.id"));
		}

		if (map.containsKey("testPaperSection.id")) {
			sb.append(" and tpi.testPaperSection=").append(map.get("testPaperSection.id"));
		}
		
		if (map.containsKey("item.id")) {
			sb.append(" and tpi.item=").append(map.get("item.id"));
		}

		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("tpi.").append(sort);
				comma = ",";
			}
		} else {
			sb.append(" order by tpi.inx ");
		}

		return this.getByHQL(sb.toString(), offset, length);

	}

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年9月24日 下午12:51:55 <br/>
	 * @param map
	 */
	public void deleteTestPaperItems(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("delete from TestPaperItem tpi where 1=1 ");

		if (map.containsKey("id")) {
			sb.append(" and tpi.id=").append(map.get("id"));
		}
		if (map.containsKey("paper.id")) {
			sb.append(" and tpi.paper=").append(map.get("paper.id"));
		}
		if (map.containsKey("testPaperSection.id")) {
			sb.append(" and tpi.testPaperSection=").append(map.get("testPaperSection.id"));
		}
		if (map.containsKey("item.id")) {
			sb.append(" and tpi.item=").append(map.get("item.id"));
		}

		this.executeHQL(sb.toString());

	}
}
