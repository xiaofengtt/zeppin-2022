package cn.zeppin.product.ntb.backadmin.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.core.controller.base.ErrorResult;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.WebUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class TokenCheckFilter extends AccessControlFilter{
       
	@Autowired
	private IInvestorService investorService;
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public TokenCheckFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 校验token规则
     * 格式：Base64(设备号+时间戳+openID(or uuid)+MD5加密的（私钥key+时间戳+用户ID(UUID)）)
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
//		HttpServletRequest httpRequest = (HttpServletRequest) request;  
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String token = request.getParameter("token");
		if(token != null && !"".equals(token)){
			token = Base64Util.getFromBase64(token);
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return false;
			}
			if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){//微信小程序
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
				Investor invertor = this.investorService.getByOpenID(openID, Investor.class);
				String uuid = "";
				if(invertor != null){
					uuid = invertor.getUuid();
				}
				String md5Str = token.substring(43);
				if(md5Str == null || "".equals(md5Str)){
					return false;
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+uuid);
				if(md5Str.equals(realMD5Str)){
					return true;
				}
				
			} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
				if(token.length() != 83){
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
				
				String uuid = token.substring(15, 51);
				if(uuid == null || "".equals(uuid)){
					return false;
				}
				String md5Str = token.substring(51);
				if(md5Str == null || "".equals(md5Str)){
					return false;
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+uuid);
				if(md5Str.equals(realMD5Str)){
					return true;
				}
				
			} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
				
			}
		}
		
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		ErrorResult result = ResultManager.createErrorResult("302", "您没有足够的权限执行该操作!");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
	
	public static void main(String[] args) {
//		String token = "MDIxNTA0MDAwMzY4MDAwNWYzMmNjZmYtMTY5NC0xMWU3LWFlMzItODg4NzBjYTcyM2I3NGY1YmE5YTBlOWVkZWI1ODM1MzM2ZTJkZWVmOWJmYjM=";
		String token = "MDExNTAzOTk5ODI5MDAwb0tUNy0wQndRU2ZDTHFidTJqZ3FqX3VudWlRWTZkNGRkZWE0ZTI2MjQ2Yzc5ZjFkYTExZjY0ZDQwNDUw";
		System.out.println(token.length());
		System.out.println(Base64Util.getFromBase64(token));
		System.out.println(Base64Util.getFromBase64(token).length());
		token = Base64Util.getFromBase64(token);
		String deviceNumber = token.substring(0,2);
		System.out.println(deviceNumber);
		String timestamp = token.substring(2,15);
		System.out.println(timestamp);
		String openID = token.substring(15, 43);
		System.out.println(openID);
		String md5Str = token.substring(43);
		System.out.println(md5Str);
		System.out.println(MD5.getMD5(Utlity.KEY));
	}
}
