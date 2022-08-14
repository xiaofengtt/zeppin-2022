package cn.product.treasuremall.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.ErrorResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.ResultManager;
import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.control.TransferService;
import cn.product.treasuremall.util.WebUtil;

public class ShiroBackFilter extends FormAuthenticationFilter{
	
	@Autowired
	private TransferService transferService;
	
	/**
     * 校验是否已登录
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		if(session.getAttribute("backAdmin") == null){
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
    		
    		ErrorResult result = ResultManager.createErrorResult("302", "用户未登录，不能进行该操作!");
    		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
            return false;
        }
    }
	
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return true;
	}
	
	@Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return false;
    }
}
