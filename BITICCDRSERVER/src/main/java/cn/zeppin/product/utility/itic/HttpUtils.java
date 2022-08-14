package cn.zeppin.product.utility.itic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	public String sendPost(String url,String Params)throws IOException{
		OutputStreamWriter out = null;
		BufferedReader reader = null;
		String response="";
		try {
			URL httpUrl = null; //HTTP URL类 用这个类来创建连接
			//创建URL
			httpUrl = new URL(url);
			//建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setUseCaches(false);//设置不要缓存
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			//POST请求
			out = new OutputStreamWriter(
					conn.getOutputStream());
			out.write(Params);
			out.flush();
			//读取响应
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				response+=lines;
			}
			reader.close();
			// 断开连接
			conn.disconnect();

			logger.info(response.toString());
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(reader!=null){
					reader.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return response;
	}
}
