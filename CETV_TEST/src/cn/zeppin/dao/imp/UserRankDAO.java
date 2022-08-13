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

import cn.zeppin.dao.api.IUserRankDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserRank;

/**
 * ClassName: SubjectDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:06:19 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class UserRankDAO extends HibernateTemplateDAO<UserRank, Integer> implements IUserRankDAO {
	
	/**
	 * 根据Name来获取Subject
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:05:22 <br/>
	 * @param name
	 * @return
	 */
	@Override
	public UserRank getUserRankByName(String name) {

		StringBuilder sb = new StringBuilder();
		sb.append("from UserRank s where 1=1").append(" and s.name='").append(name).append("'");

		List<UserRank> liT = this.getByHQL(sb.toString());

		if (liT != null && liT.size() > 0) {
			return liT.get(0);
		}

		return null;
	}

	@Override
	public int getUserRankCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserRank s where 1=1 ");
		Object result = this.getResultByHQL(sb.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}
		return 0;
	}

	@Override
	public List<UserRank> getUserRank(HashMap<String, Object> params,int offset, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append("from UserRank s where 1=1 ");
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
