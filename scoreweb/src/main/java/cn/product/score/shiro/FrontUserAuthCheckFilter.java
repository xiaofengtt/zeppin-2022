package cn.product.score.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.ErrorResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.ResultManager;
import cn.product.score.control.TransferService;
import cn.product.score.entity.FrontUser;
import cn.product.score.util.Base64Util;
import cn.product.score.util.MD5;
import cn.product.score.util.Utlity;
import cn.product.score.util.WebUtil;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class FrontUserAuthCheckFilter extends AccessControlFilter{
       
	@Autowired
	private TransferService transferService;
	
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public FrontUserAuthCheckFilter() {
        super();
    }
    
	@SuppressWarnings("unchecked")
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
				FrontUser frontUser = null;
				//this.frontUserService.getByMobile(mobile);
				InputParams params = new InputParams("frontUserService", "getByMobile");
				params.addParams("mobile", null, mobile);
				DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
				if(result.getData() != null) {
					frontUser = (FrontUser) result.getData();
				}
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
