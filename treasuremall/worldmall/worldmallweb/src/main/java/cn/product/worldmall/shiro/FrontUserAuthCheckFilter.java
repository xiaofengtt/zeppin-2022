package cn.product.worldmall.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.ErrorResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.ResultManager;
import cn.product.worldmall.control.TransferService;
import cn.product.worldmall.util.Base64Util;
import cn.product.worldmall.util.DESUtil;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.WebUtil;
import cn.product.worldmall.vo.front.FrontUserVO;

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
		//token=Base64(device+timestamp+uuid+DES(syskey+timestamp+mobile))
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
				FrontUserVO frontUser = null;
				//this.frontUserService.getByMobile(mobile);
				InputParams params = new InputParams("frontUserService", "getByMobile");
				params.addParams("uuid", null, uuid);
				DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
				if(result.getData() != null) {
					String jsonStr = JSONUtils.obj2json(result.getData());
					frontUser = JSONUtils.json2obj(jsonStr, FrontUserVO.class);
				}
				if(frontUser == null){
					return false;
				}
				String md5Str = token.substring(51);
				if(md5Str == null || "".equals(md5Str)){
					return false;
				}
				String mobile = frontUser.getMobile() == null ? "" : frontUser.getMobile();
				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+timestamp+mobile);
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
		
		ErrorResult result = ResultManager.createErrorResult("302", "User is not logged in!");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
}
