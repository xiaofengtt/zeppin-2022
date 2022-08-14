package cn.product.worldmall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.WebUtil;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.api.base.ResultManager;
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
	@ActionParam(key = "mobile", type = DataType.STRING, message = "mobile", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ActionParam(key = "channelId", type = DataType.STRING, message = "channelId")
	@ActionParam(key = "agent", type = DataType.STRING, message = "agent")
	@ActionParam(key = "country", type = DataType.STRING, message = "country")
	@ResponseBody
	public Result loginByCode(HttpServletRequest request, String token, String mobile, String channelId, String agent, String country){
		InputParams params = new InputParams("frontLoginService", "loginByCode");
		params.addParams("mobile", null, mobile);
		params.addParams("token", null, token);
		params.addParams("channelId", null, channelId);
		params.addParams("agent", null, agent);
		params.addParams("country", null, country);
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
	@ActionParam(key = "mobile", type = DataType.STRING, message = "mobile", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ActionParam(key = "countryCode", type = DataType.STRING, message = "countryCode")
	@ResponseBody
	public Result loginByPwd(HttpServletRequest request, String token, String mobile, String countryCode){
		InputParams params = new InputParams("frontLoginService", "loginByPwd");
		params.addParams("mobile", null, mobile);
		params.addParams("token", null, token);
		params.addParams("areaCode", null, countryCode);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		return this.execute(params);
	}
	
	/**
	 *  第三方Facebook登录 uniqueKey= userID
	 * token base64(device+time+DES(key+time+DES(userID)))
	 * @param request
	 * @param token
	 * @param accessToken
	 * @param userID
	 * @param nickname
	 * @param image
	 * @param countryCode
	 * @param channelId
	 * @return
	 */
	@ApiOperation(value = "三方Apple登录", notes = "token=base64(device+time+DES(key+time+DES(userID)))")
	@RequestMapping(value = "/loginByFacebook", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ActionParam(key = "accessToken", type = DataType.STRING, message = "accessToken", required = true)
	@ActionParam(key = "userID", type = DataType.STRING, message = "userID", required = true)
	@ActionParam(key = "nickname", type = DataType.STRING, message = "nickname")
	@ActionParam(key = "image", type = DataType.STRING, message = "image")
	@ActionParam(key = "countryCode", type = DataType.STRING, message = "countryCode")
	@ActionParam(key = "channelId", type = DataType.STRING, message = "channelId")
	@ResponseBody
	public Result loginByFacebook(HttpServletRequest request, String token, String accessToken, String userID, String nickname, String image, String countryCode, String channelId){
		InputParams params = new InputParams("frontLoginService", "loginByFacebook");
		params.addParams("token", null, token);
		params.addParams("accessToken", null, accessToken);
		params.addParams("userID", null, userID);
		params.addParams("nickname", null, nickname);
		params.addParams("image", null, image);
		params.addParams("countryCode", null, countryCode);
		params.addParams("channelId", null, channelId);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		return this.execute(params);
	}

	
	/**
	 * 第三方苹果登录 uniqueKey= userID
	 * token base64(device+time+DES(key+time+DES(uniqueKey)))
	 * @param request
	 * @param token
	 * @param identityToken
	 * @param nickname
	 * @param country
	 * @param channelId
	 * @return
	 */
	@ApiOperation(value = "三方Apple登录", notes = "token=base64(device+time+DES(key+time+DES(uniqueKey)))")
	@RequestMapping(value = "/loginByApple", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ActionParam(key = "identityToken", type = DataType.STRING, message = "identityToken", required = true)
	@ActionParam(key = "nickname", type = DataType.STRING, message = "nickname")
	@ActionParam(key = "countryCode", type = DataType.STRING, message = "countryCode")
	@ActionParam(key = "channelId", type = DataType.STRING, message = "channelId")
	@ResponseBody
	public Result loginByApple(HttpServletRequest request, String token, String identityToken, String nickname, String countryCode, String channelId){
		InputParams params = new InputParams("frontLoginService", "loginByApple");
		params.addParams("token", null, token);
		params.addParams("identityToken", null, identityToken);
		params.addParams("nickname", null, nickname);
		params.addParams("countryCode", null, countryCode);
		params.addParams("channelId", null, channelId);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		return this.execute(params);
	}
	
	/**
	 * 用户验证
	 * @param token  Base64(device+time+DES(key+time+uuid))
	 * @return
	 */
	@ApiOperation(value = "登录状态验证", notes = "token=Base64(device+time+DES(key+time+mobile+uuid))")
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "uuid", required = true)
	@ResponseBody
	public Result auth(String token, String uuid){
		InputParams params = new InputParams("frontLoginService", "auth");
		params.addParams("token", null, token);
		params.addParams("uuid", null, uuid);
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
	@ActionParam(key = "mobile", type = DataType.STRING, message="mobile", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message="token", required = true)
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
	@ActionParam(key = "mobile", type = DataType.STRING, message="mobile", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message="token", required = true)
	@ActionParam(key = "pwd", type = DataType.STRING, message="pwd", required = true)
	@ResponseBody
	public Result resetPwd(String mobile, String token, String pwd){
		InputParams params = new InputParams("frontLoginService", "resetPwd");
		params.addParams("token", null, token);
		params.addParams("mobile", null, mobile);
		params.addParams("pwd", null, pwd);
		return this.execute(params);
	}
	
	/**
	 * 用户设备验证
	 * @param token  Base64(mobile+str+DES(mobile+str+uuid))
	 * @return
	 */
	@ApiOperation(value = "用户设备验证", notes = "token=Base64(device+time+DES(key+time+uuid))")
	@RequestMapping(value = "/checkin", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ActionParam(key = "uniqueToken", type = DataType.STRING, message = "uniqueToken", required = true)
	@ActionParam(key = "deviceToken", type = DataType.STRING, message = "deviceToken", required = true)
	@ActionParam(key = "countryCode", message = "countryCode", type = DataType.STRING)
	@ResponseBody
	public Result checkin(HttpServletRequest request, String token, String uniqueToken, String deviceToken, String countryCode){
		InputParams params = new InputParams("frontLoginService", "checkin");
		params.addParams("token", null, token);
		params.addParams("uniqueToken", null, uniqueToken);
		params.addParams("deviceToken", null, deviceToken);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		params.addParams("countryCode", null, countryCode);
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
	
	/**
	 * 获取服务器当前时间
	 * @return
	 */
	@ApiOperation(value = "测试")
	@RequestMapping(value = "/demoNotice", method = RequestMethod.GET)
	@ResponseBody
	public Result demoNotice(){
		InputParams params = new InputParams("frontLoginService", "demoNotice");
		return this.execute(params);
	}
}
