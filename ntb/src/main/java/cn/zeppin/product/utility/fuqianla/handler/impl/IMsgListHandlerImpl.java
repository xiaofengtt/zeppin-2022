package cn.zeppin.product.utility.fuqianla.handler.impl;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.StringDealUtil;
import cn.zeppin.product.utility.fuqianla.data.base.RequestData;
import cn.zeppin.product.utility.fuqianla.datastruct.IMsgList;
import cn.zeppin.product.utility.fuqianla.datastruct.impl.MsgListForPay3_0Impl;
import cn.zeppin.product.utility.fuqianla.handler.IMsgListHandler;

/**
 * 处理IMsgList中数据
 *
 * @author Sherlock Blaze
 * @create 2017-11-05-5:01 AM
 */

public class IMsgListHandlerImpl implements IMsgListHandler {

    private final static Set<String> typeSet;
//    private final static SignStrChoose signStrChoose;

    private String signName;
    private String signWay;

    static {
        typeSet = new HashSet<String>(2);
        typeSet.add("java.lang.Double");
        typeSet.add("double");
//        signStrChoose = new SignStrChoose();
    }

    public IMsgListHandlerImpl(String signName, String signWay) {
        this.signName = signName;
        this.signWay = signWay;
    }

    private static Logger logger = LoggerFactory.getLogger(IMsgListHandlerImpl.class);

    @SuppressWarnings("rawtypes")
	@Override
    public IMsgList<String, Object> returnReqPara(List<RequestData> requestDataList, String seed) throws Throwable {
        RequestData requestData = requestDataList.get(0);

        Class cls = requestData.getClass();

        Field[] fields = cls.getDeclaredFields();

        IMsgList<String, Object> msgList = new MsgListForPay3_0Impl<String, Object>();

        Object objValue = null;
        String fieldType;
        Object value = null;

        for (RequestData element : requestDataList) {
            for (Field fieldElement : fields) {
                String fieldName = fieldElement.getName();

                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }

                fieldElement.setAccessible(true);

//                取出对应字段的值
                objValue = fieldElement.get(element);
//                获取对应字段的数据类型
                fieldType = fieldElement.getType().getName();
//                对Value进行赋值
                value = objValue != null ? objValue : "";

                if ("".equals(value)) {
                    continue;
                }

                if (typeSet.contains(fieldType)) {
                    msgList.put(fieldName, keep2Value(Double.parseDouble(value + "0")));
                } else {
                    msgList.put(fieldName, String.valueOf(value));
                }

            }

            logger.info("请求信息加密的方式为\t" + signWay + "\t请求字段名为\t" + signName);
            msgList.put(signName, signStrChoose(msgList, signWay, seed));

        }

        return msgList;
    }
    
    /**
     * @param iMsgList 请求参数列表
     * @param signWay  签名方式
     * @param seed     秘钥
     * @author Sherlock Blaze
     * @date 05/11/2017-6:36 AM
     * @description 调用对应的加密方式
     */
    @SuppressWarnings("rawtypes")
	private static String signStrChoose(IMsgList iMsgList, String signWay, String seed) {
        String signStr;
        String signInfo;

        switch (signWay) {
            case FuqianlaUtlity.MD5_TYPE:
                signStr = iMsgList.getSignStrForMD5().append(seed).toString();
                logger.info("MD5加密前字符串" + signStr);
                signInfo = StringDealUtil.md5Sign(signStr);
                logger.info("MD5加密得到的字符串为" + signInfo);
                return signInfo;
            default:
                return null;
        }
    }
    
    public static String keep2Value(double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }
}
