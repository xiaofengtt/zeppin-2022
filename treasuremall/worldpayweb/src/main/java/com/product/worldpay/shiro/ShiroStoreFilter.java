package com.product.worldpay.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.product.worldpay.controller.base.ErrorResult;
import com.product.worldpay.controller.base.ResultManager;
import com.product.worldpay.util.WebUtil;

import com.alibaba.fastjson.JSON;

public class ShiroStoreFilter extends FormAuthenticationFilter{
	
	/**
     * 校验是否已登录
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		if(session.getAttribute("companyAdmin") == null){
			return false;
		}
		
		return true;
	}
	
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
        	HttpServletResponse httpResponse = (HttpServletResponse) response;
    		
    		ErrorResult result = ResultManager.createErrorResult("302", "User not logged in!");
    		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
            return false;
        }
    }
	
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        return true;
	}
	
	@Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return false;
    }
}
