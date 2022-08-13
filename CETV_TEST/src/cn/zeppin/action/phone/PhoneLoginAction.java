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

	private int subjectId;
	private String message;
	private int clientId;
	private String clientName;

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
		 * ========================================================
		 */
		String account = request.getParameter("email");
		String password = request.getParameter("password");
		String result = "fail";
		// 访问的5个客户端标识码
		int client = this.getIntValue(request.getParameter("client"));
		this.clientId = client;
		if (account == null || password == null || account.length() == 0 || password.length() == 0) {
			message = "账号或密码不能为空";
		} else {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", account);
			map.put("password", password);

			User user = this.getUserService().getUserByMap(map);
			if (user != null) {

				// 保存会话状态
				session.setAttribute("userphone", user);
				session.setAttribute("clientId", client);
				
				if (client >= 4) {
					if (client == 4) {
						this.clientName="初中数学移动测评客户端";
						session.setAttribute("clientName", "初中数学");
						subjectId = 11;
						session.setAttribute("subject.id", subjectId);
						session.setAttribute("subject.name", "初中数学");
					} else if (client == 5) {
						this.clientName="初中英语移动测评客户端";
						session.setAttribute("clientName", "初中英语");
						subjectId = 12;
						session.setAttribute("subject.id", subjectId);
						session.setAttribute("subject.name", "初中英语");
					}

					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("user.id", user.getId());
					searchMap.put("subject.id", subjectId);

					if (user.getGrade() != null && user.getArea() != null && this.getUserTextbookService().getUserTextbookByMap(searchMap) != null) {
						result = "initChart";
					} else {
						result = "initSet";
					}

				} else {

					if (client == 1) {
						// 心理客户端
						this.clientName="心理素质移动测评客户端";
						session.setAttribute("clientName", "心理素质");
						result = "psychology";
					} else if (client == 2) {
						// 学习能力
						this.clientName="学习能力移动测评客户端";
						session.setAttribute("clientName", "学习能力");
						result = "learn";
					} else if (client == 3) {
						// 安全意思
						this.clientName="安全意识移动测评客户端";
						session.setAttribute("clientName", "安全意识");
						result = "safe";
					}

				}

				// 登陆写入log
				String userAgent = request.getHeader("User-agent");
				UserAgent ua = UserAgent.parseUserAgentString(userAgent);

				String browser = ua.getBrowser().toString();
				String os = ua.getOperatingSystem().toString();
				String device = ua.getOperatingSystem().getDeviceType().toString();

				UserAccessLog ualog = new UserAccessLog();
				ualog.setAccessBrowser(browser);
				ualog.setAccessClient(client);
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

	/**
	 * 5中客户端跳转
	 * 
	 * @author Administrator
	 * @date: 2014年11月4日 下午4:35:50 <br/>
	 * @return
	 */
	public String Go() {
		// 访问的5个客户端标识码
		int client = this.getIntValue(request.getParameter("client"));
		this.clientId = client;
		session.setAttribute("clientId", client);
		if (client == 4) {
			this.clientName="初中数学移动测评客户端";
			session.setAttribute("clientName", "初中数学");
		}
		else if (client == 5) {
			this.clientName="初中英语移动测评客户端";
			session.setAttribute("clientName", "初中英语");
		}
		else if (client == 1) {
			this.clientName="心理素质移动测评客户端";
			session.setAttribute("clientName", "心理素质");
		}
		else if (client == 2) {
			this.clientName="学习能力移动测评客户端";
			session.setAttribute("clientName", "学习能力");
		}
		else if (client == 3) {
			this.clientName="安全意识移动测评客户端";
			session.setAttribute("clientName", "安全意识");
		}
		return "go";
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

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
}
