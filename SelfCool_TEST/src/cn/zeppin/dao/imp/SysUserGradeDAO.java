/** 
 * Project Name:Self_Cool 
 * File Name:SysUserGradeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;


import java.util.List;

import cn.zeppin.dao.api.ISysUserGradeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserGrade;

/** 
 * ClassName: SysUserGradeDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:45:06 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class SysUserGradeDAO extends HibernateTemplateDAO<SysUserGrade, Integer> 
	implements ISysUserGradeDAO {

	/**
	 * 删除用户的学段权限
	 * @author Clark
	 * @date: 2014年7月20日 下午6:02:25 <br/> 
	 * @param sysUser
	 */
	@Override
	public void deleteBySysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" delete from SysUserGrade where sysUser=").append(sysUser.getId());
		this.executeHQL(sb.toString());
	}

	/**
	 * 获取用户的学段权限
	 * @param currentUser
	 * @return List<SysUserGrade>
	 */
	@Override
	public List<SysUserGrade> getSysUserGrades(SysUser currentUser) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from SysUserGrade sg ");
		sb.append(" where sg.sysUser=").append(currentUser.getId());
//		sb.append(" and ss.grade.status=").append(Dictionary.GRADE_STATUS_NOMAL);
		return this.getByHQL(sb.toString());
	}


}
