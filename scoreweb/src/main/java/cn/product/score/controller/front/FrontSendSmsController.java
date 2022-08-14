package cn.product.score.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;

@Controller
@RequestMapping(value = "/front/sms")
public class FrontSendSmsController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2773125865793473790L;

	/**
	 * 
	 * @param phone
	 * @param type 
	 * @param token base64(device+kaptchaCode+time+md5(key+time+phone+codeType))
	 * @return
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.GET)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号码", required = true)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "验证码类型", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "校验令牌", required = true)
	@ActionParam(key = "deviceType", type = DataType.STRING, message = "客户端类型")
	@ResponseBody
	public Result sendCode(String mobile, String codeType, String token, String deviceType){
		InputParams params = new InputParams("frontSendSmsService", "sendCode");
		params.addParams("mobile", null, mobile);
		params.addParams("codeType", null, codeType);
		params.addParams("token", null, token);
		params.addParams("deviceType", null, deviceType);
		return this.execute(params);
	}
	
	/**
	 * 通过session给用户发验证码
	 * @return
	 */
	@RequestMapping(value = "/sendCodeForUser", method = RequestMethod.GET)
	@ActionParam(key = "deviceType", type = DataType.STRING, message = "客户端类型")
	@ResponseBody
	public Result sendCodeForUser(HttpServletRequest request, String deviceType){
		
		InputParams params = new InputParams("frontSendSmsService", "sendCodeForUser");
//		params.addParams("su", null, getFrontUser(request).getUuid());
		params.addParams("mobile", null, getFrontUser(request).getMobile());
		params.addParams("deviceType", null, deviceType);
		return this.execute(params);
	}
}
