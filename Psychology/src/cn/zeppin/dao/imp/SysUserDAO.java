/** 
 * Project Name:CETV_TEST 
 * File Name:SysUserDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISysUserDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SysUser;
import cn.zeppin.utility.Dictionary;

/** 
 * ClassName: SysUserDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午10:09:38 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class SysUserDAO extends HibernateTemplateDAO<SysUser, Integer> implements ISysUserDAO{

	
	/**
	 * 通过用户名获取用户SysUser
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/> 
	 * @param loginname
	 * @param password
	 */
	@Override
	public SysUser getSysUser(String loginname) {
		// TODO Auto-generated method stub
		return this.getSysUser(loginname, null, null);
	}
	
	/**
	 * 通过用户名密码获取用户SysUser
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/> 
	 * @param loginname
	 * @param password
	 */
	@Override
	public SysUser getSysUser(String loginname, String password) {
		// TODO Auto-generated method stub
		return this.getSysUser(loginname, password, null);
	}
	
	/**
	 * 通过用户名密码和用户状态获取用户SysUser
	 * @author Clark
	 * @date: 2014年6月17日 下午4:40:26 <br/> 
	 * @param loginname
	 * @param password
	 */
	@Override
	public SysUser getSysUser(String loginname, String password, Integer status) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from SysUser su where 1=1 ");
		hql.append(" and (su.email='").append(loginname).append("'");
		hql.append(" or su.phone='").append(loginname).append("')");
		if (password != null && password.length() > 0){
			hql.append(" and su.password='").append(password).append("'");
		}
		if (status != null){
			hql.append(" and su.status=").append(status);
		}
//		if (status != null && status.length > 0){
//			hql.append(" and su.status in (");
//			String comma = "";
//			for(int state : status){
//				hql.append(comma);
//				hql.append(state);
//				comma = ",";
//			}
//			hql.append(")");
//		}
		List<SysUser> userlist = this.getByHQL(hql.toString());
		if (userlist!=null && userlist.size()>0){
			return userlist.get(0);
		}
		return null;
	}

	/**
	 * 获取某一种角色用户的全部记录(分页)
	 * @author Clark
	 * @date: 2014年6月22日 下午3:24:33 <br/> 
	 * @param userRoleEditor
	 * @return
	 */
	@Override
	public List<SysUser> getSysUsersByRole(SysUser currentUser, String sorts, int offset, int length, Integer role) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from SysUser su where 1=1 ");
		
//		hql.append(" and su.status=1 ");
//		if (roles != null && roles.length > 0){
//			hql.append(" and su.role in (");
//			String comma = "";
//			for(int role : roles){
//				hql.append(comma);
//				hql.append(role);
//				comma = ",";
//			}
//			hql.append(")");
//		}
		if (role != null){
			hql.append(" and su.role=").append(role);
		}
		//合作机构用户只能查询本部门的管理员
		if (currentUser != null && (currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER ||
				currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR))
		{
			hql.append(" and su.organization=").append(currentUser.getOrganization().getId());
		}
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				hql.append(comma);
				hql.append("su.").append(sort);
				comma = ",";
			}
			
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

	/** 
	 * 获得一种角色的用户数量
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/> 
	 * @param userRoleEditor
	 * @return
	 */
	@Override
	public int getSysUserCountByRole(SysUser currentUser, Integer role) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from SysUser su where 1=1 ");
		if (role != null){
			hql.append(" and su.role=").append(role);
		}
//		if (roles != null && roles.length > 0){
//			hql.append(" and su.role in (");
//			String comma = "";
//			for(int role : roles){
//				hql.append(comma);
//				hql.append(role);
//				comma = ",";
//			}
//			hql.append(")");
//		}
		//合作机构用户只能查询本部门的管理员
		if (currentUser != null && (currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER ||
				currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR))
		{
			hql.append(" and su.organization=").append(currentUser.getOrganization().getId());
		}
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}

	/**
	 * 根据条件搜索指定角色的用户
	 * @author Clark
	 * @date: 2014年7月20日 下午8:18:07 <br/> 
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @param roles
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> searchSysUser(SysUser currentUser, Map<String, Object> searchMap, 
			String sorts, int offset, int length, Integer role) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from SysUser su where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("email") != null  && !searchMap.get("email").equals("")){
			hql.append(" and su.email like '%").append(searchMap.get("email")).append("%' ");
		}
		if (searchMap.get("phone") != null && !searchMap.get("phone").equals("")){
			hql.append(" and su.phone like '%").append(searchMap.get("phone")).append("%' ");
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			hql.append(" and su.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("organization.id") != null && !searchMap.get("organization.id").equals("")){
			hql.append(" and su.organization.id=").append(searchMap.get("organization.id"));
		}
		if (searchMap.get("organization.name") != null && !searchMap.get("organization.name").equals("")){
			hql.append(" and su.organization.name like '%").append(searchMap.get("organization.name")).append("%' ");
		}
		if (searchMap.get("sysUser.id") != null && !searchMap.get("sysUser.id").equals("")){
			hql.append(" and su.sysUser.id=").append(searchMap.get("sysUser.id"));
		}
		if (searchMap.get("sysUser.name") != null && !searchMap.get("sysUser.name").equals("")){
			hql.append(" and su.sysUser.name like '%").append(searchMap.get("sysUser.name")).append("%' ");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (role != null){
			hql.append(" and su.role.id=").append(role);
		}
		if (searchMap.get("organization.type") != null && !searchMap.get("organization.type").equals("")){
			hql.append(" and su.organization.type=").append(searchMap.get("organization.type"));
		}
//		if (roles != null && roles.length > 0){
//			hql.append(" and su.role in (");
//			String comma = "";
//			for(int role : roles){
//				hql.append(comma);
//				hql.append(role);
//				comma = ",";
//			}
//			hql.append(") ");
//		}
		
		//合作机构用户只能查询本部门的管理员
		if (currentUser != null && (currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER ||
				currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR))
		{
			hql.append(" and su.organization.id=").append(currentUser.getOrganization().getId());
		}
		//排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				hql.append(comma);
				hql.append("su.").append(sort);
				comma = ",";
			}
			
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

	/**
	 * 根据条件搜索指定角色的用户数量
	 * @author Clark
	 * @date: 2014年7月20日 下午8:30:10 <br/> 
	 * @param currentUser
	 * @param searchMap
	 * @param roles
	 * @return count
	 */
	@Override
	public int searchSysUserCount(SysUser currentUser, Map<String, Object> searchMap, Integer role) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from SysUser su where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("email") != null  && !searchMap.get("email").equals("")){
			hql.append(" and su.email like '%").append(searchMap.get("email")).append("%' ");
		}
		if (searchMap.get("phone") != null && !searchMap.get("phone").equals("")){
			hql.append(" and su.phone like '%").append(searchMap.get("phone")).append("%' ");
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			hql.append(" and su.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("organization.id") != null && !searchMap.get("organization.id").equals("")){
			hql.append(" and su.organization.id=").append(searchMap.get("organization.id"));
		}
		if (searchMap.get("organization.name") != null && !searchMap.get("organization.name").equals("")){
			hql.append(" and su.organization.name like '%").append(searchMap.get("organization.name")).append("%' ");
		}
		if (searchMap.get("sysUser.id") != null && !searchMap.get("sysUser.id").equals("")){
			hql.append(" and su.sysUser.id=").append(searchMap.get("sysUser.id"));
		}
		if (searchMap.get("sysUser.name") != null && !searchMap.get("sysUser.name").equals("")){
			hql.append(" and su.sysUser.name like '%").append(searchMap.get("sysUser.name")).append("%' ");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (role != null){
			hql.append(" and su.role.id=").append(role);
		}
		
		//合作机构用户只能查询本部门的管理员
		if (currentUser != null && (currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER ||
				currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR))
		{
			hql.append(" and su.organization.id=").append(currentUser.getOrganization().getId());
		}
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}
	

}
