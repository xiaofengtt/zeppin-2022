/** 
 * Project Name:Self_Cool 
 * File Name:SysUserSubjectService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.ISysUserSubjectDAO;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserSubject;
import cn.zeppin.service.api.ISysUserSubjectService;

/** 
 * ClassName: SysUserSubjectService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:24:06 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class SysUserSubjectService implements ISysUserSubjectService {
	
	private ISysUserSubjectDAO sysUserSubjectDAO;
	
	
	/**
	 * @return the sysUserSubjectDAO
	 */
	public ISysUserSubjectDAO getSysUserSubjectDAO() {
		return sysUserSubjectDAO;
	}

	/**
	 * @param sysUserSubjectDAO the sysUserSubjectDAO to set
	 */
	public void setSysUserSubjectDAO(ISysUserSubjectDAO sysUserSubjectDAO) {
		this.sysUserSubjectDAO = sysUserSubjectDAO;
	}


	
//	/**
//	 * 添加用户学科权限
//	 * @author Clark
//	 * @date: 2014年7月8日 下午6:23:46 <br/> 
//	 * @param sysUserSubject
//	 * @return SysUserSubject
//	 */
//	@Override
//	public SysUserSubject addSysUserSubject(SysUserSubject sysUserSubject) {
//		// TODO Auto-generated method stub
//		return getSysUserSubjectDAO().save(sysUserSubject);
//	}
	
	/**
	 * 获取用户的学科权限
	 * @param currentUser
	 * @return  List<SysUserSubject>
	 */
	@Override
	public List<SysUserSubject> getSysUserSubjects(SysUser currentUser) {
		// TODO Auto-generated method stub
		return getSysUserSubjectDAO().getSysUserSubjects(currentUser);
	}

}
