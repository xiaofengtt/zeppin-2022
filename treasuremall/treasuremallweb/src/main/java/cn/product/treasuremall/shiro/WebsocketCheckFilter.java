package cn.product.treasuremall.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.product.treasuremall.api.base.ErrorResult;
import cn.product.treasuremall.api.base.ResultManager;
import cn.product.treasuremall.control.TransferService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.DESUtil;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.WebUtil;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class WebsocketCheckFilter extends AccessControlFilter{
       
	@Autowired
	private TransferService transferService;
	
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public WebsocketCheckFilter() {
        super();
    }
    
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		//token=Base64(device+timestamp+DES(syskey+timestamp))
		String token = request.getParameter("token");
		if(token != null && !"".equals(token)){
			token = Base64Util.getFromBase64(token);
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return false;
			}
			if(Utlity.DEVICE_APP.equals(deviceNumber)){//app
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return false;
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
//					if(Utlity.checkLessTime(time, nowTime)){
//						return false;
//					}
				} else {
					return false;
				}
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					return false;
				}
				
				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+timestamp);
				if(md5Str.equals(realMD5Str)){
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
		
		ErrorResult result = ResultManager.createErrorResult("302", "无权访问！");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
}
