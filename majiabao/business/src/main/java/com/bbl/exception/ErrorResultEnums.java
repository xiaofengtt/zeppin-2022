package com.bbl.exception;

public enum ErrorResultEnums {
	/** 从1000 - 8999 之间的数据作为错误返回码*/
	MENU_ERROR(1000,"生成菜单节点异常"),











    //=====================================
    /** 从9000 - 9999 之间的数据作为公共错误返回码*/
    INVOKE_SEWRVICE_ERROR(9000,"Game加载..."),
    USER_NOT_EXIST(9001,"用户不存在"),
	LOGIN_EXPIRE(9002,"用户登录失效"),
	PARAMS_ERROR(9003,"参数校验失败"),
	DATA_EXIXTS(9004,"数据不存在"),
	PHONE_HAS_REGISTER(9005,"手机号已注册"),
	DATA_EXIXTS_ERROR(9006,"缺失必传参数"),
	;


	private  Integer   code;
	private  String   msg;

	ErrorResultEnums(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
