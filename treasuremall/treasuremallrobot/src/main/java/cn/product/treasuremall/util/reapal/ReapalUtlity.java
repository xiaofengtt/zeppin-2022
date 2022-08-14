package cn.product.treasuremall.util.reapal;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import cn.product.treasuremall.util.HttpClientUtil;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.MD5;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.reapal.data.WithdrawDataArray;

/**
 * 融宝支付接口类
 * @author Administrator
 *
 */
public class ReapalUtlity {

	/**
	 * 余额查询
	 *
	 * @param model
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static String balance_Query() throws Exception {

		Map<String, String> map = new HashMap<String, String>(0);
		map.put("charset", ReapalUtil.getCharset());
		String mysign = ReapalUtil.BuildMysign(map, ReapalUtil.getKey());
		System.out.println("签名结果==========>" + mysign);
		map.put("sign", mysign);
		map.put("sign_type", ReapalUtil.getSign_type());

		String json = JSON.toJSONString(map);

		Map<String, String> maps = ReapalUtil.addkey(json);
		maps.put("merchant_id", ReapalUtil.getMerchant_id());
		maps.put("version", ReapalUtil.getVersion());
		String post = HttpClientUtil.post(ReapalUtil.url
				+ "agentpay/balancequery", maps);
		String res = ReapalUtil.pubkey(post);
//		model.addAttribute("msg", res);
		System.out.println(res);
		return "test/balancequery";
	}
	
	/**
	 * 余额查询
	 *
	 * @param model
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> get_balance_Query() throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>(0);
		map.put("charset", ReapalUtil.getCharset());
		String mysign = ReapalUtil.BuildMysign(map, ReapalUtil.getKey());
		System.out.println("签名结果==========>" + mysign);
		map.put("sign", mysign);
		map.put("sign_type", ReapalUtil.getSign_type());

		String json = JSON.toJSONString(map);

		Map<String, String> maps = ReapalUtil.addkey(json);
		maps.put("merchant_id", ReapalUtil.getMerchant_id());
		maps.put("version", ReapalUtil.getVersion());
		String post = HttpClientUtil.post(ReapalUtil.url
				+ "agentpay/balancequery", maps);
		String res = ReapalUtil.pubkey(post);
//		model.addAttribute("msg", res);
		System.out.println(res);
		result = JSONUtils.json2map(res);
		return result;
	}
	
	/**
	 * 批次提交
	 * 
	 * @param model
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static String paybatchSubmit()
			throws Exception {

		Map<String, String> map = new HashMap<String, String>(0);
		map.put("charset", ReapalUtil.getCharset());
		map.put("trans_time",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("notify_url", ReapalUtil.notify_url_pay);
		map.put("batch_no", new Timestamp(System.currentTimeMillis()).toString());
		map.put("batch_count", "2");
		map.put("batch_amount", "20");
		map.put("pay_type", "1");
		map.put("content", "1,6214830164014572,刘运涛,招商银行,,,私,10.00,CNY,北京,北京,18612033494,身份证,372330199210225111,,12306,hehe|2,6214830164014572,刘运涛,招商银行,,,私,10.00,CNY,北京,北京,18612033494,身份证,372330199210225111,,12307,hehe");
		
		String mysign = ReapalUtil.BuildMysign(map, ReapalUtil.getKey());
		
		System.out.println("签名结果==========>" + mysign);
		map.put("sign", mysign);
		map.put("sign_type", ReapalUtil.getSign_type());

		String json = JSON.toJSONString(map);

		Map<String, String> maps = ReapalUtil.addkey(json);
		maps.put("merchant_id", ReapalUtil.getMerchant_id());
		maps.put("version", ReapalUtil.getVersion());
		System.out.println("maps==========>" + com.alibaba.fastjson.JSON.toJSONString(maps));
		
		String post = HttpClientUtil.post(ReapalUtil.url
				+ "agentpay/pay", maps);
		String res = ReapalUtil.pubkey(post);
//		model.addAttribute("payBatchRequest", agentPayRequest);
//		model.addAttribute("msg", res);
		System.out.println(res);
		return "test/pay";
	}
	
	/**
	 * 批次提交
	 * 
	 * @param model
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> withdrawBatchSubmit(WithdrawDataArray wda)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>(0);
		map.put("charset", ReapalUtil.getCharset());
		map.put("trans_time", wda.getTrans_time());
		map.put("notify_url", ReapalUtil.notify_url_pay);
		map.put("batch_no", wda.getBatch_no());
		map.put("batch_count", wda.getCount()+"");
		map.put("batch_amount", wda.getPrice().toString());
		map.put("pay_type", ReapalUtil.pay_type_normal);
		map.put("content", wda.toString());
//		map.put("content", "1,6214830164014572,刘运涛,招商银行,,,私,10.00,CNY,北京,北京,18612033494,身份证,372330199210225111,,12306,hehe|2,6214830164014572,刘运涛,招商银行,,,私,10.00,CNY,北京,北京,18612033494,身份证,372330199210225111,,12307,hehe");
		
		String mysign = ReapalUtil.BuildMysign(map, ReapalUtil.getKey());
		
		System.out.println("签名结果==========>" + mysign);
		map.put("sign", mysign);
		map.put("sign_type", ReapalUtil.getSign_type());

		String json = JSON.toJSONString(map);

		Map<String, String> maps = ReapalUtil.addkey(json);
		maps.put("merchant_id", ReapalUtil.getMerchant_id());
		maps.put("version", ReapalUtil.getVersion());
		System.out.println("maps==========>" + com.alibaba.fastjson.JSON.toJSONString(maps));
		
		String post = HttpClientUtil.post(ReapalUtil.url
				+ "agentpay/pay", maps);
		String res = ReapalUtil.pubkey(post);
//		model.addAttribute("payBatchRequest", agentPayRequest);
//		model.addAttribute("msg", res);
		System.out.println(res);
		result = JSONUtils.json2map(res);
		return result;
	}
	
	// 银行卡查询
	public static void bankCardQuery() throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("card_no", "6214830164014572");
		// 请求接口
		String url = "/fast/bankcard/list";
		// 返回结果
		String post = ReapalSubmit.buildSubmit(map, url);

		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据
		String res = Decipher.decryptData(post);
		System.out.println("解密返回的数据==========>" + res);
	}
	
	// 银行卡查询
	public static Map<String, Object> bankCardQuery(String bankcard) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("card_no", bankcard);
		// 请求接口
		String url = "/fast/bankcard/list";
		// 返回结果
		String post = ReapalSubmit.buildSubmit(map, url);

		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据
		String res = Decipher.decryptData(post);
		System.out.println(res);
		result = JSONUtils.json2map(res);
		return result;
		
	}
	
	/**
	 * 单笔查询
	 * @throws Exception
	 */
	public static void paysingleQuery() throws Exception {

		Map<String, String> map = new HashMap<String, String>(0);
		map.put("charset", ReapalUtil.getCharset());
		map.put("trans_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		map.put("notify_url", ReapalUtil.notify_url_pay);
		map.put("batch_no", "252180105175713292");
		map.put("detail_no", "1");
		
		String mysign = ReapalUtil.BuildMysign(map, ReapalUtil.getKey());
		
		System.out.println("签名结果==========>" + mysign);
		map.put("sign", mysign);
		map.put("sign_type", ReapalUtil.getSign_type());

		String json = JSON.toJSONString(map);

		Map<String, String> maps = ReapalUtil.addkey(json);
		maps.put("merchant_id", ReapalUtil.getMerchant_id());
		maps.put("version", ReapalUtil.getVersion());
		String post = HttpClientUtil.post("https://agentpay.reapal.com/agentpay/singlepayquery", maps);
		String res = ReapalUtil.pubkey(post);
		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据
		System.out.println("解密返回的数据==========>" + res);
	}
	
	/**
	 * 单笔查询
	 * @param batchNo
	 * @param detailNo
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> paysingleQuery(String batchNo, String detailNo) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>(0);
		map.put("charset", ReapalUtil.getCharset());
		map.put("trans_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		map.put("notify_url", ReapalUtil.notify_url_pay);
		map.put("batch_no", "252180105175713292");
		map.put("detail_no", "1");
		
		String mysign = ReapalUtil.BuildMysign(map, ReapalUtil.getKey());
		
		System.out.println("签名结果==========>" + mysign);
		map.put("sign", mysign);
		map.put("sign_type", ReapalUtil.getSign_type());

		String json = JSON.toJSONString(map);

		Map<String, String> maps = ReapalUtil.addkey(json);
		maps.put("merchant_id", ReapalUtil.getMerchant_id());
		maps.put("version", ReapalUtil.getVersion());
		String post = HttpClientUtil.post(ReapalUtil.url+"/agentpay/singlepayquery", maps);
		String res = ReapalUtil.pubkey(post);
		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据
		System.out.println("解密返回的数据==========>" + res);
		result = JSONUtils.json2map(res);
		return result;
	}
	
	/**
	 * 储蓄卡签约
	 * @throws Exception
	 */
	public static void debit() throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("card_no", "6214830164014572");
		map.put("owner", "刘运涛");
		map.put("cert_type", "01"); // 默认
		map.put("cert_no", "372330199210225111");
		map.put("phone", "18612033494");
		map.put("order_no",
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("transtime",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("currency", "156"); // 默认
		map.put("title", "title");
		map.put("body", "body");
		map.put("member_id", UUID.randomUUID().toString());// 用户id
		map.put("terminal_type", "mobile");// 终端类型
		map.put("terminal_info", Utlity.getUUID()); // 终端标识
		map.put("notify_url", ReapalConfig.notify_url_recharge);
		map.put("member_ip", "127.0.0.1");// 用户IP
		map.put("seller_email", ReapalConfig.seller_email);
		// 金额
		map.put("total_fee", "2");
		map.put("token_id", Utlity.getUUID());
		// 请求后缀接口地址
		String url = "/fast/debit/portal";
		// 返回结果
		String post = ReapalSubmit.buildSubmit(map, url);

		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据
		String res = Decipher.decryptData(post);

		System.out.println("解密返回的数据==========>" + res);
	}
	
	/**
	 * 首次绑卡支付
	 * @param bankcard
	 * @param realName
	 * @param idcard
	 * @param mobile
	 * @param uuid
	 * @param orderNum
	 * @param userIp
	 * @param priceStr
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> debit(String bankcard, String realName, String idcard, String mobile, String uuid,
			String orderNum, String userIp, String priceStr, String notifyUrl) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		uuid = MD5.getMD5(uuid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("card_no", bankcard);
		map.put("owner", realName);
		map.put("cert_type", "01"); // 默认
		map.put("cert_no", idcard);
		map.put("phone", mobile);
		map.put("order_no", orderNum);
		map.put("time_expire", "15m");// 用户id
		map.put("transtime",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("currency", "156"); // 默认
		map.put("title", "用户充值");
		map.put("body", "用户充值");
		map.put("member_id", uuid);// 用户id
		map.put("terminal_type", "mobile");// 终端类型
		map.put("terminal_info", Utlity.getUUID()); // 终端标识
		map.put("notify_url", notifyUrl);
		map.put("member_ip", userIp);// 用户IP
		map.put("seller_email", ReapalConfig.seller_email);
		// 金额
		map.put("total_fee", Integer.parseInt(priceStr)+"");
		map.put("token_id", Utlity.getUUID());
		// 请求后缀接口地址
		String url = "/fast/debit/portal";
		// 返回结果
		String post = ReapalSubmit.buildSubmit(map, url);

		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据
		String res = Decipher.decryptData(post);

		System.out.println("解密返回的数据==========>" + res);
		result = JSONUtils.json2map(res);
		return result;
	}
	
	// 绑卡签约
	public static void bindCard() throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("member_id", "123124");
		map.put("bind_id", "1231312");
		map.put("order_no",
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("transtime",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("currency", "156"); // 默认
		map.put("title", "title");
		map.put("body", "body");
		map.put("member_id", "321345");// 用户id
		map.put("terminal_type", "mobile");// 终端类型
		map.put("terminal_info", Utlity.getUUID()); // 终端标识
		map.put("notify_url", ReapalConfig.notify_url_recharge);
		map.put("member_ip", "127.0.0.1");// 用户IP
		map.put("seller_email", ReapalConfig.seller_email);
		// 金额
		map.put("total_fee", "2");
		map.put("token_id", Utlity.getUUID());
		// 请求接口
		String url = "/fast/bindcard/portal";
		// 返回的数据
		String post = ReapalSubmit.buildSubmit(map, url);

		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据

		String res = Decipher.decryptData(post);

		System.out.println("解密返回的数据==========>" + res);
	}
	
	/**
	 * 已绑卡支付
	 * @param uuid
	 * @param bindingID
	 * @param orderNum
	 * @param userIp
	 * @param priceStr
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> bindCard(String uuid, String bindingID, String orderNum, 
			String userIp, String priceStr, String notifyUrl) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		uuid = MD5.getMD5(uuid);
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("member_id", uuid);
		map.put("bind_id", bindingID);
		map.put("order_no", orderNum);
		map.put("transtime",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("currency", "156"); // 默认
		map.put("title", "用户充值");
		map.put("body", "用户充值");
		map.put("time_expire", "15m");// 用户id
		map.put("terminal_type", "mobile");// 终端类型
		map.put("terminal_info", Utlity.getUUID()); // 终端标识
		map.put("notify_url", notifyUrl);
		map.put("member_ip", userIp);// 用户IP
		map.put("seller_email", ReapalConfig.seller_email);
		// 金额
		map.put("total_fee", Integer.parseInt(priceStr)+"");
		map.put("token_id", Utlity.getUUID());
		// 请求接口
		String url = "/fast/bindcard/portal";
		// 返回的数据
		String post = ReapalSubmit.buildSubmit(map, url);

		System.out.println("返回结果post==========>" + post);

		// 解密返回的数据

		String res = Decipher.decryptData(post);

		System.out.println("解密返回的数据==========>" + res);
		result = JSONUtils.json2map(res);
		return result;
	}
	
	//确认支付
	public static void confirmPay() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("order_no", "433425254252");
		map.put("check_code", "123343"); //6位
		//请求接口
		String url = "/fast/pay";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);
	    System.out.println("返回结果post==========>" + post);
	    
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    System.out.println("解密返回的数据==========>" + res);

	}
	
	/**
	 * 确认支付
	 * @param orderNum订单号
	 * @param code手机验证码
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> confirmPay(String orderNum, String code) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("order_no", orderNum);
		map.put("check_code", code); //6位
		//请求接口
		String url = "/fast/pay";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);
	    System.out.println("返回结果post==========>" + post);
	    
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    System.out.println("解密返回的数据==========>" + res);
	    result = JSONUtils.json2map(res);
		return result;
	}
	
	//查询订单
	public static void orderQuery() throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("order_no", "13213245646");
		//请求接口
		String url = "/fast/search";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);

	    System.out.println("返回结果post==========>" + post);
	    
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    System.out.println("解密返回的数据==========>" + res);
	}
	
	/**
	 * 查询订单
	 * @param orderNum订单号
	 * @throws Exception
	 */
	public static Map<String, Object> orderQuery(String orderNum) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("order_no", orderNum);
		//请求接口
		String url = "/fast/search";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);

	    System.out.println("返回结果post==========>" + post);
	    
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    System.out.println("解密返回的数据==========>" + res);
	    result = JSONUtils.json2map(res);
		return result;
	}
	
	//短信重发
	public static void reSms() throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		//订单号
		map.put("order_no", "13213245665");  
		//请求接口	
		String url = "/fast/sms";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);
	    System.out.println("返回结果post==========>" + post);
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    System.out.println("解密返回的数据==========>" + res);
	}
	
	/**
	 * 短信重发
	 * @param orderNum
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> reSms(String orderNum) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		//订单号
		map.put("order_no", orderNum);  
		//请求接口	
		String url = "/fast/sms";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);
	    System.out.println("返回结果post==========>" + post);
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    System.out.println("解密返回的数据==========>" + res);
	    result = JSONUtils.json2map(res);
		return result;
	}
	
	/**
	 * 关闭订单
	 * @param orderNum
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> closeorder(String orderNum) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		//订单号
		map.put("order_no", orderNum);  
		//请求接口	
		String url = "/fast/closeorder";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);
	    System.out.println("返回结果post==========>" + post);
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    System.out.println("解密返回的数据==========>" + res);
	    result = JSONUtils.json2map(res);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
//		balance_Query();
//		paybatchSubmit();
//		String data = "20180105,252180105173016801,1,6214830164014572,刘运涛,,,招商银行,私,5.00,CNY,提现,252180105173016801,成功,";
//		String[] dataArr = data.split(",");
//		int i = 0;
//		System.out.println(dataArr.length);
//		for(String str : dataArr){
//			System.out.println(i+":"+str);
//			i++;
//		}
//		String res = ReapalUtil.pubkey("+WPday2GP5qXcf9ZuCnDWb5WI9DXnK+ZGnSPNJCH/Rt5hrHwYB8eaz4WF8HlnoBrw2kkeuzw+roKsiUb5V4oc2kCpjQlb0F9OgWvKyb4iGsJF/OwdGfqlYpq8+gtZ32TOdGn2TtDtdWMLTim8rXYnw==");
//		System.out.println(res);
//		System.out.println();
//		paysingleQuery();
//		debit();
//		bindCard();

//		BigDecimal q = BigDecimal.valueOf(1.0);
//		System.out.println(q.setScale(0).toString());
		orderQuery();
	}
}
