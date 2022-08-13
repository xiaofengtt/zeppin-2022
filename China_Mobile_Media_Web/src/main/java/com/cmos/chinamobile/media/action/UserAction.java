package com.cmos.chinamobile.media.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cmos.chinamobile.media.action.base.ActionResult;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.User;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getActionLog(UserAction.class);
	
	public String execute() {
		logger.info("execute", "Start");
		ActionResult<Object> result = super.getActionResult();
		super.sendJson(super.convertOutputObject2Json(result));
		logger.info("execute", "End");
		return null;
	}
	
	public String login(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = new User();
		user.setId(1);
		user.setLoginNm("123");
		user.setLoginPw("123");
		session.setAttribute("usersession", user);
		ActionResult<Object> result = super.getActionResult();
		super.sendJson(super.convertOutputObject2Json(result));
        return null;
	}
	
	public String logout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
        session.removeAttribute("usersession");
        ActionResult<Object> result = super.getActionResult();
		super.sendJson(super.convertOutputObject2Json(result));
        return null;
    }
}
