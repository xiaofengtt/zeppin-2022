/**
 * 
 */
package com.whaty.platform.util.log;


/**
 * @author chenjian
 *
 */
public class SsoLog {

protected static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("SsoLog"); 
    
    /** Creates a new instance of Log */
    public SsoLog() {
    }
    
    /**
     * ��¼����
     * @param error ��������
     */    
    public static void setError(java.lang.String error) {
        log.error(error);
    }
    
    /**
     * ��¼������Ϣ
     * @param debug ������Ϣ
     */    
    public static void setDebug(java.lang.String debug) {
        log.debug(debug);
    }
    
    /**
     * ��¼��Ϣ
     * @param info ��Ϣ����
     */    
    public static void setInfo(java.lang.String info) {
        log.info(info);
    }
    
    /**
     * ��¼����
     * @param warn ��������
     */    
    public static void setWarn(java.lang.String warn) {
        log.warn(warn);
    }
    
    /**
     * ��¼���ش���
     * @param fatal ���ش�������
     */    
    public static void setFatal(java.lang.String fatal) {
        log.fatal(fatal);
    }
}
