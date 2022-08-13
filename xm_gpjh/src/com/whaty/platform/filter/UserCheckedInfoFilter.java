package com.whaty.platform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.sso.web.servlet.UserSession;

public class UserCheckedInfoFilter implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub

	}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		 HttpServletResponse response = (HttpServletResponse) res;
		 HttpSession session=request.getSession();
		 //ServletContext application=session.getServletContext();
		 String path1 = request.getContextPath();
		 String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
		 
		 if(session.getAttribute("user_session")==null)
	     {
	    	 response.sendRedirect(basePath1+"error/priv_error.htm");
	    	 return;
	     }
	     else
	     {
	    	 UserSession userSession = (UserSession)session.getAttribute("user_session");
	    	 if(userSession==null)
	    	 {
		    	 response.sendRedirect(basePath1+"error/priv_error.htm");
		    	 return;
		     }
	    	 else
	    	 {
	    		 SsoUser sso=userSession.getSsoUser();
	    		 if(sso.getCheckedInfo()==null||sso.getCheckedInfo().equals("0")){
	    			 response.sendRedirect(basePath1+"entity/basic/userInfoEditAction_toEdit.action");
	    		 }else if(sso.getCheckedPw()==null||sso.getCheckedPw().equals("0")){
	    			 response.sendRedirect(basePath1+"entity/basic/userInfoEditAction_toEditPw.action");
	    		 }else{
	    			 chain.doFilter(request, response);
	    		 }
	    	 }
	     }
	}
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
