package com.makati.common.util.lottery;

public class ErrorCodeUtil {

	/**
	 * 系统级错误代码
	 */
	public static final String SYSTEM_ERROR_CODE="10001";     //系统级错误
	public static final String ILEGAL_PARAM_ERROR_CODE="10007"; //缺失必选参数
	
	
	
	/**
	 * 服务级别的错误
	 */
	public static final String USER_NOT_LOGIN="20010";  //用户未登录
	public static final String ILEGAL_SIGN_ERROR_CODE="20011";  //签名验证不合法
	public static final String SESSION_INVALID_ERROR_CODE="20012";  //会话失效
	public static final String USER_EXIST_ERROR_CODE="20013";  //用户已存在
	public static final String USER_INFO_ERROR_CODE="20014";  //用户或密码不正确
	public static final String ILEGAL_PARAM_CODE="20015";  //参数不合法
	public static final String USER_BALANCE_NOT_ENOUGH="20016";  //用户余额不足
	public static final String USER_BANK_EXISTS="20017";  //用户银行卡已存在
	public static final String USER_BANK_ERROR_INFO="20018";  //提现密码未设置或者不正确
	public static final String USER_BANK_PASSWD_EXISTS="20019";  //密码已存在
	public static final String USER_PHOTO_ERROR_SIZE="20021";  //图片尺寸不合法
	public static final String USER_PHOTO_ILEGAL="20022";  //上传有效图片
	public static final String USER_ELEGAL_USER_ID="20023";  //用户名应为6-11位字母或数字组合
	public static final String USER_ELEGAL_USER_PASSWD="20024";  //密码应为6-16位字母或数字
	public static final String USER_PASSWD_ERROR="20025";  //密码不正确
	public static final String ILEGAL_NO_RETURN_ERROR="20026";  //无返回值
	public static final String USER_IS_INVALID="20027";  //用户已冻结
	public static final String ILEGAL_ERROR_CODE_NO="20028";  //验证码错误
	public static final String SIGN_ERROR_CODE_NO="20029";  //签到活动错误
	public static final String ZLJ_ERROR_CODE_NO="20030";  //助力金活动错误
	public static final String KHJ_ERROR_CODE_NO="20031";  //开户金金活动错误
	public static final String FXPYQ_ERROR_CODE_NO="20032";  //分享活动金活动错误
	public static final String IPLIMIT_ERROR_CODE_NO="20033";  //登录ip黑名单
	public static final String APPPV_ERROR_CODE_NO="20034";  //包名版本号配置出错
	public static final String THIRDLOGIN_ERROR_CODE_NO="20035";  //第三方登录错误
	public static final String USER_AUTH_ERROR_CODE_NO="20036";  //用户无此权限
	public static final String USER_BANK_ERROR="20037";  //银行卡绑定
	public static final String USER_PWD_ERROR="20038";  //密码输入错误(前端接收到之后跳登录页)
	public static final String USER_OUT_MOMENY_STATUS_INFO = "20039"; //此用户有待审核的提现订单
	public static final String USER_CASH_STATUS_INFO = "20040"; //此用户提现异常！
	public static final String USER_HELPGOLD_STATUS_INFO ="20041";  // 此用户领取彩金异常！
	public static final String USER_PHONE_NUMBER_INFO ="20042";  // 用户手机号码不正确！
	public static final String USER_CHECK_CODE_INFO ="20043";  // 验证码错误！
	public static final String USER_PHONE_EXISTS ="20044";  // 手机号码已被绑定
	public static final String USER_REAL_NAME_EXISTS ="20045";  // 用户真实姓名已存在
	public static final String USER_AGENT_DOMAIN_NOT_EXISTS ="20046";  // 代理域名不存在
	public static final String USER_AGENT_INFO_EXISTS ="20047";  // 用户代理已存在
	public static final String USER_AGENT_CODE_NO_EXISTS ="20049";  // 用户代理不存在

	public static final String USER_REPEAT_SUBMIT ="20050";  // 用户重复提交
	public static final String SHORT_URL_FAIL ="20051";  // 生成短连接
	public static final String USER_LOTTERY_TYPE_EXUSTS ="20052";  // 用户已添加过此彩种
	public static final String USER_LOTTERY_TYPE_INFO ="20053";  // 删除用户彩种异常

	public static final String USER_REGISTER_REPEAT = "20060";// 用户重复注册新账号
	public static final String REPART_PWD_ERROR ="20061";  // 确认密码不正确

	
	
	/**
	 * 购彩级别
	 */
	public static final String BET_ILEGAL_NUMBER="20101";  //投注号码不合法
	public static final String BET_LOTTERY_QH_LOCKED="20102";  //此期号已经封单
	public static final String BET_LOTTERY_ERROR_COUNT="20103";  //注数不正确
	public static final String BET_WF_REPAIRING="20104";         //玩法不存在或者维护中
	public static final String BET_LOTTERY_TYPE_REPAIRING="20105";         //彩种维护中
	public static final String BET_ERROR_BET_TIMES="20106";  //投注倍数过大
	public static final String BET_ERROR_BET_WF_TYPE="20107";  //玩法和彩种不符
	public static final String BET_ERROR_NUMBER_MONEY="20108";  //号码金额不符
	public static final String BET_ERROR_PLFLAG="20109";  //赔率标识不合法
	public static final String BET_ERROR_LOTTERY_TYPE="20110";  //不支持彩种
	public static final String BET_ERROR_QS_BS="20111";  //期数倍数信息不一致


	public static final String BET_ERROR_INFO="20112";  //信息不一致

	public static final String BET_ERROR_MEMB_LEVEL_INFO="20113";  //用户没有会员优惠领取资格
	public static final String HYDJYH_ERROR_CODE_NO="20114";  //会员等级优惠活动错误
	public static final String HYDJ_ERROR_CODE_NO="20115";  //会员等级活动错误

	public static final String INSTALLAPP_ERROR_CODE_NO="20116";  //安装APP优惠活动错误

	public static final String AGENT_ERROR_CODE_NO="20117";  //代理错误

	public static final String BET_ERROR_AG="20118";  //AG平台错误


}
