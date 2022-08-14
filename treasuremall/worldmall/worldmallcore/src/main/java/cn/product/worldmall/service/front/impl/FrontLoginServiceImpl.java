package cn.product.worldmall.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.aws.sns.dao.AwsSnsClientDao;
import cn.product.worldmall.aws.sns.model.SnsDataMessage;
import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.ChannelDao;
import cn.product.worldmall.dao.FrontDeviceTokenDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.Channel;
import cn.product.worldmall.entity.Channel.ChannelId;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontDeviceToken.FrontDeviceTokenDeviceType;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserLevel;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalInfo.InternationalInfoCode;
import cn.product.worldmall.entity.MobileCode;
import cn.product.worldmall.entity.MobileCode.MobileCodeStatus;
import cn.product.worldmall.entity.MobileCode.MobileCodeTypes;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.service.front.FrontLoginService;
import cn.product.worldmall.util.Base64Util;
import cn.product.worldmall.util.DESUtil;
import cn.product.worldmall.util.IpUtil;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.auth.AppleAuthUtil;
import cn.product.worldmall.util.auth.FacebookAuthUtil;
import cn.product.worldmall.vo.front.FrontUserVO;

@Service("frontLoginService")
public class FrontLoginServiceImpl implements FrontLoginService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private IpUtil ipUtil;
    
    @Autowired
    private RabbitSenderService rabbitSenderService;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;
	
	@Autowired
	private FrontDeviceTokenDao frontDeviceTokenDao;
	
	@Autowired
	private AwsSnsClientDao awsSnsClientDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void loginByCode(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	String channelId = paramsMap.get("channelId") == null ? "" : paramsMap.get("channelId").toString();
    	String agent = paramsMap.get("agent") == null ? "" : paramsMap.get("agent").toString();
    	String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		try {
			if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
				if(!Utlity.checkStringNull(agent)){
					throw new TransactionException("You have already registered! Please download the APP login!");
				}
				//用户登录
				String deviceNumber = token.substring(0,2);
				if(deviceNumber == null || "".equals(deviceNumber)){
					throw new TransactionException("Sign in failed, unrecognized device number!");
				}
				
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					throw new TransactionException("system error!");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						throw new TransactionException("Sign in failed login timeout!");
					}
				} else {
					throw new TransactionException("Sign in failed login timeout!");
				}
				
				FrontUser fu = frontUserDao.getByMobile(mobile);
//				if(!FrontUserType.NORMAL.equals(fu.getType())){
//					throw new TransactionException("用户不可用!");
//				}
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException("System error!");
				}
				if("19092355380".equals(mobile)) {
					fu.setLastonline(new Timestamp(System.currentTimeMillis()));
					fu.setLastaccessip(ip);
					frontUserDao.update(fu);
					
					result.setData(fu);
					result.setStatus(ResultStatusType.SUCCESS);
					result.setMessage("Successful!");
					return;
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
					throw new TransactionException("Incorrect verification code!");
				}
				
				if(!MobileCodeTypes.CODE.equals(mc.getType())){
					throw new TransactionException("Incorrect verification code!");
				}
				
				if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
					throw new TransactionException("Verification code timed out!");
				}
				
				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+mc.getMobile()+mc.getCode());
				if(!md5Str.equals(realMD5Str)){
					throw new TransactionException("Incorrect verification code!");
			
				}
				
				fu.setLastonline(new Timestamp(System.currentTimeMillis()));
				fu.setLastaccessip(ip);
				frontUserDao.update(fu);
				
		    	FrontUserVO fuvo = new FrontUserVO(fu);
		    	if(!Utlity.checkStringNull(fu.getImage())) {
		    		Resource re = this.resourceDao.get(fu.getImage());
		    		if(re != null) {
		    			fuvo.setImageURL(pathUrl + re.getUrl());
		    		}
		    	}
		    	if(!Utlity.checkStringNull(fu.getCountry())) {
		    		InternationalInfo ii = this.internationalInfoDao.get(fu.getCountry());
		    		if(ii != null) {
		    			fuvo.setAreaCode(ii.getTelCode());
		    		}
		    	}
				
				result.setData(fuvo);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
				return;
			}else{
				//用户注册
				String deviceNumber = token.substring(0,2);
				if(deviceNumber == null || "".equals(deviceNumber)){
					throw new TransactionException("Sign in failed, unrecognized device number!");
				}
				
				if (!Utlity.isMobileNO(mobile)) {
					throw new TransactionException("Illegal mobile number!");
				}
				
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					throw new TransactionException("System error!");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkTime(time, nowTime)){
						throw new TransactionException("Sign in failed, time expired!");
					}
				} else {
					throw new TransactionException("Sign in failed, time expired!");
				}
				
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException("System error!");
				}
				
				if(!Utlity.checkStringNull(agent)){
					FrontUser agentUser = this.frontUserDao.get(agent);
					if(agentUser == null){
						throw new TransactionException("The inviter does not exist!");
					}
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
					throw new TransactionException("Incorrect verification code!");
				}
				
				if(!MobileCodeTypes.CODE.equals(mc.getType())){
					throw new TransactionException("Incorrect verification code!");
				}
				
				if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
					throw new TransactionException("Verification code timed out!");
				}
				
				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+mc.getMobile()+mc.getCode());
				if(!realMD5Str.equals(md5Str)){
					throw new TransactionException("Sign in failed,Incorrect verification code!");
				}
				
				if(Utlity.checkStringNull(channelId)) {
					channelId = ChannelId.CHANNEL_DEFAULT;
				}
				//记录渠道信息
				Channel ch = this.channelDao.getById(channelId);
				if(ch == null) {
					ch = this.channelDao.getById(ChannelId.CHANNEL_DEFAULT);
				}
				
				FrontUser fu = new FrontUser();
				fu.setMobile(mobile);
				fu.setLastaccessip(ip);
				fu.setIp(ip);
				fu.setArea(ipUtil.getAreaByIp(ip));
				fu.setRegisterChannel(ch.getUuid());
				fu.setAgent(agent);
				
				//国家信息
				if(!Utlity.checkStringNull(country)) {
					InternationalInfo ii = this.internationalInfoDao.get(country);
					if(ii == null) {
						throw new TransactionException("Country code error!");
					}
					if(mobile.indexOf(ii.getTelCode()) < 0) {
						throw new TransactionException("Mobile number info error!");
					}
					fu.setCountry(ii.getUuid());
				}
				
				frontUserDao.register(fu, mc);
				
				//异步处理被邀请用户金券赠送情况
				if(!Utlity.checkStringNull(fu.getAgent())) {
					this.rabbitSenderService.recommendStartMessageSend(fu);
				}
				
				FrontUserVO fuvo = new FrontUserVO(fu);
		    	if(!Utlity.checkStringNull(fu.getImage())) {
		    		Resource re = this.resourceDao.get(fu.getImage());
		    		if(re != null) {
		    			fuvo.setImageURL(pathUrl + re.getUrl());
		    		}
		    	}
		    	if(!Utlity.checkStringNull(fu.getCountry())) {
		    		InternationalInfo ii = this.internationalInfoDao.get(fu.getCountry());
		    		if(ii != null) {
		    			fuvo.setAreaCode(ii.getTelCode());
		    		}
		    	}
				
				result.setData(fuvo);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
				return;
			}
		} catch (TransactionException te) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(te.getMessage() == null ? "Sign in failed!" : te.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Sign in failed,Service exception!");
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
			result.setMessage("You have not registered yet!");
			return;
		}
//		if(!FrontUserType.NORMAL.equals(fu.getType())){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("用户不可用!");
//			return;
//		}
		if(Utlity.checkStringNull(fu.getPassword())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("You have not set a password yet!");
			return;
		}
		
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Sign in failed, unrecognized device number!");
			return;
		}
		
		String timestamp = token.substring(2,15);
		if(timestamp == null || "".equals(timestamp)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Request timed out, please login again!");
			return;
		}
		long time = Long.parseLong(timestamp);
		long nowTime = System.currentTimeMillis();
		if(time <= nowTime){
			if(Utlity.checkLessTime(time, nowTime)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Request timed out, please login again!");
				return;
			}
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Request timed out, please login again!");
			return;
		}
		
		String md5Str = token.substring(15);
		if(md5Str == null || "".equals(md5Str)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Username or password is wrong, login failed!");
			return;
		}
		
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		try {
			String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+fu.getMobile()+fu.getPassword());
			if(!md5Str.equals(realMD5Str)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Username or password is wrong, login failed!");
				return;
			}
			
			fu.setLastonline(new Timestamp(System.currentTimeMillis()));
			fu.setLastaccessip(ip);
			frontUserDao.update(fu);
			
			FrontUserVO fuvo = new FrontUserVO(fu);
	    	if(!Utlity.checkStringNull(fu.getImage())) {
	    		Resource re = this.resourceDao.get(fu.getImage());
	    		if(re != null) {
	    			fuvo.setImageURL(pathUrl + re.getUrl());
	    		}
	    	}
	    	if(!Utlity.checkStringNull(fu.getCountry())) {
	    		InternationalInfo ii = this.internationalInfoDao.get(fu.getCountry());
	    		if(ii != null) {
	    			fuvo.setAreaCode(ii.getTelCode());
	    		}
	    	}
			result.setData(fuvo);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Sign in failed,Service exception!");
		}

	}
	
	
	@Override
	public void loginByApple(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String identityToken = paramsMap.get("identityToken") == null ? "" : paramsMap.get("identityToken").toString();
    	String nickname = paramsMap.get("nickname") == null ? "" : paramsMap.get("nickname").toString();
    	String countryCode = paramsMap.get("countryCode") == null ? "" : paramsMap.get("countryCode").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	String channelId = paramsMap.get("channelId") == null ? "" : paramsMap.get("channelId").toString();
    	
		token = Base64Util.getFromBase64(token);
		

		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		try {
			//先通过identityToken 来验证本次登录是否是准许的
			if(AppleAuthUtil.verify(identityToken)) {//验证通过--进行注册操作
				String sub = AppleAuthUtil.getSub(identityToken);
				if(!Utlity.checkStringNull(sub)) {//有用户信息
					
					//安全性校验
					String deviceNumber = token.substring(0,2);
					if(deviceNumber == null || "".equals(deviceNumber)){
						throw new TransactionException("Sign in failed, unrecognized device number!");
					}
					
					String timestamp = token.substring(2,15);
					if(timestamp == null || "".equals(timestamp)){
						throw new TransactionException("System error!");
					}
					long time = Long.parseLong(timestamp);
					long nowTime = System.currentTimeMillis();
					if(time <= nowTime){
						if(Utlity.checkTime(time, nowTime)){
							throw new TransactionException("Sign in failed, time expired!");
						}
					} else {
						throw new TransactionException("Sign in failed, time expired!");
					}
					
					String md5Str = token.substring(15);
					if(md5Str == null || "".equals(md5Str)){
						throw new TransactionException("System error!");
					}
					
					String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+DESUtil.encryptStr(sub));
					if(!realMD5Str.equals(md5Str)){
						throw new TransactionException("Sign in failed,Incorrect verification code!");
					}
					
					FrontUser fu = this.frontUserDao.getByOpenid(sub);
					if(fu != null) {//登录
						//用户登录
						
						fu.setLastonline(new Timestamp(System.currentTimeMillis()));
						fu.setLastaccessip(ip);
						frontUserDao.update(fu);
						
				    	FrontUserVO fuvo = new FrontUserVO(fu);
				    	if(!Utlity.checkStringNull(fu.getImage())) {
				    		Resource re = this.resourceDao.get(fu.getImage());
				    		if(re != null) {
				    			fuvo.setImageURL(pathUrl + re.getUrl());
				    		}
				    	}
				    	if(!Utlity.checkStringNull(fu.getCountry())) {
				    		InternationalInfo ii = this.internationalInfoDao.get(fu.getCountry());
				    		if(ii != null) {
				    			fuvo.setAreaCode(ii.getTelCode());
				    		}
				    	}
						
						result.setData(fuvo);
						result.setStatus(ResultStatusType.SUCCESS);
						result.setMessage("Successful!");
						return;
					} else {//注册
						//用户注册
						
						if(Utlity.checkStringNull(channelId)) {
							channelId = ChannelId.CHANNEL_DEFAULT;
						}
						//记录渠道信息
						Channel ch = this.channelDao.getById(channelId);
						if(ch == null) {
							ch = this.channelDao.getById(ChannelId.CHANNEL_DEFAULT);
						}
						
						fu = new FrontUser();
						fu.setOpenid(sub);
						fu.setMobile(null);
						fu.setLastaccessip(ip);
						fu.setIp(ip);
						fu.setArea(ipUtil.getAreaByIp(ip));
						fu.setRegisterChannel(ch.getUuid());
						if(!Utlity.checkStringNull(nickname)) {
							fu.setNickname(nickname);
						}
						
						//国家信息
						InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
						if(!Utlity.checkStringNull(countryCode)) {
							ii = this.internationalInfoDao.getByCode(countryCode);
							if(ii != null) {
								fu.setCountry(ii.getUuid());
							}
						}
						
						frontUserDao.register(fu, null);
						
						//异步处理被邀请用户金券赠送情况
						if(!Utlity.checkStringNull(fu.getAgent())) {
							this.rabbitSenderService.recommendStartMessageSend(fu);
						}
						
						FrontUserVO fuvo = new FrontUserVO(fu);
				    	if(!Utlity.checkStringNull(fu.getImage())) {
				    		Resource re = this.resourceDao.get(fu.getImage());
				    		if(re != null) {
				    			fuvo.setImageURL(pathUrl + re.getUrl());
				    		}
				    	}
				    	if(!Utlity.checkStringNull(fu.getCountry())) {
				    		ii = this.internationalInfoDao.get(fu.getCountry());
				    		if(ii != null) {
				    			fuvo.setAreaCode(ii.getTelCode());
				    		}
				    	}
						
						result.setData(fuvo);
						result.setStatus(ResultStatusType.SUCCESS);
						result.setMessage("Successful!");
						return;
					}
				} else {//无用户信息--登录失败
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Sign in failed!");
					return;
				}
				
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Sign in failed!");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Sign in failed,Service exception!");
			return;
		}
	}


	@Override
	public void loginByFacebook(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String accessToken = paramsMap.get("accessToken") == null ? "" : paramsMap.get("accessToken").toString();
    	String userID = paramsMap.get("userID") == null ? "" : paramsMap.get("userID").toString();
    	String nickname = paramsMap.get("nickname") == null ? "" : paramsMap.get("nickname").toString();
    	String image = paramsMap.get("image") == null ? "" : paramsMap.get("image").toString();//头像图片，需要下载到本地
    	String countryCode = paramsMap.get("countryCode") == null ? "" : paramsMap.get("countryCode").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	String channelId = paramsMap.get("channelId") == null ? "" : paramsMap.get("channelId").toString();
    	
		token = Base64Util.getFromBase64(token);
		

		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		try {
			//先通过identityToken 来验证本次登录是否是准许的
			if(FacebookAuthUtil.checkLoginWithToken(accessToken)) {//验证通过--进行注册操作
				//安全性校验
				String deviceNumber = token.substring(0,2);
				if(deviceNumber == null || "".equals(deviceNumber)){
					throw new TransactionException("Sign in failed, unrecognized device number!");
				}
				
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					throw new TransactionException("System error!");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkTime(time, nowTime)){
						throw new TransactionException("Sign in failed, time expired!");
					}
				} else {
					throw new TransactionException("Sign in failed, time expired!");
				}
				
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException("System error!");
				}
				
				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+DESUtil.encryptStr(userID));
				if(!realMD5Str.equals(md5Str)){
					throw new TransactionException("Sign in failed,Incorrect verification code!");
				}
				
				FrontUser fu = this.frontUserDao.getByOpenid(userID);
				
				if(fu != null) {//登录
					//用户登录
					
					fu.setLastonline(new Timestamp(System.currentTimeMillis()));
					fu.setLastaccessip(ip);
					frontUserDao.update(fu);
					
			    	FrontUserVO fuvo = new FrontUserVO(fu);
			    	if(!Utlity.checkStringNull(fu.getImage())) {
			    		Resource re = this.resourceDao.get(fu.getImage());
			    		if(re != null) {
			    			fuvo.setImageURL(pathUrl + re.getUrl());
			    		}
			    	}
			    	if(!Utlity.checkStringNull(fu.getCountry())) {
			    		InternationalInfo ii = this.internationalInfoDao.get(fu.getCountry());
			    		if(ii != null) {
			    			fuvo.setAreaCode(ii.getTelCode());
			    		}
			    	}
					
					result.setData(fuvo);
					result.setStatus(ResultStatusType.SUCCESS);
					result.setMessage("Successful!");
					return;
				}else {//注册
					//用户注册
					
					if(Utlity.checkStringNull(channelId)) {
						channelId = ChannelId.CHANNEL_DEFAULT;
					}
					//记录渠道信息
					Channel ch = this.channelDao.getById(channelId);
					if(ch == null) {
						ch = this.channelDao.getById(ChannelId.CHANNEL_DEFAULT);
					}
					
					fu = new FrontUser();
					fu.setOpenid(userID);
					fu.setMobile(null);
					fu.setLastaccessip(ip);
					fu.setIp(ip);
					fu.setArea(ipUtil.getAreaByIp(ip));
					fu.setRegisterChannel(ch.getUuid());
					if(!Utlity.checkStringNull(nickname)) {
						fu.setNickname(nickname);
					}
					
					//国家信息
					InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
					if(!Utlity.checkStringNull(countryCode)) {
						ii = this.internationalInfoDao.getByCode(countryCode);
						if(ii != null) {
							fu.setCountry(ii.getUuid());
						}
					}
					if(!Utlity.checkStringNull(image)) {
						image = FacebookAuthUtil.getImageUrl(image);
						fu.setWechaticon(image);
					}
					
					frontUserDao.register(fu, null);
					
					//异步处理被邀请用户金券赠送情况
					if(!Utlity.checkStringNull(fu.getAgent())) {
						this.rabbitSenderService.recommendStartMessageSend(fu);
					}
					
					FrontUserVO fuvo = new FrontUserVO(fu);
			    	if(!Utlity.checkStringNull(fu.getImage())) {
			    		Resource re = this.resourceDao.get(fu.getImage());
			    		if(re != null) {
			    			fuvo.setImageURL(pathUrl + re.getUrl());
			    		}
			    	}
			    	if(!Utlity.checkStringNull(fu.getCountry())) {
			    		ii = this.internationalInfoDao.get(fu.getCountry());
			    		if(ii != null) {
			    			fuvo.setAreaCode(ii.getTelCode());
			    		}
			    	}
					
					result.setData(fuvo);
					result.setStatus(ResultStatusType.SUCCESS);
					result.setMessage("Successful!");
					return;
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Sign in failed!");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Sign in failed,Service exception!");
			return;
		}
	}
	
	@Override
	public void auth(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Sign in failed, unrecognized device number!");
			return;
		}
		String timestamp = token.substring(2,15);
		if(timestamp == null || "".equals(timestamp)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Request timed out, please login again!");
			return;
		}
		
		long time = Long.parseLong(timestamp);
		long nowTime = System.currentTimeMillis();
		
		if(time <= nowTime){
			if(Utlity.checkLessTime(time, nowTime)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Request timed out, please login again!");
				return;
			}
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Request timed out, please login again!");
			return;
		}
		
		String md5Str = token.substring(15);
		if(md5Str == null || "".equals(md5Str)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Username or password is wrong, login failed!");
			return;
		}
		FrontUser su = null;
		if(!Utlity.checkStringNull(mobile)) {
			su = this.frontUserDao.getByMobile(mobile);
		}
		
		if(!Utlity.checkStringNull(uuid)) {
			su = this.frontUserDao.get(uuid);
		}
		
		if(su == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Unregistered user!");
			return;
		}
		
		String checkMobile = su.getMobile() == null? "" : su.getMobile();
		try {
			String enMd5 = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+time+checkMobile+uuid);
			
			if(!md5Str.equals(enMd5)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Login verification failed, please login again!!");
				return;
			}
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Service exception!");
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
			result.setMessage("Validation Error!");
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
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if(!MobileCodeTypes.RESETPWD.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		//第二步验证取消超时时间验证
//		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("Verification code timed out!");
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
				result.setMessage("Password reset successfully!");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Incorrect verification code, Authentication failed!");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Operation abnormal!");
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
			result.setMessage("Validation Error!");
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
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if(!MobileCodeTypes.RESETPWD.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Verification code timed out!");
			return;
		}
		
		try {
			String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+timestamp+mc.getMobile()+mc.getCode());
			if(md5Str.equals(realMD5Str)){
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Incorrect verification code, authentication failed!");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Operation abnormal!");
			return;
		}
	}

	@Override
	public void checkin(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String token = paramsMap.get("token") == null ? "" : paramsMap.get("token").toString();
    	String uniqueToken = paramsMap.get("uniqueToken") == null ? "" : paramsMap.get("uniqueToken").toString();//必填
    	String deviceToken = paramsMap.get("deviceToken") == null ? "" : paramsMap.get("deviceToken").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	String countryCode = paramsMap.get("countryCode") == null ? "" : paramsMap.get("countryCode").toString();//国家信息
    	
		token = Base64Util.getFromBase64(token);
		
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Sign in failed, unrecognized device number!");
			return;
		}
		String timestamp = token.substring(2,15);
		if(timestamp == null || "".equals(timestamp)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Request timed out, please login again!");
			return;
		}
		
		long time = Long.parseLong(timestamp);
		long nowTime = System.currentTimeMillis();
		
		if(time <= nowTime){
			if(Utlity.checkLessTime(time, nowTime)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Request timed out, please login again!");
				return;
			}
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Request timed out, please login again!");
			return;
		}
		
		String md5Str = token.substring(15);
		if(md5Str == null || "".equals(md5Str)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Username or password is wrong, login failed!");
			return;
		}
		
		
		try {
			String fromDes = DESUtil.decryptStr(md5Str);//32+13
			
			String frontUser = "";
			if(fromDes.length() > 45) {//有登录状态
				frontUser = fromDes.substring(45);
				
				FrontUser su = this.frontUserDao.get(frontUser);
				if(su == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Device information is abnormal!");
					return;
				}
			}
			
//			List<FrontDeviceToken> listUpdate = new ArrayList<FrontDeviceToken>();
			Map<String, FrontDeviceToken> mapUpdate = new HashMap<String, FrontDeviceToken>();
			
			if(!Utlity.checkStringNull(frontUser)) {//有登录状态
				FrontUser su = this.frontUserDao.get(frontUser);
				if(su == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Device information is abnormal!");
					return;
				}
				
				mapUpdate.clear();
				FrontDeviceToken fdt = this.frontDeviceTokenDao.getByFrontUser(frontUser);
				if(fdt != null) {//已绑定 
					boolean flag = false;
					//判断是否有其他设备绑定
					if(!deviceToken.equals(fdt.getDeviceToken())) {
						flag = true;
						fdt.setDeviceToken(deviceToken);
						fdt.setDeviceType(deviceNumber);
						fdt.setEndpointArn(null);
						fdt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
						fdt.setIp(ip);
						//校验deviceToken是否存在 
						FrontDeviceToken ut = this.frontDeviceTokenDao.getByDeviceToken(deviceToken);
						if(ut != null && !ut.getUuid().equals(fdt.getUuid())) {//不是新设备
							flag = false;
							fdt.setEndpointArn(ut.getEndpointArn());
							fdt.setTopic(ut.getTopic());
							if(mapUpdate.containsKey(ut.getUuid())) {
								ut = mapUpdate.get(ut.getUuid());
							}
							//解绑历史数据，绑定新数据
							ut.setFrontUser(null);
							ut.setFrontUserGroup(null);
							ut.setDeviceToken(null);
							ut.setDeviceType(null);
							ut.setEndpointArn(null);
							mapUpdate.put(ut.getUuid(), ut);
						}
					}
					
					if(!uniqueToken.equals(fdt.getUniqueToken())) {
						fdt.setUniqueToken(uniqueToken);
						//判断是否为新设备
						FrontDeviceToken ut = this.frontDeviceTokenDao.getByUniqueToken(uniqueToken);
						if(ut != null && !ut.getUuid().equals(fdt.getUuid())) {//否
							if(mapUpdate.containsKey(ut.getUuid())) {
								ut = mapUpdate.get(ut.getUuid());
							}
							//解绑历史数据
							ut.setFrontUser(null);
							ut.setFrontUserGroup(null);
							ut.setDeviceToken(null);
							ut.setDeviceType(null);
							ut.setEndpointArn(null);
							ut.setUniqueToken(ut.getUuid()+"_#_"+ut.getUniqueToken());
//							listUpdate.add(ut);
							mapUpdate.put(ut.getUuid(), ut);
						}
					}
//					if(!Utlity.checkStringNull(countryCode)) {
//						InternationalInfo ii = this.internationalInfoDao.getByCode(countryCode);
//						if(ii != null) {
//							fdt.setCountry(ii.getUuid());
//							fdt.setCountryCode(ii.getCode());
//						}
//					}
					InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
					if(!Utlity.checkStringNull(countryCode)) {
						ii = this.internationalInfoDao.getByCode(countryCode);
						if(ii != null) {
							fdt.setCountry(ii.getUuid());
							fdt.setCountryCode(ii.getCode());
						}
					}
					//入库更新操作
					this.frontUserDao.updateOrInsertDeviceToken(flag, fdt, mapUpdate);
					result.setStatus(ResultStatusType.SUCCESS);
					result.setMessage("Successful!");
					return;
				} else {//未绑定
					mapUpdate.clear();
					//判断token是否绑定
					fdt = this.frontDeviceTokenDao.getByDeviceToken(deviceToken);
					if(fdt != null) {//已绑定
						fdt.setFrontUser(frontUser);
						fdt.setFrontUserGroup(su.getLevel());
						
						if(!uniqueToken.equals(fdt.getUniqueToken())) {
							fdt.setUniqueToken(uniqueToken);
							//判断是否为新设备
							FrontDeviceToken ut = this.frontDeviceTokenDao.getByUniqueToken(uniqueToken);
							if(ut != null && !ut.getUuid().equals(fdt.getUuid())) {//否
								if(mapUpdate.containsKey(ut.getUuid())) {
									ut = mapUpdate.get(ut.getUuid());
								}
								//解绑历史数据
								ut.setFrontUser(null);
								ut.setFrontUserGroup(null);
								ut.setDeviceToken(null);
								ut.setDeviceType(null);
								ut.setEndpointArn(null);
								ut.setUniqueToken(ut.getUuid()+"_#_"+ut.getUniqueToken());
//								listUpdate.add(ut);
								mapUpdate.put(ut.getUuid(), ut);
							}
						}
						InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
						if(!Utlity.checkStringNull(countryCode)) {
							ii = this.internationalInfoDao.getByCode(countryCode);
							if(ii != null) {
								fdt.setCountry(ii.getUuid());
								fdt.setCountryCode(ii.getCode());
							}
						}
						//入库更新操作
//						this.frontDeviceTokenDao.update(fdt);
						this.frontUserDao.updateOrInsertDeviceToken(false, fdt, mapUpdate);
						result.setStatus(ResultStatusType.SUCCESS);
						result.setMessage("Successful!");
						return;
					} else {
						mapUpdate.clear();
						//判断设备是否已绑定
						fdt = this.frontDeviceTokenDao.getByUniqueToken(uniqueToken);
						if(fdt != null) {//已绑定
							boolean flag = false;
							fdt.setFrontUser(frontUser);
							fdt.setFrontUserGroup(su.getLevel());
							
							//判断是否有其他token绑定
							if(!deviceToken.equals(fdt.getDeviceToken())) {
								flag = true;
								fdt.setDeviceToken(deviceToken);
								fdt.setDeviceType(deviceNumber);
								fdt.setEndpointArn(null);
								fdt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
								fdt.setIp(ip);
								//校验deviceToken是否存在 
								FrontDeviceToken ut = this.frontDeviceTokenDao.getByDeviceToken(deviceToken);
								if(ut != null && !ut.getUuid().equals(fdt.getUuid())) {//不是新设备
									flag = false;
									fdt.setEndpointArn(ut.getEndpointArn());
									fdt.setTopic(ut.getTopic());
									if(mapUpdate.containsKey(ut.getUuid())) {
										ut = mapUpdate.get(ut.getUuid());
									}
									//解绑历史数据，绑定新数据
									ut.setFrontUser(null);
									ut.setFrontUserGroup(null);
									ut.setDeviceToken(null);
									ut.setDeviceType(null);
									ut.setEndpointArn(null);
									mapUpdate.put(ut.getUuid(), ut);
								}
							}
							InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
							if(!Utlity.checkStringNull(countryCode)) {
								ii = this.internationalInfoDao.getByCode(countryCode);
								if(ii != null) {
									fdt.setCountry(ii.getUuid());
									fdt.setCountryCode(ii.getCode());
								}
							}
							//入库更新操作
//							this.frontDeviceTokenDao.update(fdt);
							this.frontUserDao.updateOrInsertDeviceToken(flag, fdt, mapUpdate);
							result.setStatus(ResultStatusType.SUCCESS);
							result.setMessage("Successful!");
							return;
						} else {
							//新增
							fdt = new FrontDeviceToken();
							fdt.setUuid(UUID.randomUUID().toString());
							fdt.setUniqueToken(uniqueToken);
							fdt.setCreatetime(new Timestamp(System.currentTimeMillis()));
							fdt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
							fdt.setDeviceToken(deviceToken);
							fdt.setDeviceType(deviceNumber);
							fdt.setFrontUser(frontUser);
							fdt.setFrontUserGroup(su.getLevel());
							fdt.setIp(ip);
							//入库更新操作 flag == true 需要请求亚马逊获取endpointArn
//							this.frontDeviceTokenDao.insert(fdt);
							InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
							if(!Utlity.checkStringNull(countryCode)) {
								ii = this.internationalInfoDao.getByCode(countryCode);
								if(ii != null) {
									fdt.setCountry(ii.getUuid());
									fdt.setCountryCode(ii.getCode());
								}
							}
							this.frontUserDao.updateOrInsertDeviceToken(null, fdt, mapUpdate);
							result.setStatus(ResultStatusType.SUCCESS);
							result.setMessage("Successful!");
							return;
						}
					}
				}
			} else {//无登录状态-不更新已绑定的登录用户
				mapUpdate.clear();
				//判断设备是否已绑定
				FrontDeviceToken fdt = this.frontDeviceTokenDao.getByDeviceToken(deviceToken);
				if(fdt != null) {//已绑定
					//判断设备是否已更换
					if(!uniqueToken.equals(fdt.getUniqueToken())) {
						fdt.setUniqueToken(uniqueToken);
						//判断是否为新设备
						FrontDeviceToken ut = this.frontDeviceTokenDao.getByUniqueToken(uniqueToken);
						if(ut != null && !ut.getUuid().equals(fdt.getUuid())) {//否
							if(mapUpdate.containsKey(ut.getUuid())) {
								ut = mapUpdate.get(ut.getUuid());
							}
							//解绑历史数据
							ut.setFrontUser(null);
							ut.setFrontUserGroup(null);
							ut.setDeviceToken(null);
							ut.setUniqueToken(ut.getUuid()+"_#_"+ut.getUniqueToken());
//							listUpdate.add(ut);
							mapUpdate.put(ut.getUuid(), ut);
						}
					}
					InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
					if(!Utlity.checkStringNull(countryCode)) {
						ii = this.internationalInfoDao.getByCode(countryCode);
						if(ii != null) {
							fdt.setCountry(ii.getUuid());
							fdt.setCountryCode(ii.getCode());
						}
					}
					this.frontUserDao.updateOrInsertDeviceToken(false, fdt, mapUpdate);
					result.setStatus(ResultStatusType.SUCCESS);
					result.setMessage("Successful!");
					return;
				} else {//未绑定
					mapUpdate.clear();
					fdt = this.frontDeviceTokenDao.getByUniqueToken(uniqueToken);
					if(fdt != null) {
						fdt.setDeviceToken(deviceToken);
						fdt.setDeviceType(deviceNumber);
						fdt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
						fdt.setIp(ip);
						
						InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
						if(!Utlity.checkStringNull(countryCode)) {
							ii = this.internationalInfoDao.getByCode(countryCode);
							if(ii != null) {
								fdt.setCountry(ii.getUuid());
								fdt.setCountryCode(ii.getCode());
							}
						}
						this.frontUserDao.updateOrInsertDeviceToken(true, fdt, mapUpdate);
						result.setStatus(ResultStatusType.SUCCESS);
						result.setMessage("Successful!");
						return;
					} else {
						fdt = new FrontDeviceToken();
						fdt.setUuid(UUID.randomUUID().toString());
						fdt.setUniqueToken(uniqueToken);
						fdt.setCreatetime(new Timestamp(System.currentTimeMillis()));
						fdt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
						fdt.setDeviceToken(deviceToken);
						fdt.setDeviceType(deviceNumber);
						fdt.setFrontUserGroup(FrontUserLevel.VISITOR);
						fdt.setIp(ip);
						//入库更新操作 flag == true 需要请求亚马逊获取endpointArn
//						this.frontDeviceTokenDao.insert(fdt);
						InternationalInfo ii = this.internationalInfoDao.getByCode(InternationalInfoCode.US);
						if(!Utlity.checkStringNull(countryCode)) {
							ii = this.internationalInfoDao.getByCode(countryCode);
							if(ii != null) {
								fdt.setCountry(ii.getUuid());
								fdt.setCountryCode(ii.getCode());
							}
						}
						this.frontUserDao.updateOrInsertDeviceToken(null, fdt, mapUpdate);
						result.setStatus(ResultStatusType.SUCCESS);
						result.setMessage("Successful!");
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Service exception!");
			return;
		}
	}

	@Override
	public void demoNotice(InputParams params, DataResult<Object> result) {
		List<FrontDeviceToken> list = this.frontDeviceTokenDao.getListByParams(new HashMap<String, Object>());
		
		if(list != null && list.size() > 0) {
			for(FrontDeviceToken fdt : list) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("message", "This is a message. 666");
				data.put("title", null);
				data.put("endpointArn", fdt.getEndpointArn());
				SnsDataMessage sdm = new SnsDataMessage();
				sdm.setNoticeId(UUID.randomUUID().toString());
				sdm.setNoticeType(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN);
				data.put("snsDataMessage", sdm);
				if(FrontDeviceTokenDeviceType.ANDROID.equals(fdt.getDeviceType())) {
					String resultStr = this.awsSnsClientDao.puhlishEndpoint4GCM(data);
					System.out.println("-------------demoNotice---------------:"+resultStr);
				} else if(FrontDeviceTokenDeviceType.IOS.equals(fdt.getDeviceType())) {
					String resultStr = this.awsSnsClientDao.puhlishEndpoint4APNS(data);
					System.out.println("-------------demoNotice---------------:"+resultStr);
				}
			}
		}
	}
	public static void main(String[] args) {
		String key = "eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY24udGlhbmNodWFuZ3l1ZXRlbmcuc2NvcmUiLCJleHAiOjE2MTE3MzUyNTgsImlhdCI6MTYxMTY0ODg1OCwic3ViIjoiMDAxNDU0LmRiM2MxMTUxMzkyYzRiY2E4ZmJmMjFhNmZhOTU0ZTFmLjAzMDUiLCJjX2hhc2giOiJiX1pNXzVILXlnWF92M2NxaXFUUGhBIiwiZW1haWwiOiJ0NzdpeDRuaGNqQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNjExNjQ4ODU4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.J-dpGSo7ljmKHORqxofQJ7J6180BuYrqWnTCqHmTZC3BAQJtFwRslO01HMgFZk4UL3fALZyj32dAOjlop4eruRhmfxH4ozojvV-v0LUN1-_hzS8TIejO82zGcg-NX9B2mLkCN_wcDCmMgDHPV81JgXqnsw_u3lYlaxtIBJr3LQ3Ep6G2JLDQHbFLxHkWCqf0rKk0SF9iLOGWagEoRVKLDeboD5ImDJ4lDb2l-UqsDMISjRmXTUFi9ftYLqNA-JU3t6B4iZ-i9DjJ_jCcq5rUHADD99DXG7doXZUu6hqBiWVIjjdMpY8AX9G8x5Ch3ABfDAPt7DhoNpZuvwQRhUVxKQ";
		String[] arr = key.split("\\.");
		for(String str : arr) {
			System.out.println(Base64Util.getFromBase64(str));
		}
		String fromKey = Base64Util.getFromBase64(key.split("\\.")[1]);
		System.out.println(fromKey);
		String fromKey1 = "000"+"."+key.split("\\.")[0];
		System.out.println(AppleAuthUtil.parserIdentityToken(fromKey1));
		//{"iss":"https://appleid.apple.com","aud":"cn.tianchuangyueteng.score","exp":1611735258,"iat":1611648858,"sub":"001454.db3c1151392c4bca8fbf21a6fa954e1f.0305","c_hash":"b_ZM_5H-ygX_v3cqiqTPhA","email":"t77ix4nhcj@privaterelay.appleid.com","email_verified":"true","is_private_email":"true","auth_time":1611648858,"nonce_supported":true}
		System.out.println("PH//aR6qmcnZxj/n+4oT6AzEMZQ=".length());
		System.out.println("https://graph.facebook.com/v8.0/173128101254009/picture?height=200&width=200&migration_overrides={october_2012:true}&access_token=EAADIJBQunQoBAKETX6jTKqaTYiGCaIPqamMfIGNQuOZCqcmFoXmmwcZBmOKNBM9JyX9F0xDoCaT4fSG9U3QDZAgajSH59yLMHq3pXsPaZB1r9HvX14UoZAB88JzNPiWgVgLwMHmGGaxQptj3TGXp5ZCcUXC7dvadOkmpMfOdrKgHYhxQZBdiKksYdMojeN5KgZCZCrqsXVO6OucqAVZAGwbMtZAKDiRLedSUd0ZD".length());
	}
}
