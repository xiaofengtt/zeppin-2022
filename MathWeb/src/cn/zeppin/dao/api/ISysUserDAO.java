/** 
 * Project Name:CETV_TEST 
 * File Name:ISysUserDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SysUser;

/**
 * ClassName: ISysUserDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:10:32 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface ISysUserDAO extends IBaseDAO<SysUser, Integer> {

	/**
	 * 通过用户名获取用户SysUser
	 * 
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/>
	 * @param loginname
	 * @param password
	 */
	public SysUser getSysUser(String loginname);

	/**
	 * 通过用户名密码获取用户SysUser
	 * 
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/>
	 * @param loginname
	 * @param password
	 */
	public SysUser getSysUser(String loginname, String password);

	/**
	 * 通过用户名密码和用户状态获取用户SysUser
	 * 
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/>
	 * @param loginname
	 * @param password
	 */
	public SysUser getSysUser(String loginname, String password, Integer status);

	/**
	 * 获取某指定角色用户的全部记录(分页)
	 * 
	 * @author Clark
	 * @date: 2014年6月22日 下午3:24:33 <br/>
	 * @param userRoleEditor
	 * @return
	 */
	public List<SysUser> getSysUsersByRole(SysUser currentUser, String sorts, int offset, int length, Integer role);

	/**
	 * 获得指定角色的用户数量
	 * 
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/>
	 * @param userRoleEditor
	 * @return
	 */
	public int getSysUserCountByRole(SysUser currentUser, Integer role);

	/**
	 * 根据条件搜索指定角色的用户
	 * @author Clark
	 * @date: 2014年7月20日 下午8:18:07 <br/> 
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @param role
	 * @return List<SysUser>
	 */
	public List<SysUser> searchSysUser(SysUser currentUser, Map<String, Object> searchMap, 
			String sorts, int offset, int length, Integer role);

	/**
	 * 根据条件搜索指定角色的用户数量
	 * @author Clark
	 * @date: 2014年7月20日 下午8:30:10 <br/> 
	 * @param currentUser
	 * @param searchMap
	 * @param role
	 * @return count
	 */
	public int searchSysUserCount(SysUser currentUser, Map<String, Object> searchMap, Integer role);
}
