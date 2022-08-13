package com.zixueku.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.Constant;
import com.zixueku.util.JSONUtil;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-9 上午10:12:32
 */
public class LoginService {

	// 登录前设置往服务器端发送的数据
	public static Map<String, Object> setRequestData(User user, int type) {
		Map<String, Object> requestData = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("type", type); // 三方登录
		postData.put("data", user);
		user.setType(type);
		requestData.put("postData", JSONUtil.objectToJson(postData));
		return requestData;
	}

	public static Map<String, Object> setRequestData(User user) {
		Map<String, Object> requestData = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("data", user);
		requestData.put("postData", JSONUtil.objectToJson(postData));
		return requestData;
	}

	// 登录前，设置用户信息
	public static void setUserInfo(User user,  Map<String, Object> info) {
		/*user.setAuth_type(Constant.AUTH_PLATFORMS.get(db.getPlatformNname()));
		user.setGender(Constant.USERGENDER.get((db.getUserGender())));
		String figureUrl = db.getUserIcon();
		if (figureUrl.endsWith("40")) {
			figureUrl = figureUrl.substring(0, figureUrl.length() - 2) + "100";
		}
		user.setIcon(figureUrl);
		user.setScreen_name(db.getUserName());
		user.setUid(db.getUserId());*/
	}



	public static void setInfor2User(User user, HashMap<String, Object> params, int type) {
		int userId = ((Double) params.get("uid")).intValue();
		String nickname = (String) params.get("nickname");
		String imageUrl = (String) params.get("imageUrl");
		String password = (String) params.get("password");
		String phone = (String) params.get("phone");
		String professional = (String) params.get("professional");
		int age = ((Double) params.get("age")).intValue();
		int gender = ((Double) params.get("gender")).intValue();
		Boolean isFirstLogin = (Boolean) params.get("isFirstLogin");

		user.setUserId(userId);
		user.setPhone(phone);
		user.setIcon(imageUrl);
		user.setScreen_name(nickname);
		user.setProfessional(professional);
		user.setAge(age);
		user.setGender((short) gender);
		user.setPassword(password);
		user.setIsFirstLogin(isFirstLogin);
		user.setType(type);
	}

	// 保存用户信息到手机
	public static void persistenceUserInfor(Context context, User user) {
		/*
		 * "age":-1, "gender":-1, "imageUrl":"", "isFirstLogin":false,
		 * "nickname":"13717891733", "password":"123456", "phone":"13717891733",
		 * "professional":"", "status":true, "toke":"1441512894886", "uid":51
		 */
		SharedPreferences sp = context.getSharedPreferences(Constant.USER_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.putInt("userId", user.getUserId());
		editor.putString("nickname", user.getScreen_name());
		editor.putString("imageUrl", user.getIcon());
		editor.putString("password", user.getPassword());
		editor.putString("phone", user.getPhone());
		editor.putString("professional", user.getProfessional());
		editor.putInt("age", user.getAge());
		editor.putInt("gender", user.getGender());
		editor.putBoolean("isFirstLogin", false);
		editor.putInt("type", user.getType());
		editor.commit();
	}



	public static void loadUserInfor(SharedPreferences sharedPreferences) {
		User user = App.getInstance().getUserInfo();
		user.setGender((short) sharedPreferences.getInt("gender", -1));
		user.setIcon(sharedPreferences.getString("imageUrl", ""));
		user.setScreen_name(sharedPreferences.getString("nickname", ""));
		user.setUserId(sharedPreferences.getInt("userId", 0));
		user.setIsFirstLogin(sharedPreferences.getBoolean("isFirstLogin", false));
		user.setPassword(sharedPreferences.getString("password", ""));
		user.setPhone(sharedPreferences.getString("phone", ""));
		user.setProfessional(sharedPreferences.getString("professional", ""));
		user.setAge(sharedPreferences.getInt("age", -1));
		user.setType(sharedPreferences.getInt("type", -1));
	}
}
