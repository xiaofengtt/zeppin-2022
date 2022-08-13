package cn.zeppin.utility.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.List;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

/**
 * 发送短信
 * 说明:		http://api.duanxin.cm/?ac=send&username=用户账号&password=MD5位32密码&phone=号码&content=内容
 * 状态:
 * 100 发送成功
 * 101 验证失败
 * 102 短信不足
 * 103 操作失败
 * 104 非法字符
 * 105 内容过多
 * 106 号码过多
 * 107 频率过快
 * 108 号码内容空
 * 109 账号冻结
 * 110 禁止频繁单条发送
 * 111 系统暂定发送
 * 112 号码不正确
 * 120 系统升级
 * 
 */
public class SendSmsBydDx {
    
	private final static String apikey = "smsj59dkgksmssed";//公钥
	private final static String privatekey = "d24cqB7SMSmn9D5c";//私钥
	
	private final static String link_url = "http://sms.xjiot.link/jeecg-boot/api/zxSmsOtherApi/sendSms?apikey=${apikey}&sign=${sign}";
	
	private final static String templateId = "1419581928322953217";//模板ID
	private final static String signId = "1419581851869179906";//签名ID
	private final static String userName = "otherapi";//用户名
	private final static String sendType = "san";//发送规则
	private final static String code_success = "200";//发送规则
	
	/*
	 * 次数限制 0 代表无限制
	 */
	private final static int line_minutes = 1;
	private final static int line_hours = 5;
	private final static int line_days = 0;
	
	public static String senSms(HttpSession session, String content,String mobile) {
		//从session中获取当前手机号的发送信息
		//key为sms_mobile,value格式类型为 上次发送时间戳_发送次数_是否清空计数
		//String authCode = session.getAttribute("authCode") == null ? "" : session.getAttribute("authCode").toString();
		String checkMessage = session.getAttribute("sms_" + mobile) == null ? "" : session.getAttribute("sms_" + mobile).toString();
		boolean flagSend = true;
//		System.out.println("checkMessage==============="+checkMessage);
		String result = "";
		if(!"".equals(checkMessage)) {//非首次发送
			long timelength = Long.valueOf(checkMessage.split("_")[0]);
			int times = Integer.valueOf(checkMessage.split("_")[1]);
			result = SendCheck.check(line_minutes, line_hours, line_days, times, timelength);
			String isFlush = result.split("_")[0];
			String message = result.split("_")[1];
			if("0".equals(isFlush)) {
				//不校验清空历史计数
				session.removeAttribute("sms_" + mobile);
			} else if("1".equals(isFlush)) {
				//不清空
				if("ok".equals(message)) {//校验通过累积计数
					times++;
					session.removeAttribute("sms_" + mobile);
					session.setAttribute("sms_" + mobile, String.valueOf(System.currentTimeMillis())+"_"+times);
				} else {
					//校验不通过不累积计数
					flagSend = false;
				}
			}
		} else {//首次发送
			session.removeAttribute("sms_" + mobile);
			session.setAttribute("sms_" + mobile, String.valueOf(System.currentTimeMillis())+"_"+1);
		}
		if(flagSend) {
			return sendSms(content, mobile);
		} else {
			return "9_发送失败，"+result.split("_")[1];
		}
	}
	
	private static String sendSms(String content,String mobile){
		
		String result = "";

		try {
			String url = getRealLinkUrl();
			String json = getJSONParams(mobile, content);
			// 返回发送结果
			result = post(url, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getRsultNew(result);
	}

	
    
    public static String getRsultNew(String result) {
		String status = "";
		//{"success":true,"message":"操作成功！","code":200,"result":"success","timestamp":1627358055512}
		try {
			if(result != null && !"".equals(result)) {
				Map<String, Object> resultMap = JSONUtils.json2map(result);
				result = resultMap.get("code") == null ? "0" : resultMap.get("code")
						.toString();
				
				if(!"0".equals(result) && code_success.equals(result)) {//发送成功
					status = "0_0";
				} else {
					String message = resultMap.get("message") == null ? "" : resultMap.get("message")
							.toString();
					result = result.substring(0, 1);
					status = result + "_" + message;
				}
				
				
			} else {
				status = "9_接口请求失败，无返回值";
			}
			System.out.println(Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis()))+"-----短信发送状态："+status.toString());
			return status.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return status.toString();
		}
	}
    
    /**
     * 封装请求链接
     * 获取sign密钥
     * @return
     */
    private static String getRealLinkUrl() {
    	String url = link_url;
    	long a = System.currentTimeMillis();
        String plainText = apikey+a;
    	String sign = AES.encrypt(plainText, privatekey);
    	url = url.replace("${apikey}", apikey).replace("${sign}", sign);
    	return url;
    }
    
    /**
     * 封装请求参数
     * @param toPhone
     * @param code
     * @return
     */
    private static String getJSONParams(String toPhone, String code) {
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("templateId", templateId);
    	params.put("signId", signId);
    	params.put("userName", userName);
    	params.put("sendType", sendType);
    	params.put("toPhone", toPhone);
    	params.put("code", code);
    	return JSONUtils.obj2json(params);
    }
    
    /**
     * 发送post请求
     * @param reqUrl
     * @param json
     * @return
     */
    private static String post(String reqUrl, String json) {
        String result = "";
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.flush();
            os.close();
            if (httpURLConnection.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(),"utf-8")
                );
                String data = "";
                StringBuilder builder = new StringBuilder();
                while ((data = reader.readLine()) != null) {
                    builder.append(data);
                }
                result = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static void main(String[] args) {
    	//http://sms.xjiot.link/jeecg-boot/api/zxSmsOtherApi/sendSms?apikey=smsj59dkgksmssed&sign=iKZWg5d68y491qJyeIK4S5yGqmOi3tmOC8+wEDoDsLI=
//		System.out.println(getRealLinkUrl());
    	//{"code":903,"success":true,"message":"校验未通过","timestamp":1627356917014}
    	//{"success":true,"message":"操作成功！","code":200,"result":"success","timestamp":1627358055512}
		System.out.println(sendSms("188096", "18612033494"));
	}
}






