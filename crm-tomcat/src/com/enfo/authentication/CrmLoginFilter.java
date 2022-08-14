package com.enfo.authentication;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import org.jasig.cas.client.authentication.AttributePrincipal;

import enfo.crm.service.*;

public class CrmLoginFilter extends LoginFilter {
	protected void doLoginFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws Exception { 
		
		if("/login/sso_param_info.jsp".equals(request.getRequestURI())||"/login/callcenter_byphone.jsp".equals(request.getRequestURI())||request.getRequestURI().startsWith("/webcall/")
                || request.getRequestURI().startsWith("/services/")|| "/login/verification_code.jsp".equals(request.getRequestURI()) ){
			filterChain.doFilter(request,response);
		}else{
			super.doLoginFilter(request,response,filterChain);
		}
	}

	public void initUserAndPass(HttpServletRequest request) {
		String user;
		String pass;
		user = request.getParameter("username");
		pass = request.getParameter("password");
		user = user != null ? user.replaceAll("'","''") : "";
		pass = pass != null ? pass.replaceAll("'","''") : "";
		String login_user = "";

		//中投单点登录
		AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
		if(principal!=null)
			login_user = principal.getName();			
		if(!"".equalsIgnoreCase(login_user))user = login_user;
				
		setEnfoUsername(user);
		setEnfoPassword(pass);
		
		log.info("This user logining: "+this.getEnfoUsername());
	}
	
	public String doLoginWithoutPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginService loginService = new LoginService();
		loginService.doLogin(request, this.getEnfoUsername(), this.getEnfoPassword());
		return super.doLoginWithoutPassword(request, response);
	}
	
	public String doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//HttpSession session = request.getSession();
		//ServletContext application = (ServletContext)session.getServletContext();
		//String contextPath = request.getContextPath();
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
		AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
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
					return "";
				}
			}else{
				request.setAttribute("errormsg","请输入验证码！");
				return "";
			}
		}
		
		loginSuccess = loginService.checkUserPass(this.getEnfoUsername(), this.getEnfoPassword()); 	
		loginSuccess1 = loginService.getLoginUser(this.getEnfoUsername());
		
		if (loginSuccess==100) {
			//密码正确
			log.info("This user logined: "+this.getEnfoUsername());
			loginService.getpageSize(this.getEnfoUsername(),request);
			return doLoginWithoutPassword(request, response);
		} else if(loginSuccess1!=null&&principal!=null){//中投单点登录专用
			log.info("This user logined: "+this.getEnfoUsername());
			loginService.getpageSize(this.getEnfoUsername(),request);
			return doLoginWithoutPassword(request, response);
		}else {
			//response.sendRedirect(basePath); //跳转到当前web的根
			log.info("This user login failed: "+this.getEnfoUsername());
			request.setAttribute("errormsg","用户名或密码错误！");
			return "";
		}
		
	}

	public void doLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//todo 登出过程，清除全局参数
		super.doLogout(request, response);
	}	
}
