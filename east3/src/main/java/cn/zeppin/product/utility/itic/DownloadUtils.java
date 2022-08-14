package cn.zeppin.product.utility.itic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zeppin.product.utility.FileUtlity;

public class DownloadUtils {

	private static Logger logger = LoggerFactory.getLogger(DownloadUtils.class);

	// 通过url下载
	public static int DownloadByUrl(String urlStr, String fileName, String filePath, String params) {
		int result = 0;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为10秒
			conn.setConnectTimeout(10 * 1000);
			// post请求必须设置为true,因为post请求参数是否写在http正文中
			conn.setDoOutput(true);
			// 设置是否从HttpURLConnection读入内容，默认为true
			conn.setDoInput(true);
			// 设置是否使用缓存，post请求不使用缓存
			conn.setUseCaches(false);
			// 设置请求方法 注意大小写!
			conn.setRequestMethod("POST");
			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
			conn.setRequestProperty("Charset", "utf-8");

			// 重点部分，设置参数
			if (!params.equals("")) {
				logger.info("UrlParams: " + params);
				OutputStream os = conn.getOutputStream();
				os.write(params.getBytes());
				os.flush();
				os.close();
			}

			// 得到输入流
			InputStream inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);
			// 文件保存位置
			File saveDir = new File(filePath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			File file = new File(saveDir + File.separator + fileName);
			result = Integer.parseInt(FileUtlity.checkFileReal(inputStream));
			if (result == 1) {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(getData);
				if (fos != null) {
					fos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if ("1".equals(FileUtlity.checkFileReal(file))) {
					result = 1;
					logger.info("============Download success!============");
				} else {
					result = 0;
					logger.info("============Download fail!============");
				}
			} else {
				result = 0;
				logger.info("============Download fail!============");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============Download Error!============", e.getCause());
			result = 0;
		}
		return result;
	}

	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}
