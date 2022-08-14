package com.makati.common.response;




import com.makati.common.util.lottery.ErrorCodeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误信息
 * @author Hello
 *
 */
@Deprecated
public class ErrorResponse  {

	private String errorCode;
	private String errorMsg;
	
	private ErrorResponse(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * 系统级错误
	 */
	private static final ErrorResponse SYSTEM_ERROR=new ErrorResponse(ErrorCodeUtil.SYSTEM_ERROR_CODE,"系统错误");
	private static final ErrorResponse ILEGAL_PARAM_ERROR=new ErrorResponse(ErrorCodeUtil.ILEGAL_PARAM_ERROR_CODE,"缺失必选参数 ");
	
	
	
	/**
	 * 服务的错误
	 */
	private static final ErrorResponse ILEGAL_SIGN_PARAM_ERROR=new ErrorResponse(ErrorCodeUtil.ILEGAL_SIGN_ERROR_CODE,"签名验证不合法");
	private static final ErrorResponse SESSION_INVALID_ERROR=new ErrorResponse(ErrorCodeUtil.SESSION_INVALID_ERROR_CODE,"登录失败");
	private static final ErrorResponse USER_EXIST_ERROR=new ErrorResponse(ErrorCodeUtil.USER_EXIST_ERROR_CODE,"用户已存在");
	private static final ErrorResponse USER_INFO_ERROR=new ErrorResponse(ErrorCodeUtil.USER_INFO_ERROR_CODE,"用户名或密码不正确");
	private static final ErrorResponse ILEGAL_PARAM_CODE=new ErrorResponse(ErrorCodeUtil.ILEGAL_PARAM_CODE,"参数不合法");
	private static final ErrorResponse USER_BALANCE_NOT_ENOUGH=new ErrorResponse(ErrorCodeUtil.USER_BALANCE_NOT_ENOUGH,"用户余额不足");
	private static final ErrorResponse USER_BANK_EXISTS=new ErrorResponse(ErrorCodeUtil.USER_BANK_EXISTS,"银行卡已存在");
	private static final ErrorResponse USER_BANK_ERROR_INFO=new ErrorResponse(ErrorCodeUtil.USER_BANK_ERROR_INFO,"提现密码未设置或者不正确");
	private static final ErrorResponse USER_BANK_PASSWD_EXISTS=new ErrorResponse(ErrorCodeUtil.USER_BANK_PASSWD_EXISTS,"密码已存在");
	private static final ErrorResponse USER_PHOTO_ERROR_SIZE=new ErrorResponse(ErrorCodeUtil.USER_PHOTO_ERROR_SIZE,"图片尺寸不合法");
	private static final ErrorResponse USER_PHOTO_ILEGAL=new ErrorResponse(ErrorCodeUtil.USER_PHOTO_ILEGAL,"上传有效图片");
	private static final ErrorResponse USER_ELEGAL_USER_ID=new ErrorResponse(ErrorCodeUtil.USER_ELEGAL_USER_ID,"用户名应为6-11位字母或数字组成");
	private static final ErrorResponse USER_ELEGAL_USER_PASSWD=new ErrorResponse(ErrorCodeUtil.USER_ELEGAL_USER_PASSWD,"密码应为6-16位字母或数字组成");
	private static final ErrorResponse USER_PASSWD_ERROR=new ErrorResponse(ErrorCodeUtil.USER_PASSWD_ERROR,"密码不正确");
	private static final ErrorResponse ILEGAL_NO_RETURN_ERROR=new ErrorResponse(ErrorCodeUtil.ILEGAL_NO_RETURN_ERROR,"此条件无返回值");
	private static final ErrorResponse USER_IS_INVALID=new ErrorResponse(ErrorCodeUtil.USER_IS_INVALID,"用户被冻结");
	private static final ErrorResponse ILEGAL_ERROR_CODE_NO=new ErrorResponse(ErrorCodeUtil.ILEGAL_ERROR_CODE_NO,"验证码错误");
	private static final ErrorResponse IPLIMIT_ERROR_CODE_NO=new ErrorResponse(ErrorCodeUtil.IPLIMIT_ERROR_CODE_NO,"IP限制登录");
	private static final ErrorResponse USER_REPEAT_SUBMIT=new ErrorResponse(ErrorCodeUtil.USER_REPEAT_SUBMIT,"用户重复提交");

	
	/**
	 * 购彩级别
	 */
	private static final ErrorResponse BET_ILEGAL_NUMBER=new ErrorResponse(ErrorCodeUtil.BET_ILEGAL_NUMBER,"投注号码不合法");
	private static final ErrorResponse BET_LOTTERY_QH_LOCKED=new ErrorResponse(ErrorCodeUtil.BET_LOTTERY_QH_LOCKED,"本期已封单");
	private static final ErrorResponse BET_LOTTERY_ERROR_COUNT=new ErrorResponse(ErrorCodeUtil.BET_LOTTERY_ERROR_COUNT,"注数不正确");
	private static final ErrorResponse BET_WF_REPAIRING=new ErrorResponse(ErrorCodeUtil.BET_WF_REPAIRING,"玩法不存在或维护中");
	private static final ErrorResponse BET_LOTTERY_TYPE_REPAIRING=new ErrorResponse(ErrorCodeUtil.BET_LOTTERY_TYPE_REPAIRING,"彩种维护中");
	private static final ErrorResponse BET_ERROR_BET_TIMES=new ErrorResponse(ErrorCodeUtil.BET_ERROR_BET_TIMES,"投注倍数过大");
	private static final ErrorResponse BET_ERROR_BET_WF_TYPE=new ErrorResponse(ErrorCodeUtil.BET_ERROR_BET_WF_TYPE,"玩法和彩种不符");
	private static final ErrorResponse BET_ERROR_NUMBER_MONEY=new ErrorResponse(ErrorCodeUtil.BET_ERROR_NUMBER_MONEY,"号码金额不符");
	private static final ErrorResponse BET_ERROR_PLFLAG=new ErrorResponse(ErrorCodeUtil.BET_ERROR_PLFLAG,"赔率标识不合法");
	private static final ErrorResponse BET_ERROR_LOTTERY_TYPE=new ErrorResponse(ErrorCodeUtil.BET_ERROR_LOTTERY_TYPE,"不支持彩种");
	private static final ErrorResponse BET_ERROR_QS_BS=new ErrorResponse(ErrorCodeUtil.BET_ERROR_QS_BS,"期数倍数不一致");
	private static final ErrorResponse BET_ERROR_INFO=new ErrorResponse(ErrorCodeUtil.BET_ERROR_INFO,"信息不一致");
	
	
	public static final Map<String,ErrorResponse> errorMap=new HashMap<String,ErrorResponse>();
	static{
		errorMap.put(ErrorCodeUtil.SYSTEM_ERROR_CODE, SYSTEM_ERROR);
		errorMap.put(ErrorCodeUtil.ILEGAL_PARAM_ERROR_CODE, ILEGAL_PARAM_ERROR);
		
		errorMap.put(ErrorCodeUtil.ILEGAL_SIGN_ERROR_CODE, ILEGAL_SIGN_PARAM_ERROR);
		errorMap.put(ErrorCodeUtil.SESSION_INVALID_ERROR_CODE, SESSION_INVALID_ERROR);
		errorMap.put(ErrorCodeUtil.USER_EXIST_ERROR_CODE, USER_EXIST_ERROR);
		errorMap.put(ErrorCodeUtil.USER_INFO_ERROR_CODE, USER_INFO_ERROR);
		errorMap.put(ErrorCodeUtil.ILEGAL_PARAM_CODE, ILEGAL_PARAM_CODE);
		errorMap.put(ErrorCodeUtil.USER_BALANCE_NOT_ENOUGH, USER_BALANCE_NOT_ENOUGH);
		errorMap.put(ErrorCodeUtil.USER_BANK_EXISTS, USER_BANK_EXISTS);
		errorMap.put(ErrorCodeUtil.USER_BANK_ERROR_INFO, USER_BANK_ERROR_INFO);
		errorMap.put(ErrorCodeUtil.USER_BANK_PASSWD_EXISTS, USER_BANK_PASSWD_EXISTS);
		errorMap.put(ErrorCodeUtil.USER_PHOTO_ERROR_SIZE, USER_PHOTO_ERROR_SIZE);
		errorMap.put(ErrorCodeUtil.USER_PHOTO_ILEGAL, USER_PHOTO_ILEGAL);
		errorMap.put(ErrorCodeUtil.USER_ELEGAL_USER_ID, USER_ELEGAL_USER_ID);
		errorMap.put(ErrorCodeUtil.USER_ELEGAL_USER_PASSWD, USER_ELEGAL_USER_PASSWD);
		errorMap.put(ErrorCodeUtil.USER_PASSWD_ERROR, USER_PASSWD_ERROR);
		errorMap.put(ErrorCodeUtil.ILEGAL_NO_RETURN_ERROR, ILEGAL_NO_RETURN_ERROR);
		errorMap.put(ErrorCodeUtil.USER_IS_INVALID, USER_IS_INVALID);
		errorMap.put(ErrorCodeUtil.ILEGAL_ERROR_CODE_NO, ILEGAL_ERROR_CODE_NO);
		errorMap.put(ErrorCodeUtil.IPLIMIT_ERROR_CODE_NO, IPLIMIT_ERROR_CODE_NO);
		errorMap.put(ErrorCodeUtil.USER_REPEAT_SUBMIT, USER_REPEAT_SUBMIT);
		
		
		errorMap.put(ErrorCodeUtil.BET_ILEGAL_NUMBER, BET_ILEGAL_NUMBER);
		errorMap.put(ErrorCodeUtil.BET_LOTTERY_QH_LOCKED, BET_LOTTERY_QH_LOCKED);
		errorMap.put(ErrorCodeUtil.BET_LOTTERY_ERROR_COUNT, BET_LOTTERY_ERROR_COUNT);
		errorMap.put(ErrorCodeUtil.BET_WF_REPAIRING, BET_WF_REPAIRING);
		errorMap.put(ErrorCodeUtil.BET_LOTTERY_TYPE_REPAIRING, BET_LOTTERY_TYPE_REPAIRING);
		errorMap.put(ErrorCodeUtil.BET_ERROR_BET_TIMES, BET_ERROR_BET_TIMES);
		errorMap.put(ErrorCodeUtil.BET_ERROR_BET_WF_TYPE, BET_ERROR_BET_WF_TYPE);
		errorMap.put(ErrorCodeUtil.BET_ERROR_NUMBER_MONEY, BET_ERROR_NUMBER_MONEY);
		errorMap.put(ErrorCodeUtil.BET_ERROR_PLFLAG, BET_ERROR_PLFLAG);
		errorMap.put(ErrorCodeUtil.BET_ERROR_LOTTERY_TYPE, BET_ERROR_LOTTERY_TYPE);
		errorMap.put(ErrorCodeUtil.BET_ERROR_QS_BS, BET_ERROR_QS_BS);
		errorMap.put(ErrorCodeUtil.BET_ERROR_INFO, BET_ERROR_INFO);
	}

	/**
	 * 构造错误的代码和信息实体类
	 * @param errorCode
	 * @param errorMsg
	 * @return
	 */
	public static ErrorResponse createErrorResponse(String errorCode, String errorMsg){
		ErrorResponse errorResponse=new ErrorResponse(errorCode,errorMsg);
		return errorResponse;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
