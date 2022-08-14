package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.data.B2CQueryRequest;

/**
 * @Author: Sherlock Blaze
 * @Description:
 * @Date: Create in 16:59 28/09/2017
 * @Modified By:
 */
public class B2CQueryMsgFill {
    public static B2CQueryRequest fillMsg(){
        B2CQueryRequest b2cQueryReq = new B2CQueryRequest();

        b2cQueryReq.setBizId("fdajitafa");

        return b2cQueryReq;
    }
}
