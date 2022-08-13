/** 
 * Project Name:Self_Cool 
 * File Name:ISysUserSubjectDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserSubject;

/** 
 * ClassName: ISysUserSubjectDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:42:56 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ISysUserSubjectDAO extends IBaseDAO<SysUserSubject, Integer> {

	/**
	 * 删除用户的学科权限
	 * @author Clark
	 * @date: 2014年7月20日 下午6:02:25 <br/> 
	 * @param sysUser
	 */
	public void deleteBySysUser(SysUser sysUser);

	/**
	 * 获取用户的学科权限
	 * @param currentUser
	 * @return  List<SysUserSubject>
	 */
	public List<SysUserSubject> getSysUserSubjects(SysUser currentUser);

}
