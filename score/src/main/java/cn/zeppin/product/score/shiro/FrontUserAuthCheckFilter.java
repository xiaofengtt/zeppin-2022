package cn.zeppin.product.score.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.zeppin.product.score.controller.base.ErrorResult;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.util.Base64Util;
import cn.zeppin.product.score.util.MD5;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.util.WebUtil;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class FrontUserAuthCheckFilter extends AccessControlFilter{
       
	@Autowired
	private FrontUserService frontUserService;
	
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public FrontUserAuthCheckFilter() {
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
			if(Utlity.DEVICE_APP.equals(deviceNumber)){//app
				if(token.length() != 58){
					return false;
				}
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return false;
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						return false;
					}
				} else {
					return false;
				}
				String mobile = token.substring(15, 26);
				if(mobile == null || "".equals(mobile)){
					return false;
				}
				FrontUser frontUser = this.frontUserService.getByMobile(mobile);
				if(frontUser == null){
					return false;
				}
				String md5Str = token.substring(26);
				if(md5Str == null || "".equals(md5Str)){
					return false;
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY_FRONT_MD5+timestamp+mobile);
				if(md5Str.equals(realMD5Str)){
					request.setAttribute("frontUser", frontUser);
					return true;
				}
				
			} else {
				return false;
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
