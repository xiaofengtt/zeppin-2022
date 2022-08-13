/** 
 * Project Name:ItemDatabase 
 * File Name:ISysUserGradeDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserGrade;

/** 
 * ClassName: ISysUserGradeDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:41:37 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ISysUserGradeDAO extends IBaseDAO<SysUserGrade, Integer> {

	/**
	 * 删除用户的学段权限
	 * @author Clark
	 * @date: 2014年7月20日 下午6:01:37 <br/> 
	 * @param sysUser
	 */
	public void deleteBySysUser(SysUser sysUser);

	/**
	 * 获取用户的学段权限
	 * @param currentUser
	 * @return List<SysUserGrade>
	 */
	public List<SysUserGrade> getSysUserGrades(SysUser currentUser);

}
