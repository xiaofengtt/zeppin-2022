package cn.zeppin.action.sso;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.Auth;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Version;
import cn.zeppin.service.api.IAuthService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.IVersionService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

public class SsoLogin extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7073023133290674125L;

	private ISsoUserService ssoUserService;
	private IAuthService authService;
	private IVersionService versionService;

	/**
	 * 用户认证
	 */
	@SuppressWarnings("unchecked")
	@ActionParam(key = "postData", type = ValueType.STRING, emptyable = false, nullable = false)
	public void AuthUser() {
		// =========================================
		// 1.type 认证类型
		// 一是手机或者邮箱+密码
		// 二是第三方认证
		// =========================================
		ActionResult result = new ActionResult();

		String jsonStr = this.request.getParameter("postData");
		Map<String, Object> map = JSONUtils.json2map(jsonStr);

		short type = Short.valueOf(map.get("type").toString());

		if (type == Dictionary.AUTH_OTHER) {

			Map<String, Object> tMap = (Map<String, Object>) map.get("data");

			Map<String, Object> dataMap = authProcess(tMap);
			boolean flag = (Boolean) dataMap.get("status");

			if (flag) {
				result.init(SUCCESS_STATUS, null, dataMap);
			} else {
				result.init(ERROR_STATUS, "登陆失败", null);
			}
		} else {

		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 第三方认证
	 * 
	 * @return
	 */
	private Map<String, Object> authProcess(Map<String, Object> paMap) {

		Map<String, Object> retMap = new Hashtable<String, Object>();

		try {

			Short auth_type = Short.valueOf(paMap.get("auth_type").toString()); // 认证方式，是QQ，微信，微博，人人

			String uid = paMap.get("uid").toString();
			String screen_name = paMap.get("screen_name").toString();
			String profile_image_url = paMap.get("icon").toString();
			Short gender = Short.valueOf(paMap.get("gender").toString());

			

			Map<String, Object> searchMap = new Hashtable<String, Object>();
			searchMap.put("uid", uid);
			searchMap.put("authType", auth_type);

			SsoUser ssoUser = null;

			List<Auth> listAus = this.getAuthService().getAuthsByMap(searchMap, null, -1, -1);
			
			boolean isRegist = true;
			if (listAus != null && listAus.size() > 0) {
				// 已经注册过可以认证通过
				Auth auth = listAus.get(0);
				ssoUser = auth.getSsoUser();
				if (ssoUser != null) {
					isRegist = false;
				}
			}

			if (isRegist) {
				// 需要注册
				ssoUser = new SsoUser();
				ssoUser.setCreatetime(new Timestamp(System.currentTimeMillis()));
				ssoUser.setStatus(Dictionary.SSO_NORMAL);
				ssoUser.setNickname(screen_name);
				ssoUser.setImageUrl(profile_image_url);
				this.getSsoUserService().addSsoUser(ssoUser);

				Auth auth = new Auth();
				auth.setUid(uid);
				auth.setAuthType(auth_type);
				auth.setImageUrl(profile_image_url);
				auth.setIsbound((short) 1);
				auth.setIsdefault((short) 1);
				auth.setNickname(screen_name);
				auth.setStatus((short) 1);
				auth.setSsoUser(ssoUser);
				auth.setGender(gender);
				
				//需要将图片下载到本地

				this.getAuthService().addAuth(auth);

				retMap.put("isFirstLogin", true);

			} else {
				retMap.put("isFirstLogin", false);
			}

			this.session.setAttribute("user", ssoUser);
			
			retMap.put("uid", ssoUser.getId());
			retMap.put("toke", ((Long) System.currentTimeMillis()).toString());
			retMap.put("status", true);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			retMap.put("status", false);
		}

		return retMap;
	}
	
	@ActionParam(key = "version", type = ValueType.STRING, emptyable = false, nullable = false)
	public void VersionVerify(){
		String versionNumber = this.request.getParameter("version");
		
		Map<String, Object> searchMap = new Hashtable<String, Object>();
		searchMap.put("version", versionNumber);
		
		Version version =  this.getVersionService().getVersionByParams(searchMap);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("status", version.getStatus());
		
		ActionResult result = new ActionResult();
		result.init(SUCCESS_STATUS, null, dataMap);
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	
	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public IAuthService getAuthService() {
		return authService;
	}

	public void setAuthService(IAuthService authService) {
		this.authService = authService;
	}
	
	public IVersionService getVersionService() {
		return versionService;
	}

	public void setVersionService(IVersionService versionService) {
		this.versionService = versionService;
	}

}
