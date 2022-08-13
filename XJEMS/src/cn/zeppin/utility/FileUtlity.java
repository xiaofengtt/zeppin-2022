package cn.zeppin.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


public class FileUtlity {
	
	/**
	 * 获取文件属性
	 * 
	 * @param in
	 *            InputStream
	 * @param imgPath
	 *            图片的保存路径
	 * @param imgName
	 *            图片的名称
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map saveToImgByInputStream(InputStream in, String imgPath,
			String imgName, String path) throws IOException {

		// 将流转成临时存储文件，拿到属性存储在map里面然后删除临时文件。
		Map map = null;
		FileOutputStream fos = null;
		BufferedImage bufferedImage = null;
		File file,f;
		byte[] b = null;
		FileInputStream fis = null;
		try {
			map = new HashMap();
			
			// 将上面生成的图片格式字符串 imgStr，还原成图片显示
			file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
			fos = new FileOutputStream(file);
			b = new byte[1024];
			int nRead = 0;
			while ((nRead = in.read(b)) != -1) {
				fos.write(b, 0, nRead);
			}
			fos.flush();
			map.put("size",file.length());
			bufferedImage = ImageIO.read(file);
			map.put("width", bufferedImage.getWidth());
			map.put("height", bufferedImage.getHeight());
			path = imgPath+"/"+imgName;
			map.put("path", path);
			f=new File(path);
			fis=new FileInputStream(f);
			//获取文件类型
			byte[] bb = new byte[3];
			fis.read(bb, 0, bb.length);
			String str = bytesToHexString(bb);
			String staff = "png";
			if(str != null){
				staff = checkType(str.toUpperCase());
				if("0000".equals(staff)){
					map.put("staff", "error");
				} else {
					map.put("staff", staff);
				}
//				System.out.println(checkType(str));
			} else {
				map.put("staff", "error");
			}
//			imgName += "."+staff;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufferedImage = null;
			file = null;
			if (fos != null) {
				fos.close();
			}
			if (fis != null) {
				fis.close();
				fis = null;
			}
			if (in != null) {
				in.close();
			}
		}
		return map;
	}
	
	/**
	 * 将文件大小b转为mb
	 * 
	 * @param size
	 * @return
	 */
	public static String getPrintSize(long size) {
		// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		// 因为还没有到达要使用另一个单位的时候
		// 接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		// if (size < 1024) {
		// 因为如果以MB为单位的话，要保留最后1位小数，
		// 因此，把此数乘以100之后再取余
		size = size * 100;
		return String.valueOf((size / 100)) + "."
				+ String.valueOf((size % 100)) + "MB";
		/*
		 * } else { //否则如果要以GB为单位的，先除于1024再作同样的处理 size = size * 100 / 1024;
		 * return String.valueOf((size / 100)) + "." + String.valueOf((size %
		 * 100)) + "GB"; }
		 */
	}

	public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
	
	public static String checkType(String str) {
        
        switch (str) {
        case "FFD8FF": return "jpg";
        case "89504E": return "png";
        case "474946": return "gif";

        default: return "0000";
        }
    }
}
