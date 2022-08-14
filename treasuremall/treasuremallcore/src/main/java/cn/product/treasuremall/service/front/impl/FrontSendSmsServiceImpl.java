package cn.product.treasuremall.service.front.impl;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.MobileCodeDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.entity.MobileCode.MobileCodeCreatorType;
import cn.product.treasuremall.entity.MobileCode.MobileCodeStatus;
import cn.product.treasuremall.entity.MobileCode.MobileCodeTypes;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.service.front.FrontSendSmsService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.DESUtil;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.MD5;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;

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
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		if (!Utlity.isMobileNO(mobile)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手机号非法！");
			return;
		}
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("未识别的设备号！");
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
				deviceType = SendSmsNew.DEVICE_TYPE_TREASUREMALL_SH;
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
				deviceType = SendSmsNew.DEVICE_TYPE_TREASUREMALL_SH;
			}
			
			String codestr = Utlity.getCaptcha(4);//获取4位长度验证码
			String content = "您本次操作的验证码是："+codestr+"，验证码将在3分钟后失效。";
			//【拼购红包】您本次操作的验证码是：{1}，验证码将在3分钟后失效。
			
			if (MobileCodeTypes.RESETPWD.equals(codeType)) {//找回密码 验证手机号是否已注册
				FrontUser su = this.frontUserDao.getByMobile(mobile);
				if(su == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("用户未注册！");
					return;
				}
			}
			
			String timestamp = token.substring(8,21);
			if(timestamp == null || "".equals(timestamp)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("验证失败！");
				return;
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("验证失败，请求超时！");
					return;
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("验证失败，请求超时！");
				return;
			}
			
			String md5Str = token.substring(21);
			if(md5Str == null || "".equals(md5Str)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("验证失败！");
				return;
			}
			try {
				
				String realMD5Str = DESUtil.encryptStr(MD5.getMD5(Utlity.KEY_FRONT)+timestamp+mobile+codeType);
				if(md5Str.equals(realMD5Str)){
					
					String resultStr = SendSmsNew.sendSms4Other(codestr, content, mobile, deviceType);
					
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
						result.setMessage("短信验证码发送成功");
						return;
					}
					
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("短信验证码发送失败！");
					return;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("验证码发送失败！");
				return;
			}
		}
		result.setStatus(ResultStatusType.FAILED);
		result.setMessage("请更新至最新版继续使用！");
		return;
	}

	@Override
	public void sendCodeForUser(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String deviceType = paramsMap.get("deviceType") == null ? "" : paramsMap.get("deviceType").toString();
    	
		String phone = mobile;
		if (!Utlity.isMobileNO(phone)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手机号非法！");
			return;
		}
		
		if(!Utlity.checkStringNull(deviceType)) {//包类型
			deviceType = Base64Util.getFromBase64(deviceType);
		} else {
			deviceType = SendSmsNew.DEVICE_TYPE_TREASUREMALL_SH;
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
			deviceType = SendSmsNew.DEVICE_TYPE_TREASUREMALL_SH;
		}
		
		String codestr = Utlity.getCaptcha(4);
		String content = "您本次操作的验证码是："+codestr+"，验证码将在3分钟后失效。";
		
		
		try {
			String resultStr = SendSmsNew.sendSms4Other(codestr, content, phone, deviceType);
//			if(Utlity.DEVICE_TYPE_FREEKICK.equals(deviceType)) {//任意球
//				resultStr = SendSmsNew.sendSms4Other(content, phone, deviceType);
//			} else if(Utlity.DEVICE_TYPE_JIUZHOU.equals(deviceType)) {//九州
//				resultStr = SendSmsNew.sendSms4Other(content, phone, deviceType);
//			} else {
//				resultStr = SendSmsNew.sendSms(content, phone);
//			}
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
				result.setMessage("短信验证码发送成功");
				return;
			}
			
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("短信验证码发送失败！");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码发送失败！");
			return;
		}
	}

}
