package cn.product.payment.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.payment.controller.base.ErrorResult;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.service.CompanyAdminService;
import cn.product.payment.util.Base64Util;
import cn.product.payment.util.MD5;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.WebUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class CompanyAdminAuthFilter extends AccessControlFilter{
       
	@Autowired
	private CompanyAdminService companyAdminService;
	
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public CompanyAdminAuthFilter() {
        super();
    }
    
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		
		String token = request.getParameter("token");
		if(token != null && !"".equals(token)){
			token = Base64Util.getFromBase64(token);
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return false;
			}
			if(token.length() != 56){
				return false;
			}
			String timestamp = token.substring(0,13);
			if(timestamp == null || "".equals(timestamp)){
				return false;
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					return false;
				}
			} else {
				return false;
			}
			String mobile = token.substring(13, 24);
			if(mobile == null || "".equals(mobile)){
				return false;
			}
			CompanyAdmin companyAdmin = this.companyAdminService.getByMobile(mobile);
			if(companyAdmin == null){
				return false;
			}
			String md5Str = token.substring(24);
			if(md5Str == null || "".equals(md5Str)){
				return false;
			}
			
			String realMD5Str = MD5.getMD5(timestamp+mobile);
			if(md5Str.equals(realMD5Str)){
				request.setAttribute("companyAdmin", companyAdmin);
				return true;
			}
				
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		ErrorResult result = ResultManager.createErrorResult("302", "用户未登录!");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
}
