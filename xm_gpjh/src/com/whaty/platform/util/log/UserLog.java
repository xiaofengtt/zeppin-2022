package com.whaty.platform.util.log;

import java.util.Date;

import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;


/**
 * @author chenjian
 * עʾݿû־¼ĸʽ
 * <whaty>userid$|$chenjian</whaty>ʶû־¼еһֶ"userid"
 * Ŀǰ־ĿֶΪ:
 *  USERID       VARCHAR2(20),  ûid 
 *  BEHAVIOR     VARCHAR2(200),	Ϊ
 *  STATUS       VARCHAR2(10),	״̬
 *  NOTES       VARCHAR2(2000),	˵
 *  LOGTYPE      VARCHAR2(100),	־
 *  PRIORITY     VARCHAR2(10)	־ȼ
 *  һȫ־¼Ϊ
 *  <whaty>USERID$|$fderere</whaty><whaty>BEHAVIOR$|$fderere</whaty><whaty>STATUS$|$fderere</whaty><whaty>NOTES$|$fderere</whaty><whaty>LOGTYPE$|$fderere</whaty><whaty>PRIORITY$|$fderere</whaty>
 *  
 */
public class UserLog {
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("UserLog"); 
	 
	    /** Creates a new instance of Log */
	    public UserLog() {
	    }
	    
	    /**
	     * ¼
	     * @param error 
	     */    
	    public static void setError(java.lang.String error) {
	        log.error(error);
	    }
	    
	    /**
	     * ¼Ϣ
	     * @param debug Ϣ
	     */    
	    public static void setDebug(java.lang.String debug) {
	        log.debug(debug);
	    }
	    
	    /**
	     * ¼Ϣ
	     * @param info Ϣ
	     */    
	    public static void setInfo(java.lang.String info) {
	        log.info(info);
	    }
	    
	    /**
	     * ¼
	     * @param warn 
	     */    
	    public static void setWarn(java.lang.String warn) {
	        log.warn(warn);
	    }
	    
	    /**
	     * ¼ش
	     * @param fatal ش
	     */    
	    public static void setFatal(java.lang.String fatal) {
	        log.fatal(fatal);
	    }
	    
	    /**
	     * ¼
	     * @param error 
	     */    
	    public static void setError(java.lang.String error,Date date) {
	    	StrManage strManage=StrManageFactory.creat();
	    	String dateStr="";
			try {
				dateStr = "<whaty>OPERATE_TIME$|$"+strManage.dateToStr(date, "yyyy-MM-dd HH:mm:ss")+"</whaty>";
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				
			}
	        log.error(error+dateStr);
	    }
	    
	    /**
	     * ¼Ϣ
	     * @param debug Ϣ
	     */    
	    public static void setDebug(java.lang.String debug,Date date) {
	    	StrManage strManage=StrManageFactory.creat();
	    	String dateStr="";
	    	try {
				dateStr="<whaty>OPERATE_TIME$|$"+strManage.dateToStr(date, "yyyy-MM-dd HH:mm:ss")+"</whaty>";
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				
			}
	        log.debug(debug+dateStr);
	    }
	    
	    /**
	     * ¼Ϣ
	     * @param info Ϣ
	     */    
	    public static void setInfo(java.lang.String info,Date date) {
	    	StrManage strManage=StrManageFactory.creat();
	    	String dateStr="";
			try {
				dateStr = "<whaty>OPERATE_TIME$|$"+strManage.dateToStr(date, "yyyy-MM-dd HH:mm:ss")+"</whaty>";
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				
			}
	        log.info(info+dateStr);
	    }
	    
	    /**
	     * ¼
	     * @param warn 
	     */    
	    public static void setWarn(java.lang.String warn,Date date) {
	    	StrManage strManage=StrManageFactory.creat();
	    	String dateStr="";
			try {
				dateStr = "<whaty>OPERATE_TIME$|$"+strManage.dateToStr(date, "yyyy-MM-dd HH:mm:ss")+"</whaty>";
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				
			}
	        log.warn(warn+dateStr);
	    }
	    
	    /**
	     * ¼ش
	     * @param fatal ش
	     */    
	    public static void setFatal(java.lang.String fatal,Date date) {
	    	StrManage strManage=StrManageFactory.creat();
	    	String dateStr="";
	    	try {
				dateStr="<whaty>OPERATE_TIME$|$"+strManage.dateToStr(date, "yyyy-MM-dd HH:mm:ss")+"</whaty>";
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				
			}
	        log.fatal(fatal+dateStr);
	    }
	    
	    
}
