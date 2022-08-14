package cn.zeppin.product.ntb.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkWebmarketSwitchService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.service.api.IVersionService;
import cn.zeppin.product.ntb.backadmin.vo.BkWebmarketSwitchVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.Version.VersionStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.web.vo.VersionVO;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.HttpUtility;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.WeixinDecrypt;

@Controller
@RequestMapping(value = "/web/login")
public class LoginController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IVersionService versionService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkWebmarketSwitchService bkWebmarketSwitchService;
	
	/**
	 * 获取微信小程序用户信息 (通过openID)
	 * @param encryptedData
	 * @param iv
	 * @param sessionKey
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ActionParam(key = "encryptedData", type = DataType.STRING, message = "encryptedData", required = true)
	@ActionParam(key = "iv", type = DataType.STRING, message = "iv", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "code", required = true)
	@ResponseBody
	public Result getUserInfo(String encryptedData, String iv, String code) {
		StringBuilder url = new StringBuilder(Utlity.WX_LOGIN_URL);
		url.append("?appid=").append(Utlity.WX_APPID);
		url.append("&secret=").append(Utlity.WX_APPSECRET);
		url.append("&js_code=").append(code);
		url.append("&grant_type=authorization_code");
		String json = HttpUtility.get(url.toString());
		Map<String, Object> dataMap = JSONUtils.json2map(json);
		String sessionKey = dataMap.get("session_key").toString();
		
		byte[] resultByte = WeixinDecrypt.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
		if(null != resultByte && resultByte.length > 0){
			String userInfo;
			try {
				userInfo = new String(resultByte, "UTF-8");
				Map<String, Object> resultMap = JSONUtils.json2map(userInfo);
				String openId = "";
				if(resultMap.get("openId") != null && !"".equals(resultMap.get("openId").toString())){
					openId = resultMap.get("openId").toString();
				}
				String uuid = "";
				if(!"".equals(openId)){
					Investor invertor = this.investorService.getByOpenID(openId, Investor.class);
					if(invertor != null){
						uuid = invertor.getUuid();
					}
				}
				resultMap.put("uuid", uuid);
				return ResultManager.createDataResult(resultMap);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("数据解密失败！");
			}
		}else{
			return ResultManager.createFailResult("数据解密失败！");
		}
	}
	
	/**
	 * 用户注册
	 * @param token base64(device+time+md5(key+time+mobile+code+price))
	 * @param phone
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message = "手机号", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "红包金额")
	@ResponseBody
	public Result register(String token, String phone, String price){
		token = Base64Util.getFromBase64(token);
		phone = Base64Util.getFromBase64(phone);
		
		Map<String, Object> other = new HashMap<String, Object>();
		if(!Utlity.checkStringNull(price)){
			price = Base64Util.getFromBase64(price);
			
			if (!Utlity.isPositiveCurrency4Web(price)) {
				return ResultManager.createFailResult("红包金额错误!");
			}
			other.put("price", price);
		} else {
			price = "";
		}
		
		Map<String, Object> result = null;
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			
			result = this.investorService.register(token,phone,other);
			data.put("uuid", result.get("investor"));
			return ResultManager.createDataResult(data,result.get("message") == null ? "注册成功！" : result.get("message").toString());
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "注册失败！" : te.getMessage());
		} catch (Exception e) {
			super.flushAll();
			return ResultManager.createFailResult("注册失败，服务异常");
		}
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ResponseBody
	public Result login(String token){
		token = Base64Util.getFromBase64(token);
		
		Map<String, Object> result = null;
		try {
			result = this.investorService.login(token);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("uuid", result.get("uuid"));
			return ResultManager.createDataResult(data,result.get("message") == null ? "登录成功！" : result.get("message").toString());
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "登录失败！" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("登录失败，服务异常");
		}
	}
	
	/**
	 * 用户登录--通过短信验证码
	 * @return
	 */
	@RequestMapping(value = "/loginBycode", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ResponseBody
	public Result loginBycode(String token){
		token = Base64Util.getFromBase64(token);
		
		Map<String, Object> result = null;
		try {
			result = this.investorService.loginBycode(token);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("uuid", result.get("uuid"));
			return ResultManager.createDataResult(data,result.get("message") == null ? "登录成功！" : result.get("message").toString());
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "登录失败！" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("登录失败，服务异常");
		}
	}
	
	/**
	 * 用户重置密码--验证手机验证码
	 * @return
	 */
	@RequestMapping(value = "/resetCheck", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message = "手机号", required = true)
	@ResponseBody
	public Result resetCheck(String token, String phone){
		token = Base64Util.getFromBase64(token);
		phone = Base64Util.getFromBase64(phone);
		String step = "first";
		try {
			this.investorService.loginResetpwd(token, phone, step);
			return ResultManager.createSuccessResult("验证成功！");
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "验证失败！" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("验证失败，服务异常");
		}
	}
	
	/**
	 * 用户重置密码
	 * @return
	 */
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message = "手机号", required = true)
	@ResponseBody
	public Result resetpwd(String token, String phone){
		token = Base64Util.getFromBase64(token);
		phone = Base64Util.getFromBase64(phone);
		String step = "second";
		try {
			this.investorService.loginResetpwd(token, phone, step);
			return ResultManager.createSuccessResult("重置密码成功！");
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "重置密码失败！" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("重置密码失败，服务异常");
		}
	}
	
	/**
	 * 退出登录
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ResponseBody
	public Result logout(String token){
		token = Base64Util.getFromBase64(token);
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("未识别的设备号！");
			}
			if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){//微信小程序
				if(token.length() != 75){
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
				String openID = token.substring(15, 43);
				if(openID == null || "".equals(openID)){
					return ResultManager.createFailResult("验证失败，用户信息错误！");
				}
				Investor invertor = this.investorService.getByOpenID(openID, Investor.class);
				String uuid = "";
				if(invertor != null){
					uuid = invertor.getUuid();
				}
				String md5Str = token.substring(43);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("验证失败！");
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+uuid);
				if(md5Str.equals(realMD5Str)){
					invertor = this.investorService.get(uuid);
					invertor.setOpenid(null);
					this.investorService.update(invertor);
					return ResultManager.createSuccessResult("登出成功");
				}
				
			} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
				if(token.length() != 83){
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
				String uuid = token.substring(15, 51);
				if(uuid == null || "".equals(uuid)){
					return ResultManager.createFailResult("验证失败，用户信息错误！");
				}
				String md5Str = token.substring(51);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("验证失败！");
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+uuid);
				if(md5Str.equals(realMD5Str)){
					return ResultManager.createSuccessResult("登出成功");
				}
				
			} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
				
			}
		}
		return ResultManager.createFailResult("验证失败！");
	}
	
	
	/**
	 * 获取应用商店版本开关
	 * @param uuid
	 * @param webmarket base64加密
	 * @param version base64加密
	 * @param token 生成规则Base64(device（设备号）+timestamp+md5(key+timstamp+webmarket+version))
	 * @return
	 */
	@RequestMapping(value = "/getWebmarketSwitch", method = RequestMethod.GET)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证信息", required = true)
	@ActionParam(key = "webmarket", type = DataType.STRING, message = "应用商店", required = true)
	@ActionParam(key = "version", type = DataType.STRING, message = "版本号")
	@ResponseBody
	public Result getWebmarketSwitch(String token, String webmarket, String version){
		token = Base64Util.getFromBase64(token);
		webmarket = Base64Util.getFromBase64(webmarket);
		version = Base64Util.getFromBase64(version);
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("未识别的设备号！");
			}
			
			if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)){
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
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+webmarket+version);
				if(md5Str.equals(realMD5Str)){//验证成功 进行下一步操作
//					Map<String, Object> resultMap = new HashMap<String, Object>();
					Boolean flag = true;
					Map<String, String> inputParams = new HashMap<String, String>();
//					inputParams.put("status", VersionStatus.PUBLISHED);
					inputParams.put("version", version);
					inputParams.put("device", deviceNumber);
					List<Entity> listCurrent = this.versionService.getListForPage(inputParams, -1, -1, null, Version.class);
					if(listCurrent != null && !listCurrent.isEmpty()){//当前版本存在 继续
						
					} else {//当前版本不存在，直接返回false
						flag = false;
						return ResultManager.createDataResult(flag, "成功");
					}
					Version ver = (Version)listCurrent.get(0);
					inputParams.clear();
					inputParams.put("webMarket", webmarket);
					inputParams.put("version", ver.getUuid());
					List<Entity> list = bkWebmarketSwitchService.getListForPage(inputParams, -1, -1, null, BkWebmarketSwitchVO.class);
					if(list != null && !list.isEmpty()){
						BkWebmarketSwitchVO vo = (BkWebmarketSwitchVO)list.get(0);
						return ResultManager.createDataResult(vo.getFlagSwitch(), "成功");
					} else {
						return ResultManager.createDataResult(flag, "成功");
					}
				}
			}
		}
		return ResultManager.createFailResult("验证失败");
		
	}
	
	/**
	 * 获取当前版本信息和新版本信息
	 * 新版本信息确认
	 * 1、如果当前无更新版本，则不需要更新
	 * 2、如果最新版本不是强制更新的，判断当前版本至最新版本之间是否有强制更新的版本，如果有，则设置最新版本为强制更新；否则不强制更新
	 * 3、如果当前版本失效，必须强制更新到最新版
	 * 4、当前系统中的最新发布版本
	 * token验证信息规则
	 * Base64(device(设备号)+timestamp+md5(key+timestamp+version))
	 * @param token
	 * @param version base64加密
	 * @return
	 */
	@RequestMapping(value = "/getVersionInfo", method = RequestMethod.GET)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证信息", required = true)
	@ActionParam(key = "version", type = DataType.STRING, message = "版本号")
	@ResponseBody
	public Result getVersionInfo(String token, String version){
		token = Base64Util.getFromBase64(token);
		version = Base64Util.getFromBase64(version);
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("未识别的设备号！");
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
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+version);
				if(md5Str.equals(realMD5Str)){//验证成功 进行下一步操作
					//查询当前版本号对应版本信息
					Map<String, Object> resultMap = new HashMap<String, Object>();
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("status", VersionStatus.PUBLISHED);
					inputParams.put("version", version);
					inputParams.put("device", deviceNumber);
					List<Entity> listCurrent = this.versionService.getListForPage(inputParams, -1, -1, null, Version.class);
					Boolean flagUpdate = false;
					Boolean flagCompel = false;
					Version currVer = null;
					String currentVersion = "";
					String currentVersionName = "";
					if(listCurrent != null && !listCurrent.isEmpty()){
						currVer = (Version) listCurrent.get(0);
						currentVersion = currVer.getVersion()+"";
						currentVersionName = currVer.getVersionName();
						if(!VersionStatus.PUBLISHED.equals(currVer.getStatus())){//如果当前版本不在发布状态，则强制更新
							flagCompel = true;
						}
					}
					resultMap.put("currentVersion", currentVersion);
					resultMap.put("currentVersionName", currentVersionName);
					//查看最新版本号，并判断是否需要强制更新
					inputParams.clear();
					inputParams.put("versionUp", version);
					inputParams.put("status", VersionStatus.PUBLISHED);
					inputParams.put("device", deviceNumber);
					List<Entity> listNew = this.versionService.getListForPage(inputParams, -1, -1, null, Version.class);
					VersionVO vo = null;
					if(listNew != null && !listNew.isEmpty()){
						flagUpdate = true;
						
						Version newUpdate = (Version)listNew.get(0);
						vo = new VersionVO(newUpdate);
						Resource resource = resourceService.get(newUpdate.getResource());
						if (resource != null) {
							vo.setResourceUrl(resource.getUrl());
						}
						Boolean flag = false;
						for(Entity entity: listNew){
							Version newVer = (Version)entity;
							if(newVer.getFlagCompel()){
								flag = true;
							}
						}
						flagCompel = flag;
					} else {
						flagCompel = false;
					}
//					if(currVer == null){
//						flagUpdate = false;
//					}
					resultMap.put("flagUpdate", flagUpdate);
					resultMap.put("flagCompel", flagCompel);
					resultMap.put("newVersion", vo);
					return ResultManager.createDataResult(resultMap, "操作成功");
				}
			} else {
				return ResultManager.createFailResult("未识别的设备号！");
			}
		}
		return ResultManager.createFailResult("验证失败！");
	}

	public static void main(String[] args) {
		System.out.println(MD5.getMD5("27739700ee0bf2930cd62d72a80def0a150416953993313161346073789534"));
//		System.out.println(URLEncoder.encode("https://backadmin.niutoulicai.com/auth/auth.html"));
	}
}
