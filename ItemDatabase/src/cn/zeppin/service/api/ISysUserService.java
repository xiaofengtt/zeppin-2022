/** 
 * Project Name:ItemDatabase 
 * File Name:ISysUserService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;

/** 
 * ClassName: ISysUserService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月17日 下午4:22:41 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ISysUserService {
	
	/**
	 * 通过ID获取用户对象
	 * @author Clark
	 * @date: 2014年7月14日 下午5:58:07 <br/> 
	 * @param userID
	 * @return SysUser
	 */
	public SysUser getSysUser(Integer id);
	
	/**
	 * 通过用户名密码获取用户Entity
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/> 
	 * @param loginname
	 * @param password
	 * @return SysUser
	 */
	public SysUser getSysUser(String loginname, String password, int status);
	
	/**
	 * 通过用户名密码获取用户Entity
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/> 
	 * @param loginname
	 * @param password
	 * @return SysUser
	 */
	public SysUser getSysUser(String loginname, String password);
	
	/**
	 * 通过用户名获取用户Entity
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/> 
	 * @param loginname
	 * @return SysUser
	 */
	public SysUser getSysUser(String loginname);
	
	/**
	 * 添加用户
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/> 
	 * @param sysUser
	 * @return SysUser
	 */
	public SysUser addSysUser(SysUser sysUser);
	
	/**
	 * 添加用户
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/> 
	 * @param sysUser
	 * @param gradeList 
	 * @param subjectList 
	 * @return SysUser
	 */
	public SysUser addSysUser(SysUser sysUser, List<Subject> subjectList, List<Grade> gradeList);

	/**
	 * 删除用户
	 * @author Clark
	 * @date: 2014年7月15日 下午8:14:30 <br/> 
	 * @param SysUser sysUser
	 * @return SysUser
	 */
	public SysUser deleteSysUser(SysUser sysUser);

	/**
	 * 修改用户
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/> 
	 * @param sysUser
	 * @return SysUser
	 */
	public SysUser updateSysUser(SysUser sysUser);
	
	/**
	 * 修改用户
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/> 
	 * @param sysUser
	 * @param subjectList
	 * @param gradeList
	 * @return SysUser
	 */
	public SysUser updateSysUser(SysUser sysUser, List<Subject> subjectList, List<Grade> gradeList);

	/**
	 * 获得编辑用户的数量
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/> 
	 * @param  currentUser
	 * @return int
	 */
	public int getEditorCount(SysUser currentUser);
	
	/**
	 * 获取编辑用户的列表
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/> 
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	public List<SysUser> getEditors(SysUser currentUser, String sorts, int offset, int pagesize);
	
	
	/**
	 * 搜索符合条件的编辑数量
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/> 
	 * @param currentUser 
	 * @param searchMap
	 * @return count
	 */
	public int searchEditorCount(SysUser currentUser, Map<String, Object> searchMap);
	
	/**
	 * 搜索符合条件的编辑列表
	 * @author Clark
	 * @param currentUser 
	 * @date: 2014年7月20日 下午7:43:13 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	public List<SysUser> searchEditor(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize);
	
	/**
	 * 获得运营管理者用户的数量
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/> 
	 * @param  currentUser
	 * @return int
	 */
	public int getManagerCount(SysUser currentUser);

	/**
	 * 获取运营管理者的列表
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/> 
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	public List<SysUser> getManagers(SysUser currentUser, String sorts, int offset, int pagesize);

	/**
	 * 获得合作方用户的数量
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/> 
	 * @param  currentUser
	 * @return int
	 */
	public int getParterCount(SysUser currentUser);

	/**
	 * 获取合作方用户的列表
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/> 
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	public List<SysUser> getParters(SysUser currentUser, String sorts, int offset, int pagesize);

	/**
	 * 搜索符合条件的运营管理者列表
	 * @author Clark
	 * @date: 2014年7月21日 下午7:16:39 <br/> 
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<SysUser> searchManager(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset,int pagesize);

	/**
	 * 搜索符合条件的运营管理者数量
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/> 
	 * @param currentUser 
	 * @param searchMap
	 * @return count
	 */
	public int searchManagerCount(SysUser currentUser, Map<String, Object> searchMap);

	/**
	 * 搜索符合条件的合作机构用户列表
	 * @author Clark
	 * @date: 2014年7月21日 下午7:38:34 <br/> 
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<SysUser> searchParter(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize);

	/**
	 * 搜索符合条件的合作机构用户数量
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/> 
	 * @param currentUser 
	 * @param searchMap
	 * @return count
	 */
	public int searchParterCount(SysUser currentUser, Map<String, Object> searchMap);

	/**
	 * 用户是否有某学段的操作权限
	 * @author Clark
	 * @date: 2014年7月23日 上午11:35:18 <br/> 
	 * @param currentUser
	 * @param grade
	 * @return boolean
	 */
	public boolean isCanOpt(SysUser currentUser, Grade grade);

	/**
	 * 用户是否有某学科的操作权限
	 * @author Clark
	 * @date: 2014年7月23日 上午11:36:21 <br/> 
	 * @param currentUser
	 * @param subject
	 * @return boolean
	 */
	public boolean isCanOpt(SysUser currentUser, Subject subject);
	
	/**
	 * 获取用户的学科权限
	 * @author Administrator
	 * @date: 2014年8月27日 上午10:30:26 <br/> 
	 * @param currentUser
	 * @return
	 */
	public List<Subject> getSubjectBySysuser(SysUser currentUser);
	
	
	/**
	 * 获取用户学段权限
	 * @author Administrator
	 * @date: 2014年8月27日 上午10:30:45 <br/> 
	 * @param currentUser
	 * @return
	 */
	public List<Grade> getGradeBySysuser(SysUser currentUser);



}
