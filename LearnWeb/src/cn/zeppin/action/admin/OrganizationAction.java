/** 
 * Project Name:CETV_TEST 
 * File Name:OrganizationAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.IStandardAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IOrganizationService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: OrganizationAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月22日 下午5:47:50 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class OrganizationAction extends BaseAction implements IStandardAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7603703508040957823L;

	public IOrganizationService organizationService;

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	/**
	 * 添加一个机构
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午6:20:30 <br/>
	 */

	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	//@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {

		// *********************************
		// 1.判断参数是否合法
		// 2.判断是否存在相同机构名称
		// *********************************

		String dataType = request.getParameter("datatype");
		String type = "2";// request.getParameter("type");
		String name = request.getParameter("name");

		ActionResult result = new ActionResult();

		if (!validOrganizationName(result, name)) {
			// 验证名称是否重复
		} else {

			Organization organization = new Organization();
			organization.setName(name);
			organization.setType(Integer.valueOf(type));

			this.getOrganizationService().addOrganization(organization);

			Map<String, Object> data = SerializeEntity.organization2Map(organization);
			result.init(SUCCESS_STATUS, null, data);

		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 编辑
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午7:09:28 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	//@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {

		// *********************************
		// 1.首先判断是否有权限编辑
		// 2.判断参数是否合法
		// 3.判断是否存在相同机构名称
		// *********************************

		String dataType = request.getParameter("datatype");
		String id = request.getParameter("id");
		String name = request.getParameter("name");

		ActionResult result = new ActionResult();

		Organization organization = this.getOrganizationService().getOrganizationById(Integer.valueOf(id));
		if (organization != null) {
			if(!name.equals(organization.getName().toString())&&!validOrganizationName(result, name)){
				
			}else{
				organization.setName(name);
	
				this.getOrganizationService().updateOrganization(organization);
	
				Map<String, Object> data = SerializeEntity.organization2Map(organization);
	
				result.init(SUCCESS_STATUS, null, data);
			}
		} else {
			result.init(FAIL_STATUS, "当前机构不存在！", null);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午7:10:00 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		// **********************************
		// 1.判断是否有权限删除
		// 2.判断是否能删
		// **********************************

		String dataType = request.getParameter("datatype");
		String id = request.getParameter("id");

		ActionResult result = new ActionResult();

		Organization organization = this.getOrganizationService().getOrganizationById(Integer.valueOf(id));

		if (organization != null) {

			// 判断机构下的用户
			int sysUserCount = this.getOrganizationService().getOrganizationSysUserCount(organization.getId());
			if (sysUserCount > 0) {
				result.init(FAIL_STATUS, "当前机构存在用户，不能删除！", null);
			} else {
				this.getOrganizationService().deleteOrganization(organization);
				result.init(SUCCESS_STATUS, "删除成功！", null);
			}

		} else {
			result.init(FAIL_STATUS, "当前机构不存在！", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午7:10:08 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {

		// **********************************************
		// 1.判断参数的合法行
		// **********************************************
		String dataType = request.getParameter("datatype");
		String id = request.getParameter("id");

		ActionResult result = new ActionResult();
		Organization organization = this.getOrganizationService().getOrganizationById(Integer.valueOf(id));

		if (organization != null) {

			Map<String, Object> data = SerializeEntity.organization2Map(organization);
			result.init(SUCCESS_STATUS, "加载成功", data);

		} else {
			result.init(FAIL_STATUS, "当前机构不存在！", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午7:10:16 <br/>
	 */
	@ActionParam(key = "name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "pageunm", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void List() {

		// **********************************************
		// 搜索或者加载列表
		// 首先获取参数
		// **********************************************

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		SysUser User = (SysUser) session.getAttribute("usersession");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		HashMap<String, Object> params = new HashMap<>();
		if(User.getOrganization().getType()==2){
			params.put("organization", User.getOrganization().getId());
		}
		params.put("name", name);
		if (type != null) {
			params.put("type", type);
		}
		else{
			params.put("type", "2");
		}
		params.put("sorts", sorts);

		int records = this.getOrganizationService().getOrganizationCountByParams(params);
		List<Organization> liT = this.getOrganizationService().getOrganizationByParams(params, offset, pagesize);

		List<Map<String, Object>> liM = new ArrayList<>();
		for (Organization org : liT) {
			liM.add(SerializeEntity.organization2Map(org));
		}

		result.init(SUCCESS_STATUS, "加载成功", liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 加载列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午7:10:16 <br/>
	 */
	@ActionParam(key = "name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "pageunm", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void Search() {

		// **********************************************
		// 搜索或者加载列表
		// 首先获取参数
		// **********************************************

		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		HashMap<String, Object> params = new HashMap<>();
		params.put("name", name);
		if (type != null) {
			params.put("type", type);
		}
		params.put("sorts", sorts);

		int records = this.getOrganizationService().getOrganizationCountByParams(params);
		List<Organization> liT = this.getOrganizationService().getOrganizationByParams(params, offset, pagesize);

		List<Map<String, Object>> liM = new ArrayList<>();
		for (Organization org : liT) {
			liM.add(SerializeEntity.organization2Map(org));
		}

		result.init(SUCCESS_STATUS, "加载成功", liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 验证名称是否重复
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午6:59:46 <br/>
	 * @param result
	 * @param name
	 * @return
	 */
	protected boolean validOrganizationName(ActionResult result, String name) {

		Map<String, String[]> map = request.getParameterMap();
		if (map.containsKey("id")) {

			Organization organization = this.getOrganizationService().getOrganizationById(Integer.valueOf(request.getParameter("id")));

			if (organization != null) {
				if (!organization.getName().equals(name)) {
					Organization tmpOrganization = this.getOrganizationService().getOrganizationByName(name);
					if (tmpOrganization != null) {
						result.init(FAIL_STATUS, "已经存在“" + name + "”机构！", null);
						return false;
					}
				}
			} else {
				result.init(FAIL_STATUS, "不存在当前机构Id", null);
				return false;
			}

		} else {
			Organization tmpOrganization = this.getOrganizationService().getOrganizationByName(name);
			if (tmpOrganization != null) {
				result.init(FAIL_STATUS, "已经存在“" + name + "”机构！", null);
				return false;
			}
		}

		return true;

	}

}
