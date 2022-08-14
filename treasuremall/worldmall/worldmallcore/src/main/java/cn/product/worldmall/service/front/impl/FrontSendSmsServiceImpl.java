package cn.product.worldmall.service.front.impl;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.MobileCode;
import cn.product.worldmall.entity.MobileCode.MobileCodeCreatorType;
import cn.product.worldmall.entity.MobileCode.MobileCodeStatus;
import cn.product.worldmall.entity.MobileCode.MobileCodeTypes;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.service.front.FrontSendSmsService;
import cn.product.worldmall.util.Base64Util;
import cn.product.worldmall.util.DESUtil;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.MD5;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;

@Service("frontSendSmsService")
public class FrontSendSmsServiceImpl implements FrontSendSmsService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Override
	public void sendCode(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String codeType = paramsMap.get("codeType") == null ? "" : paramsMap.get("codeType").toString();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String deviceType = paramsMap.get("deviceType") == null ? "" : paramsMap.get("deviceType").toString();
//    	String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		if (!Utlity.isMobileNO(mobile)) {//更改国际手机号验证非法规则为验证是否为全数字
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Illegal mobile number!");
			return;
		}
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Unrecognized device number!");
				return;
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
				deviceType = SendSmsNew.DEVICE_TYPE_WORLDMALL_NX;
			}
			
			SystemParam sp = this.systemParamDao.get(SystemParamKey.SMS_SEND_DEVICE_TYPE);
			if(sp != null) {
				Map<String, Object> deviceMap = JSONUtils.json2map(sp.getParamValue());
				for(String device : deviceMap.keySet()){
					Boolean flag = Boolean.valueOf(deviceMap.get(device).toString());
					if(flag) {
						deviceType = device;
						break;
					}
				}
			} else {
				deviceType = SendSmsNew.DEVICE_TYPE_WORLDMALL_NX;
			}
			
			String codestr = Utlity.getCaptcha(4);//获取4位长度验证码
//			String content = "您本次操作的验证码是："+codestr+"，验证码将在3分钟后失效。";
			String content = codestr + " is your Verification Code. This code will expire in 3 minutes. Please do not disclose it for security purpose.";
			//【拼购红包】您本次操作的验证码是：{1}，验证码将在3分钟后失效。
			
			if (MobileCodeTypes.RESETPWD.equals(codeType)) {//找回密码 验证手机号是否已注册
				FrontUser su = this.frontUserDao.getByMobile(mobile);
				if(su == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Unregistered user!");
					return;
				}
			}
			
			String timestamp = token.substring(8,21);
			if(timestamp == null || "".equals(timestamp)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Verification failed!");
				return;
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Verification fails, request timed out!");
					return;
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Verification fails, request timed out!");
				return;
			}
			
			String md5Str = token.substring(21);
			if(md5Str == null || "".equals(md5Str)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Verification failed!");
				return;
			}
			try {
				
				String realMD5Str = DESUtil.encryptStr(MD5.getMD5(Utlity.KEY_FRONT)+timestamp+mobile+codeType);
				if(md5Str.equals(realMD5Str)){
//					deviceType = SendSmsNew.DEVICE_TYPE_WORLDMALL_NX;
					String resultStr = SendSmsNew.sendSms4Other(codestr, content, mobile, deviceType);
//					String resultStr = SendSmsNew.sendSms4Other(codestr, content, telCode + mobile, deviceType);
					
					if ("0".equals(resultStr.split("_")[0])) {
						
						MobileCode code = new MobileCode();
						code.setUuid(UUID.randomUUID().toString());
						code.setCode(codestr);
						code.setContent(content);
						code.setCreatetime(new Timestamp(System.currentTimeMillis()));
						code.setCreatortype(MobileCodeCreatorType.FRONT);
						code.setMobile(mobile);
						code.setStatus(MobileCodeStatus.NORMAL);
						code.setType(codeType);
						this.mobileCodeDao.insertMobileCode(code);
						result.setStatus(ResultStatusType.SUCCESS);
						result.setMessage("Successful!");
						return;
					}
					
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Failed to send SMS verification code!");
					return;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Failed to send SMS verification code!");
				return;
			}
		}
		result.setStatus(ResultStatusType.FAILED);
		result.setMessage("Update to the latest version to continue to use!");
		return;
	}

	@Override
	public void sendCodeForUser(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String deviceType = paramsMap.get("deviceType") == null ? "" : paramsMap.get("deviceType").toString();
//    	String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
    	
		String phone = mobile;
		if (!Utlity.isMobileNO(phone)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Illegal mobile number!");
			return;
		}
		
		if(!Utlity.checkStringNull(deviceType)) {//包类型
			deviceType = Base64Util.getFromBase64(deviceType);
		} else {
			deviceType = SendSmsNew.DEVICE_TYPE_WORLDMALL_NX;
		}
		
		SystemParam sp = this.systemParamDao.get(SystemParamKey.SMS_SEND_DEVICE_TYPE);
		if(sp != null) {
			Map<String, Object> deviceMap = JSONUtils.json2map(sp.getParamValue());
			for(String device : deviceMap.keySet()){
				Boolean flag = Boolean.valueOf(deviceMap.get(device).toString());
				if(flag) {
					deviceType = device;
					break;
				}
			}
		} else {
			deviceType = SendSmsNew.DEVICE_TYPE_WORLDMALL_NX;
		}
		
		String codestr = Utlity.getCaptcha(4);
//		String content = "您本次操作的验证码是："+codestr+"，验证码将在3分钟后失效。";
		String content = codestr + " is your Verification Code. This code will expire in 3 minutes. Please do not disclose it for security purpose.";
		
		
		try {
//			deviceType = SendSmsNew.DEVICE_TYPE_WORLDMALL_NX;
			String resultStr = SendSmsNew.sendSms4Other(codestr, content, phone, deviceType);
			
//			String telCode = "";
//			InternationalInfo ii = this.internationalInfoDao.get(country);
//			if(ii != null) {
//				telCode = ii.getTelCode();
//			}
//			String resultStr = SendSmsNew.sendSms4Other(codestr, content, telCode + phone, deviceType);
			
			if ("0".equals(resultStr.split("_")[0])) {
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatortype(MobileCodeCreatorType.FRONT);
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(MobileCodeTypes.FUNDCODE);
				this.mobileCodeDao.insertMobileCode(code);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
				return;
			}
			
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Failed to send SMS verification code!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Failed to send SMS verification code!");
			return;
		}
	}

}
