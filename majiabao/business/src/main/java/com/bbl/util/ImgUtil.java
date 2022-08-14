package com.bbl.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImgUtil {

    private String PTAH_PREFIX="D://java//img";
//    private String PTAH_PREFIX="/java/static";
    /**
     * 根据url下载图片到本地
     * @param imgUrl 图片的路径
     */
    private void saveImg(String imgUrl) {
        try {
            //建立URL连接
            URL url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2 * 1000);
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            String path = getImgPath(imgUrl);
            File result = new File(PTAH_PREFIX + path);
            if (!result.exists()) {
                result.mkdirs();
            }
            String fileName = getImgFileName(imgUrl);
            FileOutputStream outStream = new FileOutputStream(result + File.separator + fileName);
            outStream.write(data);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 根据url获取图片的路径
     */
    private String getImgPath(String imgUrl) {
        return imgUrl.substring(imgUrl.indexOf("n/") + 1, imgUrl.lastIndexOf("/"));
    }

    /**
     * 根据url获取图片的名称
     */
    private String getImgFileName(String imgUrl) {
        return imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
    }

}
