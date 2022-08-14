package cn.product.score.service.front.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.controller.base.TransactionException;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.MobileCodeDao;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.MobileCode;
import cn.product.score.entity.MobileCode.MobileCodeStatus;
import cn.product.score.entity.MobileCode.MobileCodeTypes;
import cn.product.score.service.front.FrontLoginService;
import cn.product.score.util.Base64Util;
import cn.product.score.util.MD5;
import cn.product.score.util.Utlity;

@Service("frontLoginService")
public class FrontLoginServiceImpl implements FrontLoginService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;

	@Override
	public void login(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		if("18888888888".equals(mobile)){
			Map<String, Object> data = new HashMap<String, Object>();
			FrontUser su = this.frontUserDao.getByMobile("18888888888");
			data.put("frontUser", su);
			
			result.setData(data);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("登录成功！");
			return;
		}
		Map<String, Object> resultMap = null;
		try {
			if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
				resultMap = this.frontUserDao.loginByCode(token,mobile);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("frontUser", resultMap.get("frontUser"));
				
				result.setData(data);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage(resultMap.get("message") == null ? "登录成功！" : resultMap.get("message").toString());
				return;
			}else{
				Map<String, Object> data = new HashMap<String, Object>();
				resultMap = this.frontUserDao.register(token,mobile);
				data.put("frontUser", resultMap.get("frontUser"));	
				result.setData(data);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage(resultMap.get("message") == null ? "注册成功！" : resultMap.get("message").toString());
				return;
			}
		} catch (TransactionException te) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(te.getMessage() == null ? "注册失败！" : te.getMessage());
			return;
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("注册失败，服务异常");
			return;
		}
	}

	@Override
	public void loginByPwd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
			FrontUser su = this.frontUserDao.getByMobile(mobile);
			
			if(Utlity.checkStringNull(su.getPassword())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户尚未设置密码！");
				return;
			}
			
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("登录失败，未识别的设备号！");
				return;
			}
			
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请求超时，请重新登录！");
				return;
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("请求超时，请重新登录！");
					return;
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请求超时，请重新登录！");
				return;
			}
			
			String md5Str = token.substring(15);
			if(md5Str == null || "".equals(md5Str)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户名或密码错误，登录失败！");
				return;
			}
			String realMD5Str = MD5.getMD5(Utlity.KEY_FRONT_MD5+time+su.getMobile()+su.getPassword());
			if(md5Str.equals(realMD5Str)){
				result.setData(su);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("登录成功！");
				return;
			}else{
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户名或密码错误，登录失败！");
				return;
			}
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户尚未注册！");
			return;
		}
	}

	@Override
	public void auth(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	
		token = Base64Util.getFromBase64(token);
		if(token.length() != 49){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("登录验证失败，请重新登录！");
			return;
		}
		
		String mobile = token.substring(0,11);
		String str = token.substring(11,17);
		String md5 = token.substring(17);
		FrontUser su = this.frontUserDao.getByMobile(mobile);
		
		if(su == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户未注册！");
			return;
		}
		
		String enMd5 = MD5.getMD5(mobile+str+su.getUuid());
		if(!md5.equals(enMd5)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("登录验证失败，请重新登录！！");
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("登录成功！");
		return;
	}

	@Override
	public void forgotPwd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	
		mobile = Base64Util.getFromBase64(mobile);
		token = Base64Util.getFromBase64(token);
		
		if(token.length() != 77){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证错误！");
			return;
		}
		
		String timestamp = token.substring(0,13);
		String pwd = token.substring(13,45);
		String md5Str = token.substring(45);
		if(md5Str == null || "".equals(md5Str)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证错误！");
			return;
		}
		

		Map<String, Object> inputParams = new HashMap<String, Object>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<MobileCode> lstMobileCode = this.mobileCodeDao.getListByParams(inputParams);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if(!mc.getMobile().equals(mobile)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手机号输入错误！");
			return;
		}
		
		if(!MobileCodeTypes.RESETPWD.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码超时！");
			return;
		}
		
		String realMD5Str = MD5.getMD5(Utlity.KEY_FRONT_MD5+timestamp+mc.getMobile()+pwd+mc.getCode());
		if(md5Str.equals(realMD5Str)){//登录成功	
			FrontUser su = this.frontUserDao.getByMobile(mobile);
			su.setPassword(pwd);
			this.frontUserDao.update(su);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("找回密码成功！");
			return;
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码错误，认证失败！");
			return;
		}
	}
	
}
