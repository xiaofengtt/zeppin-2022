package cn.zeppin.product.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

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
public class SendSms {
	
	public static String sendSms(String content,String mobile){
		StringBuilder url = new StringBuilder();
		//牛投理财
		String uid = "70212976";
		String pwd = "afb00fb9c7ab51889223c821a4da2a76";
		//自学酷
//		String uid = "70208580";
//		String pwd = "b3aae0eeeb290755569d22c167c77f63";
		//新疆师范大学
//		String uid = "70211188";
//		String pwd = "04ad2d52a0176ab18ed14e468bf70661";
		url.append("http://api.duanxin.cm/?");
		try {
			content = URLEncoder.encode(content, "utf-8");
			// 向StringBuffer追加用户名
			url.append("action=send&username="+uid);
			
			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			url.append("&password="+pwd);

			// 向StringBuffer追加手机号码
			url.append("&phone="+mobile);

			// 向StringBuffer追加消息内容转URL标准码
			url.append("&content="+content);
			
			// 向StringBuffer追加字符集编码
			url.append("&encode=utf8");
			
			// 创建url对象
			URL urls = new URL(url.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) urls.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(urls.openStream()));

			// 返回发送结果
			String inputline = in.readLine();
			return getRsult(inputline);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
	}

	public static String sendSms4Qcb(String content,String mobile){
		StringBuilder url = new StringBuilder();
		//牛投理财
		String uid = "70212974";
		String pwd = "acd50ce3f96114043e138b04074eeb40";
		//自学酷
//		String uid = "70208580";
//		String pwd = "b3aae0eeeb290755569d22c167c77f63";
		//新疆师范大学
//		String uid = "70211188";
//		String pwd = "04ad2d52a0176ab18ed14e468bf70661";
		url.append("http://api.duanxin.cm/?");
		try {
			content = URLEncoder.encode(content, "utf-8");
			// 向StringBuffer追加用户名
			url.append("action=send&username="+uid);
			
			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			url.append("&password="+pwd);

			// 向StringBuffer追加手机号码
			url.append("&phone="+mobile);

			// 向StringBuffer追加消息内容转URL标准码
			url.append("&content="+content);
			
			// 向StringBuffer追加字符集编码
			url.append("&encode=utf8");
			
			// 创建url对象
			URL urls = new URL(url.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) urls.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(urls.openStream()));

			// 返回发送结果
			String inputline = in.readLine();
			return getRsult(inputline);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
	} 
    
    /**
     * 
     * @param result
     * @return
     */
    public static String getRsult(String result){
    	String status = "";
    	try {
    		
    		switch (result) {
			case "100":
				status = 0+"_"+"发送成功";
				break;
			case "101":
				status = result+"_"+"验证失败";
				break;
			case "102":
				status = result+"_"+"短信不足";
				break;
			case "103":
				status = result+"_"+"操作失败";
				break;
			case "104":
				status = result+"_"+"非法字符";
				break;
			case "105":
				status = result+"_"+"内容过多";
				break;
			case "106":
				status = result+"_"+"号码过多";
				break;
			case "107":
				status = result+"_"+"频率过快";
				break;
			case "108":
				status = result+"_"+"号码内容空";
				break;
			case "109":
				status = result+"_"+"账号冻结";
				break;
			case "110":
				status = result+"_"+"禁止频繁单条发送";
				break;
			case "111":
				status = result+"_"+"系统暂定发送";
				break;
			case "112":
				status = result+"_"+"号码不正确";
				break;
			case "114":
				status = result+"_"+"账号被锁定，10分钟后解锁";
				break;
			case "120":
				status = result+"_"+"系统升级";
				break;
			default:
				status = 0+"_"+0;
				break;
			}
    		return status;
		} catch (Exception e) {
			return status;
		}
    }
    
    public static String getXmlStr(){
		
		StringBuilder xmlStr = new StringBuilder("<?xml version='1.0' encoding='GB2312' ?>");//重组xml信息
		
		xmlStr.append("<request>");
		xmlStr.append("<spnumber>09914336811</spnumber>");
		xmlStr.append("<uid>09914336811</uid>");
		xmlStr.append("<pwd>d66e2afd3a7ac1077379822d09ec31af</pwd>");
		xmlStr.append("<msgid>46978785</msgid>");
		xmlStr.append("</request>");
		
		return xmlStr.toString();
	}	
    
    public static String getStatus(){
    	
    	try {
			
    		String xmlStr = getXmlStr();
			
			String url = "http://222.83.7.147:8080/ecs/service/APUserSMS.jws?wsdl";
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			
			call.setTimeout(new Integer(5000));
			call.setTargetEndpointAddress(url);
			call.setOperationName("sendStatQuery");
			call.addParameter("xmlStr", XMLType.XSD_STRING,ParameterMode.IN);
			
			
			call.setReturnType(XMLType.XSD_STRING);
			String result = (String)call.invoke(new Object[]{xmlStr});
			return result;
		} catch (Exception e) {
		} 
    	return "";
    }
    
}