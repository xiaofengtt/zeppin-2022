package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.BatchPaymentQueryReqData;

public class BatchPaymentReqQueryMsgFill {
    public static BatchPaymentQueryReqData[] fillMsg() {
        BatchPaymentQueryReqData[] batchPaymentQueryReqData = {
                new BatchPaymentQueryReqData(),
                new BatchPaymentQueryReqData(),
                new BatchPaymentQueryReqData()
        };

        for (BatchPaymentQueryReqData element : batchPaymentQueryReqData) {
            element.setBizId("bingYu007");
            element.setMerchId(FuqianlaUtlity.MERCHANT_ID);
            element.setChannelId(FuqianlaUtlity.CHANNEL_ID);
            element.setSendTime("20170919103324");
            element.setVersionNo("1.06");
        }

        return batchPaymentQueryReqData;
    }
}
