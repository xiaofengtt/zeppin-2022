package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.data.GatewayPaymentReqV1_06;

/**
 * @Author: Sherlock Blaze
 * @Description:
 * @Date: Create in 16:19 28/09/2017
 * @Modified By:
 */
public class B2CReqMsgFill {
    public static GatewayPaymentReqV1_06 fillMsg() {
        GatewayPaymentReqV1_06 gatewayB2CPay = new GatewayPaymentReqV1_06();
        gatewayB2CPay.setAccount("fajitjeakfda");

        return gatewayB2CPay;
    }
}
