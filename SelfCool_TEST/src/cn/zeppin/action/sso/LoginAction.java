/** 
 * Project Name:Self_Cool  
 * File Name:UserController.java 
 * Package Name:cn.zeppin.action.sso 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.sso;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;






import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Auth;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.Version;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IAuthService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.service.api.IVersionService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JSONUtils;
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

	private ISsoUserService ssoUserService;
	private IAuthService authService;
	private IVersionService versionService;


	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
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

	/**
	 * 后台登录
	 * 
	 * @author Clark
	 * @date: 2014年6月22日 下午4:32:20 <br/>
	 */
	public void Login() {
		String sessionCode = session.getAttribute("authcode") == null ? "" : session.getAttribute("authcode").toString();
		ActionResult result = new ActionResult();
		if (!this.containsParameter("loginname", "password", "authcode")) {
			result.init(FAIL_STATUS, "接口参数传递不正确！", null);
		} else {
			String loginName = request.getParameter("loginname");
			String passWord = request.getParameter("password");
			String authCode = request.getParameter("authcode");
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
			else if (sessionCode == null || !authCode.equals(sessionCode)) {
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

					// =========================
					// 1.如果用户是编辑，去查询编辑所有权限
					// =========================
					if (currentUser.getRole().getId() != Dictionary.USER_ROLE_SUPERADMIN) {
						session.setAttribute("usersubject", getSysUserService().getSubjectBySysuser(currentUser));
						session.setAttribute("usergrade", getSysUserService().getGradeBySysuser(currentUser));
					}

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

			String authCode = RandomStringUtils.randomNumeric(5);
			OutputStream os = response.getOutputStream();

			// 指定图形验证码图片的大小
			BufferedImage image = Java2D.drawAuthCodeImg(authCode);

			// 将验证码保存到seesion中
			session.setAttribute("authcode", authCode);

			ImageIO.write(image, "jpeg", os);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
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
		System.out.println("jsonStr:"+jsonStr);
		
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

			boolean isRegist = false;

			Map<String, Object> searchMap = new Hashtable<String, Object>();
			searchMap.put("uid", uid);
			searchMap.put("authType", auth_type);

			SsoUser ssoUser = null;

			List<Auth> listAus = this.getAuthService().getAuthsByMap(searchMap, null, -1, -1);
			if (listAus != null && listAus.size() > 0) {
				// 已经注册过可以认证通过
				Auth auth = listAus.get(0);
				ssoUser = auth.getSsoUser();
				if (ssoUser == null) {
					isRegist = true;
				}
			} else {
				// 没有认证过，需注册然後在認證通过
				isRegist = true;
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
}
