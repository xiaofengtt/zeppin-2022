package cn.zeppin.product.score.controller.front;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.MobileCode;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.MobileCodeService;
import cn.zeppin.product.score.util.Base64Util;
import cn.zeppin.product.score.util.MD5;
import cn.zeppin.product.score.util.SendSmsNew;
import cn.zeppin.product.score.util.Utlity;

@Controller
@RequestMapping(value = "/front/sms")
public class FrontSendSmsController extends BaseController{
	
	@Autowired
	private FrontUserService frontUserService;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
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
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		if (!Utlity.isMobileNO(mobile)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("未识别的设备号！");
			}
			
//			// 验证码验证
//			String kaptchaCode = token.substring(2,6);
//			String sessionAuthCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 
//
//			if (!kaptchaCode.equalsIgnoreCase(sessionAuthCode)) {
//				return ResultManager.createFailResult( "图形验证码输入错误！");
//			}
			
			codeType = Base64Util.getFromBase64(codeType);
			if(!Utlity.checkStringNull(deviceType)) {//包类型
				deviceType = Base64Util.getFromBase64(deviceType);
			} else {
				deviceType = "";
			}
			
			String codestr = Utlity.getCaptcha();
			String content = "您本次操作的验证码为："+codestr+"，请及时使用，且勿告知他人，验证码将在5分钟后失效！";
			
			if (MobileCodeTypes.RESETPWD.equals(codeType)) {//找回密码 验证手机号是否已注册
				FrontUser su = this.frontUserService.getByMobile(mobile);
				if(su == null){
					return ResultManager.createFailResult("用户未注册！");
				}
			}
			
			if(token.length() != 51){
				return ResultManager.createFailResult("验证失败！");
			}
			String timestamp = token.substring(6,19);
			if(timestamp == null || "".equals(timestamp)){
				return ResultManager.createFailResult("验证失败！");
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					return ResultManager.createFailResult("验证失败，请求超时！");
				}
			} else {
				return ResultManager.createFailResult("验证失败，请求超时！");
			}
			
			String md5Str = token.substring(19);
			if(md5Str == null || "".equals(md5Str)){
				return ResultManager.createFailResult("验证失败！");
			}
			
			String realMD5Str = MD5.getMD5(MD5.getMD5(Utlity.KEY_FRONT)+timestamp+mobile+codeType);
			if(md5Str.equals(realMD5Str)){
				
				try {
					String result = SendSmsNew.sendSms4Other(codestr, content, mobile, deviceType);
//					if(Utlity.DEVICE_TYPE_FREEKICK.equals(deviceType)) {//任意球
//						result = SendSmsNew.sendSms4Other(content, mobile, deviceType);
//					} else if(Utlity.DEVICE_TYPE_JIUZHOU.equals(deviceType)) {//九州
//						result = SendSmsNew.sendSms4Other(content, mobile, deviceType);
//					} else {
//						result = SendSmsNew.sendSms(content, mobile);
//					}
					
					if ("0".equals(result.split("_")[0])) {
						
						MobileCode code = new MobileCode();
						code.setUuid(UUID.randomUUID().toString());
						code.setCode(codestr);
						code.setContent(content);
						code.setCreatetime(new Timestamp(System.currentTimeMillis()));
						code.setCreatortype(MobileCodeCreatorType.FRONT);
						code.setMobile(mobile);
						code.setStatus(MobileCodeStatus.NORMAL);
						code.setType(codeType);
						this.mobileCodeService.insertMobileCode(code);
						return ResultManager.createSuccessResult("短信验证码发送成功");
					}
					
					return ResultManager.createFailResult("短信验证码发送失败！");
				} catch (Exception e) {
					e.printStackTrace();
					return ResultManager.createFailResult("验证码发送失败！");
				}
				
			}
		}
		return ResultManager.createFailResult("请更新至最新版继续使用！");
	}
	
	/**
	 * 通过session给用户发验证码
	 * @return
	 */
	@RequestMapping(value = "/sendCodeForUser", method = RequestMethod.GET)
	@ActionParam(key = "deviceType", type = DataType.STRING, message = "客户端类型")
	@ResponseBody
	public Result sendCodeForUser(HttpServletRequest request, String deviceType){
		FrontUser su = getFrontUser(request);
		
		String phone = su.getMobile();
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		
		if(!Utlity.checkStringNull(deviceType)) {//包类型
			deviceType = Base64Util.getFromBase64(deviceType);
		} else {
			deviceType = "";
		}
		
		String codestr = Utlity.getCaptcha();
		String content = "您本次操作的验证码为："+codestr+"，请及时使用，且勿告知他人，验证码将在5分钟后失效！";
		
		
		try {
			String result = SendSmsNew.sendSms4Other(codestr, content, phone, deviceType);
//			if(Utlity.DEVICE_TYPE_FREEKICK.equals(deviceType)) {//任意球
//				result = SendSmsNew.sendSms4Other(content, phone, deviceType);
//			} else if(Utlity.DEVICE_TYPE_JIUZHOU.equals(deviceType)) {//九州
//				result = SendSmsNew.sendSms4Other(content, phone, deviceType);
//			} else {
//				result = SendSmsNew.sendSms(content, phone);
//			}
			if ("0".equals(result.split("_")[0])) {
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatortype(MobileCodeCreatorType.FRONT);
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(MobileCodeTypes.FUNDCODE);
				this.mobileCodeService.insertMobileCode(code);
				return ResultManager.createSuccessResult("短信验证码发送成功");
			}
			
			return ResultManager.createFailResult("短信验证码发送失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("验证码发送失败！");
		}
	}
}
