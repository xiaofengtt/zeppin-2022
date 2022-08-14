package cn.zeppin.product.ntb.web.controller;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

@Controller
@RequestMapping(value = "/web/sms")
public class SendSmsController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	/**
	 * 
	 * @param phone
	 * @param type register--注册  resetpwd--重置密码
	 * @param token base64(device+time+md5(key+time+phone+codeType))
	 * @return
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.GET)
	@ActionParam(key = "phone", type = DataType.STRING, message = "手机号码", required = true)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "验证码类型", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "校验令牌", required = true)
	@ResponseBody
	public Result sendCode(String phone, String codeType, String token){
		token = Base64Util.getFromBase64(token);
		
		phone = Base64Util.getFromBase64(phone);
		
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("未识别的设备号！");
			}
			
			codeType = Base64Util.getFromBase64(codeType);
			
			Investor investor = this.investorService.getByMobile(phone, Investor.class);
			
			
			String codestr = Utlity.getCaptcha();
			String content = "验证码："+codestr+"，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
			
			if(MobileCodeTypes.REGISTER.equals(codeType)){//注册验证手机号是否已注册
				content = "验证码："+codestr+"，用于本次登录/注册，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
				if(investor != null){
					return ResultManager.createFailResult("用户已注册！");
				}
			} else if (MobileCodeTypes.RESETPWD.equals(codeType)) {//找回密码 验证手机号是否已注册
				content = "验证码："+codestr+"，用于密码修改，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
				if(investor == null){
					return ResultManager.createFailResult("用户未注册！");
				}
			} else if (MobileCodeTypes.CODE.equals(codeType)) {
				if(investor == null){
					return ResultManager.createFailResult("用户未注册！");
				}
			} else {
				
			}
			
			if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
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
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+phone+codeType);
				if(md5Str.equals(realMD5Str)){
					
					try {
						String result = SendSms.sendSms(content, phone);
						if ("0".equals(result.split("_")[0])) {
							
							MobileCode code = new MobileCode();
							code.setUuid(UUID.randomUUID().toString());
							code.setCode(codestr);
							code.setContent(content);
							code.setCreatetime(new Timestamp(System.currentTimeMillis()));
							code.setCreatorType(MobileCodeCreatorType.INVESTOR);
							code.setMobile(phone);
							code.setStatus(MobileCodeStatus.NORMAL);
							code.setType(codeType);
							this.mobileCodeService.insertMobileCode(code);
							return ResultManager.createSuccessResult("短信验证码发送成功");
						}
						
						return ResultManager.createFailResult("短信验证码发送失败！");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						return ResultManager.createFailResult("验证码发送失败！");
					}
					
				}
			} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return ResultManager.createFailResult("验证失败！");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkTime(time, nowTime)){
						return ResultManager.createFailResult("验证失败，请求超时！");
					}
				} else {
					return ResultManager.createFailResult("验证失败，请求超时！");
				}
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("验证失败！");
				}
				String realMD5Str = MD5.getMD5(MD5.getMD5(Utlity.DEVICE_NUMBER_WEB+Utlity.KEY)+time+phone+codeType);
				if(realMD5Str.equals(md5Str)){//成功
					try {
						String result = SendSms.sendSms(content, phone);
						if ("0".equals(result.split("_")[0])) {
							
							MobileCode code = new MobileCode();
							code.setUuid(UUID.randomUUID().toString());
							code.setCode(codestr);
							code.setContent(content);
							code.setCreatetime(new Timestamp(System.currentTimeMillis()));
							code.setCreatorType(MobileCodeCreatorType.INVESTOR);
							code.setMobile(phone);
							code.setStatus(MobileCodeStatus.NORMAL);
							code.setType(codeType);
							this.mobileCodeService.insertMobileCode(code);
							return ResultManager.createSuccessResult("短信验证码发送成功");
						}
						
						return ResultManager.createFailResult("短信验证码发送失败！");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						return ResultManager.createFailResult("验证码发送失败！");
					}
				}
			} else {
				return ResultManager.createFailResult("未识别的设备号！");
			}
		}
		return ResultManager.createFailResult("请更新至最新版继续使用！");
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/sendCodeById", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "用户编号", required = true)
	@ResponseBody
	public Result sendCodeById(String uuid){
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		String phone = investor.getMobile();
		
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		
		String codestr = Utlity.getCaptcha();
		String content = "验证码："+codestr+"，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
		
		
		try {
			String result = SendSms.sendSms(content, phone);
			if ("0".equals(result.split("_")[0])) {
				
//				Map<String, String> inputParams = new HashMap<String, String>();
//				inputParams.put("mobile", phone);
//				inputParams.put("status", MobileCodeStatus.NORMAL);
//				List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
//
//				// 如果之前存在验证码，设置验证码失效
//				if (lstMobileCode != null && lstMobileCode.size() > 0) {
//					for(Entity entity: lstMobileCode){
//						MobileCode code = (MobileCode)entity;
//						code.setStatus(MobileCodeStatus.DISABLE);
//						this.mobileCodeService.update(code);
//					}
//				}
				
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatorType(MobileCodeCreatorType.INVESTOR);
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(MobileCodeTypes.FUNDCODE);
//				this.mobileCodeService.insert(code);
				this.mobileCodeService.insertMobileCode(code);
				return ResultManager.createSuccessResult("短信验证码发送成功");
			}
			
			return ResultManager.createFailResult("短信验证码发送失败！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResultManager.createFailResult("验证码发送失败！");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Base64Util.getBase64("18612033494"));
	}
}
