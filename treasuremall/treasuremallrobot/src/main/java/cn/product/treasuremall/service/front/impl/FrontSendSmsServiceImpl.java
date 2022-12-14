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
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.entity.MobileCode.MobileCodeCreatorType;
import cn.product.treasuremall.entity.MobileCode.MobileCodeStatus;
import cn.product.treasuremall.entity.MobileCode.MobileCodeTypes;
import cn.product.treasuremall.service.front.FrontSendSmsService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.DESUtil;
import cn.product.treasuremall.util.MD5;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;

@Service("frontSendSmsService")
public class FrontSendSmsServiceImpl implements FrontSendSmsService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
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
			result.setMessage("??????????????????");
			return;
		}
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			
//			// ???????????????
//			String kaptchaCode = token.substring(2,6);
//			String sessionAuthCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 
//
//			if (!kaptchaCode.equalsIgnoreCase(sessionAuthCode)) {
//				return ResultManager.createFailResult( "??????????????????????????????");
//			}
			
			codeType = Base64Util.getFromBase64(codeType);
			if(!Utlity.checkStringNull(deviceType)) {//?????????
				deviceType = Base64Util.getFromBase64(deviceType);
			} else {
				deviceType = "";
			}
			
			String codestr = Utlity.getCaptcha(4);//??????4??????????????????
			String content = "?????????????????????????????????"+codestr+"??????????????????3??????????????????";
			//???????????????????????????????????????????????????{1}??????????????????3??????????????????
			
			if (MobileCodeTypes.RESETPWD.equals(codeType)) {//???????????? ??????????????????????????????
				FrontUser su = this.frontUserDao.getByMobile(mobile);
				if(su == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("??????????????????");
					return;
				}
			}
			
			String timestamp = token.substring(8,21);
			if(timestamp == null || "".equals(timestamp)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("??????????????????????????????");
					return;
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("??????????????????????????????");
				return;
			}
			
			String md5Str = token.substring(21);
			if(md5Str == null || "".equals(md5Str)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
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
						result.setMessage("???????????????????????????");
						return;
					}
					
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("??????????????????????????????");
					return;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
		}
		result.setStatus(ResultStatusType.FAILED);
		result.setMessage("????????????????????????????????????");
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
			result.setMessage("??????????????????");
			return;
		}
		
		if(!Utlity.checkStringNull(deviceType)) {//?????????
			deviceType = Base64Util.getFromBase64(deviceType);
		} else {
			deviceType = "";
		}
		
		String codestr = Utlity.getCaptcha(4);
		String content = "?????????????????????????????????"+codestr+"??????????????????3??????????????????";
		
		
		try {
			String resultStr = SendSmsNew.sendSms4Other(codestr, content, phone, deviceType);
//			if(Utlity.DEVICE_TYPE_FREEKICK.equals(deviceType)) {//?????????
//				resultStr = SendSmsNew.sendSms4Other(content, phone, deviceType);
//			} else if(Utlity.DEVICE_TYPE_JIUZHOU.equals(deviceType)) {//??????
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
				result.setMessage("???????????????????????????");
				return;
			}
			
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
	}

}
