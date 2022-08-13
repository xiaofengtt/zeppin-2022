/** 
 * Project Name:CETV_TEST 
 * File Name:RoleFuncationDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;

import cn.zeppin.dao.api.IRoleFuncationDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.RoleFuncation;

/**
 * ClassName: RoleFuncationDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:50:22 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class RoleFuncationDAO extends HibernateTemplateDAO<RoleFuncation, Integer> implements IRoleFuncationDAO {

	/**
	 * 根据角色获取功能
	 * 
	 * @author Administrator
	 * @date: 2014年7月24日 下午1:39:47 <br/>
	 * @param role
	 * @return
	 */
	@Override
	public List<RoleFuncation> getRoleFunctionByRoleid(int role) {

		StringBuilder sb = new StringBuilder();
		sb.append("from RoleFuncation rf where 1=1 ");
		sb.append(" and rf.role=").append(role);
		return this.getByHQL(sb.toString());

	}

}
