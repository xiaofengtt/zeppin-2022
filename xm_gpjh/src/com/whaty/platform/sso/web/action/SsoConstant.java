package com.whaty.platform.sso.web.action;
/**
 * 定义sso模块中使用的常量
 * @author lwx 2008-7-28
 * @email  <p>liweixin@whaty.com</p>
 */
public class SsoConstant {
//	public static final String SSO_STUDENT = "student";
//	public static final String SSO_MANAGER = "manager";
//	public static final String SSO_SITEMANAGER = "sitemanager";
//	public static final String SSO_TEACHER = "teacher";
	public static final String SSO_STUDENT = "0";
	public static final String SSO_MANAGER = "3";
//	public static final String SSO_SITEMANAGER = "2";
	public static final String SSO_VALUEEXPERT = "2";
//	public static final String SSO_TEACHER = "1";
	
	public static final String SSO_USER_SESSION_KEY = "user_session";
	public static final String SSO_USER_SESSION_KEY_BAK = "user_session_bak";
	
	public static final String SSO_PRO_GROUP_ADDUSER = "add";
	public static final String SSO_PRO_GROUP_DELUSER = "del";
	
	public static final String SSO_DEFAULT_MANAGER_ROLE_NAME = "默认管理员";
	public static final String SSO_DEFAULT_SITEMANAGER_ROLE_NAME = "分站管理员";
	public static final String SSO_DEFAULT_TEACHER_ROLE_NAME = "教师";
	public static final String SSO_DEFAULT_STUDENT_ROLE_NAME = "学生";
	
	public static final String ENUM_CONST_FLAG_IS_VALID = "FlagIsvalid";
	public static final String ENUM_CONST_FLAG_USER_TYPE = "UserType";
	
	public static final String SSO_SITEMANAGER_GROUP_SIR = "0"; //分站站长	
	public static final String SSO_SITEMANAGER_GROUP_PERSON = "1";//分站管理员
	
	public static final String INTERCEPTOR_EXCLUDEMETHOD = "gridjs";
	
	public static final String SSO_USER_IS_VALID = "1";//用户有效
	public static final String SSO_USER_IS_UNVALID = "0";//用户无效
	
	
	//平台操作权限
	public static final String PLATFROM_OPERATOR_PRIORITY_NAME = "平台操作";
	
	public static final String PLATFORM_VOTE_ADD_VOTE_PAPER = "addVotePaper";
	public static final String PLATFORM_VOTE_GET_VOTE_PAPER = "getVotePaper";
	
	/***************************************************************************
	 * 新闻管理权限名称 *
	 **************************************************************************/
	public static final String PLATFORM_INOFNEWS_GET_NEWS = "getNews"; // 查看新闻
	public static final String PLATFORM_INFONEWS_ADD_NEWS = "addNews";  // 添加新闻
	public static final String PLATFORM_INFONEWS_UPDATE_NEWS = "updateNews"; // 修改新闻
	public static final String PLATFORM_INFONEWS_DELETE_NEWS = "deleteNews"; // 删除新闻
	public static final String PLATFORM_INFONEWS_TOP_NEWS = "topNews"; // 置顶新闻
	public static final String PLATFORM_INFONEWS_POP_NEWS = "popNews"; // 弹出新闻
	public static final String PLATFORM_INFONEWS_LOCK_NEWS ="lockNews"; // 锁定新闻
	public static final String PLATFORM_INFONEWS_CONFIRM_NEWS =  "confirmNews"; // 审核新闻
	public static final String PLATFORM_INFONEWS_COPY_NEWS =  "copyNews"; // 拷贝新闻
	public static final String PLATFORM_INFONEWS_GET_NEWSTYPE = "getNewsType"; // 查看新闻类型
	
	/***************************************************************************
	 * 短信管理权限 *
	 **************************************************************************/
	public static final String PLATFORM_SMS_SEND_SMS =     "sendSms";                                                 
	public static final String PLATFORM_SMS_GET_SMS =      "getSms";                                                   
	public static final String PLATFORM_SMS_UPDATE_SMS =   "updateSms" ;                                                
	public static final String PLATFORM_SMS_DELETE_SMS =   "deleteSms" ;                                               
	public static final String PLATFORM_SMS_CHECK_SMS =    "checkSms"  ;                                                
	public static final String PLATFORM_SMS_ADD_SMS =      "addSms"   ;                                                
	public static final String PLATFORM_SMS_BATCH_IMPORT_MOBILES = "batchImportMobiles" ;
	public static final String PLATFORM_SMS_MANAGE_SMS_SYSTEMPOINT = "manageSmsSystemPoint"  ;// 系统短信点管理         
	public static final String PLATFORM_SMS_GET_SMS_STATISTIC = "getSmsStatistic"  ;// 系统短信点管理         
	public static final String PLATFORM_SMS_REJECT_SMS = "rejectSms"  ;// 拒绝短信         
	
	
	/***************************************************************************
	 * 留言管理权限 *
	 **************************************************************************/
	public static final String PLATFROM_LEARVWORD_GET ="getLeaveWord";
	public static final String PLATFROM_LEARVWORD_TOTAL = "totalLeaveWord";
	
	
	
	/****************************************
	 * 进入公共论坛指定id 
	 ***************************************/
	public static final String ITERACTION_COMMON_FORUM_APPLICATIONID = "1"; //与版区id一致
	public static final String INTERACTION_COMMON_FORUM_APPLICATION_TYPE = "0";//定义类型为0
	public static final String INTERACTION_COMMON_FORUM_BOARDNAME = "公共论坛";
	
	public static final String INTERACTION_COURSE_FORUM_APPLICTION_TYPE = "0"; //定义课程论坛类型为0
	public static final String INTERACTION_PAPER_FORUM_APPLICTION_TYPE = "0"; //定义论文论坛类型为0
	
	public static final String INTERACTION_FORUM_MANAGE_PATH = "login.whaty?action=external";
	
	/*****************
	 * 数据库模型分析相关
	 ****************/
	
	public static final String DB_FOREIGN_KEY = "dbForeignKey";
	public static final String DB_ALTERNATE_KEY = "dbAlternateKey";
}
