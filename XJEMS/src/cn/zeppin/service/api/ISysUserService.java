package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SysUser;

/** 
 * ClassName: ISysUserService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 */
public interface ISysUserService {
	
	/**
	 * 通过ID获取用户对象
	 * @author Clark
	 * @param userID
	 * @return SysUser
	 */
	public SysUser getSysUserById(Integer id);
	
	/**
	 * 通过用户名密码获取用户Entity
	 * @author Clark
	 * @param loginname
	 * @param password
	 * @return SysUser
	 */
	public SysUser getSysUser(String loginname, String password, int status);
	
	/**
	 * 通过用户名密码获取用户Entity
	 * @author Clark
	 * @param loginname
	 * @param password
	 * @return SysUser
	 */
	public SysUser getSysUser(String loginname, String password);
	
	/**
	 * 通过用户名获取用户Entity
	 * @author Clark
	 * @param loginname
	 * @return SysUser
	 */
	public SysUser getSysUser(String loginname);
	
	/**
	 * 添加用户
	 * @author Clark
	 * @param sysUser
	 * @return SysUser
	 */
	public void addSysUser(SysUser sysUser);
	

	/**
	 * 删除用户
	 * @author Clark
	 * @param SysUser sysUser
	 * @return SysUser
	 */
	public void deleteSysUser(SysUser sysUser);

	/**
	 * 修改用户
	 * @author Clark
	 * @param sysUser
	 * @return SysUser
	 */
	public void updateSysUser(SysUser sysUser);
	

	/**
	 * 获得运营管理者用户的数量
	 * @author Clark
	 * @param  currentUser
	 * @return int
	 */
	public int getManagerCount(SysUser currentUser);

	/**
	 * 获取运营管理者的列表
	 * @author Clark
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	public List<SysUser> getManagers(SysUser currentUser, String sorts, int offset, int pagesize);


	/**
	 * 搜索符合条件的运营管理者列表
	 * @author Clark
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
	 * @param currentUser 
	 * @param searchMap
	 * @return count
	 */
	public int searchManagerCount(SysUser currentUser, Map<String, Object> searchMap);

	/**
	 * 搜索符合条件的合作机构用户列表
	 * @author Clark
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<SysUser> searchParter(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize);



}
