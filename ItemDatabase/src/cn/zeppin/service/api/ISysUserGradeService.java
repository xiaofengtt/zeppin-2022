/** 
 * Project Name:ItemDatabase 
 * File Name:ISysUserGradeService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserGrade;


/** 
 * ClassName: ISysUserGradeService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:20:06 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ISysUserGradeService {



//	/**
//	 * 添加用户学段权限
//	 * @author Clark
//	 * @date: 2014年7月8日 下午6:37:37 <br/> 
//	 * @param sysUserGrade
//	 * @return SysUserGrade
//	 */
//	public SysUserGrade addSysUserGrade(SysUserGrade sysUserGrade);
	
	/**
	 * 获取用户的学段权限
	 * @param currentUser
	 * @return List<SysUserGrade>
	 */
	public List<SysUserGrade> getSysUserGrades(SysUser currentUser);
}
