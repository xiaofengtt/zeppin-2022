package cn.product.treasuremall.util.reapal;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.product.treasuremall.util.Utlity;

public class ReapalUtil {

	// /resource/user-rsa.pfx
	protected static String privateKey = "/www/webapps/ntb/security/rongbao-user.pfx";// 私钥//生产
//	protected static String privateKey = "/Users/zeppin/security/rongbao-user.pfx";// 私钥//生产//mac
//	protected static String privateKey = "D:\\cert\\rongbao-user.pfx";// 私钥//生产//windows
//	protected static String privateKey = "D:\\cert\\itrus001.pfx";// 私钥
	protected static String password = "niutoulicai";// 密码//生产
//	protected static String password = "123456";// 密码
	
	protected static String key = "ga021c79aba8260438f9e07dd1g83b7eac7e917e473gb36679b33c1a1f1fg2a4";//生产
//	protected static String key = "g0be2385657fa355af68b74e9913a1320af82gb7ae5f580g79bffd04a402ba8f";// 用户key
	
	//100000001302509
	protected static String merchant_id = "100000001302509";// 商户ID//生产
//	protected static String merchant_id = "100000000000147";// 商户ID
	
	// /www/webapps/ntb/security/rongbao.cer
	protected static String pubKeyUrl = "/www/webapps/ntb/security/rongbao.cer";// 公钥//生产
//	protected static String pubKeyUrl = "/Users/zeppin/security/rongbao.cer";// 公钥//生产//mac
//	protected static String pubKeyUrl = "D:\\cert\\rongbao.cer";// 公钥//生产//windows
//	protected static String pubKeyUrl = "D:\\cert\\itrus001.cer";// 公钥
	
	protected static final String url = "https://agentpay.reapal.com/";//生产
//	protected static final String url = "http://testagentpay.reapal.com/agentpay/";
	
	public static final String notify_url_pay = Utlity.PATH_URL+"/notice/withdraw/byReapal";//异步通知地址--代付
	protected static String version = "1.0";// 版本
	protected static String charset = "UTF-8";// 编码
	protected static String sign_type = "MD5";// 签名方式，暂时仅支持MD5
	
	public static final String pay_type_urgent = "0";
	public static final String pay_type_normal = "1";
	
	public static final String card_type_debit = "1";
	public static final String card_type_deposit = "0";
	
	public static final BigDecimal POUNDAGE_FEE = BigDecimal.valueOf(Double.parseDouble("0.002"));
	
	public static final String RETSTATUS_SUCCESS = "completed";//成功
	public static final String RETSTATUS_FAIL = "failed";//失败
	public static final String RETSTATUS_RROSESS = "processing";//处理中
	public static final String RETSTATUS_WAIT = "wait";//处理中
	public static final String RETSTATUS_CLOSE = "closed";//处理中
	
	public static String getKey() {

		return key;
	}
	 public static String getPrivateKey() {
		return privateKey;
	}
	public static void setPrivateKey(String privateKey) {
		ReapalUtil.privateKey = privateKey;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		ReapalUtil.password = password;
	}
	public static String getMerchant_id() {
		return merchant_id;
	}
	public static void setMerchant_id(String merchant_id) {
		ReapalUtil.merchant_id = merchant_id;
	}
	public static String getPubKeyUrl() {
		return pubKeyUrl;
	}
	public static void setPubKeyUrl(String pubKeyUrl) {
		ReapalUtil.pubKeyUrl = pubKeyUrl;
	}
	public static String getVersion() {
		return version;
	}
	public static void setVersion(String version) {
		ReapalUtil.version = version;
	}
	public static String getCharset() {
		return charset;
	}
	public static void setCharset(String charset) {
		ReapalUtil.charset = charset;
	}
	
	public static String getSign_type() {
		return sign_type;
	}
	public static void setSign_type(String sign_type) {
		ReapalUtil.sign_type = sign_type;
	}
	/**
     * 参数加密
     * @return
     */
    public static Map<String, String> addkey(String json)throws Exception{

        System.out.println("数据=============>" + json);

        //商户获取融宝公钥
        PublicKey pubKeyFromCrt = RSA.getPubKeyFromCRT(pubKeyUrl);
        //随机生成16数字
        String key = RandomUtil.getRandom(16);

        System.out.println("key=============>" + key);
        //对随机数进行加密
        String encryptkey = RSA.encrypt(key, pubKeyFromCrt);
        String encryData = AES.encryptToBase64(json, key);

        System.out.println("密文key============>" + encryptkey);
        System.out.println("密文数据===========>" + encryData);

        Map<String, String> map = new HashMap<String, String>();
        map.put("data", encryData);
		map.put("encryptkey", encryptkey);

        return map;
    }
    


    /**
     * 解密
     * @param post
     * @return
     * @throws Exception
     */
    public static String pubkey(String post)throws Exception{

        System.out.println("密文================>" + post);
        // 将返回的json串转换为map

        TreeMap<String, String> map = JSON.parseObject(post,
                new TypeReference<TreeMap<String, String>>() {
                });
        String encryptkey = map.get("encryptkey");
        String data = map.get("data");
        
        System.out.println("输出data  = ="+data);

        //获取自己私钥解密
        PrivateKey pvkformPfx = RSA.getPvkformPfx(privateKey, ReapalUtil.getPassword());
        String decryptData = RSA.decrypt(encryptkey, pvkformPfx);

        post = AES.decryptFromBase64(data, decryptData);

        System.out.println("明文================>" + post);


        return post;
    }
    /**
     * 解密
     * @param post
     * @return
     * @throws Exception
     */
    public static String pubkey(String encryptkey,String data)throws Exception{
    	String post;
    	System.out.println("encryptkey================>" + encryptkey);
    	System.out.println("密文================>" + data);
        //获取自己私钥解密
        PrivateKey pvkformPfx = RSA.getPvkformPfx(privateKey, ReapalUtil.getPassword());
        String decryptData = RSA.decrypt(encryptkey, pvkformPfx);

        post = AES.decryptFromBase64(data, decryptData);

        System.out.println("明文================>" + post);


        return post;
    }
    
    public static String getMapOrderStr(Map<String,Object> request){
        List<String> fieldNames = new ArrayList<String>(request.keySet());
        Collections.sort(fieldNames);
        StringBuffer buf = new StringBuffer();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = String.valueOf(request.get(fieldName));
            if ((fieldValue != null) && (fieldValue.length() > 0)){
                buf.append(fieldName+"="+fieldValue+"&");
            }
        }
        if(buf.length()>1) buf.setLength(buf.length()-1);
        return buf.toString(); //去掉最后&

    }



    /**
     * 生成订单号
     * @return
     */
    public static String no(){
        String code = "10" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "01" ;
        return code;
    }

    /**
     *
     * @param sArray
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static String BuildMysign(Map sArray, String key) {
        if(sArray!=null && sArray.size()>0){
            StringBuilder prestr = CreateLinkString(sArray);
            System.out.println("prestr====>" + prestr);
            //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            return Md5Encrypt.md5(prestr.toString()+key,ReapalUtil.getCharset());
        }
        return null;
    }

    /**
     * 拼装参数
     * @param params
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static StringBuilder CreateLinkString(Map params){
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuilder prestr = new StringBuilder();
        String key="";
        String value="";
        for (int i = 0; i < keys.size(); i++) {
            key=(String) keys.get(i);
            value = (String) params.get(key);
            if("".equals(value) || value == null ||
                    key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")){
                continue;
            }
            prestr.append(key).append("=").append(value).append("&");
        }
        return prestr.deleteCharAt(prestr.length()-1);
    }
    
    @SuppressWarnings({ "static-access", "unused" })
	public static void main(String[] arg0) throws Exception{
    	ReapalUtil ru = new ReapalUtil();
    	Map<String, String> map = ru.addkey("wo ai beijing tian an men");
    	String anwer = ru.pubkey(JSON.toJSONString(map));
    }
}
