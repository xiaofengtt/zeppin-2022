/*
 * FtpAccount.java
 *
 * Created on 2004年11月27日, 下午8:37
 */

package com.whaty.platform.sso;

/**
 * ftp帐户信息类
 * @author  chenjian
 */
public abstract class FtpAccount {
    
    private String ftpLoginId;
    
    private String ftpPwd;
    
    private String ftpHomeDir;
    
    /** Creates a new instance of FtpAccount */
    public FtpAccount() {
    }
    
    /**
     * 属性 ftpLoginId 的获取方法。
     * @return 属性 ftpLoginId 的值。
     */
    public java.lang.String getFtpLoginId() {
        return ftpLoginId;
    }    
   
    /**
     * 属性 ftpLoginId 的设置方法。
     * @param ftpLoginId 属性 ftpLoginId 的新值。
     */
    public void setFtpLoginId(java.lang.String ftpLoginId) {
        this.ftpLoginId = ftpLoginId;
    }
    
    /**
     * 属性 ftpPwd 的获取方法。
     * @return 属性 ftpPwd 的值。
     */
    public java.lang.String getFtpPwd() {
        return ftpPwd;
    }
    
    /**
     * 属性 ftpPwd 的设置方法。
     * @param ftpPwd 属性 ftpPwd 的新值。
     */
    public void setFtpPwd(java.lang.String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }
    
    /**
     * 属性 ftpHomeDir 的获取方法。
     * @return 属性 ftpHomeDir 的值。
     */
    public java.lang.String getFtpHomeDir() {
        return ftpHomeDir;
    }
    
    /**
     * 属性 ftpHomeDir 的设置方法。
     * @param ftpHomeDir 属性 ftpHomeDir 的新值。
     */
    public void setFtpHomeDir(java.lang.String ftpHomeDir) {
        this.ftpHomeDir = ftpHomeDir;
    }
    
}
