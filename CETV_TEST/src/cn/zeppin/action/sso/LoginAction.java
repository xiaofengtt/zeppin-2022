/** 
 * Project Name:CETV_TEST 
 * File Name:UserController.java 
 * Package Name:cn.zeppin.action.sso 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.sso;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;

//import org.apache.commons.lang3.RandomStringUtils;

import org.apache.commons.lang.RandomStringUtils;

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

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
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
					if (currentUser.getRole().getId() == Dictionary.USER_ROLE_EDITOR) {
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

}
