package cn.zeppin.product.utility.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zeppin.product.ntb.core.entity.weixin.WechatUserInfo;
import cn.zeppin.product.ntb.core.entity.weixin.WeiXinUserList;
import cn.zeppin.product.utility.JSONUtils;

public class ConfigUtil {
	/**
	 * 服务号相关信息
	 */
	// 正式
	public final static String APPID = "wx9de73d80c317b6c7";// 服务号的应用号
	public final static String APP_SECRECT = "1a4f88dfae1cfb69542d03d22a70c882";// 服务号的应用密码
	public final static String TOKEN = "ntb";// 服务号的配置token
	// 企财宝
	//测试
//	 public final static String QCB_APPID = "wx2a3df3ca05c5be3f";// 服务号的应用号
//	 public final static String QCB_APP_SECRECT = "786209952a01e220864ea6cf8f523baf";// 服务号的应用密码
	// 正式
	public final static String QCB_APPID = "wxae71d4aafb72f0aa";// 服务号的应用号
	public final static String QCB_APP_SECRECT = "a858d00be08fb81e3a2ec8b08a6b48f7";// 服务号的应用密码
	public final static String QCB_TOKEN = "qcb";// 服务号的配置token

	public final static String QCB_AUTH_APPID = "wxaa2f0493bea2784c";// 服务号的应用号
	public final static String QCB_AUTH_SECRECT = "af7740f408adccbbd69122edf6fb105c";// 服务号的应用密码

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
	// 通过openid获取用户信息
	public final static String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 发送消息
	public final static String MESSAGE_TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	// 获取标签下粉丝列表
	public final static String GET_TAG = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";

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

	public static class QcbTemplate {
		// 提现申请成功
		public final static String WITHDRAW_APPLY_ID = "fGcjp9FPRchBSMgpuIOzW2AKES4OQYASR3lcf-2AKUk";
		// 信用卡还款申请成功
		public final static String REPAYMENT_APPLY_ID = "ej2cvEDo2frhL1dhwaxiK2C60X0hMvlma46zJKmGDYg-2AKUk";
		// 福利金发放提醒
		public final static String EMPLOYEES_PAY_ID = "DWtIDBGVloBqBVcjKChKWIRi29RLOTo4KDCyL2fQ7lI";
		// 信用卡还款提醒
		public final static String CREDITCARD_REMIND_ID = "WPUBKrASiYEnGYqpCwdmxFbiLfYM6LggyN5uVAe7J6s";
		// 提现申请通知给管理员
		public final static String WITHDRAW_NOTICE_ID = "JMGxSZ_z4hwujuCnX9t-mNzO3rH7btkKB0Xl0bIKm24";
		// 融宝余额不足通知给管理员
		public final static String BALANCE_NOTICE_ID = "nQEjTx-t8T7l9iya97fsJJO0tow3I0ZEK6XSxGcC1J8";
		//企业认证待审核通知
		public final static String COMPANY_CER_ID = "hrH9t15d5pTGwGs8GK_xtyXbqt-92CIsHqal274UhBE";
		
		//测试
//		// 提现申请成功
//		public final static String WITHDRAW_APPLY_ID = "iVctZ9WXg7MaQmc2zvnvzKJNFQxRhYJexDcL-ecQ3xc";
//		// 信用卡还款申请成功
//		public final static String REPAYMENT_APPLY_ID = "RNOB_QXAgSHz1ap7KjVOF9sWyz7GkI4_Xdqfjr_VQPw";
//		// 福利金发放提醒
//		public final static String EMPLOYEES_PAY_ID = "SfTecY3sufOzPgRBVA6PZa1xIoX_F_Z-8Dyn8vJ-zGM";
//		// 信用卡还款提醒
//		public final static String CREDITCARD_REMIND_ID = "nw4jOvuwkp2geDbML8rTOceQopVOJvaLOY6US3Up5EM";
//		// 提现申请通知给管理员
//		public final static String WITHDRAW_NOTICE_ID = "R8ZCJ5VGR6WViMIhbhDnjf0hHo6IEw6gZ7jv059eZsc";
//		//企业认证待审核通知
//		public final static String COMPANY_CER_ID = "_i8ojEIQ5ksNjR7Zf2U5RxLX-u_AgZfmTW7-PM16zDQ";
		
	}

	// 获取永久素材
	public final static String MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

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

	/**
	 * 获取标签下粉丝列表
	 * 
	 * @param token
	 * @param data{
	 *            "tagid" : 134, "next_openid":""//第一个拉取的OPENID，不填默认从头开始拉取}
	 * @return
	 */
	public static String[] getTagOpenid(String token, String data) {
		String[] openid = null;
		String url = GET_TAG.replace("ACCESS_TOKEN", token);
		String httpsRequest = CommonUtil.httpsRequest(url, "POST", data);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			JSONObject dataJson = jsonObject.getJSONObject("data");
			if (dataJson != null) {
				JSONArray array = dataJson.getJSONArray("openid");
				openid = new String[array.size()];
				for (int i = 0; i < array.size(); i++) {
					openid[i] = array.getString(i);
				}
			}
		}
		return openid;
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

	public static String getCodeUrl(String url, String appId) throws UnsupportedEncodingException {
		url = URLEncoder.encode(url, "UTF-8");
		String CodeUrl = OAUTH2_CONNECT_URL.replace("APPID", appId).replace("REDIRECT_URI", url)
				.replace("SCOPE", "snsapi_base").replace("STATE", "xj");
		System.out.println(CodeUrl);
		return CodeUrl;
	}

	public static String getOauthAccessOpenId(String code, String appId, String app_secrect) {
		String openId = "";
		String url = OAUTH2_URL.replace("APPID", appId).replace("SECRET", app_secrect).replace("CODE", code);
		String httpsRequest = CommonUtil.httpsRequest(url, "GET", null);
//		System.out.println(httpsRequest);
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);
		if (jsonObject != null) {
			openId = jsonObject.getString("openid");
		}
		return openId;
	}

	public static List<String> findWeiXinUserList(List<String> openidList, String accessToken, String nextOpenid) {
		WeiXinUserList weixinUserList = null;
		String url = USERLIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenid);
		String result = CommonUtil.httpsRequest(url, "GET", null);
		if (result != null) {
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
	
	/**
	 *  获取微信用户信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static WechatUserInfo getUserInfo(String accessToken,String openid) {
		String url = USER_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		String httpsRequest = CommonUtil.httpsRequest(url, "GET", null);
//		System.out.println(httpsRequest);
		WechatUserInfo wechatUserInfo = null;
		if(httpsRequest != null){
			wechatUserInfo =  JSONUtils.json2obj(httpsRequest, WechatUserInfo.class);
		}
		return wechatUserInfo;
	}
}
