package com.cmos.chinamobile.media.remote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;

import com.cmos.chinamobile.media.web.vo.User;
import com.cmos.security.shiro.service.IShiroLoginService;

public class shiroLoginService implements IShiroLoginService {

	@Override
	public void beforeLogin(HttpServletRequest request,
			HttpServletResponse response) {
	}

	@Override
	public boolean afterLoginSuccess(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = new User();
		user.setId("");
		user.setName((String)SecurityUtils.getSubject().getPrincipal());
		user.setRole("");
		session.setAttribute("usersession", (Object)user);
		return true;
	}

	@Override
	public void afterLoginFail(HttpServletRequest request,
			HttpServletResponse response) {
	}
	
}
