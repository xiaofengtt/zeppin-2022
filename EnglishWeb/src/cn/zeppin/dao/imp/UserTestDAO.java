/** 
 * Project Name:CETV_TEST 
 * File Name:UserTestDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserTestDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserTest;

/**
 * ClassName: UserTestDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月14日 下午2:15:36 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserTestDAO extends HibernateTemplateDAO<UserTest, Long> implements IUserTestDAO {

	/**
	 * 添加用户考试记录
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/>
	 * @param ut
	 */
	public void addUserTest(UserTest ut) {

		this.save(ut);

	}

	/**
	 * 计算个数
	 * 
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:04:40 <br/>
	 * @param map
	 * @return
	 */
	public int getUserTestCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserTest ut where 1=1 ");

		if (map.get("user.id") != null) {
			sb.append(" and ut.user=").append(map.get("user.id"));
		}

		if (map.get("paper.type") != null) {

			String type = map.get("paper.type").toString();

			if (type.indexOf(",") > -1) {

				sb.append(" and ut.paper.type in(").append(map.get("paper.type")).append(") ");

			} else {
				sb.append(" and ut.paper.type=").append(map.get("paper.type"));
			}

		}

		if (map.get("paper.id") != null) {
			sb.append(" and ut.paper=").append(map.get("paper.id"));
		}

		if (map.get("duration") != null) {
			sb.append(" and ut.duration<").append(map.get("duration"));
		}

		if (map.get("score") != null) {
			sb.append(" and ut.score<").append(map.get("score"));
		}

		if (map.get("paper.subject.id") != null) {
			sb.append(" and ut.paper.subject.id=").append(map.get("paper.subject.id"));
		}

		if (map.get("status") != null) {
			sb.append(" and ut.status=").append(map.get("status"));
		}
		return ((Long) this.getResultByHQL(sb.toString())).intValue();

	}

	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:05:06 <br/>
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTest> getUserTest(Map<String, Object> map, String sorts, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from UserTest ut where 1=1 ");

		if (map.get("user.id") != null) {
			sb.append(" and ut.user=").append(map.get("user.id"));
		}

		if (map.get("paper.type") != null) {

			String type = map.get("paper.type").toString();

			if (type.indexOf(",") > -1) {

				sb.append(" and ut.paper.type in(").append(map.get("paper.type")).append(") ");

			} else {
				sb.append(" and ut.paper.type=").append(map.get("paper.type"));
			}

		}

		if (map.get("paper.id") != null) {
			sb.append(" and ut.paper=").append(map.get("paper.id"));
		}

		if (map.get("duration") != null) {
			sb.append(" and ut.duration<").append(map.get("duration"));
		}

		if (map.get("score") != null) {
			sb.append(" and ut.score<").append(map.get("score"));
		}

		if (map.get("paper.subject.id") != null) {
			sb.append(" and ut.paper.subject.id=").append(map.get("paper.subject.id"));
		}

		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("ut.").append(sort);
				comma = ",";
			}
		} else {
			sb.append(" order by ut.starttime desc");
		}

		return this.getByHQL(sb.toString(), offset, length);

	}

	/**
	 * 获取试卷的品均值(平均时间和平均成绩)
	 * 
	 * @author Administrator
	 * @date: 2014年10月28日 下午2:48:31 <br/>
	 * @param paperId
	 * @return
	 */
	public List<Object[]> getUserTestAvgByPaper(int paperId) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT AVG(t.duration) ,AVG(t.score) FROM user_test t  WHERE t.paper=").append(paperId);

		return this.getBySQL(sb.toString());
	}

}