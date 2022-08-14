package cn.zeppin.product.utility.fuqianla;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送请求
 *
 * @author Sherlock Blaze
 * @create 2017-11-05-2:20 AM
 */

public class HttpReqSend {
    private static final HttpClient httpClient;
    private static PostMethod postMethod = null;

    private static Logger logger = LoggerFactory.getLogger(HttpReqSend.class);
    static {
        httpClient = new HttpClient();
    }

    public HttpReqSend(String url) {
        postMethod = new PostMethod(url);
        System.out.println("send_url:" + url);
    }

	public String httpReqSend(String reqStr, String contentType, String charSet) throws Exception{
//        httpClient.setConnectionTimeout(20000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);

        System.out.println("send_data:" + reqStr);

        postMethod.setRequestEntity(new StringRequestEntity(reqStr, contentType, charSet));

        String responseString = "";

        try {

            // 执行请求
            int statusCode = httpClient.executeMethod(postMethod);

            logger.info("请求发送获得的状态码为:\t" + statusCode);

            // 读取响应信息
            if (statusCode == HttpStatus.SC_OK) {
                BufferedInputStream bis = new BufferedInputStream(postMethod.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte, 0, strByte.length, "utf-8");
                bos.close();
                bis.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        postMethod.releaseConnection();
        return responseString;
    }
}
