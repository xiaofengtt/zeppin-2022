package cn.zeppin.product.utility.fuqianla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zeppin.product.utility.fuqianla.datastruct.IMsgList;

/**
 * @author Sherlock Blaze
 * @date 05/11/2017-2:10 AM
 * @description 请求参数拼接
 */
public class HttpReqPrepare {
    private static final Logger logger = LoggerFactory.getLogger(HttpReqPrepare.class);

    /**
     * @author Sherlock Blaze
     * @date 05/11/2017-3:05 AM
     * @description 拼接XML参数
     */
    public static String combineForPost(IMsgList<String, Object> params) throws Exception {
        StringBuffer sb = new StringBuffer();
        String reqStr;
        if (params != null) {
            sb.append(params.getReqPostFormStr());

            String temp = sb.toString();
            reqStr = temp.substring(0, temp.length() - 1);
        } else {
            logger.info("参数异常");
            throw new Exception("参数异常");
        }

        return reqStr;
    }

    public static String combineForXml(IMsgList<String, Object> params, String[] requestHeader) throws Exception {
        StringBuffer sb = new StringBuffer(requestHeader[0]);

        if (params != null) {
            sb.append(params.getReqXmlStr());
            sb.append(requestHeader[1]);
        } else {
            logger.info("参数异常");
            throw new Exception("参数异常");
        }

        return sb.toString();
    }
}
