/** 
 * Project Name:CETV_TEST 
 * File Name:PhoneLoginAction.java 
 * Package Name:cn.zeppin.action.phone 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.phone;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserAccessLog;
import cn.zeppin.service.api.IUserAccessLogService;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.service.api.IUserTextbookService;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.ua.UserAgent;

/**
 * 主要是手机端---登陆action ClassName: PhoneLoginAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月14日 下午3:41:45 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class PhoneLoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2520010497648088318L;

	private IUserService userService;
	private IUserAccessLogService userAccessLogService;
	private IUserTextbookService userTextbookService;

	private String message;

	/**
	 * 注册用户
	 * 
	 * @author Administrator
	 * @date: 2014年10月14日 下午3:48:52 <br/>
	 */
	public void Regist() {

	}

	/**
	 * 登陆
	 * 
	 * @author Administrator
	 * @date: 2014年10月14日 下午3:49:20 <br/>
	 */
	public String Login() {

		/**
		 * ======================================================= 
		 * 1.获取用户信息
		 * 2.判断用户登陆
		 *  ========================================================
		 */
		String account = request.getParameter("email");
		String password = request.getParameter("password");
		String result = "fail";

		if (account == null || password == null || account.length() == 0 || password.length() == 0) {
			message = "账号或密码不能为空";
		} else {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", account);

			// 根据用户名来判断在系统是否存在当前用户
			User tmpUser = this.getUserService().getUserByMap(map);
			User user = null;
			if (tmpUser == null) {
				user = new User();
				user.setAccount(account);
				user.setPassword(password);
				user.setName(account);
				user.setStatus(1);
				user.setFavicon("");
				user.setIndentity(1);
				user.setType(1);
				user.setScore(1L);
				
				this.getUserService().addUser(user);
				
			} else {
				map.put("password", password);
				user = this.getUserService().getUserByMap(map);
			}
			
			if (user != null) {

				// 保存会话状态
				session.setAttribute("userphone", user);

				// 写cookie
				Cookie cook = new Cookie("username", account);
				cook.setMaxAge(60 * 60 * 24);
				response.addCookie(cook);
				Cookie cook2 = new Cookie("password", password);
				cook2.setMaxAge(60 * 60 * 24);
				response.addCookie(cook2);
				
				result = "learn";

				// 登陆写入log
				String userAgent = request.getHeader("User-agent");
				UserAgent ua = UserAgent.parseUserAgentString(userAgent);

				String browser = ua.getBrowser().toString();
				String os = ua.getOperatingSystem().toString();
				String device = ua.getOperatingSystem().getDeviceType().toString();

				UserAccessLog ualog = new UserAccessLog();
				ualog.setAccessBrowser(browser);
				ualog.setAccessClient(2);
				ualog.setAccessEquipment(device);
				ualog.setAccessOs(os);
				ualog.setAccessIp(Utlity.getIpAddr(request));
				ualog.setAccessType(1);
				ualog.setUser(user);

				this.getUserAccessLogService().addUserAccessLog(ualog);

			} else {
				message = "账号或密码错误";
			}
		}

		return result;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IUserAccessLogService getUserAccessLogService() {
		return userAccessLogService;
	}

	public void setUserAccessLogService(IUserAccessLogService userAccessLogService) {
		this.userAccessLogService = userAccessLogService;
	}

	public IUserTextbookService getUserTextbookService() {
		return userTextbookService;
	}

	public void setUserTextbookService(IUserTextbookService userTextbookService) {
		this.userTextbookService = userTextbookService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
