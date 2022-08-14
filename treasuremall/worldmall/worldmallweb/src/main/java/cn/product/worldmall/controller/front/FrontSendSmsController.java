package cn.product.worldmall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

@Controller
@RequestMapping(value = "/front/sms")
@Api(tags= {"sms"})
public class FrontSendSmsController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2773125865793473790L;

	/**
	 * 
	 * @param phone
	 * @param type 
	 * @param token base64(device+kaptchaCode+time+DES(key+time+phone+codeType))
	 * @return
	 */
	@ApiOperation(value = "发送验证码到指定手机号", notes = "token=base64(device+kaptchaCode+time+DES(key+time+telCode+phone+codeType))")
	@RequestMapping(value = "/sendCode", method = RequestMethod.GET)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "mobile", required = true)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "codeType", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ActionParam(key = "deviceType", type = DataType.STRING, message = "deviceType")
	@ResponseBody
	public Result sendCode(String mobile, String codeType, String token, String deviceType, String country){
		InputParams params = new InputParams("frontSendSmsService", "sendCode");
		params.addParams("mobile", null, mobile);
		params.addParams("codeType", null, codeType);
		params.addParams("token", null, token);
		params.addParams("deviceType", null, deviceType);
		params.addParams("country", null, country);
		return this.execute(params);
	}
	
	/**
	 * 通过session给用户发验证码
	 * @return
	 */
	@ApiOperation(value = "发送验证码到登录用户")
	@RequestMapping(value = "/sendCodeForUser", method = RequestMethod.GET)
	@ActionParam(key = "deviceType", type = DataType.STRING, message = "deviceType")
	@ResponseBody
	public Result sendCodeForUser(HttpServletRequest request, String deviceType){
		
		InputParams params = new InputParams("frontSendSmsService", "sendCodeForUser");
//		params.addParams("su", null, getFrontUser(request).getUuid());
		params.addParams("mobile", null, getFrontUser(request).getMobile());
		params.addParams("deviceType", null, deviceType);
		return this.execute(params);
	}
}
