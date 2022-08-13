/** 
 * Project Name:CETV_TEST 
 * File Name:UserDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.User;

/**
 * ClassName: UserDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月14日 下午12:47:08 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserDAO extends HibernateTemplateDAO<User, Integer> implements IUserDAO {

	/**
	 * 根据Map来获取User
	 * 
	 * @author Administrator
	 * @date: 2014年10月20日 上午11:11:42 <br/>
	 * @param map
	 * @return
	 */
	public User getUserByMap(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append(" from User u where 1=1 ");
		if (map.get("account") != null) {
			sb.append(" and u.account=").append(map.get("account"));
		}

		if (map.get("password") != null) {
			sb.append(" and u.password=").append(map.get("password"));
		}

		List<User> lstUser = this.getByHQL(sb.toString());
		if (lstUser != null && lstUser.size() > 0) {
			return lstUser.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 更新用户信息
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:21:10 <br/> 
	 * @param user
	 */
	public void updateUser(User user){
		this.update(user);
	}

	/**
	 * 根据Map来获取Count
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:21:10 <br/> 
	 * @param map
	 */
	public int getUserCountByMap(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append(" from User u where 1=1 ");
		if (map.get("account") != null) {
			sb.append(" and u.account=").append(map.get("account"));
		}

		if (map.get("password") != null) {
			sb.append(" and u.password=").append(map.get("password"));
		}
		
		if (map.get("subject.id") != null) {
			sb.append(" and u.subject=").append(map.get("subject.id"));
		}

		if (map.get("grade.scode") != null){
			sb.append(" and u.grade.scode like '").append(map.get("gradescode")).append("%'");
		}
		int result = Integer.parseInt(getResultByHQL(sb.toString()).toString());
		
		return result;
	}
}
