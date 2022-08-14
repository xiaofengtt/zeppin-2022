package cn.zeppin.product.ntb.shbx.web.controller;

import java.sql.Timestamp;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSmsNew;
import cn.zeppin.product.utility.Utlity;

@Controller
@RequestMapping(value = "/shbxWeb/sms")
public class ShbxWebSendSmsController extends BaseController{
	
	@Autowired
	private IShbxUserService shbxUserService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	/**
	 * 
	 * @param phone
	 * @param type 
	 * @param token base64(device+time+md5(key+time+phone+codeType))
	 * @return
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.GET)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号码", required = true)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "验证码类型", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "校验令牌", required = true)
	@ResponseBody
	public Result sendCode(String mobile, String codeType, String token){
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
			
			codeType = Base64Util.getFromBase64(codeType);
			
			ShbxUser su = this.shbxUserService.getByMobile(mobile);
			
			
			String codestr = Utlity.getCaptcha();
//			String codestr = "000000";
			String content = "验证码："+codestr+"，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
			
			if (MobileCodeTypes.RESETPWD.equals(codeType)) {//找回密码 验证手机号是否已注册
				content = "验证码："+codestr+"，用于密码修改，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
				if(su == null){
					return ResultManager.createFailResult("用户未注册！");
				}
			}
			
			if(token.length() != 47){
				return ResultManager.createFailResult("验证失败！");
			}
			String timestamp = token.substring(2,15);
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
			
			String md5Str = token.substring(15);
			if(md5Str == null || "".equals(md5Str)){
				return ResultManager.createFailResult("验证失败！");
			}
			
			String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+mobile+codeType);
			if(md5Str.equals(realMD5Str)){
				
				try {
//					String result = SendSms.sendSms(content, phone);
					String result = SendSmsNew.sendSms4Shbx(content, mobile);
					if ("0".equals(result.split("_")[0])) {
						
						MobileCode code = new MobileCode();
						code.setUuid(UUID.randomUUID().toString());
						code.setCode(codestr);
						code.setContent(content);
						code.setCreatetime(new Timestamp(System.currentTimeMillis()));
						code.setCreatorType(MobileCodeCreatorType.SHBX_USER);
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
	@ResponseBody
	public Result sendCodeForUser(){
		Session session = SecurityUtils.getSubject().getSession();
		ShbxUser su = (ShbxUser)session.getAttribute("shbxUser");
		
		String phone = su.getMobile();
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		
		String codestr = Utlity.getCaptcha();
		String content = "验证码："+codestr+"，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
		
		
		try {
//			String result = SendSms.sendSms(content, phone);
			String result = SendSmsNew.sendSms4Shbx(content, phone);
			if ("0".equals(result.split("_")[0])) {
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatorType(MobileCodeCreatorType.SHBX_USER);
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
