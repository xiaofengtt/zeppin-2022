package com.makati.common.exception;

public enum ErrorResultEnums {
	/** 从1000 - 8999 之间的数据作为错误返回码*/
	MENU_ERROR(1000,"生成菜单节点异常"),
	PHONE_REGEX_ERROR(1001,"手机号格式错误"),
    CHARGE_ERROR(1002,"充值异常"),
	USER_NAME_FORMART(1007,"昵称格式,不正确"),
	KINGKONG_BET_ERROR(2001,"请求投注记录接口异常"),
	KINGKONG_GAME_URL_ERROR(2002,"请求进入游戏链接接口异常"),
	KINGKONG_SAFE_ERROR(2003,"请求保险箱转账接口异常"),
	KINGKONG_SAFE_IF_ERROR(2003,"保险箱余额不足"),
	WITHDRAW_NOT_RANGE(2004,"金额不在提现范围内"),
	REPART_PWD_ERROR(2005,"重复密码输入错误"),
	WX_SPREAD_CODE_ERROR(2006,"微信分享码不正确"),
	WX_FRIEND_HAS_INVITED(2007,"该手机号已被他人使用"),
	WX_NOT_BIND(2008,"请先绑定微信"),
	WX_NOT_SUBSCRIBE(2009,"请先关注微信公众号"),
	WX_NOT_OUTSHAREBALANCE(2010,"微信分享余额不足"),
	WX_EXCEED(2010,"超过奖金总额"),
	WX_HAS_DIND(2011,"微信已被其他账号绑定，请绑定其他微信"),
	WX_HAS_EXPIRE(2012,"公众号已过期，请联系客服解绑"),
	DAILI_EXCEED(3010,"设置比例超过上级配置,或上级未设置比例"),
	WANGWANGDAILI_ADD(3011,"只能添加1条配置"),
	ILLAGE_REGISTER(2013,"非法注册"),
	CODE_ERROR(2014,"验证码错误"),
	GAME_SERVER_GAME_WEIHU(4001,"游戏维护中,请稍后再试"),
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
