package com.whaty.platform.sso.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.SsoUserService;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SsoLogin extends MyBaseAction {
	private String passwd;
	private String loginId;
	private String loginType;
	private String authCode;
	private String loginErrMessage;
	private SsoUserService ssoUserService;

	// 下列自段用于取回密码
	private String trueName;
	private String cardId;

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public String execute() {
		return this.login();
	}

	/**
	 * 当前状态学员是否缴费
	 * 
	 * @throws EntityException
	 */
	public boolean isPay() {
		PeTrainee peTrainee = null;
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		this.getGeneralService().getGeneralDao()
				.setEntityClass(PeTrainee.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("enumConstByStatus", "enumConstByStatus");
		dc.add(Restrictions.eq("ssoUser", ssoUser));
		List peTraineeList = new ArrayList();
		try {
			peTraineeList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (peTraineeList != null && peTraineeList.size() > 0) {
			peTrainee = (PeTrainee) peTraineeList.get(0);
		}
		return true;
	}

	/**
	 * @description 
	 * @return
	 */
	public String login() {
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (ssoUser == null) {
			logger.error(this.getText("login.error.onuser", this.getLoginId()));
			this.loginErrMessage = "帐号错误,请检查帐号是否使用学员卡上的学号登录，及帐号位数是否正确。";
			return "back";
		}
		if (ssoUser.getEnumConstByFlagIsvalid() != null
				&& ssoUser.getEnumConstByFlagIsvalid().getCode() != null
				&& "0".equals(ssoUser.getEnumConstByFlagIsvalid().getCode())) {
			this.loginErrMessage = "您的账号处于无效状态，暂不能登录平台。";
			return "back";
		}
		if (!this.getPasswd().equals(ssoUser.getPassword())) {
			logger.error(this.getText("login.error.errorpassword", this
					.getPasswd()));
			this.loginErrMessage = "密码错误,请注意字母大小写及0(数字零)与O(大写字母),1(数字1)与I(大写字母)的区分。";
			return "back";
		}
		// 验证码校验
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String code = (String) ctx.getSession().get("authCode");
		if (!this.getAuthCode().equals(code)) {
			logger.error(this.getText("login.error.errorauthocode", this
					.getAuthCode()));
			this.loginErrMessage = "验证码错误"; 
			return "back";
		}else {// sso验证通过
			boolean passFlag = false;
			try {
				for (int i = 0; i < 4; i++) {
					passFlag = getSsoUserService().CheckSsoUserByType(ssoUser,
							i + ""); // 验证用户身份是否正确
					if (passFlag) {
						this.setLoginType(i + "");
						System.out.println("logintype is " + i);
						break;
					}
				}
			} catch (SsoException e) {
				logger.error(e);
				this.loginErrMessage = e.getMessage();
				return "back";
			}
			if (!passFlag) {// 验证失败
				logger.error(this.getText("login.error.erroridentity", this
						.getLoginId()));
				this.loginErrMessage = "用户身份错误";
				return "back";
			}
			Date lastDate = null;
			try {
				// 设置用户属性
				lastDate = ssoUser.getLastLoginDate();
				ssoUser.setLastLoginDate(new Date());
				ssoUser.setLastLoginIp(request.getRemoteAddr());
				long num = ssoUser.getLoginNum() == null ? 0 : ssoUser
						.getLoginNum();
				ssoUser.setLoginNum((++num));
				ssoUser = getSsoUserService().save(ssoUser);
			} catch (Exception e) {
				logger.error(e);
				this.loginErrMessage = "用户信息设置失败";
				return "back";
			}
			// 验证通过；存储用户session;
			UserSession userSession = null;
			try {
				ssoUser.setLastLoginDate(lastDate);
				userSession = getSsoUserService().getUserSession(ssoUser,
						getLoginType());
				request.getSession().setAttribute("Logintype", getLoginType());
				request.getSession().setAttribute("roleid",
						userSession.getRoleId());
				request.getSession().setAttribute("userSession", userSession);
				request.getSession().setAttribute("whole_sumLogin", getLoginNums());
				this.setUserLogo(userSession.getRoleId());
			} catch (SsoException e) {
				this.loginErrMessage = "用户信息设置失败";
				return "back";
			}
			if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY_BAK) != null)
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
			ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession);
			return "success";
		}
	}

	public String setUserLogo(String roleid) {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		String sql = "select ec.code from pe_pri_role p inner join enum_const ec on p.flag_role_type=ec.id where p.id=:ids";
		Map map = new HashMap();
		map.put("ids", roleid);
		List list = new LinkedList();
		session.putValue("userpic", "g_20");
		try {
			list = this.getGeneralService().getBySQL(sql, map);
			if (list != null && list.size() > 0) {
				Object obj = list.get(0);
				if (((String) obj).equals("2")) { // 承办单位
					session.putValue("userpic", "g_28");
				} else if (((String) obj).equals("1")) { // 评审专家
					session.putValue("userpic", "pszj");
				} else if (((String) obj).equals("3")) { // 省厅师范处
					session.putValue("userpic", "g_29");
				} else {
					session.putValue("userpic", "g_20");
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "";
	}
	public String autoLogin(){
		SsoUser ssoUser = null;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (ssoUser == null) {
			logger.error(this.getText("login.error.onuser",this.getLoginId()));
			this.loginErrMessage = "没有这个用户";
			return "back";
		}
		if (ssoUser.getEnumConstByFlagIsvalid() != null
				&& ssoUser.getEnumConstByFlagIsvalid().getCode() != null
				&& "0".equals(ssoUser.getEnumConstByFlagIsvalid().getCode())) {
			this.loginErrMessage = "您的账号处于无效状态，暂不能登录平台。";
			return "back";
		}
		if (!this.getPasswd().equals(ssoUser.getPassword())) {
			logger.error(this.getText("login.error.errorpassword",this.getPasswd()));
			this.loginErrMessage = "密码错误";
			return "back";
		}else {//sso验证通过
			boolean passFlag = false;
			try{
				for (int i = 0; i < 4; i++) {
					passFlag = getSsoUserService().CheckSsoUserByType(ssoUser,
							i + ""); // 验证用户身份是否正确				
					if (passFlag) {
						this.setLoginType(i + "");
						break;
					}
				}
				
			}catch(SsoException e){
				logger.error(e);
				this.loginErrMessage = e.getMessage();
				return "back";
			}
			if(!passFlag){//验证失败
				logger.error(this.getText("login.error.erroridentity", this.getLoginId()));
				this.loginErrMessage = "用户身份错误";
				return "back";
			}
			
			Date lastDate = null;
			try {
				// 设置用户属性
				lastDate = ssoUser.getLastLoginDate();
				ssoUser.setLastLoginDate(new Date());
				ssoUser.setLastLoginIp(request.getRemoteAddr());
				long num = ssoUser.getLoginNum() == null ? 0 : ssoUser.getLoginNum();
				ssoUser.setLoginNum((++num));
				ssoUser = getSsoUserService().save(ssoUser);
			} catch (Exception e) {
				logger.error(e);
				this.loginErrMessage = "用户信息设置失败";
				return "back";
			}
			// 验证通过；存储用户session;
			UserSession userSession = null;
			try {
				ssoUser.setLastLoginDate(lastDate);
				userSession = getSsoUserService().getUserSession(ssoUser, getLoginType());
				request.getSession().setAttribute("Logintype", getLoginType());
				request.getSession().setAttribute("roleid", userSession.getRoleId());
				request.getSession().setAttribute("userSession", userSession);
				request.getSession().setAttribute("whole_sumLogin", getLoginNums());
				this.setUserLogo(userSession.getRoleId());
				
			} catch (SsoException e) {
				this.loginErrMessage = "用户信息设置失败";
				return "back";
			}
			if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY_BAK) != null)
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
				ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession);
			//return "success";
			return "";
		}
	}
	/**
	 * 模拟登录
	 * 
	 * @return
	 */
	public String simulate() {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		UserSession userSession = null;
		userSession = (UserSession) ctx.getSession().get(
				SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			this.loginErrMessage = "登录超时，请重新登录！";
			return "information";
		}
		if (!userSession.getUserLoginType().equals(SsoConstant.SSO_MANAGER)) {
			this.loginErrMessage = "模拟登录一次只能模拟登录一个人的，请注销或关闭刚模拟登录的窗口。";
			return "information";
		}
		// 将session中的用户信息转存
		ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY_BAK, userSession);
		// 开始登录
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			logger.error(e1);
			this.loginErrMessage = e1.getMessage();
			return "information";
		}
		// 验证通过；存储用户session;
		UserSession userSession2 = null;
		try {
			userSession2 = getSsoUserService().getUserSession(ssoUser,
					SsoConstant.SSO_STUDENT);
		} catch (SsoException e) {
			this.loginErrMessage = "用户信息设置失败";
			return "information";
		}
		ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession2);
		return "success";
	}

	public void close() {
		ActionContext ctx = ActionContext.getContext();
		if (this.getLoginErrMessage() != null
				&& this.getLoginErrMessage().equals("clear")) {
			ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
			ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY);
		} else {
			UserSession userSession2 = (UserSession) ctx.getSession().get(
					SsoConstant.SSO_USER_SESSION_KEY_BAK);
			// 将session中的用户信息转存
			if (userSession2 != null) {
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY);
				ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY,
						userSession2);
			} else {
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY);
			}
		}
	}

	/**
	 * 首页取回密码功能
	 * 
	 * @return
	 */
	public String toPassword() {
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (ssoUser == null) {
			this.loginErrMessage = "没有这个用户!";
			return "pwd";
		}
		boolean passFlag = false;
		try {
			passFlag = getSsoUserService().CheckSsoUserByType(ssoUser,
					getLoginType()); // 验证用户身份是否正确
		} catch (SsoException e) {
			passFlag = true;
		}

		if (!passFlag) {// 验证失败
			this.loginErrMessage = "用户分类错误!";
			return "pwd";
		}
		if (this.getSsoUserService().checkTrue(ssoUser, this.getLoginType(),
				this.getTrueName(), this.getCardId())) {
			this.loginErrMessage = "true";
		} else {
			this.loginErrMessage = "姓名或者证件号码错误！";
		}
		return "pwd";
	}

	public String newPassword() {
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		this.getSsoUserService().saveNewPassword(ssoUser, this.getPasswd());
		this.setLoginErrMessage("success");
		return "pwd";
	}

	public String getLoginErrMessage() {
		return loginErrMessage;
	}

	public void setLoginErrMessage(String loginErrMessage) {
		this.loginErrMessage = loginErrMessage;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {

	}
	
	private Long getLoginNums() {
		String sql = "select sum(login_num) from sso_user";
		try {
			List<BigDecimal> list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				return list.get(0).longValue();
			}
		} catch (EntityException e) {
		}
		return 0L;
	}
}
