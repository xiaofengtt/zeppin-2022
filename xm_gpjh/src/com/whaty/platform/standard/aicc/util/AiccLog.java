package com.whaty.platform.standard.aicc.util;


public class AiccLog {
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("AiccLog"); 
    
    /** Creates a new instance of Log */
    public AiccLog() {
    }
    
    /**
     * 记录错误
     * @param error 错误描述
     */    
    public static void setError(java.lang.String error) {
        log.error(error);
    }
    
    /**
     * 记录调试信息
     * @param debug 调试信息
     */    
    public static void setDebug(java.lang.String debug) {
        log.debug(debug);
    }
    
    /**
     * 记录信息
     * @param info 信息描述
     */    
    public static void setInfo(java.lang.String info) {
        log.info(info);
    }
    
    /**
     * 记录警告
     * @param warn 警告描述
     */    
    public static void setWarn(java.lang.String warn) {
        log.warn(warn);
    }
    
    /**
     * 记录严重错误
     * @param fatal 严重错误描述
     */    
    public static void setFatal(java.lang.String fatal) {
        log.fatal(fatal);
    }
    
}
