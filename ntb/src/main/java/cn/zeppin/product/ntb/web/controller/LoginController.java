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
	 * ????????????????????????????????? (??????openID)
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
				return ResultManager.createFailResult("?????????????????????");
			}
		}else{
			return ResultManager.createFailResult("?????????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param token base64(device+time+md5(key+time+mobile+code+price))
	 * @param phone
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message = "?????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????")
	@ResponseBody
	public Result register(String token, String phone, String price){
		token = Base64Util.getFromBase64(token);
		phone = Base64Util.getFromBase64(phone);
		
		Map<String, Object> other = new HashMap<String, Object>();
		if(!Utlity.checkStringNull(price)){
			price = Base64Util.getFromBase64(price);
			
			if (!Utlity.isPositiveCurrency4Web(price)) {
				return ResultManager.createFailResult("??????????????????!");
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
			return ResultManager.createDataResult(data,result.get("message") == null ? "???????????????" : result.get("message").toString());
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "???????????????" : te.getMessage());
		} catch (Exception e) {
			super.flushAll();
			return ResultManager.createFailResult("???????????????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ResponseBody
	public Result login(String token){
		token = Base64Util.getFromBase64(token);
		
		Map<String, Object> result = null;
		try {
			result = this.investorService.login(token);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("uuid", result.get("uuid"));
			return ResultManager.createDataResult(data,result.get("message") == null ? "???????????????" : result.get("message").toString());
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "???????????????" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("???????????????????????????");
		}
	}
	
	/**
	 * ????????????--?????????????????????
	 * @return
	 */
	@RequestMapping(value = "/loginBycode", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ResponseBody
	public Result loginBycode(String token){
		token = Base64Util.getFromBase64(token);
		
		Map<String, Object> result = null;
		try {
			result = this.investorService.loginBycode(token);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("uuid", result.get("uuid"));
			return ResultManager.createDataResult(data,result.get("message") == null ? "???????????????" : result.get("message").toString());
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "???????????????" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("???????????????????????????");
		}
	}
	
	/**
	 * ??????????????????--?????????????????????
	 * @return
	 */
	@RequestMapping(value = "/resetCheck", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message = "?????????", required = true)
	@ResponseBody
	public Result resetCheck(String token, String phone){
		token = Base64Util.getFromBase64(token);
		phone = Base64Util.getFromBase64(phone);
		String step = "first";
		try {
			this.investorService.loginResetpwd(token, phone, step);
			return ResultManager.createSuccessResult("???????????????");
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "???????????????" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("???????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message = "?????????", required = true)
	@ResponseBody
	public Result resetpwd(String token, String phone){
		token = Base64Util.getFromBase64(token);
		phone = Base64Util.getFromBase64(phone);
		String step = "second";
		try {
			this.investorService.loginResetpwd(token, phone, step);
			return ResultManager.createSuccessResult("?????????????????????");
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "?????????????????????" : te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ResponseBody
	public Result logout(String token){
		token = Base64Util.getFromBase64(token);
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){//???????????????
				if(token.length() != 75){
					return ResultManager.createFailResult("???????????????");
				}
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return ResultManager.createFailResult("???????????????");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						return ResultManager.createFailResult("??????????????????????????????");
					}
				} else {
					return ResultManager.createFailResult("??????????????????????????????");
				}
				String openID = token.substring(15, 43);
				if(openID == null || "".equals(openID)){
					return ResultManager.createFailResult("????????????????????????????????????");
				}
				Investor invertor = this.investorService.getByOpenID(openID, Investor.class);
				String uuid = "";
				if(invertor != null){
					uuid = invertor.getUuid();
				}
				String md5Str = token.substring(43);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("???????????????");
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+uuid);
				if(md5Str.equals(realMD5Str)){
					invertor = this.investorService.get(uuid);
					invertor.setOpenid(null);
					this.investorService.update(invertor);
					return ResultManager.createSuccessResult("????????????");
				}
				
			} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
				if(token.length() != 83){
					return ResultManager.createFailResult("???????????????");
				}
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return ResultManager.createFailResult("???????????????");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						return ResultManager.createFailResult("??????????????????????????????");
					}
				} else {
					return ResultManager.createFailResult("??????????????????????????????");
				}
				String uuid = token.substring(15, 51);
				if(uuid == null || "".equals(uuid)){
					return ResultManager.createFailResult("????????????????????????????????????");
				}
				String md5Str = token.substring(51);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("???????????????");
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+uuid);
				if(md5Str.equals(realMD5Str)){
					return ResultManager.createSuccessResult("????????????");
				}
				
			} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
				
			}
		}
		return ResultManager.createFailResult("???????????????");
	}
	
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @param webmarket base64??????
	 * @param version base64??????
	 * @param token ????????????Base64(device???????????????+timestamp+md5(key+timstamp+webmarket+version))
	 * @return
	 */
	@RequestMapping(value = "/getWebmarketSwitch", method = RequestMethod.GET)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "webmarket", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "version", type = DataType.STRING, message = "?????????")
	@ResponseBody
	public Result getWebmarketSwitch(String token, String webmarket, String version){
		token = Base64Util.getFromBase64(token);
		webmarket = Base64Util.getFromBase64(webmarket);
		version = Base64Util.getFromBase64(version);
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)){
				if(token.length() != 47){
					return ResultManager.createFailResult("???????????????");
				}
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return ResultManager.createFailResult("???????????????");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						return ResultManager.createFailResult("??????????????????????????????");
					}
				} else {
					return ResultManager.createFailResult("??????????????????????????????");
				}
				
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("???????????????");
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+webmarket+version);
				if(md5Str.equals(realMD5Str)){//???????????? ?????????????????????
//					Map<String, Object> resultMap = new HashMap<String, Object>();
					Boolean flag = true;
					Map<String, String> inputParams = new HashMap<String, String>();
//					inputParams.put("status", VersionStatus.PUBLISHED);
					inputParams.put("version", version);
					inputParams.put("device", deviceNumber);
					List<Entity> listCurrent = this.versionService.getListForPage(inputParams, -1, -1, null, Version.class);
					if(listCurrent != null && !listCurrent.isEmpty()){//?????????????????? ??????
						
					} else {//????????????????????????????????????false
						flag = false;
						return ResultManager.createDataResult(flag, "??????");
					}
					Version ver = (Version)listCurrent.get(0);
					inputParams.clear();
					inputParams.put("webMarket", webmarket);
					inputParams.put("version", ver.getUuid());
					List<Entity> list = bkWebmarketSwitchService.getListForPage(inputParams, -1, -1, null, BkWebmarketSwitchVO.class);
					if(list != null && !list.isEmpty()){
						BkWebmarketSwitchVO vo = (BkWebmarketSwitchVO)list.get(0);
						return ResultManager.createDataResult(vo.getFlagSwitch(), "??????");
					} else {
						return ResultManager.createDataResult(flag, "??????");
					}
				}
			}
		}
		return ResultManager.createFailResult("????????????");
		
	}
	
	/**
	 * ??????????????????????????????????????????
	 * ?????????????????????
	 * 1???????????????????????????????????????????????????
	 * 2?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 * 3????????????????????????????????????????????????????????????
	 * 4???????????????????????????????????????
	 * token??????????????????
	 * Base64(device(?????????)+timestamp+md5(key+timestamp+version))
	 * @param token
	 * @param version base64??????
	 * @return
	 */
	@RequestMapping(value = "/getVersionInfo", method = RequestMethod.GET)
	@ActionParam(key = "token", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "version", type = DataType.STRING, message = "?????????")
	@ResponseBody
	public Result getVersionInfo(String token, String version){
		token = Base64Util.getFromBase64(token);
		version = Base64Util.getFromBase64(version);
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
				if(token.length() != 47){
					return ResultManager.createFailResult("???????????????");
				}
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return ResultManager.createFailResult("???????????????");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						return ResultManager.createFailResult("??????????????????????????????");
					}
				} else {
					return ResultManager.createFailResult("??????????????????????????????");
				}
				
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("???????????????");
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+version);
				if(md5Str.equals(realMD5Str)){//???????????? ?????????????????????
					//???????????????????????????????????????
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
						if(!VersionStatus.PUBLISHED.equals(currVer.getStatus())){//??????????????????????????????????????????????????????
							flagCompel = true;
						}
					}
					resultMap.put("currentVersion", currentVersion);
					resultMap.put("currentVersionName", currentVersionName);
					//?????????????????????????????????????????????????????????
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
					return ResultManager.createDataResult(resultMap, "????????????");
				}
			} else {
				return ResultManager.createFailResult("????????????????????????");
			}
		}
		return ResultManager.createFailResult("???????????????");
	}

	public static void main(String[] args) {
		System.out.println(MD5.getMD5("27739700ee0bf2930cd62d72a80def0a150416953993313161346073789534"));
//		System.out.println(URLEncoder.encode("https://backadmin.niutoulicai.com/auth/auth.html"));
	}
}
