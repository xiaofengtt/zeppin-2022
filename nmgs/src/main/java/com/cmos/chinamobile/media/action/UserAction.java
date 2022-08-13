package com.cmos.chinamobile.media.action;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cmos.chinamobile.media.action.base.ActionResult;
import com.cmos.chinamobile.media.web.vo.User;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getActionLog(UserAction.class);
	
	/**
	 * 用户登录
	 */
	public String login() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		logger.info("execute", "Start");
		ActionResult<Object> result = super.getActionResult();
		if("success".equals(result.getStatus())){
			if(result.getData()!=null && !"[]".equals(result.getData().toString())){
				@SuppressWarnings("unchecked")
				Map<String,Object> data =(Map<String,Object>) result.getData();
				if(data!=null && data.get("name")!=null && data.get("id")!=null){
					String id = data.get("id").toString();
					String name = data.get("name").toString();
					String role = data.get("role").toString();
					User user = new User();
					user.setId(id);
					user.setName(name);
					user.setRole(role);
					session.setAttribute("usersession", (Object)user);
				}
			}
		}
		super.sendJson(super.convertOutputObject2Json(result));
		logger.info("execute", "End");
		return null;
	}
	
	public String login1(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = new User();
		user.setId("");
		user.setName(UUID.randomUUID().toString());
		user.setRole("1");
		session.setAttribute("usersession", (Object)user);
		ActionResult<Object> result = super.getActionResult();
		super.sendJson(super.convertOutputObject2Json(result));
        return null;
	}
}
