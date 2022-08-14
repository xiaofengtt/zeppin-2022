package cn.zeppin.action.sso;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;

import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Java2D;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: UserController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月17日 下午3:26:53 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class LoginAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3909927948294558147L;

	private ISysUserService sysUserService;
//
//	private ISsoUserService ssoUserService;
//	private IAuthService authService;
//	private IVersionService versionService;
//	private IMobileCodeService mobileCodeService;
//
//
	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
//	
//	public ISsoUserService getSsoUserService() {
//		return ssoUserService;
//	}
//
//	public void setSsoUserService(ISsoUserService ssoUserService) {
//		this.ssoUserService = ssoUserService;
//	}
//
//	public IAuthService getAuthService() {
//		return authService;
//	}
//
//	public void setAuthService(IAuthService authService) {
//		this.authService = authService;
//	}
//
//	public IVersionService getVersionService() {
//		return versionService;
//	}
//
//	public void setVersionService(IVersionService versionService) {
//		this.versionService = versionService;
//	}
//
//	public IMobileCodeService getMobileCodeService() {
//		return mobileCodeService;
//	}
//
//	public void setMobileCodeService(IMobileCodeService mobileCodeService) {
//		this.mobileCodeService = mobileCodeService;
//	}
	/**
	 * 后台登录
	 * 
	 * @author Clark
	 */
	public void Login() {
		String sessionCode = session.getAttribute("authcode") == null ? "" : session.getAttribute("authcode").toString();
		ActionResult result = new ActionResult();
		if (!this.containsParameter("loginname", "password", "authcode")) {
			result.init(FAIL_STATUS, "接口参数传递不正确！", null);
		} else {
			String loginName = Utlity.getBase64Decoder(request.getParameter("loginname"));
			String passWord = Utlity.getBase64Decoder(request.getParameter("password"));
			String authCode = Utlity.getBase64Decoder(request.getParameter("authcode"));
			// 验证用户名为空
			if (loginName == null || loginName.equals("")) {
				result.init(FAIL_STATUS, "没有输入用户名！", null);
			}
			// 密码为空
			else if (passWord == null || passWord.equals("")) {
				result.init(FAIL_STATUS, "没有输入密码！", null);
			}
			// 验证验证码为空
			else if (authCode == null || authCode.equals("")) {
				result.init(FAIL_STATUS, "没有输入验证码！", null);
			}
			// 验证码输入不正确
			else if (sessionCode == null || !authCode.toLowerCase().equals(sessionCode.toLowerCase())) {
				result.init(FAIL_STATUS, "验证码输入不正确！", null);
			} else {
				SysUser currentUser = getSysUserService().getSysUser(loginName);
				if (currentUser == null) {
					result.init(FAIL_STATUS, "此用户不存在！", null);
				} else if (!currentUser.getPassword().equals(passWord)) {
					result.init(FAIL_STATUS, "用户密码输入错误！", null);
				} else if (currentUser.getStatus() != Dictionary.USER_STATUS_NOMAL) {
					result.init(FAIL_STATUS, "该账户已停用！", null);
				} else {
					// 保存会话状态
					session.setAttribute("usersession", currentUser);


					Map<String, Object> data = SerializeEntity.sysUser2Map(currentUser);
					result.init(SUCCESS_STATUS, "登录成功，正在努力为您加载页面！", data);
				}
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * @category 生成验证码
	 */
	public void AuthImg() {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("image/jpeg");
			response.addHeader("expires", "0");

			char[] random = {'A','B','C','D','E','F','G',
							'H','I','J','K','L','M','N',
							'O','P','Q','R','S','T',
							'U','V','W','X','Y','Z',
							'1','2','3','4','5','6','7','8','9','0'};
			
			String authCode = RandomStringUtils.random(5,random);
			OutputStream os = response.getOutputStream();

			// 指定图形验证码图片的大小
			BufferedImage image = Java2D.drawNewAuthCodeImg(authCode);

			// 将验证码保存到seesion中
			session.setAttribute("authcode", authCode);

			ImageIO.write(image, "jpeg", os);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	/**
	 * 用户登出
	 */
	public void Logout(){
		ActionResult result = new ActionResult();
		session.removeAttribute("usersession");
		result.init(SUCCESS_STATUS, null, null);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
//	
//	/**
//	 * 用户认证
//	 */
//	@SuppressWarnings("unchecked")
//	@ActionParam(key = "postData", type = ValueType.STRING, emptyable = false, nullable = false)
//	public void AuthUser() {
//		// =========================================
//		// 1.type 认证类型
//		// 一是手机+密码登录
//		// 二是第三方认证
//		// 三是手机+密码注册
//		// 四是手机+验证码登录
//		// 五是用户绑定/改绑手机
//		// =========================================
//		ActionResult result = new ActionResult();
//
//		String jsonStr = this.request.getParameter("postData");
//		
//		Map<String, Object> map = JSONUtils.json2map(jsonStr);
//
//		short type = Short.valueOf(map.get("type").toString());
//
//		if (type == Dictionary.AUTH_OTHER) {
//
//			Map<String, Object> tMap = (Map<String, Object>) map.get("data");
//
//			Map<String, Object> dataMap = authProcess(tMap);
//			boolean flag = (Boolean) dataMap.get("status");
//
//			if (flag) {
//				result.init(SUCCESS_STATUS, null, dataMap);
//			} else {
//				result.init(ERROR_STATUS, "登录失败", null);
//			}
//		} else if (type == Dictionary.AUTH_NORMAL){
//			Map<String, Object> tMap = (Map<String, Object>) map.get("data");
//			
//			Map<String, Object> dataMap = passwordProcess(tMap);
//			boolean flag = (Boolean) dataMap.get("status");
//			
//			if (flag) {
//				result.init(SUCCESS_STATUS, null, dataMap);
//			} else {
//				result.init(ERROR_STATUS, "登录失败", null);
//			}
//		} else if (type == Dictionary.AUTH_REGISERT){
//			Map<String, Object> tMap = (Map<String, Object>) map.get("data");
//			
//			Map<String, Object> dataMap = userRegister(tMap);
//			boolean flag = (Boolean) dataMap.get("status");
//			
//			if (flag) {
//				result.init(SUCCESS_STATUS, null, dataMap);
//			} else {
//				result.init(ERROR_STATUS,(String)dataMap.get("reason"), null);
//			}
//		} else if (type == Dictionary.AUTH_CODELOGIN){
//			Map<String, Object> tMap = (Map<String, Object>) map.get("data");
//			
//			Map<String, Object> dataMap = userCodeLogin(tMap);
//			boolean flag = (Boolean) dataMap.get("status");
//			
//			if (flag) {
//				result.init(SUCCESS_STATUS, null, dataMap);
//			} else {
//				result.init(ERROR_STATUS,"登录失败", null);
//			}
//		}else if (type == Dictionary.AUTH_ADDPHONE){
//			Map<String, Object> tMap = (Map<String, Object>) map.get("data");
//			
//			Map<String, Object> dataMap = userAddPhone(tMap);
//			boolean flag = (Boolean) dataMap.get("status");
//			
//			if (flag) {
//				result.init(SUCCESS_STATUS, null, dataMap);
//			} else {
//				result.init(ERROR_STATUS,(String)dataMap.get("reason"), null);
//			}
//		}
//
//		String dataType = request.getParameter("datatype");
//		Utlity.ResponseWrite(result, dataType, response);
//
//	}
//
//	/**
//	 * 用户绑定手机
//	 * 
//	 * @return
//	 */
//	private Map<String, Object> userAddPhone(Map<String, Object> paMap) {
//		
//		Map<String, Object> retMap = new Hashtable<String, Object>();
//		
//		try {
//			String uid = paMap.get("user.id").toString();
//			String phone = paMap.get("phone").toString();
//			String code = paMap.get("code").toString();
//			
//			if(checkPhoneCode(phone,code)){
//				Map<String, Object> searchMap = new Hashtable<String, Object>();
//				searchMap.put("phone", phone);
//				
//				List<SsoUser> listSso = this.getSsoUserService().getSsoUserByMap(searchMap);
//				if(listSso.size() > 0){
//					retMap.put("status", false);
//					retMap.put("reason", "该手机号已于其他账号绑定");
//				}else{
//					SsoUser ssoUser = this.getSsoUserService().getById(Integer.valueOf(uid));
//					if (ssoUser != null) {
//						ssoUser.setPhone(phone);
//						this.getSsoUserService().updateSsoUser(ssoUser);
//						this.session.setAttribute("user", ssoUser);
//						retMap.put("status", true);
//					}else{
//						retMap.put("status", false);
//						retMap.put("reason", "用户不存在");
//					}
//				}
//			}else{
//				retMap.put("status", false);
//				retMap.put("reason", "手机号或验证码错误");
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			retMap.put("status", false);
//			retMap.put("reason", "手机号或验证码错误");
//		}
//		
//		return retMap;
//	}
//	
//	/**
//	 * 通过手机号和验证码登录
//	 * 
//	 * @return
//	 */
//	private Map<String, Object> userCodeLogin(Map<String, Object> paMap) {
//		
//		Map<String, Object> retMap = new Hashtable<String, Object>();
//		
//		try {
//			String phone = paMap.get("phone").toString();
//			String code = paMap.get("code").toString();
//			
//			if(checkPhoneCode(phone,code)){
//				Map<String, Object> searchMap = new Hashtable<String, Object>();
//				searchMap.put("phone", phone);
//				
//				SsoUser ssoUser = null;
//				
//				List<SsoUser> listSso = this.getSsoUserService().getSsoUserByMap(searchMap);
//				if (listSso != null && listSso.size() > 0) {
//					ssoUser = listSso.get(0);
//					this.session.setAttribute("user", ssoUser);
//					retMap.put("isFirstLogin", false);
//					retMap.put("nickname", ssoUser.getNickname());
//					retMap.put("uid", ssoUser.getId());
//					retMap.put("toke", ((Long) System.currentTimeMillis()).toString());
//					retMap.put("status", true);
//					retMap.put("gender", ssoUser.getGender() == null ? -1 : ssoUser.getGender());
//					retMap.put("age", ssoUser.getAge() == null ? -1 : ssoUser.getAge());
//					retMap.put("phone", ssoUser.getPhone() == null ? "" : ssoUser.getPhone());
//					retMap.put("password", ssoUser.getPassword() == null ? "" : ssoUser.getPassword());
//					retMap.put("professional", ssoUser.getProfessional() == null ? "" : ssoUser.getProfessional());
//					retMap.put("imageUrl", ssoUser.getImageUrl() == null ? "" : ssoUser.getImageUrl());
//				}else{
//					retMap.put("status", false);
//				}
//			}else{
//				retMap.put("status", false);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			retMap.put("status", false);
//			retMap.put("reason", "手机号或验证码错误");
//		}
//		
//		return retMap;
//	}
//	
//	/**
//	 * 用户注册
//	 * 
//	 * @return
//	 */
//	private Map<String, Object> userRegister(Map<String, Object> paMap) {
//		
//		Map<String, Object> retMap = new Hashtable<String, Object>();
//		
//		try {
//			String phone = paMap.get("phone").toString();
//			String code = paMap.get("code").toString();
//			String password = paMap.get("password").toString();
//			
//			Map<String, Object> searchMap = new Hashtable<String, Object>();
//			searchMap.put("phone", phone);
//			
//			List<SsoUser> listSso = this.getSsoUserService().getSsoUserByMap(searchMap);
//			if (listSso.size() > 0) {
//				retMap.put("status", false);
//				retMap.put("reason", "该手机号已注册");
//			}else{
//				if(checkPhoneCode(phone,code)){
//					SsoUser ssoUser = new SsoUser();
//					ssoUser.setNickname(phone);
//					ssoUser.setPhone(phone);
//					ssoUser.setPassword(password);
//					ssoUser.setCreatetime(new Timestamp(System.currentTimeMillis()));
//					ssoUser.setStatus(Dictionary.SSO_NORMAL);
//					this.ssoUserService.addSsoUser(ssoUser);
//					retMap.put("status", true);
//					retMap.put("isFirstLogin", true);
//					retMap.put("nickname", ssoUser.getNickname());
//					retMap.put("uid", ssoUser.getId());
//					retMap.put("toke", ((Long) System.currentTimeMillis()).toString());
//					retMap.put("status", true);
//					retMap.put("gender", ssoUser.getGender() == null ? -1 : ssoUser.getGender());
//					retMap.put("age", ssoUser.getAge() == null ? -1 : ssoUser.getAge());
//					retMap.put("phone", ssoUser.getPhone() == null ? "" : ssoUser.getPhone());
//					retMap.put("password", ssoUser.getPassword() == null ? "" : ssoUser.getPassword());
//					retMap.put("professional", ssoUser.getProfessional() == null ? "" : ssoUser.getProfessional());
//					retMap.put("imageUrl", ssoUser.getImageUrl() == null ? "" : ssoUser.getImageUrl());
//				}else{
//					retMap.put("status", false);
//					retMap.put("reason", "手机号或验证码错误");
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			retMap.put("status", false);
//			retMap.put("reason", "手机号或验证码错误");
//		}
//		
//		return retMap;
//	}
//	
//
//	/**
//	 * 手机号密码认证
//	 * 
//	 * @return
//	 */
//	private Map<String, Object> passwordProcess(Map<String, Object> paMap) {
//		
//		Map<String, Object> retMap = new Hashtable<String, Object>();
//		
//		try {
//			String phone = paMap.get("phone").toString();
//			String password = paMap.get("password").toString();
//			
//			Map<String, Object> searchMap = new Hashtable<String, Object>();
//			searchMap.put("phone", phone);
//			searchMap.put("password", password);
//			
//			SsoUser ssoUser = null;
//			
//			List<SsoUser> listSso = this.getSsoUserService().getSsoUserByMap(searchMap);
//			if (listSso != null && listSso.size() > 0) {
//				// 已经注册过可以认证通过
//				ssoUser = listSso.get(0);
//				this.session.setAttribute("user", ssoUser);
//				retMap.put("isFirstLogin", false);
//				retMap.put("nickname", ssoUser.getNickname());
//				retMap.put("uid", ssoUser.getId());
//				retMap.put("toke", ((Long) System.currentTimeMillis()).toString());
//				retMap.put("status", true);
//				retMap.put("gender", ssoUser.getGender() == null ? -1 : ssoUser.getGender());
//				retMap.put("age", ssoUser.getAge() == null ? -1 : ssoUser.getAge());
//				retMap.put("phone", ssoUser.getPhone() == null ? "" : ssoUser.getPhone());
//				retMap.put("password", ssoUser.getPassword() == null ? "" : ssoUser.getPassword());
//				retMap.put("professional", ssoUser.getProfessional() == null ? "" : ssoUser.getProfessional());
//				retMap.put("imageUrl", ssoUser.getImageUrl() == null ? "" : ssoUser.getImageUrl());
//			}else{
//				retMap.put("status", false);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			retMap.put("status", false);
//		}
//		
//		return retMap;
//	}
//	
//	/**
//	 * 第三方认证
//	 * 
//	 * @return
//	 */
//	private Map<String, Object> authProcess(Map<String, Object> paMap) {
//
//		Map<String, Object> retMap = new Hashtable<String, Object>();
//
//		try {
//
//			Short auth_type = Short.valueOf(paMap.get("auth_type").toString()); // 认证方式，是QQ，微信，微博，人人
//
//			String uid = paMap.get("uid").toString();
//			String screen_name = paMap.get("screen_name").toString();
//			String profile_image_url = paMap.get("icon").toString();
//			Short gender = Short.valueOf(paMap.get("gender").toString());
//
//			boolean isRegist = false;
//
//			Map<String, Object> searchMap = new Hashtable<String, Object>();
//			searchMap.put("uid", uid);
//			searchMap.put("authType", auth_type);
//
//			SsoUser ssoUser = null;
//
//			List<Auth> listAus = this.getAuthService().getAuthsByMap(searchMap, null, -1, -1);
//			if (listAus != null && listAus.size() > 0) {
//				// 已经注册过可以认证通过
//				Auth auth = listAus.get(0);
//				ssoUser = auth.getSsoUser();
//				if (ssoUser == null) {
//					isRegist = true;
//				}
//			} else {
//				// 没有认证过，需注册然後在認證通过
//				isRegist = true;
//			}
//
//			if (isRegist) {
//				// 需要注册
//				ssoUser = new SsoUser();
//				ssoUser.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				ssoUser.setStatus(Dictionary.SSO_NORMAL);
//				ssoUser.setNickname(screen_name);
//				ssoUser.setImageUrl(profile_image_url);
//				ssoUser.setGender(gender);
//				this.getSsoUserService().addSsoUser(ssoUser);
//
//				Auth auth = new Auth();
//				auth.setUid(uid);
//				auth.setAuthType(auth_type);
//				auth.setImageUrl(profile_image_url);
//				auth.setIsbound((short) 1);
//				auth.setIsdefault((short) 1);
//				auth.setNickname(screen_name);
//				auth.setStatus((short) 1);
//				auth.setSsoUser(ssoUser);
//				auth.setGender(gender);
//				
//				//需要将图片下载到本地
//
//				this.getAuthService().addAuth(auth);
//
//				retMap.put("isFirstLogin", true);
//
//			} else {
//				retMap.put("isFirstLogin", false);
//			}
//
//			this.session.setAttribute("user", ssoUser);
//			retMap.put("nickname", ssoUser.getNickname());
//			retMap.put("uid", ssoUser.getId());
//			retMap.put("toke", ((Long) System.currentTimeMillis()).toString());
//			retMap.put("status", true);
//			retMap.put("gender", ssoUser.getGender() == null ? -1 : ssoUser.getGender());
//			retMap.put("age", ssoUser.getAge() == null ? -1 : ssoUser.getAge());
//			retMap.put("phone", ssoUser.getPhone() == null ? "" : ssoUser.getPhone());
//			retMap.put("password", ssoUser.getPassword() == null ? "" : ssoUser.getPassword());
//			retMap.put("professional", ssoUser.getProfessional() == null ? "" : ssoUser.getProfessional());
//			retMap.put("imageUrl", ssoUser.getImageUrl());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			retMap.put("status", false);
//		}
//
//		return retMap;
//	}
//
//	@ActionParam(key = "version", type = ValueType.STRING, emptyable = false, nullable = false)
//	@ActionParam(key = "device", type = ValueType.NUMBER)
//	public void VersionVerify(){
//		String versionNumber = this.request.getParameter("version");
//		Short device = Short.valueOf(this.request.getParameter("device") == null ? "1" : this.request.getParameter("device"));
//		
//		Map<String, Object> searchMap = new Hashtable<String, Object>();
//		searchMap.put("version", versionNumber);
//		searchMap.put("device", device);
//		Version version =  this.getVersionService().getVersionByParams(searchMap);
//		
//		Map<String, Object> map = new Hashtable<String, Object>();
//		map.put("device", device);
//		map.put("id", version.getId());
//		List<Version> newVersions = this.getVersionService().getNewVersions(map);
//		
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		dataMap.put("status", version.getStatus());
//		if(newVersions.size() > 0){
//			dataMap.put("hasNew", true);
//			
//			Map<String, Object> newVersionMap = new HashMap<String, Object>();
//			newVersionMap.put("version", newVersions.get(0).getVersion());
//			newVersionMap.put("downloadPath", newVersions.get(0).getResource() == null ? "" : newVersions.get(0).getResource().getPath());
//			
//			Boolean forced = false;
//			for(Version v : newVersions){
//				if(v.getForcedUpdate() == Dictionary.VERSION_UPDATE_FORCED){
//					forced = true;
//					break;
//				}
//			}
//			newVersionMap.put("forced", forced);
//			dataMap.put("newVersion", newVersionMap);
//		}else{
//			dataMap.put("hasNew", false);
//			dataMap.put("newVersion",new HashMap<String, Object>());
//		}
//		
//		ActionResult result = new ActionResult();
//		result.init(SUCCESS_STATUS, null, dataMap);
//		
//		String dataType = request.getParameter("datatype");
//		Utlity.ResponseWrite(result, dataType, response);
//	}
//	
//	/**
//	 * 检验手机验证码是否正确
//	 * @return
//	 */
//	public boolean checkPhoneCode(String mobile, String captcha){
//		
//		
//		if(mobile != null || captcha != null){
//			String uuid = (String)session.getAttribute("code");
//			Timestamp time = new Timestamp(System.currentTimeMillis());
//			MobileCode mc = null;
//			if(uuid != null){
//				mc = this.mobileCodeService.getRecordByUuid(uuid);
//			} else {
//				Map<String , Object> params = new HashMap<String, Object>();
//				params.put("mobile", mobile);
//				params.put("status", Dictionary.MOBILECODE_STATUS_VALID);
//				
//				List<MobileCode> lstMobileCode = this.mobileCodeService.getMobileCodeByParams(params);
//				
//				if(lstMobileCode != null && lstMobileCode.size() > 0){
//					mc = lstMobileCode.get(0);
//				}
//			}
//			
//			if(mc == null){
//				return false;
//			}
//			
//			int seconds = (int)Math.ceil((time.getTime()-mc.getCreatetime().getTime())/(60*1000));
//			
//			if(!mc.getMobile().equals(mobile)){//比对是否同一手机发送
//				return false;
//			}else if(!mc.getCode().equals(captcha)){//比较验证码是否正确
//				return false;
//			}else if (seconds>20) {//比较验证码是否超时
//				return false;
//			}else{//验证成功，设置验证码失效
//				
//				mc.setStatus(Dictionary.MOBILECODE_STATUS_INVALID);
//				this.mobileCodeService.deleteMobileCode(mc);
//				return true;
//			}
//		}else{
//			return true;
//		}
//	}
}
