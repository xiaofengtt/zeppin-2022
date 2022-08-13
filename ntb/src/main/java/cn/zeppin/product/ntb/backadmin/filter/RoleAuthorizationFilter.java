/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;

import cn.zeppin.product.ntb.core.controller.base.ErrorResult;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.utility.WebUtil;

/**
 * @author elegantclack
 *
 */
public class RoleAuthorizationFilter extends AuthorizationFilter {

	/**
	 * 
	 */
	public RoleAuthorizationFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 当访问被拒绝时动作
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {  

		HttpServletRequest httpRequest = (HttpServletRequest) request;  
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		String unauthorizedUrl = getUnauthorizedUrl();

		Subject subject = getSubject(request, response);  
		//未登录状态
		if (subject.getPrincipal() == null) {  
			//ajax请求，返回错误信息
			if (WebUtil.isAjax(httpRequest)) {
				ErrorResult result = ResultManager.createErrorResult("301", "您尚未登录或登录超时，请重新登录!");
				result.setRedirect(unauthorizedUrl);
				WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
			}
			//非ajax请求，跳转到登录页面
			else {  
				saveRequestAndRedirectToLogin(request, response);  
			}  
		}
		//登录状态
		else {  
			//ajax请求，返回错误信息
			if (WebUtil.isAjax(httpRequest)) {  
				ErrorResult result = ResultManager.createErrorResult("302", "您没有足够的权限执行该操作!");
				result.setRedirect(unauthorizedUrl);
				WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
			}
			//非ajax请求, 跳转到没有权限401错误页面
			else {  
				if (StringUtils.hasText(unauthorizedUrl)) {  
					WebUtils.issueRedirect(request, response, unauthorizedUrl);  
				} else {  
					WebUtils.toHttp(response).sendError(401);  
				}  
			}  
		}  
		return false;  
	}  

	/**
	 * 当访问被允许时的动作
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);  
		String[] rolesArray = (String[]) mappedValue;  

		if (rolesArray == null || rolesArray.length == 0) {  
			// no roles specified, so nothing to check - allow access.  
			return true;  
		}  

		Set<String> roles = CollectionUtils.asSet(rolesArray);  
		for (String role : roles) {  
			if (subject.hasRole(role)) {  
				return true;  
			}  
		}  
		return false;  
	}

}
