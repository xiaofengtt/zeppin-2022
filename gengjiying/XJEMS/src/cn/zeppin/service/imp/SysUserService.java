package cn.zeppin.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISysUserDAO;
import cn.zeppin.entity.SysUser;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;

/**
 * ClassName: SysUserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class SysUserService implements ISysUserService {

	private ISysUserDAO sysUserDAO;

	/**
	 * @return the sysUserDAO
	 */
	public ISysUserDAO getSysUserDAO() {
		return sysUserDAO;
	}

	/**
	 * @param sysUserDAO
	 *            the sysUserDAO to set
	 */
	public void setSysUserDAO(ISysUserDAO sysUserDAO) {
		this.sysUserDAO = sysUserDAO;
	}

	/**
	 * 通过ID获取用户对象
	 * 
	 * @author Clark
	 * @param userID
	 * @return SysUser
	 */
	@Override
	public SysUser getSysUserById(Integer id) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().get(id);
	}

	/**
	 * 通过用户名密码获取用户Entity
	 * 
	 * @author Clark
	 * @param loginname
	 * @param password
	 * @return SysUser
	 */
	@Override
	public SysUser getSysUser(String loginname, String password) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().getSysUser(loginname, password);
	}

	/**
	 * 通过用户名获取用户Entity
	 * 
	 * @author Clark
	 * @param loginname
	 * @return SysUser
	 */
	@Override
	public SysUser getSysUser(String loginname) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().getSysUser(loginname);
	}

	/**
	 * 通过用户名密码和用户状态获取用户SysUser
	 * 
	 * @author Clark
	 * @param loginname
	 * @param password
	 */
	@Override
	public SysUser getSysUser(String loginname, String password, int status) {
		// TODO Auto-generated method stub
		return this.getSysUserDAO().getSysUser(loginname, password, status);
	}

	/**
	 * 添加用户
	 * 
	 * @author Clark
	 * @param sysUser
	 * @return SysUser
	 */
	@Override
	public void addSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		getSysUserDAO().save(sysUser);
	}

	/**
	 * 删除用户，这里对用户仅做逻辑删除
	 * 
	 * @author Clark
	 * @param userID
	 * @return SysUser
	 */
	@Override
	public void deleteSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		// 停用，不真删
		sysUser.setStatus(Dictionary.USER_STATUS_CLOSED);
		this.getSysUserDAO().update(sysUser);
	}

	/**
	 * 修改用户
	 * 
	 * @author Clark
	 * @param sysUser
	 * @return SysUser
	 */
	@Override
	public void updateSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		this.getSysUserDAO().update(sysUser);
	}


	/**
	 * 获得运营管理者用户的数量
	 * 
	 * @author Clark
	 * @param currentUser
	 * @return int
	 */
	@Override
	public int getManagerCount(SysUser currentUser) {
		// TODO Auto-generated method stub
		return getSysUserDAO().getSysUserCountByRole(currentUser, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 获取运营管理者的列表
	 * 
	 * @author Clark
	 * @param currentUser
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> getManagers(SysUser currentUser, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return getSysUserDAO().getSysUsersByRole(currentUser, sorts, offset, pagesize, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 搜索符合条件的运营管理者列表
	 * 
	 * @author Clark
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@Override
	public List<SysUser> searchManager(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return getSysUserDAO().searchSysUser(currentUser, searchMap, sorts, offset, pagesize, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 搜索符合条件的运营管理者数量
	 * 
	 * @author Clark
	 * @param currentUser
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchManagerCount(SysUser currentUser, Map<String, Object> searchMap) {
		return getSysUserDAO().searchSysUserCount(currentUser, searchMap, Dictionary.USER_ROLE_MANAGER);
	}

	/**
	 * 搜索符合条件的合作机构用户列表
	 * 
	 * @author Clark
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@Override
	public List<SysUser> searchParter(SysUser currentUser, Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		List<SysUser> result = new ArrayList<>();
		result = getSysUserDAO().searchSysUser(currentUser, searchMap, sorts, offset, pagesize, null);
		return result;
	}

}
