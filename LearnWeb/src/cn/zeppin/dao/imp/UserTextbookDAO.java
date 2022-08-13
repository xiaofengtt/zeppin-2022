/** 
 * Project Name:CETV_TEST 
 * File Name:UserTextbookDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserTextbookDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserTextbook;

/**
 * ClassName: UserTextbookDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月14日 下午3:28:49 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserTextbookDAO extends HibernateTemplateDAO<UserTextbook, Integer> implements IUserTextbookDAO {

	/**
	 * 搜索UserTextbook
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:41 <br/>
	 * @param map
	 * @return
	 */
	public UserTextbook getUserTextbookByMap(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("from UserTextbook utb where 1=1 ");

		if (map.get("user.id") != null) {
			sb.append(" and utb.user=").append(map.get("user.id"));
		}

		if (map.get("grade.id") != null) {
			sb.append(" and utb.grade=").append(map.get("grade.id"));
		}

		if (map.get("subject.id") != null) {
			sb.append(" and utb.subject=").append(map.get("subject.id"));
		}

		List<UserTextbook> lstUtb = this.getByHQL(sb.toString());
		if (lstUtb != null && lstUtb.size() > 0) {
			return lstUtb.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 添加UserTextbook
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:56 <br/>
	 * @param book
	 */
	public void addUserTextbook(UserTextbook book) {
		this.save(book);
	}

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:45:10 <br/>
	 * @param book
	 */
	public void deleteUserTextbook(UserTextbook book) {
		this.delete(book);
	}

}
