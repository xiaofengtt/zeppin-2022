package cn.zeppin.action.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.User;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IRoleService;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = -3909927948294558147L;

	private IUserService userService;
	private IRoleService roleService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}
	
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	
	

	/**
	 * 普通用户注册
	 * 验证验证码、手机号
	 */
	public void Regist(){
		String sessionCode = session.getAttribute("authcode") == null ? "" : session.getAttribute("authcode").toString();
		ActionResult result = new ActionResult();
		if (!this.containsParameter("name", "password", "confimPassword", "phone", "email", "company", "job", "authcode")) {
			result.init(FAIL_STATUS, "接口参数传递不正确！", null);
		} else {
			String loginName = request.getParameter("name");
			String passWord = request.getParameter("password");
			String confimPassword = request.getParameter("confimPassword");
			String phone = request.getParameter("phone").trim();
			String email = request.getParameter("email");
			String company = request.getParameter("company");
			String job = request.getParameter("job");
			String authCode = request.getParameter("authcode");
			// 验证用户名为空
			if (loginName == null || loginName.equals("")) {
				result.init(FAIL_STATUS, "没有输入用户名！", null);
			}
			// 密码为空
			else if (passWord == null || passWord.equals("")) {
				result.init(FAIL_STATUS, "没有输入密码！", null);
			}
			// 确认密码为空
			else if (confimPassword == null || confimPassword.equals("")) {
				result.init(FAIL_STATUS, "没有输入确认密码！", null);
			}
			// 两次输入的密码不一致
			else if (!confimPassword.equals(passWord)) {
				result.init(FAIL_STATUS, "两次输入的密码不一致", null);
			}
			// 确认密码为空
			else if (phone == null || phone.equals("")) {
				result.init(FAIL_STATUS, "没有输入手机号！", null);
			}
			// 验证验证码为空
			else if (authCode == null || authCode.equals("")) {
				result.init(FAIL_STATUS, "没有输入验证码！", null);
			}
			// 验证码输入不正确
			else if (sessionCode == null || !authCode.equals(sessionCode)) {
				result.init(FAIL_STATUS, "验证码输入不正确！", null);
			} else {
				//验证手机号格式
				if (!Utlity.isMobileNO(phone)) {
					result.init(FAIL_STATUS, "手机号格式不正确！", null);
				} else {
					HashMap<String,String> searchMap = new HashMap<String,String>();
					searchMap.put("phone", phone);
					searchMap.put("role", Dictionary.USER_ROLE_USER+"");
					List<User> userList = this.userService.getListByParams(searchMap);
					
					if(userList.size() > 0){
						result.init(FAIL_STATUS, "此用户已存在！", null);
					}else{
						
						User user = new User();
						user.setName(loginName);
						user.setPassword(passWord);
						user.setPhone(phone);
						user.setRole(this.roleService.getRoleById(Dictionary.USER_ROLE_USER));
						user.setStatus(Dictionary.USER_STATUS_NOMAL);
						
						user.setEmail(email);
						user.setCompany(company);
						
						user.setJob(job);
						this.userService.addUser(user);

						session.setAttribute("usersession", user);
						
						Map<String, Object> data = SerializeEntity.user2Map(user);
						result.init(SUCCESS_STATUS, "注册成功，正在努力为您加载页面！", data);
					}
				}
				
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 普通用户修改信息，密码
	 */
	public void Edit(){
		ActionResult result = new ActionResult();
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String confimPassword = request.getParameter("confimPassword");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String company = request.getParameter("company");
		String job = request.getParameter("job");
		
		User user = (User) session.getAttribute("usersession");
		if(user!=null){
			if(password != null && newPassword != null && confimPassword!=null){
				if(!password.equals(user.getPassword())){
					result.init(FAIL_STATUS, "原密码错误！", null);
				}else if(!newPassword.equals(confimPassword)){
					result.init(FAIL_STATUS, "两次输入的密码不相同！", null);
				}else if (newPassword.equals("")) {
					result.init(FAIL_STATUS, "没有输入密码！", null);
				}else if (confimPassword.equals("")) {
					result.init(FAIL_STATUS, "没有输入确认密码！", null);
				}else{
					user.setPassword(newPassword);
					user = this.userService.updateUser(user);
					session.setAttribute("usersession", user);
				}
			}else{
				if(name != null){
					if(name.equals("")){
						result.init(FAIL_STATUS, "用户名不能为空！", null);
					}else{
						user.setName(name);
						if(email!=null){
							user.setEmail(email);
						}
						if(company!=null){
							user.setCompany(company);
						}
						if(job!=null){
							user.setJob(job);
						}
						user = this.userService.updateUser(user);
						session.setAttribute("usersession", user);
						Map<String, Object> data = SerializeEntity.user2Map(user);
						result.init(SUCCESS_STATUS, "更新成功！", data);
					}
				}else{
					result.init(FAIL_STATUS, "接口参数错误！", null);
				}
			}
		}else{
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	public void Logout(){
		ActionResult result = new ActionResult();
		session.removeAttribute("usersession");
		result.init(SUCCESS_STATUS, "登出成功", null);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	public void Load(){
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		if(user!=null){
			Map<String, Object> data = SerializeEntity.user2Map(user);
			result.init(SUCCESS_STATUS, "数据获取成功", data);
		}else{
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
}
