package cn.zeppin.product.itic.backadmin.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.alibaba.fastjson.JSON;

import cn.zeppin.product.itic.core.controller.base.ErrorResult;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.utility.WebUtil;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class LoginCheckFilter extends AccessControlFilter{
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public LoginCheckFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 简洁验证是否已登录
     * 格式：Base64(设备号+时间戳+openID(or uuid)+MD5加密的（私钥key+时间戳+用户ID(UUID)）)
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
//		HttpServletRequest httpRequest = (HttpServletRequest) request;  
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		if(session.getAttribute("username") != null) {//登录
			return true;
		} else {//未登录
			return false;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		ErrorResult result = ResultManager.createErrorResult("000", "您未登录!");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
	
}
