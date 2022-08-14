package cn.product.treasuremall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.api.base.ResultManager;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value = "/front/login")
@Api(tags= {"login"})
public class FrontLoginController extends BaseController{
	
	private static final long serialVersionUID = -6314247389756021851L;

	/**
	 * 验证码登录
	 * @param mobile base64
	 * @param token base64(device+time+des(key+time+mobile+code))
	 * @return
	 */
	@ApiOperation(value = "验证码登录", notes = "token=base64(device+time+des(key+time+mobile+code))||channelId默认值default_channel")
	@RequestMapping(value = "/loginByCode", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "channelId", type = DataType.STRING, message = "注册渠道")
	@ActionParam(key = "agent", type = DataType.STRING, message = "邀请人")
	@ResponseBody
	public Result loginByCode(HttpServletRequest request, String token, String mobile, String channelId, String agent){
		InputParams params = new InputParams("frontLoginService", "loginByCode");
		params.addParams("mobile", null, mobile);
		params.addParams("token", null, token);
		params.addParams("channelId", null, channelId);
		params.addParams("agent", null, agent);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		return this.execute(params);
	}
	
	/**
	 * 密码登录
	 * @param mobile base64
	 * @param token base64(device+time+DES(key+time+mobile+DES(pwd)))
	 * @return
	 */
	@ApiOperation(value = "密码登录", notes = "token=base64(device+time+DES(key+time+mobile+DES(pwd)))")
	@RequestMapping(value = "/loginByPwd", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ResponseBody
	public Result loginByPwd(HttpServletRequest request, String token, String mobile){
		InputParams params = new InputParams("frontLoginService", "loginByPwd");
		params.addParams("mobile", null, mobile);
		params.addParams("token", null, token);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		return this.execute(params);
	}
	
	/**
	 * 用户验证
	 * @param token  Base64(mobile+str+DES(mobile+str+uuid))
	 * @return
	 */
	@ApiOperation(value = "登录状态验证", notes = "token=Base64(mobile+str+DES(mobile+str+uuid))")
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ResponseBody
	public Result auth(String token){
		InputParams params = new InputParams("frontLoginService", "auth");
		params.addParams("token", null, token);
		return this.execute(params);
	}

	/**
	 * 重置密码-验证
	 * @param mobile base64
	 * @param token 规则Base64(timestamps+DES(key+time+mobile+code))
	 * @return
	 */
	@ApiOperation(value = "重置密码", notes = "token=Base64(timestamps+DES(key+time+mobile+code))")
	@RequestMapping(value = "/resetCheck", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message="手机号", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message="加密信息", required = true)
	@ResponseBody
	public Result resetCheck(String mobile, String token, String pwd){
		InputParams params = new InputParams("frontLoginService", "resetCheck");
		params.addParams("token", null, token);
		params.addParams("mobile", null, mobile);
		params.addParams("pwd", null, pwd);
		return this.execute(params);
	}
	
	/**
	 * 重置密码
	 * @param mobile base64
	 * @param token 规则Base64(timestamps+DES(key+time+mobile+DES(新pwd)+code))
	 * @param pwd base64(DES(新pwd))
	 * @return
	 */
	@ApiOperation(value = "重置密码", notes = "token=Base64(timestamps+DES(key+time+mobile+DES(新pwd)+code))")
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message="手机号", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message="加密信息", required = true)
	@ActionParam(key = "pwd", type = DataType.STRING, message="新密码", required = true)
	@ResponseBody
	public Result resetPwd(String mobile, String token, String pwd){
		InputParams params = new InputParams("frontLoginService", "resetPwd");
		params.addParams("token", null, token);
		params.addParams("mobile", null, mobile);
		params.addParams("pwd", null, pwd);
		return this.execute(params);
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@ApiOperation(value = "退出登录")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Result logout(){
		return ResultManager.createSuccessResult("退出成功！");
	}
	
	/**
	 * 获取服务器当前时间
	 * @return
	 */
	@ApiOperation(value = "获取服务器当前时间")
	@RequestMapping(value = "/getTime", method = RequestMethod.GET)
	@ResponseBody
	public Result getTime(){
		return ResultManager.createDataResult(System.currentTimeMillis());
	}
}
