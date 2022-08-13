package cn.zeppin.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


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
		String uid = "70208580";
		String pwd = "b3aae0eeeb290755569d22c167c77f63";
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
			
			return inputline;
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
