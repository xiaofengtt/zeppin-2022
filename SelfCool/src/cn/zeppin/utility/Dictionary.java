/** 
 * Project Name:Self_Cool 
 * File Name:Dictionary.java 
 * Package Name:cn.zeppin.utility 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.utility;

import java.util.Hashtable;

/**
 * ClassName: Dictionary <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月18日 下午1:05:37 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class Dictionary {

	public static final Short CLOSE = 0;
	public static final Short NORMAL = 1;
	public static final int USER_ROLE_SUPERADMIN = 1;
	public static final int USER_ROLE_MANAGER = 2;
	public static final int USER_ROLE_EDITOR = 3;
	public static final int USER_ROLE_EX_MANAGER = 4;
	public static final int USER_ROLE_EX_EDITOR = 5;

	public static final short USER_STATUS_CLOSED = 0;
	public static final short USER_STATUS_NOMAL = 1;
	public static final int PAGESIZE_DEFAULT = 20;
	public static final int DEFAULT_ITEM_NUMBER = 10;

	public static final short KNOWLEDGE_STATUS_CLOSED = 0;
	public static final short KNOWLEDGE_STATUS_NOMAL = 1;

	public static final short GRADE_STATUS_CLOSED = 0;
	public static final short GRADE_STATUS_NOMAL = 1;

	public static final short SUBJECT_STATUS_CLOSED = 0;
	public static final short SUBJECT_STATUS_NOMAL = 1;
	
	public static final short USER_SUBJECT_STATUS_CLOSED = 0;
	public static final short USER_SUBJECT_STATUS_NOMAL = 1;

	public static final int UPLOAD_FILE_MAX_SIZE = 999999999;

	public static final short ITEM_ANSWER_TYPESTANDARD = 1;
	public static final short ITEM_ANSWER_TYPENOSTANDARD = 0;

	public static final short ITEM_STATUS_RELEASE = 3;
	public static final short ITEM_STATUS_DRAFT = 2;
	public static final short ITEM_STATUS_AUDITING = 1;
	public static final short ITEM_STATUS_STOP = 0;

	public static final short RESOURCE_TYPE_IMAGE = 1;
	public static final short RESOURCE_TYPE_VIDEO = 2;
	public static final short RESOURCE_TYPE_AUDIO = 3;
	public static final short RESOURCE_TYPE_DOCUMENT = 4;

	public static final short PAPER_STATUS_RELEASE = 3;
	public static final short PAPER_STATUS_DRAFT = 2;
	public static final short PAPER_STATUS_AUDITING = 1;
	public static final short PAPER_STATUS_STOP = 0;
	
	public static final short PAPER_TYPE_REAL = 0;         //真题
	public static final short PAPER_TYPE_SIMULATION = 1;   //模拟试题
	public static final short PAPER_TYPE_AUTOCREATE = 2;   //自动智能组题
	public static final short PAPER_TYPE_PREDICTION = 3;   //预测题
	
	public static final short NOT_SSO_USER_WRONGBOOK_ITEM = 0;  //试题不在用户的错题本中
	public static final short SSO_USER_WRONGBOOK_ITEM = 1;      //试题在用户的错题本中
	
	public static final short SSO_USER_WRONGBOOK_ITEM_NOTTEST = 0;    //错题本中试题未做
	public static final short SSO_USER_WRONGBOOK_ITEM_TESTED = 1;     //错题本中试题已做
	
	public static final short SSO_USER_WRONGBOOK_ITEM_NOTMASTER = 0;  //错题本中试题设置为未掌握 
	public static final short SSO_USER_WRONGBOOK_ITEM_MASTERED = 1;     //错题本中试题设置为已掌握
	
	public static final short ITEM_MODEL_TYPE_SINGLE = 1;
	public static final short ITEM_MODEL_TYPE_PACK = 2;
	public static final short ITEM_MODEL_TYPE_DETER = 3;
	
	public static final short RETRIEVE_STATUS_CLOSED = 0;
	public static final short RETRIEVE_STATUS_NOMAL = 1;
	
	public static final short RETRIEVE_TYPE_STATUS_CLOSED = 0;
	public static final short RETRIEVE_TYPE_STATUS_NOMAL = 1;
	
	public static final short CATEGORY_STATUS_CLOSED = 0;
	public static final short CATEGORY_STATUS_NOMAL = 1;
	
	public static final short SUBJECT_COUNT_DOWN_STATUS_CLOSED = 0;
	public static final short SUBJECT_COUNT_DOWN_STATUS_NOMAL = 1;
	
	public static final short ACTIVITY_STATUS_CLOSED = 0;
	public static final short ACTIVITY_STATUS_NOMAL = 1;
	public static final short ACTIVITY_STATUS_PUBLISH = 2;
	
	public static final short ADVERT_STATUS_CLOSED = 0;
	public static final short ADVERT_STATUS_NOMAL = 1;
	
	public static final short ITEM_TYPE_STATUS_CLOSED = 0;
	public static final short ITEM_TYPE_STATUS_NOMAL = 1;
	
	public static final short AUTH_NORMAL = 1;
	public static final short AUTH_OTHER = 2;
	public static final short AUTH_REGISERT = 3;
	public static final short AUTH_CODELOGIN = 4;
	public static final short AUTH_ADDPHONE = 5;

	public static final short SSO_CLOSE = 0;
	public static final short SSO_NORMAL = 1;

	
	public static final short BUSINESS_STATUS_CLOSED = 0;
	public static final short BUSINESS_STATUS_NOMAL = 1;
	
	public static final short INFORMATION_STATUS_CLOSED = 0;
	public static final short INFORMATION_STATUS_NOMAL = 1;
	public static final short INFORMATION_STATUS_AUDITING = 2;
	public static final short INFORMATION_STATUS_RELEASE = 3;
	
	
	public static final short USER_TEST_STATUS_START = 1;
	public static final short USER_TEST_STATUS_GIVEUP = 2;
	public static final short USER_TEST_STATUS_COMPLETE = 3;

	public static final short ITEM_TYPE_MODEL_SINGLE = 1;
	public static final short ITEM_TYPE_MODEL_JUDGE = 3;
	public static final short ITEM_TYPE_MODEL_MULTI = 5;

	public static final short SSO_USER_TEST_ITEM_WRONG = 0;
	public static final short SSO_USER_TEST_ITEM_CORRECT = 1;
	public static final short SSO_USER_TEST_ITEM_CUSTOM = 2;
	public static final short USER_TEST_ITEM_ANSWERED = 1;
	public static final short USER_TEST_ITEM_NOANSWERE = 1;
	
	public static final short DEVICE_IOS = 1;
	public static final short DEVICE_ANDROID = 2;
	
	public static final short VERSION_TEST = 0;
	public static final short VERSION_ONLINE = 1;
	
	public static final short VERSION_UPDATE_UNFORCED = 0;
	public static final short VERSION_UPDATE_FORCED = 1;
	
	
	//用户做过题的标志 和没有做过题的标志;
	public static final short SSO_USER_TEST_ITEM_ANSWERED=1;
	public static final short SSO_USER_TEST_ITEM_NOANSWERED=0;
	
	
	public static final short MOBILECODE_STATUS_INVALID=0;//手机验证码失效
	public static final short MOBILECODE_STATUS_VALID=1;//手机验证码有效


	public static final Hashtable<Short, String> AUTH_KEYS = new Hashtable<Short, String>() {

		/**
		 *  
		 */
		private static final long serialVersionUID = -854873416398465742L;
		{
			this.put((short) 1, "qq");
			this.put((short) 2, "weixin");
			this.put((short) 3, "sina");
			this.put((short) 4, "renren");
		};
	};



}
