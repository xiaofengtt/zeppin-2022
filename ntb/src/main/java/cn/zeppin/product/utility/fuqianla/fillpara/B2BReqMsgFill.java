package cn.zeppin.product.utility.fuqianla.fillpara;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.GatewayPaymentReqV1_06;

/**
 * @author Sherlock Blaze
 * @date 05/11/2017-7:29 AM
 * @description
 */
public class B2BReqMsgFill {
    public static GatewayPaymentReqV1_06 fillMsg() {
        GatewayPaymentReqV1_06 gatewayB2B = new GatewayPaymentReqV1_06();

        gatewayB2B.setVersionNo("1.06");
        gatewayB2B.setAmount(2.01);
        gatewayB2B.setPaymentTp("50");//支付类型 50-B2B支付
        gatewayB2B.setAcqId("01");//终端号01-Web 02-POS 03-APP
        gatewayB2B.setCardTp("01");//借贷标示01-借记卡 02-贷记卡 03-借贷不分,默认是借记卡

        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String curString = sdf.format(curDate);
        gatewayB2B.setSendTime(curString);//提交时间

        gatewayB2B.setUserIP("192.168.1.157");//用户IP
//        gatewayB2B.setBankId("102100099996");//银行编号(付款方)
        gatewayB2B.setBizTp("01");//业务类型 01-投资
        gatewayB2B.setBankId("308584000013");

        gatewayB2B.setChannelId("00000000008");//渠道ID
        gatewayB2B.setMerchId("Z0080379");//商户Id
        gatewayB2B.setMerchId(FuqianlaUtlity.MERCHANT_ID);
        gatewayB2B.setBizId("sherlockblaze01625");//订单号
        gatewayB2B.setNotifyUrl("http://auto.test.fuqianla.net/Notification/notice");
        gatewayB2B.setReturnUrl("http://auto.test.fuqianla.net/Notification/notice");

        return gatewayB2B;
    }
}
