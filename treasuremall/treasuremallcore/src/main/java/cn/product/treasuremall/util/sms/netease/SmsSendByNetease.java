package cn.product.treasuremall.util.sms.netease;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.Consts;
import org.apache.http.client.HttpClient;

import com.alibaba.fastjson.JSONObject;

import cn.product.treasuremall.util.SendSmsNew;

/**
 * 
 * @author user
 *
 */
public class SmsSendByNetease {
    /**
     * 产品密钥ID，产品标识
     */
    private final static String SECRETID = "bfe1a3815c82cbf1de7854d25848782b";
    /**
     * 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露
     */
    private final static String SECRETKEY = "72c1aff6dbb8fd1eaf180a4d22bbde85";

    /**
     * 接口地址
     */
    private final static String API_URL = "https://sms.dun.163.com/v2/sendsms";
    
    /**
     * 业务ID
     */
    private final static String BUSINESSID_GN_CODE = "a33d860bb5a44633a224d5bb755a0ceb";//国内验证码
    private final static String BUSINESSID_GN_NOTICE = "2ce2b31c4802464a8d60a8ac8609de78";//国内通知
    private final static String BUSINESSID_GN_MARKET = "b3e7ae211d9a462c983f1563352eea70";//国内营销
    private final static String BUSINESSID_GJ_CODE = "a04645815be240b2b7e7cd2037ace6e9";//国际验证码
    private final static String BUSINESSID_GJ_NOTICE = "4d94227f98604006b174e38359a8cf4d";//国际通知
    
	public static final String TREASUREMALL_SH_TEMP = "12100";
	public static final String TREASUREMALL_SH_TEMP_DELIVERY = "12101";
	public static final String TREASUREMALL_SH_TEMP_WITHDRAW_FAIL = "12115";
	public static final String TREASUREMALL_SH_TEMP_RECHARGE = "12116";
	public static final String TREASUREMALL_SH_TEMP_WITHDRAW = "12117";
	public static final String TREASUREMALL_SH_TEMP_DAILY = "12118";
	public static final String TREASUREMALL_SH_TEMP_CERTIFICATE = "12119";
	public static final String TREASUREMALL_SH_TEMP_RECEIVE = "12120";
	public static final String TREASUREMALL_SH_TEMP_LOGIN = "12121";
	public static final String TREASUREMALL_SH_TEMP_WINNING = "12122";
	public static final String TREASUREMALL_SH_TEMP_RESETPWD = "12123";
	public static final String TREASUREMALL_SH_TEMP_REGISTERED = "12124";
	public static final String TREASUREMALL_SH_TEMP_REGISTER = "12125";
	public static final String TREASUREMALL_SH_TEMP_ACTIVITY = "12126";
	public static final String TREASUREMALL_SH_TEMP_GROUPWIN = "12313";
	
	public static final Map<String, String> businessidMap = new HashMap<String, String> () {
		private static final long serialVersionUID = 1L;

		{
			put(TREASUREMALL_SH_TEMP,BUSINESSID_GN_CODE);
			put(TREASUREMALL_SH_TEMP_DELIVERY,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_WITHDRAW_FAIL,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_RECHARGE,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_WITHDRAW,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_DAILY,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_CERTIFICATE,BUSINESSID_GN_CODE);
			put(TREASUREMALL_SH_TEMP_RECEIVE,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_LOGIN,BUSINESSID_GN_CODE);
			put(TREASUREMALL_SH_TEMP_WINNING,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_RESETPWD,BUSINESSID_GN_CODE);
			put(TREASUREMALL_SH_TEMP_REGISTERED,BUSINESSID_GN_NOTICE);
			put(TREASUREMALL_SH_TEMP_REGISTER,BUSINESSID_GN_CODE);
			put(TREASUREMALL_SH_TEMP_ACTIVITY,BUSINESSID_GN_MARKET);
			put(TREASUREMALL_SH_TEMP_GROUPWIN,BUSINESSID_GN_NOTICE);
		}
	};
	
	public static final Map<String, String> templateMap = new HashMap<String, String> () {
		private static final long serialVersionUID = 1L;

		{
			put(SendSmsNew.TREASUREMALL_SH_TEMP,TREASUREMALL_SH_TEMP);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_DELIVERY,TREASUREMALL_SH_TEMP_DELIVERY);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL,TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE,TREASUREMALL_SH_TEMP_RECHARGE);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW,TREASUREMALL_SH_TEMP_WITHDRAW);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_DAILY,TREASUREMALL_SH_TEMP_DAILY);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_CERTIFICATE,TREASUREMALL_SH_TEMP_CERTIFICATE);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_RECEIVE,TREASUREMALL_SH_TEMP_RECEIVE);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_LOGIN,TREASUREMALL_SH_TEMP_LOGIN);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_WINNING,TREASUREMALL_SH_TEMP_WINNING);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_RESETPWD,TREASUREMALL_SH_TEMP_RESETPWD);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_REGISTERED,TREASUREMALL_SH_TEMP_REGISTERED);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_REGISTER,TREASUREMALL_SH_TEMP_REGISTER);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_ACTIVITY,TREASUREMALL_SH_TEMP_ACTIVITY);
			put(SendSmsNew.TREASUREMALL_SH_TEMP_GROUPWIN,TREASUREMALL_SH_TEMP_GROUPWIN);
		}
	};
	
    /**
     * 实例化HttpClient，发送http请求使用，可根据需要自行调参
     */
    private static HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 10000, 2000, 2000);

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 此处用申请通过的模板id
        String templateId = TREASUREMALL_SH_TEMP;
        // 模板参数对应的json格式数据,例如模板为您的验证码为${p1},请于${p2}时间登陆到我们的服务器
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "123666");
//        jsonObject.put("p2", "20180816");
        String params = jsonObject.toJSONString();
        String mobile = "18612033494";
        Map<String, String> datas = buildParam(BUSINESSID_GN_CODE, mobile, templateId, params);
        String result = HttpClient4Utils.sendPost(httpClient, API_URL, datas, Consts.UTF_8);
        System.out.println("result = [" + result + "]");
    }

    public static String sendSms(String businessId, String mobile, String templateId, String params) throws Exception {
    	 Map<String, String> datas = buildParam(BUSINESSID_GN_CODE, mobile, templateId, params);
         String result = HttpClient4Utils.sendPost(httpClient, API_URL, datas, Consts.UTF_8);
         return result;
    }
    private static Map<String, String> buildParam(String businessId, String mobile, String templateId, String params) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("businessId", businessId);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("version", "v2");
        map.put("templateId", templateId);
        map.put("mobile", mobile);
        // 国际短信对应的国际编码(非国际短信接入请注释掉该行代码)
        // map.put("internationalCode", "对应的国家编码");
        map.put("paramType", "json");
        map.put("params", params);
        map.put("nonce", UUID.randomUUID().toString().replace("-", ""));
        map.put("secretId", SECRETID);
        String sign = SignatureUtils.genSignature(SECRETKEY, map);
        map.put("signature", sign);
        return map;
    }
}
