/** 
 * Project Name:CETV_TEST 
 * File Name:UserTestItemDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserTestItemDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserTestItem;

/**
 * ClassName: UserTestItemDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月14日 下午3:21:40 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserTestItemDAO extends HibernateTemplateDAO<UserTestItem, Long> implements IUserTestItemDAO {

	/**
	 * 添加用户做题记录
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/>
	 * @param ut
	 */
	public void addUserTestItem(UserTestItem uti) {
		this.save(uti);
	}

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年10月28日 上午11:49:25 <br/>
	 * @param map
	 * @return
	 */
	public int getUserTestItemCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserTestItem uti where 1=1 ");

		if (map.get("usertest.id") != null) {
			sb.append(" and uti.userTest=").append(map.get("usertest.id"));
		}
		if (map.get("item.id") != null) {
			sb.append(" and uti.item=").append(map.get("item.id"));
		}		
		if (map.get("item.knowledge.scode") != null){
			sb.append(" and uti.item.knowledge.scode like'").append(map.get("item.knowledge.scode")).append("%'");
		}
		if (map.get("result") != null&&map.get("result").equals("true")){
			sb.append(" and uti.score=uti.itemAnswer.score");
		}
		return ((Long) this.getResultByHQL(sb.toString())).intValue();

	}

	/**
	 * 获取列表
	 * 
	 * @author Administrator
	 * @date: 2014年10月28日 上午11:49:36 <br/>
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTestItem> getUserTestItem(Map<String, Object> map, String sorts, int offset, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append("from UserTestItem uti where 1=1 ");

		if (map.get("usertest.id") != null) {
			sb.append(" and uti.userTest=").append(map.get("usertest.id"));
		}

		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("uti.").append(sort);
				comma = ",";
			}
		} else {
			sb.append(" order by uti.inx");
		}

		return this.getByHQL(sb.toString(), offset, length);
	}

}
