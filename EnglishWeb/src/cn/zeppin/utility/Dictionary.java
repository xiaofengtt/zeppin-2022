/** 
 * Project Name:CETV_TEST 
 * File Name:Dictionary.java 
 * Package Name:cn.zeppin.utility 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.utility;

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

	public static final int USER_ROLE_SUPERADMIN = 1;
	public static final int USER_ROLE_MANAGER = 2;
	public static final int USER_ROLE_EDITOR = 3;
	public static final int USER_ROLE_EX_MANAGER = 4;
	public static final int USER_ROLE_EX_EDITOR = 5;

	public static final short USER_STATUS_CLOSED = 0;
	public static final short USER_STATUS_NOMAL = 1;
	public static final int PAGESIZE_DEFAULT = 20;

	public static final short KNOWLEDGE_STATUS_CLOSED = 0;
	public static final short KNOWLEDGE_STATUS_NOMAL = 1;

	public static final short GRADE_STATUS_CLOSED = 0;
	public static final short GRADE_STATUS_NOMAL = 1;

	public static final short SUBJECT_STATUS_CLOSED = 0;
	public static final short SUBJECT_STATUS_NOMAL = 1;

	public static final Integer UPLOAD_FILE_MAX_SIZE = 999999999;

	public static final Short ITEM_ANSWER_TYPESTANDARD = 1;
	public static final Short ITEM_ANSWER_TYPENOSTANDARD = 0;

	public static final Short ITEM_STATUS_RELEASE = 3;
	public static final Short ITEM_STATUS_DRAFT = 2;
	public static final Short ITEM_STATUS_AUDITING = 1;
	public static final Short ITEM_STATUS_STOP = 0;

	public static final Short RESOURCE_TYPE_IMAGE = 1;
	public static final Short RESOURCE_TYPE_VIDEO = 2;
	public static final Short RESOURCE_TYPE_AUDIO = 3;
	public static final Short RESOURCE_TYPE_DOCUMENT = 4;

	public static final Short PAPER_STATUS_RELEASE = 3;
	public static final Short PAPER_STATUS_DRAFT = 2;
	public static final Short PAPER_STATUS_AUDITING = 1;
	public static final Short PAPER_STATUS_STOP = 0;

	public static final Short CLIENT_TYPE_A = 1;
	public static final Short CLIENT_TYPE_B = 2;

	public static final String PAPER_TYPE_ALL = "0,1,2,3,4";
	public static final String PAPER_TYPE_ZT = "0,1";
	public static final String PAPER_TYPE_ZJ = "2,3,4";

	public static final Short ITEM_MODEL_TYPE_SINGLE = 1;
	public static final Short ITEM_MODEL_TYPE_PACK = 2;
	public static final Short ITEM_MODEL_TYPE_DETER = 3;

	public static final int SUBJECT_ID = 12;

}
