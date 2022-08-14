package cn.product.payment.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

/**
 * 文件操作代码
 * 
 * @author cn.outofmemory
 * @date 2013-1-7
 */
@SuppressWarnings("restriction")
@Component
public class ImageBase64Utils {
	
	private static String fileBasePath;
	@Value("${app.filePath}")  
	public void setFilePath(String path) {
		ImageBase64Utils.fileBasePath = path;
	}
	
	public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = fileBasePath + imgPath;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		String encode = null; // 返回Base64编码过的字节数组字符串
		
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			// 读取图片字节数组
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			 encode = encoder.encode(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return encode;
	}
}
