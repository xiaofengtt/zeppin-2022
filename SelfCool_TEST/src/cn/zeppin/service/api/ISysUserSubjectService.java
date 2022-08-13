/** 
 * Project Name:Self_Cool 
 * File Name:ISysUserSubjectService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserSubject;


/** 
 * ClassName: ISysUserSubjectService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:19:52 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ISysUserSubjectService {



//	/**
//	 * 添加用户学科权限
//	 * @author Clark
//	 * @date: 2014年7月8日 下午6:23:46 <br/> 
//	 * @param sysUserSubject
//	 * @return SysUserSubject
//	 */
//	public SysUserSubject addSysUserSubject(SysUserSubject sysUserSubject);
	
	/**
	 * 获取用户的学科权限
	 * @param currentUser
	 * @return  List<SysUserSubject>
	 */
	public List<SysUserSubject> getSysUserSubjects(SysUser currentUser);
}
