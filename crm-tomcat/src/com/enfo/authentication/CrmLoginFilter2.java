package com.enfo.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import enfo.crm.service.LoginService;

/**
 * 过滤类，去除单点登陆不稳定因素
 * @author HongFei
 *
 */
public class CrmLoginFilter2 implements Filter {
	
	public static final String CONST_ENFO_LOGINUSER = "_const_enfo_loginuser_";
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private String enfoUsername;
	
	private String enfoPassword;
	
	private String loginUrl;
	
	private String logoutUrl;
	
	
	public String getEnfoUsername() {
		return enfoUsername;
	}

	public void setEnfoUsername(String enfoUsername) {
		this.enfoUsername = enfoUsername;
	}

	public String getEnfoPassword() {
		return enfoPassword;
	}

	public void setEnfoPassword(String enfoPassword) {
		this.enfoPassword = enfoPassword;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}


	public void init(FilterConfig filterConfig) throws ServletException {
		String loginUrl = filterConfig.getInitParameter("loginUrl");
		setLoginUrl(loginUrl);
		
		String logoutUrl = filterConfig.getInitParameter("logoutUrl");
		setLogoutUrl(logoutUrl);
	}	
	
	public void destroy() {
		setLoginUrl("");
		setLogoutUrl("");
	}

	@Override
	public void doFilter(final ServletRequest servletRequest,final ServletResponse servletResponse, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (request.getRequestURI().endsWith(".js")
				|| request.getRequestURI().endsWith(".css")
				|| request.getRequestURI().endsWith(".vbs")
				|| request.getRequestURI().endsWith(".dwr")
				|| request.getRequestURI().endsWith(".jpg")
				|| request.getRequestURI().endsWith(".gif")
				|| request.getRequestURI().endsWith(".bmp")
				|| request.getRequestURI().endsWith(".png")
				|| request.getRequestURI().endsWith(".tif")
				|| request.getRequestURI().endsWith(".js")) {
			filterChain.doFilter(request, response);
		} else {
			try {
				doLoginFilter(request, response, filterChain);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}	
		}
	}
	
	protected void doLoginFilter(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws Exception { 
		String REQ_URL = request.getRequestURI().toString();
		
		//外置去除
		if("/login/sso_param_info.jsp".equals(request.getRequestURI())||"/login/callcenter_byphone.jsp".equals(request.getRequestURI())||request.getRequestURI().startsWith("/webcall/")
                || request.getRequestURI().startsWith("/services/")|| "/login/verification_code.jsp".equals(request.getRequestURI()) ){
			filterChain.doFilter(request,response);
		}
		
		//登入登出去除
		if (request.getRequestURI().endsWith(this.logoutUrl)) {
			doLogout(request, response);
			filterChain.doFilter(request, response);
			return;
		}
		
		//登陆标志
		boolean _is_login_page  = request.getRequestURI().endsWith(this.loginUrl) || request.getRequestURI().equals("/");
		boolean _is_token = request.getSession() != null && null != request.getSession().getAttribute(CONST_ENFO_LOGINUSER);
		
		if(REQ_URL.equals("/") && _is_token){
			request.getRequestDispatcher(request.getContextPath()+"/login/index.jsp").forward(request, response);
			return;
		}
		
		//登陆
		if(_is_login_page && !_is_token){
			initUserAndPass(request);
			doLogin(request, response, filterChain);
		}else if(!_is_token){
			request.getRequestDispatcher(request.getContextPath()+"/login/login.jsp").forward(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}

	public void initUserAndPass(HttpServletRequest request) {
		String user;
		String pass;
		user = request.getParameter("username");
		pass = request.getParameter("password");
		user = user != null ? user.replaceAll("'","''") : "";
		pass = pass != null ? pass.replaceAll("'","''") : "";

		setEnfoUsername(user);
		setEnfoPassword(pass);
		
		log.info("This user logining: "+this.getEnfoUsername());
	}
	
	
	public String doLoginWithoutPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginService loginService = new LoginService();
		loginService.doLogin(request, this.getEnfoUsername(), this.getEnfoPassword());
		
		final HttpSession session = request.getSession(false);
		session.setAttribute(CONST_ENFO_LOGINUSER, enfoUsername);
		return enfoUsername;

	}
	
	public void doLogin(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {
		boolean is_post = request.getMethod().equals("POST");
		int loginSuccess = 0;
		String loginSuccess1 = null;
		LoginService loginService = new LoginService();
		String checkInput = "";
		String rand = "";
		String showLoginVerification = request.getParameter("showLoginVerification");
		if("1".equals(showLoginVerification)){
			checkInput = request.getParameter("checkCode");
			rand = (String)request.getSession().getAttribute("rand");
			if(checkInput!=null&&rand!=null){
				if(!rand.equals(checkInput)){
					request.setAttribute("errormsg","验证码错误！");
			
				}
			}else{
				request.setAttribute("errormsg","请输入验证码！");
			}
		}
		
		loginSuccess = loginService.checkUserPass(this.getEnfoUsername(), this.getEnfoPassword()); 	
		loginSuccess1 = loginService.getLoginUser(this.getEnfoUsername());
		
		if (loginSuccess==100) {
			//密码正确
			log.info("This user logined: "+this.getEnfoUsername());
			loginService.getpageSize(this.getEnfoUsername(),request);
			doLoginWithoutPassword(request, response);
			
			request.getRequestDispatcher(request.getContextPath()+"/login/index.jsp").forward(request, response);
		} else {
			//response.sendRedirect(basePath); //跳转到当前web的根
			log.info("This user login failed: "+this.getEnfoUsername());
			if(is_post){
				request.setAttribute("errormsg","用户名或密码错误！");
			}
			//filterChain.doFilter(request, response);
		}
	}

	public void doLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final HttpSession session = request.getSession(false);
		session.removeAttribute(CONST_ENFO_LOGINUSER);
	}
}
