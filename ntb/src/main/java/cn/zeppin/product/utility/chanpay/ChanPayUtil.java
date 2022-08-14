package cn.zeppin.product.utility.chanpay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

public class ChanPayUtil {
	
	/**
	 * MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/
	 * 86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/
	 * uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/
	 * 2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB
	 */

	/**
	 * 畅捷支付平台公钥
	 * 											MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4qGiT8ACMd6dQxUoMh9xeqGB/g/LKLX31x0ptfL1xcLSdQpYZt3V4Ow6W23ZpQfdBJ3DHtFzvBp/xcrzjf7ozm7Z84DrCTZc23YDmdRK6fIHae6p8rE05sbUvd3p0+9fY9ALzhMjw+/bWSEwum1D0Ope8n92BcdsFLaMSryu4ZwIDAQAB
	 *                                          MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB
	 */
	public static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";//生产环境公钥
	//											MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxO4ddxne/2w/zz+8c34zVCv4ort/CnPH0VQss3BLLldmF3VSFbu9SG/H2NRiu/zOI1s5mYylzGDs8xhffhxab28WtTdsE0WGKoKf5KxMmn1JqdkgiTnkoorXnk8zPwWxBmfB4g0dimG5mQXp5idlfww6Nio3E3DQ23YvprUP1OQIDAQAB
//	public static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxO4ddxne/2w/zz+8c34zVCv4ort/CnPH0VQss3BLLldmF3VSFbu9SG/H2NRiu/zOI1s5mYylzGDs8xhffhxab28WtTdsE0WGKoKf5KxMmn1JqdkgiTnkoorXnk8zPwWxBmfB4g0dimG5mQXp5idlfww6Nio3E3DQ23YvprUP1OQIDAQAB";
	/**
	 * MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz
	 * /+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+
	 * wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx
	 * /AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/
	 * KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6
	 * +
	 * nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE
	 * +ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/
	 * DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/
	 * ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/
	 * OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/
	 * 72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+
	 * eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+
	 * QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=
	 */
	/**
	 * 商户私钥
	 */
	//生产环境 59 商户号私钥
	//private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";//
	//生产环境 测试商户号私钥
//	public static String MERCHANT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANB5cQ5pf+QHF9Z2+DjrAXstdxQHJDHyrni1PHijKVn5VHy/+ONiEUwSd5nx1d/W+mtYKxyc6HiN+5lgWSB5DFimyYCiOInh3tGQtN+pN/AtE0dhMh4J9NXad0XEetLPRgmZ795O/sZZTnA3yo54NBquT19ijYfrvi0JVf3BY9glAgMBAAECgYBFdSCox5eXlpFnn+2lsQ6mRoiVAKgbiBp/FwsVum7NjleK1L8MqyDOMpzsinlSgaKfXxnGB7UgbVW1TTeErS/iQ06zx3r4CNMDeIG1lYwiUUuguIDMedIJxzSNXfk65Bhps37lm129AE/VnIecpKxzelaUuzyGEoFWYGevwc/lQQJBAPO0mGUxOR/0eDzqsf7ehE+Iq9tEr+aztPVacrLsEBAwqOjUEYABvEasJiBVj4tECnbgGxXeZAwyQAJ5YmgseLUCQQDa/dgviW/4UMrY+cQnzXVSZewISKg/bv+nW1rsbnk+NNwdVBxR09j7ifxg9DnQNk1Edardpu3z7ipHDTC+z7exAkAM5llOue1JKLqYlt+3GvYr85MNNzSMZKTGe/QoTmCHStwV/uuyN+VMZF5cRcskVwSqyDAG10+6aYqD1wMDep8lAkBQBoVS0cmOF5AY/CTXWrht1PsNB+gbzic0dCjkz3YU6mIpgYwbxuu69/C3SWg7EyznQIyhFRhNlJH0hvhyMhvxAkEAuf7DNrgmOJjRPcmAXfkbaZUf+F4iK+szpggOZ9XvKAhJ+JGd+3894Y/05uYYRhECmSlPv55CBAPwd8VUsSb/1w==";
	//正式商户私钥
	public static String MERCHANT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALE7h13Gd7/bD/PP7xzfjNUK/iiu38Kc8fRVCyzcEsuV2YXdVIVu71Ib8fY1GK7/M4jWzmZjKXMYOzzGF9+HFpvbxa1N2wTRYYqgp/krEyafUmp2SCJOeSiiteeTzM/BbEGZ8HiDR2KYbmZBenmJ2V/DDo2KjcTcNDbdi+mtQ/U5AgMBAAECgYAlbt9ObSbJvOkHo/lq7cR/Es1Ppm/YWPG5m2S62tiVa1cAMRky/ZVKkGBwkhJek8Q8uLHrajJM/uc0FNrh+VRKyPURvpQD/LQvga2R4yuCUvaUBwzLNT+xerLbEZzcctCgdTzGZ8tpZLUSHRYADxHbYkQ+70e8R3Dd1vQQCiEGAQJBAN6swnDQ4lvRkAZSTJs8kZeeNJeqpAMlg/sQ4OKkIZrYNH9TLlYsXN2tctjOUomHJhZ3mLgd4jbYOsR/avSH1xkCQQDLwcI4HFKa/fFlKWgLd1hcDxLijs3RzTZPTxr0CT0U6iy7Ydrwq0tEMh8gd9QzSx1zCrWUbmn3apvRsvasSXMhAkEAn2gFekXPBfQrqprYBHmDsIqtE7Yw25/Lr7wRga4F8Je+XTfq//2aLYexo8twAswhVAwh5AxgmOqKh4i/xHZKYQJAZQstOY4yGNKeB0DFbAo/M0f2YYvX3Zcau7HK+AIs3FZU4Ififz8zEBqa/QrvnJA/hnFlIrquqhegNhm94ip6gQJAP8mpc1mP0lzjjQ91RTxWY7we9IxCWqZ9BOd404IcDqCIe7n/q5iTtOmTeOzpxJn8LNlCVVX+bPuG2LRR+VHpdQ==";
//	public static String PARTNERID = "200001160097";//200001160097签约合作方的唯一编号 200001300018
	public static String PARTNERID = "200001300018";
	public static String ACCOUNT_ID = "200100100120000130001800001";
	
//	private static String devURL = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";//测试
	private static String prdURL = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";//生产
	
	private static String msgURL = "https://pay.chanpay.com/mgs/service.do";
	/**
	 * 编码类型
	 */
	public static String charset = "UTF-8";
	
	/**
	 * 异步通知地址
	 */
	public static String noticeURL = Utlity.PATH_URL+"/web/notice/chanpinNotice";//鉴权绑卡通知接口地址
	public static String noticeBuyURL = Utlity.PATH_URL+"/web/notice/chanpinBuyNotice";//支付通知接口地址(购买理财)
	public static String noticeRechargeURL = Utlity.PATH_URL+"/web/notice/chanpinRechargeNotice";//支付通知接口地址（充值）
	
	
	public static final String RETACCEPTSTATUS_SUCCESS = "S";//成功
	public static final String RETACCEPTSTATUS_FAIL = "F";//失败
	
	public static final String RETSTATUS_SUCCESS = "S";//成功
	public static final String RETSTATUS_FAIL = "F";//失败
	public static final String RETSTATUS_RROSESS = "P";//处理中
	
	public static final String TYPE_AUTH_ORDER = "auth_order";
	public static final String TYPE_PAY_ORDER = "pay_order";
	
	/**
	 * 付款到银行卡单笔扣除手续费
	 */
	public static final BigDecimal POUNDAGE = BigDecimal.ONE;
	/**
	 * 快捷支付单笔扣除手续费费率
	 */
	public static final BigDecimal POUNDAGE_FEE = BigDecimal.valueOf(Double.parseDouble("0.0035"));
//	/**
//	 * 生产环境通知地址
//	 */
//	public static String noticeURL = "https://api.niutoulicai.com//web/notice/chanpingNotice";//鉴权绑卡通知接口地址
//	public static String noticeBuyURL = "https://api.niutoulicai.com//web/notice/chanpingBuyNotice";//支付通知接口地址


	public static String createLinkString(Map<String, String> params, boolean encode) {

	        params = paraFilter(params);

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
	
	public static String lingString(Map<String, String> params, boolean encode) {

        params = paraFilter(params);

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
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
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
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key,
                                      String inputCharset, String gatewayUrl) throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);

        request.setMethod(HttpRequest.METHOD_POST);

        request.setParameters(generatNameValuePair(sPara, inputCharset));
        request.setUrl(gatewayUrl);
        System.out.println(gatewayUrl+""+httpProtocolHandler.toString(generatNameValuePair(createLinkRequestParas(sPara), inputCharset)));
        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }

        String strResult = response.getStringResult();

        return strResult;
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
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    
    /**
     * 生成要请求给钱包的参数数组
     *
     * @param sParaTemp         请求前的参数数组
     * @return                  要请求的参数数组
     */
	public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key,
			String inputCharset) throws Exception {
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
		System.out.println("Sign:" + mysign);
		sPara.put("Sign", mysign);
		sPara.put("SignType", signType);

		return sPara;
	}
    
    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key,
                                           String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }
    
    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey,
                                           String inputCharset) throws Exception {
    	String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    	String mysign = RSA.sign(prestr, privateKey, inputCharset);
        System.out.println("buildRequestByRSA======加密前排序后串======" + prestr + "======加密后串======" +  mysign);
        return mysign;
    }
    
    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset) throws Exception{
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(),charset));
            //nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }
    
	public static Map<String, String> createLinkRequestParas(Map<String, String> params) {
		Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
		List<String> keys = new ArrayList<String>(params.keySet());
		String charset = params.get("InputCharset");
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
	 * 加密，部分接口，有参数需要加密
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
	 * 向测试服务器发送post请求
	 * 
	 * @param origMap
	 *            参数map
	 * @param charset
	 *            编码字符集
	 * @param MERCHANT_PRIVATE_KEY
	 *            私钥
	 * @throws Exception 
	 */
	public static String gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY)  {
		String result="";
		try {
			result = buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, prdURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		
		return result;
	}
	
	public static String gatewayPost4Msg(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY)  {
		String result="";
		try {
			result = buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, msgURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		
		return result;
	}
    
    /**
     * 公共参数设置
     * @param origMap
     * @return
     */
	public static Map<String, String> setCommonMap(Map<String, String> origMap) {
		// 2.1 基本参数
		origMap.put("Version", "1.0");
//		origMap.put("PartnerId", "200000140001"); // 测试环境商户号
		//origMap.put("PartnerId", "200000400059");//200000400059 生产参数
		origMap.put("PartnerId", PARTNERID);//200000400059 生产测试参数
		
		origMap.put("InputCharset", charset);// 字符集
		Timestamp time = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.format(time);
		origMap.put("TradeDate", sdf.format(time));// 商户请求日期
		sdf = new SimpleDateFormat("HHmmss");
		origMap.put("TradeTime", sdf.format(time));// 商户请求时间
//		origMap.put("Memo", null);
		return origMap;
	}
    
	/**
	 * 鉴权绑卡
	 */
	public static Map<String, Object> nmg_biz_api_auth_req(String uuid, String idcard, String username,String phone,String bankcard, String orderNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		origMap = setCommonMap(origMap);
		origMap.put("Memo", "鉴权绑卡请求");
		uuid = cn.zeppin.product.utility.MD5.getMD5(uuid);
		// 2.1 鉴权绑卡 api 业务参数
		origMap.put("Service", "nmg_biz_api_auth_req");// 鉴权绑卡的接口名(商户采集方式)；银行采集方式更换接口名称为：nmg_canal_api_auth_req
//		String trxId = Long.toString(System.currentTimeMillis());	
		origMap.put("TrxId", orderNum);// 订单号
		origMap.put("ExpiredTime", "90m");// 订单有效期
		origMap.put("MerUserId", uuid);// 用户标识
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
		origMap.put("BkAcctNo", encrypt(bankcard, MERCHANT_PUBLIC_KEY, charset));// 卡号
		//System.out.println(this.encrypt("621483011*******", MERCHANT_PUBLIC_KEY, charset));
		origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
		origMap.put("IDNo", encrypt(idcard, MERCHANT_PUBLIC_KEY, charset));// 证件号
		//System.out.println(this.encrypt("13010*********", MERCHANT_PUBLIC_KEY, charset));
		origMap.put("CstmrNm", encrypt(username, MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
		origMap.put("MobNo", encrypt(phone, MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
		//信用卡
//		origMap.put("CardCvn2", "004");// cvv2码
//		origMap.put("CardExprDt", "0921");// 有效期
		origMap.put("NotifyUrl", noticeURL);// 异步通知url
		origMap.put("SmsFlag", "1");
		origMap.put("Extension", "");
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		result = JSONUtils.json2map(resultStr);
		return result;
	}
	
	/**
	 * 鉴权绑卡确认
	 */
	public static Map<String, Object> nmg_api_auth_sms(String orderNum, String scode, String orderNumN) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		origMap.put("Memo", "鉴权绑卡确认");
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_auth_sms");// 鉴权绑卡确认的接口名
		// 2.1 鉴权绑卡  业务参数
//		String trxId = Long.toString(System.currentTimeMillis());
		origMap.put("TrxId", orderNumN);// 订单号
		origMap.put("OriAuthTrxId", orderNum);// 原鉴权绑卡订单号
		origMap.put("SmsCode", scode);// 鉴权短信验证码
		origMap.put("Extension", "");// 异步通知地址
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		result = JSONUtils.json2map(resultStr);
		return result;
	}
	
	/**
	 * 
	 * 用户鉴权解绑 api nmg_api_auth_unbind  
	 */
	public static Map<String, Object> nmg_api_auth_unbind(String uuid, String cardbegin, String cardend, String orderNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		origMap.put("Memo", "鉴权解绑");
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		uuid = cn.zeppin.product.utility.MD5.getMD5(uuid);
		origMap.put("Service", "nmg_api_auth_unbind");// 用户鉴权解绑接口名
		// 2.2 业务参数
//		String trxId = Long.toString(System.currentTimeMillis());		
		origMap.put("TrxId", orderNum);// 商户网站唯一订单号
		origMap.put("MerchantNo", PARTNERID);// 子商户号
		origMap.put("MerUserId", uuid); // 用户标识
		origMap.put("UnbindType", "0"); // 解绑模式。0为物理解绑，1为逻辑解绑
//		origMap.put("CardId", "");// 卡号标识
		origMap.put("CardBegin", cardbegin);// 卡号前6位
		origMap.put("CardEnd", cardend);// 卡号后4位
		origMap.put("Extension", "");// 扩展字段
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		result = JSONUtils.json2map(resultStr);
		return result;
	}
	
	/**
	 * 
	 * 2.9 用户鉴权绑卡信息查询 api nmg_api_auth_info_qry
	 */

	public static void nmg_api_auth_info_qry() {
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		String trxId = Long.toString(System.currentTimeMillis());
		origMap = setCommonMap(origMap);
		origMap.put("Memo", "绑卡信息查询");
		String uuid = cn.zeppin.product.utility.MD5.getMD5("d84216b7-b856-4223-9a5b-7fb3dee80743");
		origMap.put("Service", "nmg_api_auth_info_qry");// 用户鉴权绑卡信息查询接口名
		// 2.2 业务参数
		origMap.put("TrxId", trxId);// 商户网站唯一订单号
		origMap.put("MerUserId", uuid); // 用户标识
		//origMap.put("CardBegin", "430000");// 卡号前6位
		//origMap.put("CardEnd", "4700");// 卡号后4位
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡）
		gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	/**
	 * 
	 * 2.4 支付请求接口 api nmg_biz_api_quick_payment
	 */
	public static Map<String, Object> nmg_biz_api_quick_payment(String url, String message, String uuid, String cardbegin, String cardend, String price, String orderNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Memo", message);
		origMap.put("Service", "nmg_biz_api_quick_payment");// 支付的接口名
		// 2.2 业务参数
//		String trxId = Long.toString(System.currentTimeMillis());
		origMap.put("TrxId", orderNum);// 订单号
		uuid = cn.zeppin.product.utility.MD5.getMD5(uuid);
		//origMap.put("TrxId", "201703131045234230112");// 订单号
		origMap.put("OrdrName", "畅捷支付支付接口测试");// 商品名称
		origMap.put("MerUserId", uuid);// 用户标识
		origMap.put("SellerId", PARTNERID);// 商户号
//		origMap.put("SubMerchantNo", "");// 子商户号
		origMap.put("ExpiredTime", "40m");// 订单有效期
//		origMap.put("CardBegin", "430000");// 卡号前6位
//		origMap.put("CardEnd", "7200");// 卡号后4位
		origMap.put("CardBegin", cardbegin);// 卡号前6位
		origMap.put("CardEnd", cardend);// 卡号后4位
		origMap.put("TrxAmt", price);// 交易金额
		origMap.put("TradeType", "11");// 交易类型
		origMap.put("SmsFlag", "1");
		origMap.put("NotifyUrl", url);// 异步通知地址
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		result = JSONUtils.json2map(resultStr);
		return result;
	}
	
	/**
	 * 
	 * 2.5 支付确认接口： api nmg_api_quick_payment_smsconfirm
	 */
	public static Map<String, Object> nmg_api_quick_payment_smsconfirm(String message,String orderNum, String scode, String orderNumN) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Memo", message);
		origMap.put("Service", "nmg_api_quick_payment_smsconfirm");// 请求的接口名称
		// 2.2 业务参数
//		String trxId = Long.toString(System.currentTimeMillis());		
		origMap.put("TrxId", orderNumN);// 订单号

		//origMap.put("TrxId", "101149785980144593760");// 订单号
		origMap.put("OriPayTrxId", orderNum);// 原有支付请求订单号
		origMap.put("SmsCode", scode);// 短信验证码
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		result = JSONUtils.json2map(resultStr);
		return result;
	}
	
	/**
	 * 
	 * 2.11 商户短信验证码重发  ：nmg_api_quick_payment_resend
	 * type 鉴权订单：auth_order  支付订单；pay_order

	 */
	public static Map<String, Object> nmg_sms_resend(String message,String orderNum, String type, String orderNumStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		origMap.put("Memo", message);
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		// 2.2 业务参数
		origMap.put("Service", "nmg_api_quick_payment_resend");
		origMap.put("TrxId", orderNumStr);// 订单号
		origMap.put("OriTrxId", orderNum);// 原业务请求订单号
		origMap.put("TradeType", type);// 原业务订单类型
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		result = JSONUtils.json2map(resultStr);
		return result;
	}
	
	/**
	 * 
	 * 2.14 商户鉴权/支付/退款订单状态查询接口 api tzt_order_query
	 */
	public static Map<String, Object> nmg_api_query_trade(String orderNum, String tradeType, String orderNumStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_query_trade");// 请求的接口名
		// 2.2 业务参数
		origMap.put("TrxId", orderNumStr);// 订单号
		origMap.put("OrderTrxId", orderNum);// 原业务请求订单号
		origMap.put("TradeType", tradeType);// 原业务订单类型
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		result = JSONUtils.json2map(resultStr);
		return result;
	}
	
    /**
     * 2.14 付款到卡cjt_payment_to_card
     */
    public void pay2card() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_payment_to_card");
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);
        // 2.14 付款到卡
        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
        origMap.put("outer_trade_no", (out_trade_no));
        System.out.println("pay 2 card out_trade_no :" + out_trade_no);
        origMap.put("bank_account_no", encrypt("6214830215878947", MERCHANT_PUBLIC_KEY, charset));
        origMap.put("account_name", encrypt("测试01", MERCHANT_PUBLIC_KEY, charset));
        origMap.put("bank_code", "CMB");// 每家银行简码到底是什么，调用2.7接口去查
        origMap.put("bank_name", "招商银行");
        origMap.put("bank_branch", "中国招商银行上海市浦建路支行");// 务必填写准确
        origMap.put("province", "上海市");
        origMap.put("city", "上海市");
        origMap.put("card_type", "DEBIT");
        origMap.put("card_attribute", "C");// B对公 C对私
        origMap.put("amount", "100.00");
        origMap.put("notify_url", "https://tadmin.chanpay.com/tpu/mag/syncNotify.do");// 换成自己的
        gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);

    }
    
    public static String getResultMessage(String verifyCode){
		String message = "";
		switch (verifyCode) {
		case "V_PH_NA":
			message = "查询不到电话号码信息";
			break;
		case "V_PH_CN_UM":
			message = "电话号码与本人不匹配";
			break;
		case "V_PH_CN_MA_UL30D":
			message = "电话号码与本人匹配，30天内有使用";
			break;
		case "V_PH_CN_MA_UL90D":
			message = "电话号码与本人匹配，90天内有使用";
			break;
		case "V_PH_CN_MA_UL180D":
			message = "电话号码与本人匹配，180天内有使用";
			break;
		case "V_PH_CN_MA_UM180D":
			message = "电话号码与本人匹配，180天内没有使用";
			break;
		case "V_PH_NM_UM":
			message = "电话号码与姓名不匹配";
			break;
		case "V_PH_NM_MA_UL30D":
			message = "电话号码与姓名匹配，30天内有使用";
			break;
		case "V_PH_NM_MA_UL90D":
			message = "电话号码与姓名匹配，90天内有使用";
			break;
		case "V_PH_NM_MA_UL180D":
			message = "电话号码与姓名匹配，180天内有使用";
			break;
		case "V_PH_NM_MA_UM180D":
			message = "电话号码与姓名匹配，180天内没有使用";
			break;
		case "V_CN_NA":
			message = "查询不到身份证信息";
			break;
		case "V_CN_NM_UM":
			message = "姓名与身份证号不匹配";
			break;
		case "V_CN_NM_MA":
			message = "姓名与身份证号匹配";
			break;
		case "V_BC_CN_UK":
			message = "银行卡号与本人关系未知";
			break;
		case "V_BC_CN_UM":
			message = "银行卡号与本人不匹配";
			break;
		case "V_BC_CN_MA_UL180D":
			message = "银行卡号与本人匹配，180天内有使用";
			break;
		case "V_BC_CN_MA_UL360D":
			message = "银行卡号与本人匹配，360天内有使用";
			break;
		case "V_BC_CN_MA_UM360D":
			message = "银行卡号与本人匹配，360天内没有使用";
			break;
		case "V_BC_PH_UK":
			message = "银行卡号与手机号码关系未知";
			break;
		case "V_BC_PH_UM":
			message = "银行卡号与手机号码不匹配";
			break;
		case "V_BC_PH_MA_UL180D":
			message = "银行卡号与手机号码匹配，180天内有使用";
			break;
		case "V_BC_PH_MA_UL360D":
			message = "行卡号与手机号码匹配，360天内有使用";
			break;
		case "V_BC_PH_MA_UM360D":
			message = "银行卡号与手机号码匹配，360天内没有使用";
			break;

		default:
			break;
		}
		return message;
	}
	public static void main(String[] args) {
//		String rsa_private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";
//		try {
//			System.out.println(ChanPayUtil.buildRequestByRSA(null,rsa_private_key,"UTF-8"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		/*
		 * InputCharset=UTF-8
		 * &MerchantNo=200000400059
		 * &OriAuthTrxId=346788888888888888888888888
		 * &PartnerId=200000400059
		 * &Service=nmg_api_auth_sms
		 * &Sign=TMshBDGEDQNpTbOgDCQ%2FCmx6WJkBrbIs%2BtQFTR6GJ6LT8UDxo5K4yMVJtlzuVcyM4HsXJu5ZxI22pY75UvqyWt3IM7EXNwNfC03VS1lKAmlEYL5tBkVcPcDfduNWw7pDJYBVn4052%2BSqaohuMBdbF1vcqIRZkO0aR32fdp9DXuU%3D&
		 * SignType=RSA
		 * &SmsCode=823456
		 * &TradeDate=20171016
		 * &TradeTime=094500
		 * &TrxId=2017101609452023063170966493
		 * &Version=1.0
		 */
//		nmg_biz_api_auth_req();
//		nmg_api_auth_sms();
//		nmg_api_auth_unbind();
//		nmg_api_auth_info_qry();
//		nmg_biz_api_quick_payment();
		
//		Map<String, String> origMap = new HashMap<String, String>();
//		String uuid = cn.zeppin.product.utility.MD5.getMD5("c38e48f0-dafd-4830-b066-0dfda10d0707");//c38e48f0-dafd-4830-b066-0dfda10d0707
//		origMap = setCommonMap(origMap);
//		// 2.1 鉴权绑卡 api 业务参数d84216b7-b856-4223-9a5b-7fb3dee80743
//		origMap.put("Service", "nmg_biz_api_auth_req");// 鉴权绑卡的接口名(商户采集方式)；银行采集方式更换接口名称为：nmg_canal_api_auth_req
////		String orderNumStr = Utlity.getOrderNumStr(billDevice,Utlity.BILL_PAYTYPE_CHANPAY,Utlity.BILL_PURPOSE_CHANPAY);
//		String trxId = Long.toString(System.currentTimeMillis());	
//		origMap.put("TrxId", trxId);// 订单号
//		origMap.put("ExpiredTime", "90m");// 订单有效期
//		origMap.put("MerUserId", uuid);// 用户标识
//		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
//		origMap.put("BkAcctNo", encrypt("6214830101709243", MERCHANT_PUBLIC_KEY, charset));// 卡号6214830101709243/6217920603928560/6214830163940199
//		//System.out.println(this.encrypt("621483011*******", MERCHANT_PUBLIC_KEY, charset));
//		origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
//		origMap.put("IDNo", encrypt("11010119900902151X", MERCHANT_PUBLIC_KEY, charset));// 证件号11010119900902151x/130727199312152424
//		//System.out.println(this.encrypt("13010*********", MERCHANT_PUBLIC_KEY, charset));
//		origMap.put("CstmrNm", encrypt("朱克鑫", MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
//		origMap.put("MobNo", encrypt("18601142193", MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号18601142193/13716640748
//		//信用卡
////		origMap.put("CardCvn2", "004");// cvv2码
////		origMap.put("CardExprDt", "0921");// 有效期
//		origMap.put("NotifyUrl", noticeURL);// 异步通知url
//		origMap.put("SmsFlag", "1");
//		origMap.put("Extension", "");
//		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		System.out.println(resultStr);
		
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_auth_sms");// 鉴权绑卡确认的接口名
		// 2.1 鉴权绑卡  业务参数
		String trxId = Long.toString(System.currentTimeMillis());
//		String orderNumStr = Utlity.getOrderNumStr(billDevice,Utlity.BILL_PAYTYPE_CHANPAY,Utlity.BILL_PURPOSE_CHANPAY);
		origMap.put("TrxId", trxId);// 订单号
		origMap.put("OriAuthTrxId", "1515139622665");// 原鉴权绑卡订单号1508404406329/1515139782720
		origMap.put("SmsCode", "651269");// 鉴权短信验证码
		origMap.put("Extension", "");// 异步通知地址
		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		System.out.println(resultStr);
		
//		Map<String, String> origMap = new HashMap<String, String>();
//		// 2.1 基本参数
//		origMap = setCommonMap(origMap);
//		String uuid = cn.zeppin.product.utility.MD5.getMD5("bb4ac373-33e2-42d0-8ce8-b004bb5f0e1f");
//		origMap.put("Service", "nmg_api_auth_unbind");// 用户鉴权解绑接口名
//		// 2.2 业务参数
//		String trxId = Long.toString(System.currentTimeMillis());		
////		String orderNumStr = Utlity.getOrderNumStr(billDevice,Utlity.BILL_PAYTYPE_CHANPAY,Utlity.BILL_PURPOSE_CHANPAY);
//		origMap.put("TrxId", trxId);// 商户网站唯一订单号
//		origMap.put("MerchantNo", PARTNERID);// 子商户号
//		origMap.put("MerUserId", uuid); // 用户标识
//		origMap.put("UnbindType", "0"); // 解绑模式。0为物理解绑，1为逻辑解绑
////		origMap.put("CardId", "");// 卡号标识
//		origMap.put("CardBegin", "621483");// 卡号前6位6217920602045168
//		origMap.put("CardEnd", "9243");// 卡号后4位0199
//		origMap.put("Extension", "");// 扩展字段6214830101846326/6225210694776064/5650662879628866/6214830163940199/6214830163940199
//		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		System.out.println(resultStr);
		
//		nmg_biz_api_quick_payment();
//		nmg_api_auth_info_qry();
		
//		Map<String, String> origMap = new HashMap<String, String>();
//		// 2.1 基本参数
//		origMap = setCommonMap(origMap);
//		origMap.put("Memo", "充值");
//		origMap.put("Service", "nmg_biz_api_quick_payment");// 支付的接口名
//		// 2.2 业务参数
//		String trxId = Long.toString(System.currentTimeMillis());
//		origMap.put("TrxId", trxId);// 订单号
//		String uuid = cn.zeppin.product.utility.MD5.getMD5("d84216b7-b856-4223-9a5b-7fb3dee80743");
//		//origMap.put("TrxId", "201703131045234230112");// 订单号
//		origMap.put("OrdrName", "畅捷支付支付接口测试");// 商品名称
//		origMap.put("MerUserId", uuid);// 用户标识 769249
//		origMap.put("SellerId", PARTNERID);// 商户号
////		origMap.put("SubMerchantNo", "");// 子商户号
//		origMap.put("ExpiredTime", "40m");// 订单有效期
////		origMap.put("CardBegin", "430000");// 卡号前6位
////		origMap.put("CardEnd", "7200");// 卡号后4位
//		origMap.put("CardBegin", "621483");// 卡号前6位622521
//		origMap.put("CardEnd", "4572");// 卡号后4位6064
//		origMap.put("TrxAmt", "3.99");// 交易金额
//		origMap.put("TradeType", "11");// 交易类型
//		origMap.put("SmsFlag", "1");
//		origMap.put("NotifyUrl", noticeBuyURL);// 异步通知地址
//		origMap.put("Extension", "");// 扩展字段
//		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		System.out.println(resultStr);
		
//		Map<String, String> origMap = new HashMap<String, String>();
//		// 2.1 基本参数
//		origMap = setCommonMap(origMap);
//		origMap.put("Memo", "充值");
//		origMap.put("Service", "nmg_api_quick_payment_smsconfirm");// 请求的接口名称
//		// 2.2 业务参数
//		String trxId = Long.toString(System.currentTimeMillis());		
//		origMap.put("TrxId", trxId);// 订单号
//
//		//origMap.put("TrxId", "101149785980144593760");// 订单号
//		origMap.put("OriPayTrxId", "1508814884504");// 原有支付请求订单号
//		origMap.put("SmsCode", "145650");// 短信验证码
//		origMap.put("Extension", "");// 扩展字段
//		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		System.out.println(resultStr);
		
//		Map<String, String> origMap = new HashMap<String, String>();
//		// 2.1 基本参数
//		origMap = setCommonMap(origMap);
//		origMap.put("Service", "nmg_api_query_trade");// 请求的接口名
//		// 2.2 业务参数
//		String trxId = Long.toString(System.currentTimeMillis());		
//		origMap.put("TrxId", trxId);// 订单号
//		origMap.put("OrderTrxId", "222171025181258425");// 原业务请求订单号
//		origMap.put("TradeType", "pay_order");// 原业务订单类型
//		String resultStr = gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		System.out.println(resultStr);
		
//    	Map<String, String> origMap = new HashMap<String, String>();
//        // 2.1 基本参数
//        origMap.put("service", "query_balance");
//        origMap.put("version", "1.0");
//        origMap.put("partner_id", ChanPayUtil.PARTNERID); // 畅捷支付分配的商户号
//        origMap.put("_input_charset", ChanPayUtil.charset);
//        origMap.put("origMap", "查询余额");
//        // 3.9 付查询余额
//        origMap.put("identity_no", ChanPayUtil.PARTNERID);
//        origMap.put("identity_type", "MEMBER_ID");
//        
//        String resultStr =  gatewayPost4Msg(origMap, ChanPayUtil.charset, ChanPayUtil.MERCHANT_PRIVATE_KEY);
//        System.out.println(resultStr);
	}
}
