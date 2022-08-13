package com.zixueku.entity;

public class User {
	private int userId;
	private String uid; // 第三方用户标识
	private String screen_name;
	private Integer type; // 登录类型 1是正常登录，2是第三方认证登录
	private Short auth_type; // 第三方登录类型,1|2|3|4 (qq,weixin,sina,renren)
	private Short gender; // 性别 gender:1|2 (1：男，2女）
	private String icon; // 用户头像
	private String password; // 密码,正常登录时需要
	private Boolean isFirstLogin;
	private String code;// 验证码
	private String phone;
	private String professional;
	private int age;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", uid=" + uid + ", screen_name=" + screen_name + ", type=" + type + ", auth_type=" + auth_type + ", gender="
				+ gender + ", icon=" + icon + ", password=" + password + ", isFirstLogin=" + isFirstLogin + ", code=" + code + ", phone=" + phone
				+ ", professional=" + professional + ", age=" + age + "]";
	}

}
