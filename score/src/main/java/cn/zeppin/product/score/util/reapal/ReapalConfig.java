package cn.zeppin.product.score.util.reapal;

import cn.zeppin.product.score.util.Utlity;

/* *
 *功能：设置帐户有关信息及返回路径（基础配置页面）
 *版本：3.1.3
 *日期：2015-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究融宝支付接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.访问融宝支付商户后台，然后用您的签约融宝支付账号登陆(注册邮箱号).
 *2.点击导航栏中的“商家服务”，即可查看

 * */

public class ReapalConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	protected static String privateKey = "/www/webapps/ntb/security/rongbao-user.pfx";// 私钥//生产
//	protected static String privateKey = "/Users/zeppin/security/rongbao-user.pfx";// 私钥//生产//mac
//	protected static String privateKey = "D:\\cert\\rongbao-user.pfx";// 私钥//生产
//	protected static String privateKey = "D:\\cert\\itrus001.pfx";// 私钥
	protected static String password = "niutoulicai";// 密码//生产
//	protected static String password = "123456";// 密码
	
	//ga021c79aba8260438f9e07dd1g83b7eac7e917e473gb36679b33c1a1f1fg2a4
	protected static String key = "ga021c79aba8260438f9e07dd1g83b7eac7e917e473gb36679b33c1a1f1fg2a4";//生产
//	protected static String key = "g0be2385657fa355af68b74e9913a1320af82gb7ae5f580g79bffd04a402ba8f";// 用户key
	
	//100000001302509
	protected static String merchant_id = "100000001302509";// 商户ID//生产
//	protected static String merchant_id = "100000000000147";// 商户ID
	
	// /www/webapps/ntb/security/rongbao.cer
	protected static String pubKeyUrl = "/www/webapps/ntb/security/rongbao.cer";// 公钥//生产
//	protected static String pubKeyUrl = "/Users/zeppin/security/rongbao.cer";// 公钥//生产//mac
//	protected static String pubKeyUrl = "D:\\cert\\rongbao.cer";// 公钥//生产
//	protected static String pubKeyUrl = "D:\\cert\\itrus001.cer";// 公钥
	
	protected static final String url = "https://api.reapal.com";//生产
//	protected static final String url = "http://testapi.reapal.com";
	
	public static final String notify_url_pay = Utlity.PATH_URL+"/web/notice/reapalWithdrawNotice";//异步通知地址--代付
	public static final String notify_url_recharge = Utlity.PATH_URL+"/web/notice/reapalRechargeNotice";//异步通知地址--快捷
	public static final String notify_url_qcb_recharge = Utlity.PATH_URL+"/web/notice/reapalQcbRechargeNotice";//异步通知地址--快捷
	public static final String notify_url_shbx_recharge = Utlity.PATH_URL+"/web/notice/reapalShbxRechargeNotice";//异步通知地址--快捷
//	public static final String seller_email = "rongjingfeng@zeppin.cn";//生产
	public static final String seller_email = "rongjingfeng@zeppin.cn";
	// 访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
	public static String transport = "http";
	// 接口版本号
	public static String version = "3.1.3";
	// 字符编码格式 目前支持 utf-8
	public static String charset = "utf-8";
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	
	public final static String REAPAL_SUCCESS = "0000";
	public final static String REAPAL_UNCHECK = "4035";
	public final static String REAPAL_CHECKED = "4036";

}
