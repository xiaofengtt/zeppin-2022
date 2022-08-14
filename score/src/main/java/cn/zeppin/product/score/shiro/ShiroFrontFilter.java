package cn.zeppin.product.score.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zeppin.product.score.controller.base.ErrorResult;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUser.FrontUserStatus;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.util.WebUtil;

import com.alibaba.fastjson.JSON;

public class ShiroFrontFilter extends FormAuthenticationFilter{
	
	@Autowired
    private FrontUserService frontUserService;
	
	/**
     * 校验是否已登录
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		if(session.getAttribute("frontUser") == null){
			return false;
		}
		
		FrontUser su = (FrontUser) session.getAttribute("frontUser");
		if(!FrontUserStatus.NORMAL.equals(su.getStatus())){
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
        	HttpServletRequest httpRequest = (HttpServletRequest)request;
        	HttpServletResponse httpResponse = (HttpServletResponse) response;
        	//跨域
        	httpResponse.setHeader("Access-Control-Allow-Origin",httpRequest.getHeader("origin"));
        	httpResponse.setHeader("Access-Control-Allow-Credentials","true");
        	ErrorResult result = ResultManager.createErrorResult("302", "用户未登录，不能进行该操作!");
        	
        	WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));
            return false;
        }
    }
	
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
        FrontUser frontUser = frontUserService.getByMobile(token.getPrincipal().toString());
        session.setAttribute("frontUser", frontUser);
        return true;
	}
	
	@Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return false;
    }
}
