package cn.zeppin.action.admin;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.SysUser;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Utlity;

public class PasswordAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8490089490576020871L;

	private ISysUserService sysUserService;

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 修改密码
	 * 
	 * @author Administrator
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "password", type = ValueType.STRING)
	@ActionParam(key = "newpassword", type = ValueType.STRING)
	@ActionParam(key = "confimpassword", type = ValueType.STRING)
	public void Edit() {
		ActionResult result = new ActionResult();
		String password=request.getParameter("password");
		String newpassword=request.getParameter("newpassword");
		String confimpassword=request.getParameter("confimpassword");
		if(!password.equals("")&&!newpassword.equals("")&&!confimpassword.equals("")){
			if(confimpassword.equals(newpassword)){
				SysUser user = (SysUser) session.getAttribute("usersession");
				if(password.equals(user.getPassword())){
					user.setPassword(newpassword);
					this.getSysUserService().updateSysUser(user);
					result.init(SUCCESS_STATUS, "修改成功", null);
				}else{
					result.init(FAIL_STATUS, "原密码错误", null);
				}
			}else{
				result.init(FAIL_STATUS, "两次输入的新密码不一致", null);
			}
		}else{
			result.init(FAIL_STATUS, "密码不能为空", null);
		}
		Utlity.ResponseWrite(result, dataType, response);

	}



}
