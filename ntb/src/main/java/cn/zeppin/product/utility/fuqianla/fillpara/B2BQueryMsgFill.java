package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.data.GatewayQueryReq4B2B;

/**
 * @Author: Sherlock Blaze
 * @Description:
 * @Date: Create in 16:28 28/09/2017
 * @Modified By:
 */
public class B2BQueryMsgFill {
    public static GatewayQueryReq4B2B fillMsg(){
        GatewayQueryReq4B2B queryReq4B2B = new GatewayQueryReq4B2B();

        queryReq4B2B.setBizId("fdajktjhiafja");

        return queryReq4B2B;
    }
}
