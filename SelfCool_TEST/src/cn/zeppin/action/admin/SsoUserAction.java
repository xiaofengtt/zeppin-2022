/** 
 * Project Name:Self_Cool  
 * File Name:SsoUserAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: SsoUserAction <br/>
 * date: 2014年7月20日 下午7:05:24 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class SsoUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;
	
	private ISsoUserService ssoUserService;

	/**
	 * 更新个人信息
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@ActionParam(key = "nickname", type = ValueType.STRING)
	@ActionParam(key = "school", type = ValueType.STRING)
	@ActionParam(key = "professional", type = ValueType.STRING)
	@ActionParam(key = "imageUrl", type = ValueType.STRING)
	public void Update() {
		ActionResult result = new ActionResult();
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		
		if (request.getParameterMap().containsKey("nickname")) {
			String nickname = request.getParameter("nickname");
			currentUser.setNickname(nickname);
		}
		if (request.getParameterMap().containsKey("school")) {
			String school = request.getParameter("school");
			currentUser.setSchool(school);
		}
		if (request.getParameterMap().containsKey("professional")) {
			String professional = request.getParameter("professional");
			currentUser.setProfessional(professional);
		}
		if (request.getParameterMap().containsKey("imageUrl")) {
			String imageUrl = request.getParameter("imageUrl");
			currentUser.setImageUrl(imageUrl);
		}
		this.getSsoUserService().updateSsoUser(currentUser);
		result.init(SUCCESS_STATUS, "修改成功", null);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

}
