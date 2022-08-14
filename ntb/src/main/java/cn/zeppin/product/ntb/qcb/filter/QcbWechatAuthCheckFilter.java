package cn.zeppin.product.ntb.qcb.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zeppin.product.ntb.core.controller.base.ErrorResult;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.WebUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class QcbWechatAuthCheckFilter extends AccessControlFilter{
       
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public QcbWechatAuthCheckFilter() {
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
			if(Utlity.DEVICE_NUMBER_QCB_WECHAT.equals(deviceNumber)){//微信公众号
				if(token.length() != 75){
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
				String openID = token.substring(15, 43);
				if(openID == null || "".equals(openID)){
					return false;
				}
				QcbEmployee employee = this.qcbEmployeeService.getByOpenId(openID);
				if(employee == null){
					return false;
				}
				String md5Str = token.substring(43);
				if(md5Str == null || "".equals(md5Str)){
					return false;
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY_QCB+timestamp+openID);
				if(md5Str.equals(realMD5Str)){
					request.setAttribute("employee", employee);
					return true;
				}
				
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		ErrorResult result = ResultManager.createErrorResult("302", "鉴权失败!");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
}
