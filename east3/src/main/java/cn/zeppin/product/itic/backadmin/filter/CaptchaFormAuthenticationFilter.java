/**
 * 
 */
package cn.zeppin.product.itic.backadmin.filter;


import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.utility.WebUtil;

/**
 * @author elegantclack
 *
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

	
	private static final Logger log = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);  
	/**
	 * 
	 */
	public CaptchaFormAuthenticationFilter() {
		// TODO Auto-generated constructor stub
	}
	
	 /* 
     *  主要是针对登录成功的处理方法。对于请求头是ajax的之间返回JSON字符串。 
     */  
    @Override  
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)  
            throws Exception {  
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;  
        // 不是ajax请求，跳转到登录成功后的首页面
        if (!WebUtil.isAjax(request)) {
            issueSuccessRedirect(request, response);  
        } 
        //ajax请求，返回登录成功结果
        else {  
        	Result result = ResultManager.createSuccessResult("登录成功!");
        	WebUtil.sendJson(httpServletResponse, JSON.toJSONString(result, true)); 
        }  
        return false;  
    }  
  
    /** 
     * 主要是处理登录失败的方法 
     */  
    @Override  
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {  
    	 HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    	// 不是ajax请求，  
        if (!WebUtil.isAjax(request)) {
            setFailureAttribute(request, e);  
            return true;  
        }  
        //是ajax请求
        try {  
        	if (e.getClass().equals(UnknownAccountException.class)) {
        		Result result = ResultManager.createErrorResult("001", "没有此账户信息!");
        		WebUtil.sendJson(httpServletResponse, JSON.toJSONString(result, true));
        	}
        	else if (e.getClass().equals(LockedAccountException.class)) {
        		Result result = ResultManager.createErrorResult("002", "密码输入错误超过次数限制，账户已锁定!");
        		WebUtil.sendJson(httpServletResponse, JSON.toJSONString(result, true));
        	}
        	else if (e.getClass().equals(IncorrectCredentialsException.class)) {
        		Result result = ResultManager.createErrorResult("003", e.getMessage());
        		WebUtil.sendJson(httpServletResponse, JSON.toJSONString(result, true));
        	}
        	else if (e.getClass().equals(ExcessiveAttemptsException.class)) {
        		Result result = ResultManager.createErrorResult("004", "密码输入错误超过次数限制，账户已锁定!");
        		WebUtil.sendJson(httpServletResponse, JSON.toJSONString(result, true));
        	}
        	else {
        		Result result = ResultManager.createErrorResult("005", "该账户已停用或其他错误!");
        		WebUtil.sendJson(httpServletResponse, JSON.toJSONString(result, true));
        	}
        } catch (IOException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }  
        return false;  
    }  
  
    /** 
     * 所有请求都会经过的方法。 
     */  
    @Override  
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
    	
    	//是已登录状态的请求
        if (isLoginRequest(request, response)) {  
        	//是post请求
            if (isLoginSubmission(request, response)) {  
                if (log.isTraceEnabled()) {  
                    log.trace("Login submission detected.  Attempting to execute login.");  
                }  
                return executeLogin(request, response);  
            } else {  
                if (log.isTraceEnabled()) {  
                    log.trace("Login page view.");  
                }  
                // allow them to see the login page ;)  
                return true;  
            }  
        } 
        //是未登录状态的请求
        else {  
            if (log.isTraceEnabled()) {  
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the "  
                        + "Authentication url [" + getLoginUrl() + "]");  
            }  
            //非ajax请求，跳转到登录页面
            if (!WebUtil.isAjax(request)) {
                saveRequestAndRedirectToLogin(request, response);  
            } 
            //ajax请求，返回错误信息
            else {  
				Result result = ResultManager.createErrorResult("301", "您尚未登录或登录超时，请重新登录!");
				WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
            }  
            return false;  
        }  
        
    }
}
