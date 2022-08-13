/** 
 * Project Name:Self_Cool 
 * File Name:SysUserGradeService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.ISysUserGradeDAO;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserGrade;
import cn.zeppin.service.api.ISysUserGradeService;

/** 
 * ClassName: SysUserGradeService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:25:38 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class SysUserGradeService implements ISysUserGradeService {

	private ISysUserGradeDAO sysUserGradeDAO;
	
	/**
	 * @return the sysUserGradeDAO
	 */
	public ISysUserGradeDAO getSysUserGradeDAO() {
		return sysUserGradeDAO;
	}

	/**
	 * @param sysUserGradeDAO the sysUserGradeDAO to set
	 */
	public void setSysUserGradeDAO(ISysUserGradeDAO sysUserGradeDAO) {
		this.sysUserGradeDAO = sysUserGradeDAO;
	}

//	/**
//	 * 添加用户学段权限
//	 * @author Clark
//	 * @date: 2014年7月8日 下午6:37:37 <br/> 
//	 * @param sysUserGrade
//	 * @return SysUserGrade
//	 */
//	@Override
//	public SysUserGrade addSysUserGrade(SysUserGrade sysUserGrade) {
//		// TODO Auto-generated method stub
//		return getSysUserGradeDAO().save(sysUserGrade);
//	}
	
	/**
	 * 获取用户的学段权限
	 * @param currentUser
	 * @return List<SysUserGrade>
	 */
	@Override
	public List<SysUserGrade> getSysUserGrades(SysUser currentUser) {
		// TODO Auto-generated method stub
		return this.getSysUserGradeDAO().getSysUserGrades(currentUser);
	}
	
}
