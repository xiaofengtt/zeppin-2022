package cn.zeppin.product.utility.chanpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;

import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

public class ChanPay4UserUtil {

	private static String pay4URL = "https://pay.chanpay.com/mag/gateway/receiveOrder.do?";
	private static String noticeURL = Utlity.PATH_URL+"/web/notice/chanpinPayNotice";//生产
	private static String msgURL = "https://pay.chanpay.com/mgs/service.do";

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("", "",sParaTemp)
     *
     * @param strParaFileName
     *            文件类型的参数名
     * @param strFilePath
     *            文件路径
     * @param sParaTemp
     *            请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset, String gatewayUrl) throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(createLinkRequestParas(sPara), inputCharset));
        request.setUrl(gatewayUrl);
        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset) throws Exception {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // nameValuePair[i++] = new NameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(),charset));
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    /**
     * 生成要请求给钱包的参数数组
     * 
     * @param sParaTemp
     *            请求前的参数数组
     * @param signType
     *            RSA
     * @param key
     *            商户自己生成的商户私钥
     * @param inputCharset
     *            UTF-8
     * @return 要请求的参数数组
     * @throws Exception
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key, String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = "";
        if ("MD5".equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, key, inputCharset);
        } else if ("RSA".equalsIgnoreCase(signType)) {
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }
        // 签名结果与签名方式加入请求提交参数组中
        System.out.println("sign:" + mysign);
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);

        return sPara;
    }

    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }

    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static Map<String, String> createLinkRequestParas(Map<String, String> params) {
        Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
        List<String> keys = new ArrayList<String>(params.keySet());
        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value;
            try {
                value = URLEncoder.encode(params.get(key), charset);
                encodeParamsValueMap.put(key, value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return encodeParamsValueMap;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        // params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            // try {
            // value = URLEncoder.encode(value,charset);
            // } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            // }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 向测试服务器发送post请求
     * 
     * @param origMap
     *            参数map
     * @param charset
     *            编码字符集
     * @param MERCHANT_PRIVATE_KEY
     *            私钥
     */
    public static String gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY) {
        try {
//            String urlStr = "https://tpay.chanpay.com/mag/gateway/receiveOrder.do?";
            Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
            System.out.println(pay4URL + createLinkString(sPara, true));
            String resultString = buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, pay4URL);
            System.out.println(resultString);
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 向测试服务器发送post请求
     * 
     * @param origMap
     *            参数map
     * @param charset
     *            编码字符集
     * @param MERCHANT_PRIVATE_KEY
     *            私钥
     */
    public static String gatewayPost4Msg(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY) {
        try {
//            String urlStr = "https://tpay.chanpay.com/mag/gateway/receiveOrder.do?";
            Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
            System.out.println(pay4URL + createLinkString(sPara, true));
            String resultString = buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, msgURL);
            System.out.println(resultString);
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 加密，2.14付款到卡接口等部分接口，有参数需要加密
     * 
     * @param src
     *            原值
     * @param publicKey
     *            畅捷支付发送的平台公钥
     * @param charset
     *            UTF-8
     * @return RSA加密后的密文
     */
    private static String encrypt(String src, String publicKey, String charset) {
        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), publicKey);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 2.14 付款到卡cjt_payment_to_card
     */
    public static Map<String, Object> pay2card(String message, String orderNum, String bankcardNum, String name, String bankCode, String bankName, 
    		String bankBranch, String province, String city, String price) {
    	Map<String, Object> result = new HashMap<String, Object>();
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_payment_to_card");
        origMap.put("version", "1.0");
        origMap.put("partner_id", ChanPayUtil.PARTNERID); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);
        origMap.put("origMap", message);
        // 2.14 付款到卡
//        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
        origMap.put("outer_trade_no", orderNum);
        System.out.println("pay 2 card out_trade_no :" + orderNum);
        origMap.put("bank_account_no", encrypt(bankcardNum, ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
        origMap.put("account_name", encrypt(name, ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
        origMap.put("bank_code", bankCode);// 每家银行简码到底是什么，调用2.7接口去查
        origMap.put("bank_name", bankName);
        origMap.put("bank_branch", bankBranch);// 务必填写准确
        origMap.put("province", province);
        origMap.put("city", city);
        origMap.put("card_type", "DEBIT");
        origMap.put("card_attribute", "C");// B对公 C对私
        origMap.put("amount", price);
        origMap.put("notify_url", noticeURL);// 换成自己的
        
        String resultStr =  gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
		result = JSONUtils.json2map(resultStr);
		
		return result;
    }
    
    /**
     * 3.9 查询账户余额query_balance
     */
    public static Map<String, Object> checkBalance() {
    	Map<String, Object> result = new HashMap<String, Object>();
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "query_balance");
        origMap.put("version", "1.0");
        origMap.put("partner_id", ChanPayUtil.PARTNERID); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);
        // 3.9 付查询余额
        origMap.put("identity_no", ChanPayUtil.PARTNERID);
        origMap.put("identity_type", "MEMBER_ID");
        origMap.put("account_type", "201");
        
        String resultStr =  gatewayPost4Msg(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
		result = JSONUtils.json2map(resultStr);
		System.out.println(result.get("account_list"));
		Map<String, Object> resultBalance = new HashMap<String, Object>();
		List<String> list = JSONUtils.json2list(result.get("account_list").toString(), String.class);
        for(String str : list){
        	System.out.println(str);
        	resultBalance = JSONUtils.json2map(str);
        	if(!ChanPayUtil.ACCOUNT_ID.equals(resultBalance.get("account_id").toString())){
        		continue;//只取固定的账户信息
        	}
        	System.out.println(resultBalance.get("balance"));
        }
		return resultBalance;
    }


/**
     * 2.20 2.20单笔订单提现接口cjt_order_withdraw
     */
    public void cjt_order_withdraw() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_order_withdraw");
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);
        
		 origMap.put("order_withdraw_no", "30000043551252390");
		 origMap.put("trade_src_voucher_no", "6741334835157966");
        origMap.put("bank_card_no", encrypt("6214830215878947", ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
        origMap.put("account_name", encrypt("测试01", ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
        origMap.put("notify_url", "https://tadmin.chanpay.com/tpu/mag/syncNotify.do");// 换成自己的
        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);

    }


	/**
     * 2.23账户余额普通提现API接口 cjt_site_withdraw
     */
    public void cjt_site_withdraw() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_site_withdraw");
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);
        
        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
        origMap.put("outer_trade_no", (out_trade_no));
        System.out.println("pay 2 card out_trade_no :" + out_trade_no);
        origMap.put("bank_account_no", encrypt("6214830215878947", ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
        origMap.put("account_name", encrypt("测试01", ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
        origMap.put("bank_code", "CMB");// 每家银行简码到底是什么，调用2.7接口去查
        origMap.put("amount", "100.00");
        origMap.put("notify_url", "https://tadmin.chanpay.com/tpu/mag/syncNotify.do");// 换成自己的
        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);

    }

/**
     * 2.27转账到户网关接口 cjt_balance_transfer
     */
    public void cjt_balance_transfer() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_balance_transfer");
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);
        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
        origMap.put("outer_trade_no", (out_trade_no));
        System.out.println("pay 2 card out_trade_no :" + out_trade_no);
        origMap.put("fundout_identity_no", "200000400007");
        origMap.put("fundin_identity_no", "200000400007");
        origMap.put("amount", "100.00");
        origMap.put("notify_url", "https://tadmin.chanpay.com/tpu/mag/syncNotify.do");// 换成自己的
        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);

    }


    /**
     * 2.11 日支付对账文件cjt_everyday_trade_file 需要自行从response流里获取xls对账文件 2.12 日退款对账文件和2.13 手续费对账文件参照此接口调用
     */
    public void everyTradeFile() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_everyday_trade_file");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);// 字符集
        // 2.11 日支付对账文件
        origMap.put("transDate", "20160728");// 交易日期

        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
    }

	 /**
     * 2.12 商户日退款对账单文件cjt_refund_trade_file
     */
    public void cjt_refund_trade_file() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_refund_trade_file");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);// 字符集
        // 2.11 日支付对账文件
        origMap.put("transDate", "20160728");// 交易日期

        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.13 手续费对账文件cjt_fee_trade_file
     */
    public void cjt_fee_trade_file() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);// 字符集
        origMap.put("service", "cjt_fee_trade_file");// 支付的接口名
        // 2.13 手续费对账文件
        origMap.put("transDate", "20160606");// 交易日期

        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
    }

    /**
     * 异步通知验签仅供参考 2.3单笔支付，2.18，2.19快捷支付api，对应异步通知参数见2.8 2.5退款接口，对应异步通知参数见2.9 2.14付款到卡接口，对应异步通知参数见2.15 2.20订单提现接口，对应异步通知参数见2.15
     */
    public void notifyVerify() {

        String sign = "5Ji173IpLoax0pqDKNLOCp2SVOHZanTSSmESI3Y/66RtY02DxcAWvGukwRZ9/6+neP1OoDXWpAVqhFeMpdYMcHPqDImt9o5O+7MFuH5qjqM2WolgTT+54qzlnzuo3ST60eQWkS31ePmHulknJZNVsjFwmS4TB1d2lWQAW4Zo7Cg=";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("notify_id", "82daf55676574973b8f25c0154a8d2ef");
        paramMap.put("notify_type", "trade_status_sync");
        paramMap.put("notify_time", "20160715130526");
        paramMap.put("_input_charset", "UTF-8");
        paramMap.put("version", "1.0");
        paramMap.put("outer_trade_no", "NO201607062019409608");
        paramMap.put("inner_trade_no", "101146780758085591877");
        paramMap.put("trade_status", "TRADE_SUCCESS");
        paramMap.put("trade_amount", "200.00");
        paramMap.put("gmt_create", "20160706202016");
        paramMap.put("gmt_payment", "20160706202016");
        // paramMap.put("gmt_close", "");
        paramMap.put("extension", "{}");
        String text = createLinkString(paramMap, false);
        System.out.println("ori_text:" + text);
        try {
            System.out.println(RSA.verify(text, sign, ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.7查询银行列表接口cjt_get_paychannel
     */
    public void cjt_get_paychannel() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_get_paychannel");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);// 字符集
        // 2.7查询银行列表接口
        origMap.put("product_code", "20201");// 产品吗，生产环境，测试环境固定20201

        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.5退款接口cjt_create_refund
     */
    public void refund4jc() {
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "2.0");
        origMap.put("partner_id", "200000200012"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);// 字符集
        origMap.put("is_anonymous", "Y");// 是否匿名
        origMap.put("service", "cjt_create_refund");// 支付的接口名
        // 2.5退款接口
        String out_trade_no = (UUID.randomUUID().toString()).replace("-", "");
        System.out.println("out_trade_no:\r\n" + out_trade_no);
        origMap.put("out_trade_no", out_trade_no);// 订单号
        origMap.put("orig_outer_trade_no", "自行填写需要退款的原始订单号");// 原始订单号
        origMap.put("refund_amount", "0.50");// 退款金额
        origMap.put("notify_url", "https://www.baidu.com");// 后台通知的url
        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.6查询交易接口cjt_query_trade 测试环境例子，订单一NO201607062019409608【INSTANT】订单二CJ20160715M200000017【WITHDRAWAL】 快捷支付api的也用【INSTANT】
     */
    public void cjt_query_trade() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_query_trade");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);// 字符集
        // 2.6查询交易接口
        origMap.put("outer_trade_no", "NO201607062019409608");// 这里有坑，是outer_trade_no【outer】不是out_trade_no【out】
        origMap.put("trade_type", "INSTANT");// 交易的类型类型包括 即时到账(INSTANT)，担保交易(ENSURE)， 退款(REFUND),提现（WITHDRAWAL）

        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.10回单下载cjt_view_receipt 测试环境订单号2016072815470375755736903945
     */
    public void cjt_view_receipt() {
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);// 字符集
        origMap.put("service", "cjt_view_receipt");// 支付的接口名
        // 2.10回单下载
        origMap.put("outer_trade_no", "自行填写已经交易完毕的订单号");// 原始订单号
        gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
    }

    
    // //////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
//        ChanPay4UserUtil test = new ChanPay4UserUtil();
        // test.notifyVerify();// 异步通知验签
        // test.pay2card();// 2.14付款到卡
        // test.everyTradeFile();// 2.11日交易对账文件
        // test.cjt_get_paychannel();// 2.7 查询银行卡列表
//        test.cjt_query_trade();// 2.6 主动查询
        // test.cjt_view_receipt();// 2.10回单下载
        // test.cjt_fee_trade_file();// 2.13手续费对账文件
//        Map<String, String> origMap = new HashMap<String, String>();
////		origMap = setCommonMap(origMap);
//		origMap.put("memo", "提现");
//        // 2.1 基本参数
//        origMap.put("service", "cjt_payment_to_card");
//        origMap.put("version", "1.0");
//        origMap.put("partner_id", ChanPayUtil.PARTNERID); // 畅捷支付分配的商户号
//        origMap.put("_input_charset", ChanPayUtil.charset);
//        origMap.put("origMap", "用户提现");
//        // 2.14 付款到卡
//        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
//        origMap.put("outer_trade_no", (out_trade_no));
//        System.out.println("pay 2 card out_trade_no :" + out_trade_no);
//        origMap.put("bank_account_no", encrypt("6214830163940199", ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
//        origMap.put("account_name", encrypt("耿继英", ChanPayUtil.MERCHANT_PUBLIC_KEY, ChanPayUtil.charset));
//        origMap.put("bank_code", "CMB");// 每家银行简码到底是什么，调用2.7接口去查
//        origMap.put("bank_name", "招商银行");
//        origMap.put("bank_branch", "中国招商银行上海市浦建路支行");// 务必填写准确
//        origMap.put("province", "上海市");
//        origMap.put("city", "上海市");
//        origMap.put("card_type", "DEBIT");
//        origMap.put("card_attribute", "C");// B对公 C对私
//        origMap.put("amount", "0.50");
//        origMap.put("notify_url", noticeURL);// 换成自己的
//		String resultStr = gatewayPost(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
//		System.out.println(resultStr);
    	
    	
    	Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "query_balance");
        origMap.put("version", "1.0");
        origMap.put("partner_id", ChanPayUtil.PARTNERID); // 畅捷支付分配的商户号
        origMap.put("_input_charset", ChanPayUtil.charset);
        // 3.9 付查询余额
        origMap.put("identity_no", ChanPayUtil.PARTNERID);
        origMap.put("identity_type", "MEMBER_ID");
        origMap.put("account_type", "201");
        
        String resultStr =  gatewayPost4Msg(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
        System.out.println(resultStr);
        Map<String, Object> result = JSONUtils.json2map(resultStr);
        System.out.println(result.get("account_list"));
        List<String> list = JSONUtils.json2list(result.get("account_list").toString(), String.class);
        for(String str : list){
        	System.out.println(str);
        	Map<String, Object> accountBalance = JSONUtils.json2map(str);
        	System.out.println(accountBalance.get("balance"));
        }
       
    }
}
