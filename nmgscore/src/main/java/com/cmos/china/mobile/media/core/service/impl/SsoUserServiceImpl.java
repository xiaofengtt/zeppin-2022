package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



import com.cmos.china.mobile.media.core.bean.MsgCode;
import com.cmos.china.mobile.media.core.bean.SsoUser;
import com.cmos.china.mobile.media.core.bean.SsoUserLogin;
import com.cmos.china.mobile.media.core.service.ISsoUserService;
import com.cmos.china.mobile.media.core.util.ESBUtil;
import com.cmos.china.mobile.media.core.util.EncodindgUtlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.esbclient.bean.EsbOutObject;

public class SsoUserServiceImpl extends BaseServiceImpl implements ISsoUserService {
	private static Logger logger = LoggerFactory.getUtilLog(EncodindgUtlity.class);
	private String loginUrl = "http://192.168.100.35:9950/ngmttcore/ws/auth/cust/custLogin/";
	private String esbId = "com.cmos.esb.user.nmgscore";
	private String smsEsbUrl = "http://192.168.100.5:9099/sms/message/smsMessage";
	/**
	 * 用户登录
	 */
	@Override
	public void login(InputObject inputObject, OutputObject outputObject) throws Exception {
		
		String loginname = inputObject.getValue("loginname");
		String password = inputObject.getValue("password");
		String ip = inputObject.getValue("ip");
		logger.warn("testPwd", password);
		
		//ip冻结判定
		Map<String, String> ipErrorParams = new HashMap<String, String>();
		ipErrorParams.put("ip", ip);
		ipErrorParams.put("time", new Timestamp(new Date().getTime()).toString());
		ipErrorParams.put("start", "0");
		ipErrorParams.put("limit", "10");
		List<SsoUserLogin> ipErrorList = this.getBaseDao().queryForList("ssoUserLogin_getListByParams", ipErrorParams, SsoUserLogin.class);
		if(ipErrorList.size() == 10){
			boolean userErrorMark = true;
			for(SsoUserLogin ipError : ipErrorList){
				if("success".equals(ipError.getResult())){
					userErrorMark = false;
					break;
				}
			}
			if(userErrorMark){
				throw new Exception("ip已被冻结");
			}
		}
		
		if(loginname==null||loginname.equals("")){
			throw new Exception("账号不能为空");
		}
		
		//验证账号是否被冻结
		SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_getByPhone", loginname, SsoUser.class);
		if(ssoUser!=null && ssoUser.getFreezeTime()!=null && new Timestamp(new Date().getTime()).before(ssoUser.getFreezeTime())){
			throw new Exception("账号已被冻结");
		}
		
		password = EncodindgUtlity.getFromCipher(password);
		Boolean mark = true;
		String message = "";
		
		//验证登录
		if(password==null||password.equals("")){
			mark = false;
			message = "密码输入错误";
		}else{
			Map<String, String> params=new HashMap<String, String>();
			params.put("mobileNo", loginname);
			params.put("hashPwd", password);
			EsbOutObject esbresult = ESBUtil.ssologin(params, esbId, loginUrl);
			if(esbresult!=null && !esbresult.equals("")){
				if(esbresult.getBean()!=null && !esbresult.getBean().equals("")){
					String result = esbresult.getBean().get("rtnCode");
					if(result!=null && !result.equals("")){
						if("0".equals(result)){
							//登录成功
							if(ssoUser != null && !"".equals(ssoUser.getId())){
								//存在用户返回信息
								String token = UUID.randomUUID().toString();
								ssoUser.setToken(token);
								this.getBaseDao().update("ssoUser_update", ssoUser);
								
								Map<String,Object> data = new HashMap<String,Object>();
								data.put("phone", ssoUser.getPhone());
								data.put("name", ssoUser.getName());
								data.put("status", ssoUser.getStatus());
								data.put("token", token);
								outputObject.setBean(data);
							}else{
								//不存在用户,注册并返回信息
								ssoUser = new SsoUser();
								String id = UUID.randomUUID().toString();
								ssoUser.setId(id);
								ssoUser.setName("用户");
								ssoUser.setPhone(loginname);
								ssoUser.setStatus("normal");
								ssoUser.setCreatetime(new Timestamp((new Date()).getTime()));
								String token = UUID.randomUUID().toString();
								ssoUser.setToken(token);
								this.getBaseDao().update("ssoUser_add", ssoUser);
								
								Map<String,Object> data = new HashMap<String,Object>();
								data.put("phone", ssoUser.getPhone());
								data.put("name", ssoUser.getName());
								data.put("status", ssoUser.getStatus());
								data.put("token", token);
								outputObject.setBean(data);
							}
						}else{
							mark = false;
							message = "用户名或密码错误";
						}
					}else{
						mark = false;
						message = "用户名或密码错误";
					}
				}else{
					mark = false;
					message = "用户名或密码错误";
				}
			}else{
				mark = false;
				message = "用户名或密码错误";
			}
		}
		
		//写入登录记录
		SsoUserLogin sul = new SsoUserLogin();
		String id = UUID.randomUUID().toString();
		sul.setId(id);
		sul.setIp(ip);
		sul.setResult(mark ? "success":"fail");
		sul.setType("password");
		sul.setCreatetime(new Timestamp(new Date().getTime()));
		if(ssoUser!=null){
			sul.setUser(ssoUser.getId());
		}
		this.getBaseDao().insert("ssoUserLogin_add", sul);
		
		if(!mark){
			//账号冻结判定
			if(ssoUser!=null){
				Map<String, String> userErrorParams = new HashMap<String, String>();
				userErrorParams.put("user", ssoUser.getId());
				userErrorParams.put("time", new Timestamp(new Date().getTime()).toString());
				userErrorParams.put("start", "0");
				userErrorParams.put("limit", "5");
				List<SsoUserLogin> userErrorList = this.getBaseDao().queryForList("ssoUserLogin_getListByParams", userErrorParams, SsoUserLogin.class);
				
				if(userErrorList.size() == 5){
					boolean userErrorMark = true;
					for(SsoUserLogin userError : userErrorList){
						if("success".equals(userError.getResult())){
							userErrorMark = false;
							break;
						}
					}
					if(userErrorMark){
						ssoUser.setFreezeTime(new Timestamp(new Date().getTime() + 3600000));
						this.getBaseDao().update("ssoUser_update", ssoUser);
					}
				}
			}
			throw new Exception(message);
		}
	}
	
	/**
	 * 第三方登录验证
	 */
	public void verify(InputObject inputObject, OutputObject outputObject) throws Exception{

//		System.out.println(inputObject.toJson());
		
		String loginname = inputObject.getValue("loginname");
		String token = inputObject.getValue("token");
		if(loginname==null||loginname.equals("")){
			throw new Exception("账号不能为空");
		}
		if(token==null||token.equals("")){
			throw new Exception("token不能为空");
		}
		
		SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_getByPhone", loginname, SsoUser.class);
		if(ssoUser!=null){
			if(ssoUser.getFreezeTime()!=null && new Timestamp(new Date().getTime()).before(ssoUser.getFreezeTime())){
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("rtnCode", "-9998");
				data.put("message", "账号已被冻结");
				outputObject.setBean(data);
			}else{
				if(token.equals(ssoUser.getToken())){
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("rtnCode", "0");
					data.put("message", "验证成功");
					outputObject.setBean(data);
				}else{
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("rtnCode", "-9999");
					data.put("message", "验证失败");
					outputObject.setBean(data);
				}
			}
		}else{
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("rtnCode", "-9999");
			data.put("message", "验证失败");
			outputObject.setBean(data);
		}
	}
	
	/**
	 * 加载用户详细信息
	 */
	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_get", id, SsoUser.class);
		if(ssoUser==null){
			throw new Exception("用户不存在");
		}else{
			outputObject.convertBean2Map(ssoUser);
		}
	}
	
	/**
	 * 修改用户信息
	 */
	@Override
	public void editInfomation(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		if(name==null||name.equals("")){
			throw new Exception("昵称不能为空");
		}
		
		SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_get", id, SsoUser.class);
		if(ssoUser == null){
			throw new Exception("用户不存在");
		}else{
			ssoUser.setName(name);
			this.getBaseDao().update("ssoUser_update", ssoUser);
		}
	}
	
	/**
	 * 修改密码
	 */
	@Override
	public void editPassword(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String password = inputObject.getValue("password");
		String newPassword = inputObject.getValue("newPassword");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		if(password==null||password.equals("")){
			throw new Exception("未填写密码");
		}
		if(newPassword==null||newPassword.equals("")){
			throw new Exception("新密码不能为空");
		}
		
		SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_get", id, SsoUser.class);
		if(ssoUser == null){
			throw new Exception("用户不存在");
		}else{
			//修改密码
		}
	}
	
	/**
	 * 停用用户
	 */	
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_get", id, SsoUser.class);
		if(ssoUser==null){
			throw new Exception("用户不存在");
		}else{
			this.getBaseDao().update("ssoUser_delete", ssoUser);
		}
	}

	
	/**
	 * 获取短信验证码
	 */	
	@Override
	public void getSmsCode(InputObject inputObject, OutputObject outputObject)
			throws Exception {
		String user = inputObject.getValue("phone");
		String code  = null;
		MsgCode msgCode = this.getBaseDao().queryForObject("msgCode_getByPhone", user, MsgCode.class);
			if(msgCode!=null && !msgCode.equals("")){
				if(!new Timestamp(new Date().getTime()).after(msgCode.getNextTime())){
					throw new Exception("请等待60s后重新发送");
				}
			}
				code = String.valueOf((int) ((Math.random()*9+1)*100000));
//				code = "123456";
				Map<String, String> params = new HashMap<String, String>();
				 params.put("apiKey", "a4451e7ba1d519c32894492821dbaf8f");
				 params.put("userName", "newmedia");
				 params.put("billId", user);
				 params.put("rspId", "7425");
				 params.put("infoMap", "{\"data_identifykey\":\""+code+"\"}");
				 
				 EsbOutObject esbresult = ESBUtil.smslogin(params, esbId, smsEsbUrl);
				 if(esbresult!=null && !esbresult.equals("")){
					 String result = esbresult.getRtnCode();
						if(result!=null && result!=null){
							if ("0".equals(result)) {
								if(msgCode!=null && !"".equals(msgCode.getId())){
								msgCode.setCode(EncodindgUtlity.getCipher(code));
								msgCode.setNextTime(new Timestamp(new Date().getTime() + 60000));
								msgCode.setUsableTime(new Timestamp(new Date().getTime() + 300000));		
								this.getBaseDao().update("msgCode_update", msgCode);
								}else{
									MsgCode msg = new MsgCode();
									String id = UUID.randomUUID().toString();
									msg.setId(id);
									msg.setCode(EncodindgUtlity.getCipher(code));
									msg.setNextTime(new Timestamp(new Date().getTime() + 60000));
									msg.setUsableTime(new Timestamp(new Date().getTime() + 300000));
									msg.setUser(user);
									this.getBaseDao().update("msgCode_add", msg);
								}
								Map<String,Object> data = new HashMap<String,Object>();
								data.put("rtnCode", "0");
								data.put("Code", code);
								data.put("message", "发送成功");
								outputObject.setBean(data);	
							} else {
								throw new Exception("获取验证码错误");
							}
						}else {
								throw new Exception("获取验证码错误");
						}
				 	}else {
						throw new Exception("获取验证码错误");
					}
			}	
		

	/**
	 * 验证短信验证码
	 */	
	@Override
	public void authSmsCode(InputObject inputObject, OutputObject outputObject)
			throws Exception {
		String user = inputObject.getValue("phone");
		String code = inputObject.getValue("code");
		String ip = inputObject.getValue("ip");
		
		//ip冻结判定
		Map<String, String> ipErrorParams = new HashMap<String, String>();
		ipErrorParams.put("ip", ip);
		ipErrorParams.put("time", new Timestamp(new Date().getTime()).toString());
		ipErrorParams.put("start", "0");
		ipErrorParams.put("limit", "10");
		List<SsoUserLogin> ipErrorList = this.getBaseDao().queryForList("ssoUserLogin_getListByParams", ipErrorParams, SsoUserLogin.class);
		if(ipErrorList.size() == 10){
			boolean userErrorMark = true;
			for(SsoUserLogin ipError : ipErrorList){
				if("success".equals(ipError.getResult())){
					userErrorMark = false;
					break;
				}
			}
//			if(userErrorMark){
//				throw new Exception("ip已被冻结");
//			}
		}
		
		String message = "";
		Boolean mark = true;
		if(user==null||user.equals("")){
			mark = false;
			throw new Exception("手机号不能为空");
		}
		if(code==null||code.equals("")){
			mark = false;
			throw new Exception("验证码不能为空");
		}
		//验证账号是否被冻结
		SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_getByPhone", user, SsoUser.class);
		if(ssoUser!=null && ssoUser.getFreezeTime()!=null && new Timestamp(new Date().getTime()).before(ssoUser.getFreezeTime())){
			throw new Exception("账号已被冻结");
		}
		
		MsgCode msgCode = this.getBaseDao().queryForObject("msgCode_getByPhone", user, MsgCode.class);
		if(msgCode!=null){
			if(new Timestamp(new Date().getTime()).before(msgCode.getUsableTime()))
			{
				if(code.equals(EncodindgUtlity.getFromCipher(msgCode.getCode()))){
					if(ssoUser != null && !"".equals(ssoUser.getId())){
						//存在用户返回信息
						String token = UUID.randomUUID().toString();
						ssoUser.setToken(token);
						this.getBaseDao().update("ssoUser_update", ssoUser);
						
						Map<String,Object> data = new HashMap<String,Object>();
						data.put("phone", ssoUser.getPhone());
						data.put("name", ssoUser.getName());
						data.put("status", ssoUser.getStatus());
						data.put("token", token);
						outputObject.setBean(data);
					}else{
						//不存在用户,注册并返回信息
						ssoUser = new SsoUser();
						String id = UUID.randomUUID().toString();
						ssoUser.setId(id);
						ssoUser.setName("用户");
						ssoUser.setPhone(user);
						ssoUser.setStatus("normal");
						ssoUser.setCreatetime(new Timestamp((new Date()).getTime()));
						String token = UUID.randomUUID().toString();
						ssoUser.setToken(token);
						this.getBaseDao().update("ssoUser_add", ssoUser);
						
						Map<String,Object> data = new HashMap<String,Object>();
						data.put("phone", ssoUser.getPhone());
						data.put("name", ssoUser.getName());
						data.put("status", ssoUser.getStatus());
						data.put("token", token);
						outputObject.setBean(data);
					}
				}else{
					mark = false;
					message = "验证码错误";
				}
			}else{
				mark = false;
				message = "短信已失效";
			}
		}else{
			mark = false;
			message = "短信验证失败";
		}
		
		
		
		//写入登录记录
		SsoUserLogin sul = new SsoUserLogin();
		String id = UUID.randomUUID().toString();
		sul.setId(id);
		sul.setIp(ip);
		sul.setResult(mark ? "success":"fail");
		sul.setType("message");
		sul.setCreatetime(new Timestamp(new Date().getTime()));
		if(ssoUser!=null){
			sul.setUser(ssoUser.getId());
		}
		this.getBaseDao().insert("ssoUserLogin_add", sul);
		
		if(!mark){
			//账号冻结判定
			if(ssoUser!=null){
				Map<String, String> userErrorParams = new HashMap<String, String>();
				userErrorParams.put("user", ssoUser.getId());
				userErrorParams.put("time", new Timestamp(new Date().getTime()).toString());
				userErrorParams.put("start", "0");
				userErrorParams.put("limit", "5");
				List<SsoUserLogin> userErrorList = this.getBaseDao().queryForList("ssoUserLogin_getListByParams", userErrorParams, SsoUserLogin.class);
				
				if(userErrorList.size() == 5){
					boolean userErrorMark = true;
					for(SsoUserLogin userError : userErrorList){
						if("success".equals(userError.getResult())){
							userErrorMark = false;
							break;
						}
					}
					if(userErrorMark){
						ssoUser.setFreezeTime(new Timestamp(new Date().getTime() + 3600000));
						this.getBaseDao().update("ssoUser_update", ssoUser);
					}
				}
			}
			throw new Exception(message);
		}
	} 
}
