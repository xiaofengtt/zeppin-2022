package com.whaty.platform.entity.user;

/**
 * @author 陈健
 * 该类描述了用户在平台上的相关信息
 *
 */
public class HumanPlatformInfo {
	private String lastlogin_time=null; //上次登录时间
	private String lastlogin_ip=null;   //上次登录IP
	public String getLastlogin_ip() {
		return lastlogin_ip;
	}
	public void setLastlogin_ip(String lastlogin_ip) {
		this.lastlogin_ip = lastlogin_ip;
	}
	public String getLastlogin_time() {
		return lastlogin_time;
	}
	public void setLastlogin_time(String lastlogin_time) {
		this.lastlogin_time = lastlogin_time;
	}

}
