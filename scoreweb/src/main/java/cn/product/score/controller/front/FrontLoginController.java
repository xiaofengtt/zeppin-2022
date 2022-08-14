package cn.product.score.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.api.base.ResultManager;
import cn.product.score.controller.BaseController;


@Controller
@RequestMapping(value = "/loginFront")
public class FrontLoginController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6314247389756021851L;

	/**
	 * 用户登录
	 * @param token base64(device+time+md5(key+time+mobile+code))
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ResponseBody
	public Result login(String token, String mobile){
		
		InputParams params = new InputParams("frontLoginService", "login");
		params.addParams("token", null, token);
		params.addParams("mobile", null, mobile);
		return this.execute(params);
	}
	
	/**
	 * 用户登录
	 * @param token base64(device+time+md5(key+time+mobile+md5(pwd)))
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/loginByPwd", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ResponseBody
	public Result loginByPwd(String token, String mobile){
		
		InputParams params = new InputParams("frontLoginService", "loginByPwd");
		params.addParams("token", null, token);
		params.addParams("mobile", null, mobile);
		return this.execute(params);
	}
	
	/**
	 * 用户验证
	 * @param token  Base64(mobile+str+md5(mobile+str+uuid))
	 * @return
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ResponseBody
	public Result auth(String token){
		
		InputParams params = new InputParams("frontLoginService", "auth");
		params.addParams("token", null, token);
		return this.execute(params);
	}

	/**
	 * 找回密码
	 * @param phone base64
	 * @param token 规则Base64(timestamps+md5(pwd)+MD5(key+time+md5(mobile)+md5(pwd)+md5(验证码)))
	 * @return
	 */
	@RequestMapping(value = "/forgotPwd", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message="手机号", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message="用户密码加密信息", required = true)
	@ResponseBody
	public Result forgotPwd(String mobile, String token){
		
		InputParams params = new InputParams("frontLoginService", "loginByPwd");
		params.addParams("token", null, token);
		params.addParams("mobile", null, mobile);
		return this.execute(params);
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Result logout(){
		return ResultManager.createSuccessResult("退出成功！");
	}
	
	/**
	 * 获取服务器当前时间
	 * @return
	 */
	@RequestMapping(value = "/getTime", method = RequestMethod.GET)
	@ResponseBody
	public Result getTime(){
		return ResultManager.createDataResult(System.currentTimeMillis());
	}
}
