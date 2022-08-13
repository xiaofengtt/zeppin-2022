package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Role;
import cn.zeppin.entity.User;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class UserManageAction extends BaseAction {

	private static final long serialVersionUID = 8490089490576020871L;
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 列表
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "phone", type = ValueType.NUMBER)
	public void List() {
		ActionResult result = new ActionResult();
		HashMap<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("phone", request.getParameter("phone"));
		searchMap.put("role", Dictionary.USER_ROLE_USER + "");

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.userService.getCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<User> userList = this.userService.getListForPage(searchMap, sorts, offset, pagesize);

		List<Map<String, Object>> dataList = new ArrayList<>();
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				Map<String, Object> data = SerializeEntity.user2Map(user);
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
	
	/**
	 * 加载
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));

		User user = this.userService.getUser(userID);
		if (user != null && user.getRole().getId() == Dictionary.USER_ROLE_USER) {
			Map<String, Object> data = SerializeEntity.user2Map(user);
			result.init(SUCCESS_STATUS, "加载编辑信息成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的编辑ID！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 添加
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "phone", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {
		ActionResult result = new ActionResult();
		String name = request.getParameter("name").trim();
		String phone = request.getParameter("phone").trim();
		Integer status = Integer.valueOf(request.getParameter("status"));
		String email = request.getParameter("email").trim();
		String company = request.getParameter("company");
		String job = request.getParameter("job");
		if(name == null || name.equals("")){
			result.init(FAIL_STATUS, "用户名不能为空", null);
		}else if(!Utlity.isMobileNO(phone)){
			result.init(FAIL_STATUS, "手机号不合法", null);
		}else{		
			HashMap<String,String> searchMap = new HashMap<String,String>();
			searchMap.put("phone", phone);
			searchMap.put("role", Dictionary.USER_ROLE_USER + "");
			Integer count = this.userService.getCountByParams(searchMap);
			
			if(count > 0 ){
				result.init(FAIL_STATUS, "手机号已存在", null);
			}else{
				Role role = new Role();
				role.setId(Dictionary.USER_ROLE_USER);
				
				User user = new User();
				user.setName(name);
				user.setPhone(phone);
				user.setPassword(phone.substring(phone.length() - 6, phone.length()));
				user.setEmail(email);
				user.setCompany(company);
				user.setJob(job);
				user.setRole(role);
				user.setStatus(status);
				this.userService.addUser(user);
				result.init(SUCCESS_STATUS, "添加成功", null);
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "phone", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Edit() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name").trim();
		String phone = request.getParameter("phone").trim();
		Integer status = Integer.valueOf(request.getParameter("status"));
		String email = request.getParameter("email").trim();
		String company = request.getParameter("company");
		String job = request.getParameter("job");
		if(name == null || name.equals("")){
			result.init(FAIL_STATUS, "用户名不能为空", null);
		}else if(!Utlity.isMobileNO(phone)){
			result.init(FAIL_STATUS, "手机号不合法", null);
		}else{
			User user = this.userService.getUser(id);
			HashMap<String,String> searchMap = new HashMap<String,String>();
			searchMap.put("phone", phone);
			searchMap.put("role", Dictionary.USER_ROLE_USER + "");
			List<User> userList = this.userService.getListByParams(searchMap);
			if(user == null){
				result.init(FAIL_STATUS, "用户不存在", null);
			}if(userList.size() > 0 && !userList.get(0).getId().equals(user.getId())){
				result.init(FAIL_STATUS, "手机号重复", null);
			}else{
				user.setName(name);
				user.setPhone(phone);
				user.setEmail(email);
				user.setCompany(company);
				user.setJob(job);
				user.setStatus(status);
				this.userService.updateUser(user);
				result.init(SUCCESS_STATUS, "修改成功", null);
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 删除
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));
		User user = this.userService.getUser(userID);
		if (user != null && user.getRole().getId() == Dictionary.USER_ROLE_USER) {
			user = this.userService.deleteUser(user);
			Map<String, Object> data = SerializeEntity.user2Map(user);
			result.init(SUCCESS_STATUS, "停用成功！", data);
		} else {
			result.init(FAIL_STATUS, "用户不存在！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
}
