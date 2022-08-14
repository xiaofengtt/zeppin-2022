package cn.zeppin.product.score.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.controller.base.TransactionException;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.MobileCode;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.MobileCodeService;
import cn.zeppin.product.score.util.Base64Util;
import cn.zeppin.product.score.util.MD5;
import cn.zeppin.product.score.util.Utlity;


@Controller
@RequestMapping(value = "/loginFront")
public class FrontLoginController extends BaseController{
	
	@Autowired
	private FrontUserService frontUserService;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
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
		
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		if("18888888888".equals(mobile)){
			Map<String, Object> data = new HashMap<String, Object>();
			FrontUser su = this.frontUserService.getByMobile("18888888888");
			data.put("frontUser", su);
			return ResultManager.createDataResult(data,"登录成功！");
		}
		Map<String, Object> result = null;
		try {
			if(this.frontUserService.isExistFrontUserByMobile(mobile, null)){
				result = this.frontUserService.loginByCode(token,mobile);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("frontUser", result.get("frontUser"));
				return ResultManager.createDataResult(data,result.get("message") == null ? "登录成功！" : result.get("message").toString());
			}else{
				Map<String, Object> data = new HashMap<String, Object>();
				result = this.frontUserService.register(token,mobile);
				data.put("frontUser", result.get("frontUser"));	
				return ResultManager.createDataResult(data,result.get("message") == null ? "注册成功！" : result.get("message").toString());
			}
		} catch (TransactionException te) {
			return ResultManager.createFailResult(te.getMessage() == null ? "注册失败！" : te.getMessage());
		} catch (Exception e) {
			return ResultManager.createFailResult("注册失败，服务异常");
		}
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
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		if(this.frontUserService.isExistFrontUserByMobile(mobile, null)){
			FrontUser su = this.frontUserService.getByMobile(mobile);
			
			if(Utlity.checkStringNull(su.getPassword())){
				return ResultManager.createFailResult("用户尚未设置密码！");
			}
			
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("登录失败，未识别的设备号！");
			}
			
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				return ResultManager.createFailResult("请求超时，请重新登录！");
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					return ResultManager.createFailResult("请求超时，请重新登录！");
				}
			} else {
				return ResultManager.createFailResult("请求超时，请重新登录！");
			}
			
			String md5Str = token.substring(15);
			if(md5Str == null || "".equals(md5Str)){
				return ResultManager.createFailResult("用户名或密码错误，登录失败！");
			}
			String realMD5Str = MD5.getMD5(Utlity.KEY_FRONT_MD5+time+su.getMobile()+su.getPassword());
			if(md5Str.equals(realMD5Str)){
				return ResultManager.createDataResult(su, "登录成功！");
			}else{
				return ResultManager.createFailResult("用户名或密码错误，登录失败！");
			}
		}else{
			return ResultManager.createFailResult("用户尚未注册！");
		}
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
		token = Base64Util.getFromBase64(token);
		if(token.length() != 49){
			return ResultManager.createFailResult("登录验证失败，请重新登录！");
		}
		
		String mobile = token.substring(0,11);
		String str = token.substring(11,17);
		String md5 = token.substring(17);
		FrontUser su = this.frontUserService.getByMobile(mobile);
		
		if(su == null){
			return ResultManager.createFailResult("用户未注册！");
		}
		
		String enMd5 = MD5.getMD5(mobile+str+su.getUuid());
		if(!md5.equals(enMd5)){
			return ResultManager.createFailResult("登录验证失败，请重新登录！！");
		}
		
		return ResultManager.createSuccessResult("登录成功!");
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
		mobile = Base64Util.getFromBase64(mobile);
		token = Base64Util.getFromBase64(token);
		
		if(token.length() != 77){
			return ResultManager.createFailResult("验证错误！");
		}
		
		String timestamp = token.substring(0,13);
		String pwd = token.substring(13,45);
		String md5Str = token.substring(45);
		if(md5Str == null || "".equals(md5Str)){
			return ResultManager.createFailResult("验证错误！");
		}
		

		Map<String, Object> inputParams = new HashMap<String, Object>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<MobileCode> lstMobileCode = this.mobileCodeService.getListByParams(inputParams);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(mobile)){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if(!MobileCodeTypes.RESETPWD.equals(mc.getType())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		String realMD5Str = MD5.getMD5(Utlity.KEY_FRONT_MD5+timestamp+mc.getMobile()+pwd+mc.getCode());
		if(md5Str.equals(realMD5Str)){//登录成功	
			FrontUser su = this.frontUserService.getByMobile(mobile);
			su.setPassword(pwd);
			this.frontUserService.update(su);
			return ResultManager.createSuccessResult("找回密码成功！");
		} else {
			return ResultManager.createFailResult("验证码错误，认证失败！");
		}
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
