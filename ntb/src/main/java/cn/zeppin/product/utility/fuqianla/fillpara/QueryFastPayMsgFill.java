package cn.zeppin.product.utility.fuqianla.fillpara;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.QueryFastPayRequest;

public class QueryFastPayMsgFill {
    public static QueryFastPayRequest fillMsg(){
    	DateFormat formart = new SimpleDateFormat("yyyyMMddHHmmss");
        QueryFastPayRequest queryFastPayRequest = new QueryFastPayRequest();
        queryFastPayRequest.setChannelId(FuqianlaUtlity.CHANNEL_ID);
        queryFastPayRequest.setMerchId(FuqianlaUtlity.MERCHANT_ID);
        queryFastPayRequest.setSendTime(formart.format(new Date()));
        queryFastPayRequest.setVersionNo("1.06");
        queryFastPayRequest.setBizId("1512463773393"); //测试数据，自己修改
        return queryFastPayRequest;
    }
}
