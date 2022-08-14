package cn.zeppin.utility.wx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.zeppin.entity.weixin.Button;
import cn.zeppin.entity.weixin.Menu;
import cn.zeppin.entity.weixin.ViewButton;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.WeiXinUserList;

public class ConfigUtil {
	/**
	 * 服务号相关信息
	 */
	// 测试
	// public final static String APPID = "wx2a3df3ca05c5be3f";// 服务号的应用号
	// public final static String APP_SECRECT =
	// "786209952a01e220864ea6cf8f523baf";// 服务号的应用密码
	// // 正式
	public final static String APPID = "wxb5465b3377c55c8e";// 服务号的应用号
	public final static String APP_SECRECT = "63355942ab04136014f33dab97aae12d";// 服务号的应用密码
	public final static String TOKEN = "xjems";// 服务号的配置token
	public final static String MCH_ID = "";// 商户号
	public final static String API_KEY = "";// API密钥
	public final static String SIGN_TYPE = "MD5";// 签名加密方式
	public final static String CERT_PATH = "D:/apiclient_cert.p12";// 微信支付证书存放路径地址
	// 微信支付统一接口的回调action
	public final static String NOTIFY_URL = "http://14.117.25.80:8016/wxweb/config/pay!paySuccess.action";
	// 微信支付成功支付后跳转的地址
	public final static String SUCCESS_URL = "http://14.117.25.80:8016/wxweb/contents/config/pay_success.jsp";
	// oauth2授权时回调action
	public final static String REDIRECT_URI = "http://14.117.25.80:8016/GoMyTrip/a.jsp?port=8016";
	/**
	 * 微信基础接口地址
	 */
	// 获取token接口(GET)
	public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// oauth2授权接口(GET)
	public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public final static String OAUTH2_CONNECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	// 刷新access_token接口（GET）
	public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	// 菜单创建接口（POST）
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 菜单查询（GET）
	public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除（GET）
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * 微信支付接口地址
	 */
	// 微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	// 对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	// 短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	// 接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";

	// 获取关注用户列表
	public final static String USERLIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	// 发送消息
	public final static String MESSAGE_TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 微信群发
	 */
	// 上传多媒体
	public final static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN";
	// 上传图片
	public final static String UPLOADIMG_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	// 上传图文消息素材
	public final static String UPLOADNEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	// 群发接口(根据标签进行群发)
	public final static String SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	// 群发接口(根据OpenID列表群发)
	public final static String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	// 群发预览
	public final static String PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";

	// 模板id
	// 教师注册信息管理员审核通知
	public final static String TEMPLATE_CHECK_ID = "jljJqhuSWmk-4WeD9Do-EfKDm33FwcYKIqGtkrG9ZFM";
	// 教师申请监考成功通知
	public final static String TEMPLATE_APPLY_ID = "JAEltTFBV-1cRii1ui_bBJgBlEu6ULuZv5DmdBXpXUg";
	// 教师考试管理员分配通知
	public final static String TEMPLATE_AGREE_ID = "zP2wLsv6nXxPDxRPn-w9b8NxbNFwqp-hFAZRqxETLA8";
	// 教师未录用结果通知
	public final static String TEMPLATE_DECLINED_ID = "RRjWmy4UqEMUaQD7uAkgY9TOBJRSvqunMnRhzsw7m3o";
	// 二次确认考场安排通知通知
	public final static String TEMPLATE_ARRANGE_ID = "dJjFHDFAX0TD6XGLSW8pHD-D5nd6Cd9MmbcFFRaP024";

	// 测试
	// //教师注册信息管理员审核通知
	// public final static String TEMPLATE_CHECK_ID =
	// "pz18HwqeVKe-Ue6dPqdesiwUFXYnz4twEwV8ESVrRoI";
	// // 教师申请监考成功通知
	// public final static String TEMPLATE_APPLY_ID =
	// "aTQTX8X_lf8EsAl9mOjSHdr0x3a_4azdwz2repKViQE";
	// // 教师考试管理员分配通知
	// public final static String TEMPLATE_AGREE_ID =
	// "vhT0Z5WavvcfU8-KsvkTjBI-kh8Tmt0xUSHsPnzPm9E";
	// // 教师未录用结果通知
	// public final static String TEMPLATE_DECLINED_ID =
	// "90nWIih6yREU0e3Ps8sXxcSz3qc2tMJDK40DYsNGgTU";
	// // 二次确认考场安排通知通知
	// public final static String TEMPLATE_ARRANGE_ID =
	// "vA24lY4foOH9gZ9GQCoj_LN47XDAq_3EHXQHwjD4ARc";

	// 获取永久素材
	public final static String MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

	/**
	 * 菜单的URL一定要是全路径，可以再微信公众号菜单接口那里直接配置
	 * 
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		ViewButton btnOne = new ViewButton();
		btnOne.setName("监考申报");
		btnOne.setType("view");
		btnOne.setUrl("../XJ_wechat/tip.html");

		ViewButton btnTwo = new ViewButton();
		btnTwo.setName("历史监考");
		btnTwo.setType("view");
		btnTwo.setUrl("../XJ_wechat/history.html");

		ViewButton btnThree = new ViewButton();
		btnThree.setName("我的资料");
		btnThree.setType("view");
		btnThree.setUrl("../XJ_wechat/myMessage.html");

		menu.setButton(new Button[] { btnOne, btnTwo, btnThree });
		return menu;
	}

	public static int createMenu(String token, String menu) throws ParseException, IOException {
		int result = 0;
		String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", menu);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}

	public static int messageSend(String token, String message) {
		int result = 0;
		String url = MESSAGE_TEMPLATE_SEND.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", message);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}

	public static int sendTemplate(String token, String data) {
		int result = 0;
		String url = MESSAGE_TEMPLATE_SEND.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", data);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			result = jsonObject.getIntValue("errcode");
		}
		System.out.println(jsonObject.toString());
		return result;
	}

	public static String upload(String token, String menu, String type) throws ParseException, IOException {
		String result = "";
		// h4Y8Q2FgMng6yPcO7lS22mV3zX0iKiqT5E1u8DFL_3Pz6CdlS_KDS86R9J7IZnhe
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", token);
		url += "&type=" + type;
		String httpsRequest = CommonUtil.sendFile(url, menu);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			System.out.println(jsonObject);
			result = jsonObject.getString("media_id");
		}
		return result;
	}

	public static String uploadnews(String token, String menu) throws ParseException, IOException {
		String result = "";
		String url = UPLOADNEWS_URL.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", menu);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			System.out.println(jsonObject);
			result = jsonObject.getString("media_id");
		}
		return result;
	}

	public static int sendAll(String token, String menu) throws ParseException, IOException {
		int result = 0;
		// h4Y8Q2FgMng6yPcO7lS22mV3zX0iKiqT5E1u8DFL_3Pz6CdlS_KDS86R9J7IZnhe
		String url = SENDALL_URL.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", menu);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			System.out.println(jsonObject);
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}

	public static int preview(String token, String menu) throws ParseException, IOException {
		int result = 0;
		// h4Y8Q2FgMng6yPcO7lS22mV3zX0iKiqT5E1u8DFL_3Pz6CdlS_KDS86R9J7IZnhe
		String url = PREVIEW_URL.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", menu);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			System.out.println(jsonObject);
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}

	public static int send(String token, String menu) throws ParseException, IOException {
		int result = 0;
		// h4Y8Q2FgMng6yPcO7lS22mV3zX0iKiqT5E1u8DFL_3Pz6CdlS_KDS86R9J7IZnhe
		String url = SEND_URL.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", menu);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			System.out.println(jsonObject);
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}

	/**
	 * 获取素材列表
	 * 
	 * @param token
	 * @return
	 */
	public static String getMaterialList(String token, String params) {
		String url = MATERIAL.replace("ACCESS_TOKEN", token);
		return CommonUtil.httpsRequest(url, "POST", params);
	}

	public static String getCodeUrl(String url) throws UnsupportedEncodingException {
		url = URLEncoder.encode(url, "UTF-8");
		String CodeUrl = OAUTH2_CONNECT_URL.replace("APPID", APPID).replace("REDIRECT_URI", url)
				.replace("SCOPE", "snsapi_base").replace("STATE", "xj");
		System.out.println(CodeUrl);
		return CodeUrl;
	}

	// 以Code换取用户的openid、access_token
	/// <summary>根据Code获取用户的openid、access_token</summary>
	public static String getOauthAccessOpenId(String code) {
		String openId = "";
		String url = OAUTH2_URL.replace("APPID", APPID).replace("SECRET", APP_SECRECT).replace("CODE", code);
		String httpsRequest = CommonUtil.httpsRequest(url, "GET", null);
		System.out.println("拿到的url是：" + url);
		System.out.println("获取到的gethtml是" + httpsRequest);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			openId = jsonObject.getString("openid");
		}
		System.out.println("openId: " + openId);
		return openId;
	}

	public static List<String> findWeiXinUserList(List<String> openidList, String accessToken, String nextOpenid) {
		WeiXinUserList weixinUserList = null;
		String url = USERLIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenid);
		String result = CommonUtil.httpsRequest(url, "GET", null);
		if (result != null) {
			System.out.println("获取用户列表：" + result);
			try {
				weixinUserList = JSONUtils.json2obj(result, WeiXinUserList.class);
				if (weixinUserList.getTotal() > 0) {
					openidList.addAll(weixinUserList.getData().getOpenid());
					if (weixinUserList.getCount() >= 10000) {
						// 如果大于10000的,继续查询
						findWeiXinUserList(openidList, accessToken, weixinUserList.getNext_openid());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				openidList = null;
			}
		}
		return openidList;
	}

	public static void main(String[] args) throws ParseException, IOException {
//		ConfigUtil.findWeiXinUserList(new ArrayList<>(), CommonUtil.getAccessToken().getAccessToken(), null);
//		String token = CommonUtil.getAccessToken().getAccessToken();
//		System.out.println(token);
//		String url = UPLOAD_URL.replace("ACCESS_TOKEN", null);
//		System.out.println(url);
		System.out.println(URLEncoder.encode("http://ks.xsdkszx.cn/XJ_wechat/myMessage.html"));
	}
}
