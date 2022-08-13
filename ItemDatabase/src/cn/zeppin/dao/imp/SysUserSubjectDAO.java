/** 
 * Project Name:ItemDatabase 
 * File Name:SysUserSubjectDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;



import java.util.List;

import cn.zeppin.dao.api.ISysUserSubjectDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserSubject;

/** 
 * ClassName: SysUserSubjectDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:43:46 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class SysUserSubjectDAO extends HibernateTemplateDAO<SysUserSubject, Integer> implements
		ISysUserSubjectDAO {

	/**
	 * 删除用户的学科权限
	 * @author Clark
	 * @date: 2014年7月20日 下午6:02:25 <br/> 
	 * @param sysUser
	 */
	@Override
	public void deleteBySysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" delete from SysUserSubject where sysUser=").append(sysUser.getId());
		this.executeHQL(sb.toString());
	}

	/**
	 * 获取用户的学科权限
	 * @param currentUser
	 * @return  List<SysUserSubject>
	 */
	@Override
	public List<SysUserSubject> getSysUserSubjects(SysUser currentUser) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from SysUserSubject ss ");
		sb.append(" where ss.sysUser=").append(currentUser.getId());
//		sb.append(" and ss.subject.status=").append(Dictionary.SUBJECT_STATUS_NOMAL);
		return this.getByHQL(sb.toString());
	}


}
