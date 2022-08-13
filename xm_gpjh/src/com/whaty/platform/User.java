/*
 * User.java
 *
 * Created on 2004年9月24日, 下午2:51
 */

package com.whaty.platform;

import com.whaty.platform.sso.FtpAccount;



/**
 * 用户类，
 * @author chenjian
 */
public class User{
    
    
    /**
     * 全局唯一登录ID
     */
    private String loginId;
    /**
     * 电子邮件
     */
    private String email;
    private FtpAccount ftpaccount;
    /**
     * 全局统一标识ID
     */
    private String id;
    private String name;
    private String password;
    private String loginType;
    
    public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	/** Creates a new instance of User */
    public User() {
    }
    
    /**
     * 属性 email 的获取方法。
     * @return 属性 email 的值。
     */
    public java.lang.String getEmail() {
		if(email == null || email.length() ==0)
			return "";
        return email;
    }    
   
    /**
     * 属性 email 的设置方法。
     * @param email 属性 email 的新值。
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    /**
     * 属性 login_id 的获取方法。
     * @return 属性 login_id 的值。
     */
    public java.lang.String getLoginId() {
        return loginId;
    }
    
    /**
     * 属性 login_id 的设置方法。
     * @param login_id 属性 login_id 的新值。
     */
    public void setLoginId(java.lang.String loginId) {
        this.loginId = loginId;
    }
    
   
    /**
     * 属性 name 的获取方法。
     * @return 属性 name 的值。
     */
    public java.lang.String getName() {
		if(name == null || name.length() ==0)
			return "";
        return name;
    }
    
    /**
     * 属性 name 的设置方法。
     * @param name 属性 name 的新值。
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /**
     * 属性 id 的获取方法。
     * @return 属性 id 的值。
     */
    public java.lang.String getId() {
        return id;
    }
    
    /**
     * 属性 id 的设置方法。
     * @param id 属性 id 的新值。
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    /**
     * 属性 password 的获取方法。
     * @return 属性 password 的值。
     */
    public java.lang.String getPassword() {
        return password;
    }
    
    /**
     * 属性 password 的设置方法。
     * @param password 属性 password 的新值。
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    
    /**
     * 属性 ftpaccount 的获取方法。
     * @return 属性 ftpaccount 的值。
     */
    public com.whaty.platform.sso.FtpAccount getFtpaccount() {
        return ftpaccount;
    }
    
    /**
     * 属性 ftpaccount 的设置方法。
     * @param ftpaccount 属性 ftpaccount 的新值。
     */
    public void setFtpaccount(com.whaty.platform.sso.FtpAccount ftpaccount) {
        this.ftpaccount = ftpaccount;
    }

    
  }
