package com.zixueku.service;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.PlatformDb;

import com.zixueku.entity.User;
import com.zixueku.util.Constant;
import com.zixueku.util.JSONUtil;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-9 上午10:12:32
 */
public class LoginService {

    //登录前设置往服务器端发送的数据
	public Map<String, Object> setRequestData(User user, int type) {
		Map<String, Object> requestData = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("type", type); // 三方登录
		postData.put("data", user);
		requestData.put("postData", JSONUtil.objectToJson(postData));
		return requestData;
	}
	
	//登录前，设置用户信息
	public void setUserInfo(User user, PlatformDb db) {
		user.setAuth_type(Constant.AUTH_PLATFORMS.get(db.getPlatformNname()));
		user.setGender(Constant.USERGENDER.get((db.getUserGender())));
		String figureUrl = db.getUserIcon();
		if (figureUrl.endsWith("40")) {
			figureUrl = figureUrl.substring(0, figureUrl.length() - 2) + "100";
		}
		user.setIcon(figureUrl);
		user.setScreen_name(db.getUserName());
		user.setUsername(db.getUserName());
		user.setUid(db.getUserId());
	}
	
	//登录过程中，服务器发送回数据
	//{Records={isfristLogin=false, status=true, toke=1425869098351, uid=1.0}, Status=success}
	public void setUserInfo(User user, Map<String, Object> paramObject){
		user.setIsFirstLogin((Boolean) paramObject.get("isFirstLogin"));
		Double userId = (Double) paramObject.get("uid");
		user.setUserId(userId.intValue());
	}

}
