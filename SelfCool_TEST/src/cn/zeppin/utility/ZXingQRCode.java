package cn.zeppin.utility;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * ZXing生成二维码工具类
 * @author Administrator
 *
 */
public class ZXingQRCode {

	public static Logger logger = LogManager.getLogger(ZXingQRCode.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";

	public static void encoderQRCoder(String content, HttpServletResponse response) {
		try {
			
			int size=120;
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(content, BarcodeFormat.QR_CODE, size, size);
            MatrixToImageWriter.writeToStream(m, "jpg", response.getOutputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
