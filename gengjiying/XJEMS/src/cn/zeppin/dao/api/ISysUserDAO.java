package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SysUser;

/**
 * ClassName: ISysUserDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface ISysUserDAO extends IBaseDAO<SysUser, Integer> {

	/**
	 * 通过用户名获取用户SysUser
	 * 
	 * @author Clark
	 * @param loginname
	 * @param password
	 */
	public SysUser getSysUser(String loginname);

	/**
	 * 通过用户名密码获取用户SysUser
	 * 
	 * @author Clark
	 * @param loginname
	 * @param password
	 */
	public SysUser getSysUser(String loginname, String password);

	/**
	 * 通过用户名密码和用户状态获取用户SysUser
	 * 
	 * @author Clark
	 * @param loginname
	 * @param password
	 */
	public SysUser getSysUser(String loginname, String password, Integer status);

	/**
	 * 获取某指定角色用户的全部记录(分页)
	 * 
	 * @author Clark
	 * @param userRoleEditor
	 * @return
	 */
	public List<SysUser> getSysUsersByRole(SysUser currentUser, String sorts, int offset, int length, Integer role);

	/**
	 * 获得指定角色的用户数量
	 * 
	 * @author Clark
	 * @param userRoleEditor
	 * @return
	 */
	public int getSysUserCountByRole(SysUser currentUser, Integer role);

	/**
	 * 根据条件搜索指定角色的用户
	 * @author Clark
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @param role
	 * @return List<SysUser>
	 */
	public List<SysUser> searchSysUser(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int length, Integer role);

	/**
	 * 根据条件搜索指定角色的用户数量
	 * @author Clark
	 * @param currentUser
	 * @param searchMap
	 * @param role
	 * @return count
	 */
	public int searchSysUserCount(SysUser currentUser, Map<String, Object> searchMap, Integer role);
}
