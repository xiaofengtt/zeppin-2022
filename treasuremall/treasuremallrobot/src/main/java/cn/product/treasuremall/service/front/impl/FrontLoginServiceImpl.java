package cn.product.treasuremall.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.MobileCodeDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.entity.MobileCode.MobileCodeStatus;
import cn.product.treasuremall.entity.MobileCode.MobileCodeTypes;
import cn.product.treasuremall.service.front.FrontLoginService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.DESUtil;
import cn.product.treasuremall.util.IpUtil;
import cn.product.treasuremall.util.Utlity;

@Service("frontLoginService")
public class FrontLoginServiceImpl implements FrontLoginService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private IpUtil ipUtil;

	@Override
	public void loginByCode(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		try {
			if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
				//用户登录
				String deviceNumber = token.substring(0,2);
				if(deviceNumber == null || "".equals(deviceNumber)){
					throw new TransactionException("登录失败，未识别的设备号！");
				}
				
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					throw new TransactionException("系统错误！");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						throw new TransactionException("登录失败，登录时间超时！");
					}
				} else {
					throw new TransactionException("登录失败，登录时间超时!");
				}
				
				FrontUser fu = frontUserDao.getByMobile(mobile);
				if(!FrontUserType.NORMAL.equals(fu.getType())){
					throw new TransactionException("用户不可用！");
				}
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException("系统错误！");
				}
				
				Map<String, Object> inputParams = new HashMap<String, Object>();
				inputParams.put("mobile", mobile);
				inputParams.put("status", MobileCodeStatus.NORMAL);
				inputParams.put("type", MobileCodeTypes.CODE);
				List<MobileCode> lstMobileCode = mobileCodeDao.getListByParams(inputParams);
				MobileCode mc = null;
				if(lstMobileCode != null && lstMobileCode.size() > 0){
					mc = (MobileCode) lstMobileCode.get(0);
				}
				if(mc == null){
					throw new TransactionException("验证码输入错误！");
				}
				
				if(!MobileCodeTypes.CODE.equals(mc.getType())){
					throw new TransactionException("验证码输入错误！");
				}
				
				if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
					throw new TransactionException("验证码超时！");
				}
				
				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+mc.getMobile()+mc.getCode());
				if(!md5Str.equals(realMD5Str)){
					throw new TransactionException("验证码输入错误！");
			
				}
				
				fu.setLastonline(new Timestamp(System.currentTimeMillis()));
				fu.setLastaccessip(ip);
				frontUserDao.update(fu);
				
				result.setData(fu);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("登录成功！");
				return;
			}else{
				//用户注册
				String deviceNumber = token.substring(0,2);
				if(deviceNumber == null || "".equals(deviceNumber)){
					throw new TransactionException("注册失败，未识别设备号");
				}
				
				if (!Utlity.isMobileNO(mobile)) {
					throw new TransactionException("手机号非法！");
				}
				
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					throw new TransactionException("系统错误！");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkTime(time, nowTime)){
						throw new TransactionException("注册失败，登录时间超时！");
					}
				} else {
					throw new TransactionException("注册失败，登录时间超时！");
				}
				
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException("系统错误！");
				}
				
				Map<String, Object> inputParams = new HashMap<String, Object>();
				inputParams.put("mobile", mobile);
				inputParams.put("status", MobileCodeStatus.NORMAL);
				inputParams.put("type", MobileCodeTypes.CODE);
				List<MobileCode> lstMobileCode = mobileCodeDao.getListByParams(inputParams);
				MobileCode mc = null;
				if(lstMobileCode != null && lstMobileCode.size() > 0){
					mc = (MobileCode) lstMobileCode.get(0);
				}
				if(mc == null){
					throw new TransactionException("验证码输入错误！");
				}
				
				if(!MobileCodeTypes.CODE.equals(mc.getType())){
					throw new TransactionException("验证码输入错误！");
				}
				
				if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
					throw new TransactionException("验证码超时！");
				}
				
				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+mc.getMobile()+mc.getCode());
				if(!realMD5Str.equals(md5Str)){
					throw new TransactionException("登录失败，验证码输入错误！");
				}
				
				FrontUser fu = new FrontUser();
				fu.setMobile(mobile);
				fu.setRegisterChannel(deviceNumber);
				fu.setLastaccessip(ip);
				fu.setIp(ip);
				fu.setArea(ipUtil.getAreaByIp(ip));
				frontUserDao.register(fu, mc);
				
				result.setData(fu);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("注册成功！");
				return;
			}
		} catch (TransactionException te) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(te.getMessage() == null ? "登录失败！" : te.getMessage());
			return;
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("登录失败，服务异常");
			return;
		}
	}

	@Override
	public void loginByPwd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		FrontUser fu = this.frontUserDao.getByMobile(mobile);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户尚未注册！");
			return;
		}
		if(!FrontUserType.NORMAL.equals(fu.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不可用！");
			return;
		}
		if(Utlity.checkStringNull(fu.getPassword())){
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
		
		try {
			String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+fu.getMobile()+fu.getPassword());
			if(!md5Str.equals(realMD5Str)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户名或密码错误，登录失败！");
				return;
			}
			
			fu.setLastonline(new Timestamp(System.currentTimeMillis()));
			fu.setLastaccessip(ip);
			frontUserDao.update(fu);
			
			result.setData(fu);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("登录成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("登录异常！");
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
		
		try {
			String enMd5 = DESUtil.encryptStr(mobile+str+su.getUuid());
			
			if(!md5.equals(enMd5)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("登录验证失败，请重新登录！！");
				return;
			}
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("登录成功！");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("登录验证异常！！");
			return;
		}
		
	}

	@Override
	public void resetPwd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String pwd = paramsMap.get("pwd") == null ? "" : paramsMap.get("pwd").toString();
    	
		mobile = Base64Util.getFromBase64(mobile);
		token = Base64Util.getFromBase64(token);
		pwd = Base64Util.getFromBase64(pwd);
		
		String timestamp = token.substring(0,13);
		String md5Str = token.substring(13);
		if(md5Str == null || "".equals(md5Str)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证错误！");
			return;
		}

		Map<String, Object> inputParams = new HashMap<String, Object>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.RESETPWD);
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
		
		if(!MobileCodeTypes.RESETPWD.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		//第二步验证取消超时时间验证
//		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("验证码超时！");
//			return;
//		}
		
		try {
			String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+timestamp+mc.getMobile()+pwd+mc.getCode());
			if(md5Str.equals(realMD5Str)){
				mc.setStatus(MobileCodeStatus.DISABLE);
				List<MobileCode> codeList = new ArrayList<MobileCode>();
				codeList.add(mc);
				
				FrontUser su = this.frontUserDao.getByMobile(mobile);
				su.setPassword(pwd);
				this.frontUserDao.updateFrontUser(su, null, codeList);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("重置密码成功！");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("验证码错误，认证失败！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
		
	}

	@Override
	public void resetCheck(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	
		mobile = Base64Util.getFromBase64(mobile);
		token = Base64Util.getFromBase64(token);
		
		String timestamp = token.substring(0,13);
		String md5Str = token.substring(13);
		if(md5Str == null || "".equals(md5Str)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证错误！");
			return;
		}

		Map<String, Object> inputParams = new HashMap<String, Object>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.RESETPWD);
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
		
		try {
			String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+timestamp+mc.getMobile()+mc.getCode());
			if(md5Str.equals(realMD5Str)){
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("验证码验证成功！");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("验证码错误，认证失败！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}
	
}
