/** 
 * Project Name:CETV_TEST 
 * File Name:SysUserAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Role;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IOrganizationService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: SysUserAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月17日 下午3:26:53 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class ParterAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3909927948294558147L;

	private ISysUserService sysUserService;
	private IOrganizationService organizationService;

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	/**
	 * 添加合作方用户（负责人、编辑）
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:54 <br/>
	 */
	@AuthorityParas(userGroupName = "EX_MANAGER_ADD_EDIT")
	@ActionParam(key = "role.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "phone", type = ValueType.PHONE, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.EMAIL, nullable = false, emptyable = false)
	@ActionParam(key = "organization.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		Integer roleID = Integer.valueOf(request.getParameter("role.id"));
		Integer orgID = Integer.valueOf(request.getParameter("organization.id"));
		Organization organization = this.getOrganizationService().getOrganizationById(orgID);
		if (organization == null) {
			result.init(FAIL_STATUS, "无效的机构id信息！", null);
		} else if (getSysUserService().getSysUser(email) != null) {
			result.init(FAIL_STATUS, "已存在该邮箱的用户！", null);
		} else if (getSysUserService().getSysUser(phone) != null) {
			result.init(FAIL_STATUS, "已存在该手机的用户！", null);
		} else if (roleID == Dictionary.USER_ROLE_EX_EDITOR || roleID == Dictionary.USER_ROLE_EX_MANAGER) {
			SysUser sysUser = new SysUser();
			sysUser.setRole(new Role());
			sysUser.getRole().setId(roleID);
			sysUser.setEmail(email);
			sysUser.setPhone(phone);
			sysUser.setName(name);
			sysUser.setStatus(Dictionary.USER_STATUS_NOMAL);
			sysUser.setPassword(phone.substring(phone.length() - 6, phone.length()));

			sysUser.setOrganization(new Organization());
			sysUser.getOrganization().setId(orgID);
			sysUser.setSysUser(currentUser); // 创建人
			sysUser.setCreatetime(new Timestamp((new Date()).getTime()));
			sysUser = this.getSysUserService().addSysUser(sysUser);
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			result.init(SUCCESS_STATUS, "添加合作方用户成功！", data);
		} else {
			result.init(FAIL_STATUS, "角色信息不合法！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载合作单位管理员信息，
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午3:06:23 <br/>
	 */
	@AuthorityParas(userGroupName = "EX_MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		int roleId = currentUser.getRole().getId();

		Integer userID = Integer.valueOf(request.getParameter("id"));
		SysUser sysUser = this.getSysUserService().getSysUser(userID);
		if (sysUser != null && (sysUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER || sysUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR)) {

			if (roleId == Dictionary.USER_ROLE_EX_MANAGER && currentUser.getOrganization().getId() != sysUser.getOrganization().getId()) {
				result.init(FAIL_STATUS, "该管理员不能管理其他合作机构用户！", null);
			} else {
				Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
				result.init(SUCCESS_STATUS, "加载合作机构用户信息成功！", data);
			}

		} else {
			result.init(FAIL_STATUS, "无效的ID！", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除合作方用户
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "EX_MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		int currentUserRole = currentUser.getRole().getId();

		Integer userID = Integer.valueOf(request.getParameter("id"));
		SysUser sysUser = this.getSysUserService().getSysUser(userID);
		if (sysUser != null && (sysUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER || sysUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR)) {
			if (currentUser.getOrganization().getId() != sysUser.getOrganization().getId() && currentUserRole == Dictionary.USER_ROLE_EX_MANAGER) {
				result.init(FAIL_STATUS, "该管理员不能管理其他合作机构用户！", null);
			} else {
				sysUser = this.getSysUserService().deleteSysUser(sysUser);
				Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
				result.init(SUCCESS_STATUS, "删除合作方用户成功！", data);
			}
		} else {
			result.init(FAIL_STATUS, "无效的合作方用户ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改合作方用户信息
	 */
	@AuthorityParas(userGroupName = "EX_MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "role.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "phone", type = ValueType.PHONE, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.EMAIL, nullable = false, emptyable = false)
	@ActionParam(key = "organization.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void Update() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		int currentUserRole = currentUser.getRole().getId();

		Integer userID = Integer.valueOf(request.getParameter("id"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		Integer roleID = Integer.valueOf(request.getParameter("role.id"));
		Integer orgID = Integer.valueOf(request.getParameter("organization.id"));
		SysUser sysUser = this.getSysUserService().getSysUser(userID);
		Organization organization = this.getOrganizationService().getOrganizationById(orgID);
		if (organization == null) {
			result.init(FAIL_STATUS, "无效的机构id信息！", null);
		} else if (sysUser != null && (sysUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER || sysUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR)) {
			if (currentUser.getOrganization().getId() != sysUser.getOrganization().getId() && currentUserRole == Dictionary.USER_ROLE_EX_MANAGER) {
				result.init(FAIL_STATUS, "该管理员不能管理其他合作机构用户！", null);
			} else if (roleID == Dictionary.USER_ROLE_EX_EDITOR || roleID == Dictionary.USER_ROLE_EX_MANAGER) {
				if (!email.equals(sysUser.getEmail())&&getSysUserService().getSysUser(email) != null) {
					result.init(FAIL_STATUS, "已存在该邮箱的用户！", null);
				} else if (!phone.equals(sysUser.getPhone())&&getSysUserService().getSysUser(phone) != null) {
					result.init(FAIL_STATUS, "已存在该手机的用户！", null);
				} else{
					sysUser.setRole(new Role());
					sysUser.getRole().setId(roleID);
					sysUser.setEmail(email);
					sysUser.setPhone(phone);
					sysUser.setName(name);
					if (status != null) {
						Short shStatus = Short.valueOf(status);
						sysUser.setStatus(shStatus);
					}
					sysUser.setOrganization(new Organization());
					sysUser.getOrganization().setId(orgID);
					sysUser = this.getSysUserService().updateSysUser(sysUser);
					Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
					result.init(SUCCESS_STATUS, "修改合作方用户成功！", data);
				}
			} else {
				result.init(FAIL_STATUS, "角色信息不合法！", null);
			}
		} else {
			result.init(FAIL_STATUS, "无效的合作方用户ID信息！", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	// /**
	// * 获取后台用户——合作机构用户列表
	// * @author Clark
	// * @date: 2014年6月22日 下午2:56:12 <br/>
	// */
	// @AuthorityParas(userGroupName = "EX_MANAGER_ADD_EDIT")
	// @ActionParam(key = "pagenum", type = ValueType.NUMBER)
	// @ActionParam(key = "pagesize", type = ValueType.NUMBER)
	// @ActionParam(key = "sorts", type = ValueType.STRING)
	// public void List(){
	// // 排序
	// String sorts = this.getStrValue(request.getParameter("sorts"),
	// "").replaceAll("-", " ");
	// int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
	// int pagesize = this.getIntValue(request.getParameter("pagesize"),
	// Dictionary.PAGESIZE_DEFAULT);
	// int offset = (pagenum - 1) * pagesize;
	//
	// SysUser currentUser = (SysUser) session.getAttribute("usersession");
	// ActionResult result = new ActionResult();
	//
	// //可以传入多个角色，返回多个角色的列表
	// int recordCount = getSysUserService().getParterCount(currentUser);
	// int pageCount = (int) Math.ceil((double) recordCount/pagesize);
	//
	// List<SysUser> exUserList = getSysUserService().getParters(currentUser,
	// sorts, offset, pagesize);
	// List<Map<String, Object>> dataList = new ArrayList<>();
	// if (exUserList != null && exUserList.size() > 0){
	// for (SysUser user : exUserList){
	// Map<String, Object> data = SerializeEntity.sysUser2Map(user);
	// dataList.add(data);
	// }
	// }
	// result.init(SUCCESS_STATUS, "正在努力加载合作单位用户信息！", dataList);
	// result.setPageCount(pageCount);
	// result.setPageNum(pagenum);
	// result.setPageSize(pagesize);
	// result.setTotalCount(recordCount);
	//
	// String dataType = request.getParameter("datatype");
	// Utlity.ResponseWrite(result, dataType, response);
	// }
	//
	/**
	 * 搜索
	 */
	@AuthorityParas(userGroupName = "EX_MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "email", type = ValueType.STRING)
	@ActionParam(key = "phone", type = ValueType.STRING)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "organization.id", type = ValueType.NUMBER)
	@ActionParam(key = "organization.name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "sysUser.name", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("email", request.getParameter("email"));
		searchMap.put("phone", request.getParameter("phone"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("organization.id", request.getParameter("organization.id"));
		searchMap.put("organization.name", request.getParameter("organization.name"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));
		searchMap.put("sysUser.name", request.getParameter("sysUser.name"));
		searchMap.put("organization.type", "2");

		int recordCount = getSysUserService().searchParterCount(currentUser, searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<SysUser> editorList = getSysUserService().searchParter(currentUser, searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (editorList != null && editorList.size() > 0) {
			for (SysUser user : editorList) {
				Map<String, Object> data = SerializeEntity.sysUser2Map(user);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

}
