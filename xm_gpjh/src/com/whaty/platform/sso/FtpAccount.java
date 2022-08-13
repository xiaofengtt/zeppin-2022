/*
 * FtpAccount.java
 *
 * Created on 2004��11��27��, ����8:37
 */

package com.whaty.platform.sso;

/**
 * ftp�ʻ���Ϣ��
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
     * ���� ftpLoginId �Ļ�ȡ������
     * @return ���� ftpLoginId ��ֵ��
     */
    public java.lang.String getFtpLoginId() {
        return ftpLoginId;
    }    
   
    /**
     * ���� ftpLoginId �����÷�����
     * @param ftpLoginId ���� ftpLoginId ����ֵ��
     */
    public void setFtpLoginId(java.lang.String ftpLoginId) {
        this.ftpLoginId = ftpLoginId;
    }
    
    /**
     * ���� ftpPwd �Ļ�ȡ������
     * @return ���� ftpPwd ��ֵ��
     */
    public java.lang.String getFtpPwd() {
        return ftpPwd;
    }
    
    /**
     * ���� ftpPwd �����÷�����
     * @param ftpPwd ���� ftpPwd ����ֵ��
     */
    public void setFtpPwd(java.lang.String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }
    
    /**
     * ���� ftpHomeDir �Ļ�ȡ������
     * @return ���� ftpHomeDir ��ֵ��
     */
    public java.lang.String getFtpHomeDir() {
        return ftpHomeDir;
    }
    
    /**
     * ���� ftpHomeDir �����÷�����
     * @param ftpHomeDir ���� ftpHomeDir ����ֵ��
     */
    public void setFtpHomeDir(java.lang.String ftpHomeDir) {
        this.ftpHomeDir = ftpHomeDir;
    }
    
}
