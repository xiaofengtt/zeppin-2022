/** 
 * Project Name:CETV_TEST 
 * File Name:ManagerAction.java 
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
import cn.zeppin.entity.Role;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/** 
 * ClassName: ManagerAction <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月20日 下午7:05:24 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class ManagerAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private ISysUserService sysUserService;
	
	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	
	/**
	 * 添加运营管理者信息
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "phone", type = ValueType.PHONE, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.EMAIL, nullable = false, emptyable = false)
	public void Add(){
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		if (getSysUserService().getSysUser(email) != null){
			result.init(FAIL_STATUS, "已存在该邮箱的用户！", null);
		}
		else if (getSysUserService().getSysUser(phone)!= null){
			result.init(FAIL_STATUS, "已存在该手机的用户！", null);
		}
		else {
			SysUser sysUser = new SysUser();
			sysUser.setRole(new Role());
			sysUser.getRole().setId(Dictionary.USER_ROLE_MANAGER);
			sysUser.setEmail(email);
			sysUser.setPhone(phone);
			sysUser.setName(name);
			sysUser.setStatus(Dictionary.USER_STATUS_NOMAL);
			sysUser.setPassword(phone.substring(phone.length()-6, phone.length()));
			sysUser.setOrganization(currentUser.getOrganization()); //本部运营管理者与超级管理员的机构保持一致
			sysUser.setSysUser(currentUser); //创建人
			sysUser.setCreatetime(new Timestamp((new Date()).getTime()));
			sysUser = this.getSysUserService().addSysUser(sysUser); 
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			result.init(SUCCESS_STATUS, "添加运营管理者信息成功！", data);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 删除运营管理者用户
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete(){
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));
		SysUser sysUser = this.getSysUserService().getSysUser(userID);
		if (sysUser != null && sysUser.getRole().getId() == Dictionary.USER_ROLE_MANAGER){
			sysUser = this.getSysUserService().deleteSysUser(sysUser);
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			result.init(SUCCESS_STATUS, "删除运营管理者成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的运营管理者ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 加载运营管理者信息，一般用于修改的时候load
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load(){
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));
		SysUser sysUser = this.getSysUserService().getSysUser(userID);
		if (sysUser != null && sysUser.getRole().getId() == Dictionary.USER_ROLE_MANAGER) {
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			result.init(SUCCESS_STATUS, "加载运营管理者信息成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的ID！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 修改运营管理者信息
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "phone", type = ValueType.PHONE, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.EMAIL, nullable = false, emptyable = false)
	public void Update(){
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		SysUser sysUser = this.getSysUserService().getSysUser(userID);
		if (sysUser != null && sysUser.getRole().getId() == Dictionary.USER_ROLE_MANAGER){
			sysUser.setEmail(email);
			sysUser.setPhone(phone);
			sysUser.setName(name);
			sysUser = this.getSysUserService().updateSysUser(sysUser); 
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			result.init(SUCCESS_STATUS, "修改运营管理者信息成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的运营管理者ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

//	/**
//	 * 获取后台用户——资源经理列表
//	 * @author Clark
//	 * @date: 2014年6月23日 下午2:56:12 <br/>
//	 */
//	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
//	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
//	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
//	@ActionParam(key = "sorts", type = ValueType.STRING)
//	public void List() {
//		// 排序
//		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
//		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
//		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
//		int offset = (pagenum - 1) * pagesize;
//
//		SysUser currentUser = (SysUser) session.getAttribute("usersession");
//		ActionResult result = new ActionResult();
//
//		int recordCount =  getSysUserService().getManagerCount(currentUser);
//		int pageCount = (int) Math.ceil((double) recordCount/pagesize);
//		List<SysUser> managerList = getSysUserService().getManagers(currentUser, sorts, offset, pagesize);
//		List<Map<String, Object>> dataList = new ArrayList<>();
//		if (managerList != null && managerList.size() > 0){
//			for (SysUser user : managerList){
//				Map<String, Object> data = SerializeEntity.sysUser2Map(user);
//				dataList.add(data);
//			}
//		}
//		result.init(SUCCESS_STATUS, "正在努力加载运营管理者信息！", dataList);
//		result.setPageCount(pageCount);
//		result.setPageNum(pagenum);
//		result.setPageSize(pagesize);
//		result.setTotalCount(recordCount);
//
//		String dataType = request.getParameter("datatype");
//		Utlity.ResponseWrite(result, dataType, response);
//	}

	/**
	 * 搜索
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
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
	public void List(){
		
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
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount =  getSysUserService().searchManagerCount(currentUser, searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);

		List<SysUser> editorList = getSysUserService().searchManager(currentUser, searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (editorList != null && editorList.size() > 0){
			for (SysUser user : editorList){
				Map<String, Object> data = SerializeEntity.sysUser2Map(user);
				//加自定义属性
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
