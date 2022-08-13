package com.zixueku.entity;

public class User {
	private int userId;
	private String uid; // 第三方用户标识
	private String username; // 用户名
	private String screen_name;
	private Integer type; // 登录类型 1是正常登陆，2是第三方认证登陆
	private Short auth_type; // 第三方登陆类型,1|2|3|4 (qq,weixin,sina,renren)
	private Short gender; // 性别 gender:1|2 (1：男，2女）
	private String icon; // 用户头像
	private String password; // 密码,正常登录时需要
	private Boolean isFirstLogin;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public Short getAuth_type() {
		return auth_type;
	}

	public void setAuth_type(Short auth_type) {
		this.auth_type = auth_type;
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", uid=" + uid + ", username=" + username + ", type=" + type + ", auth_type=" + auth_type + ", gender=" + gender
				+ ", icon=" + icon + ", password=" + password + "]";
	}
}
