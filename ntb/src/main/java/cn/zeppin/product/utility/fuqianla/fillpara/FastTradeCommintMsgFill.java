package cn.zeppin.product.utility.fuqianla.fillpara;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.FastTradeCommitRequest;

/**
 * 初始化快捷交易提交请求数据
 */
public class FastTradeCommintMsgFill {
    public static FastTradeCommitRequest fillMsg() {
    	DateFormat formart = new SimpleDateFormat("yyyyMMddHHmmss");
        FastTradeCommitRequest fastTradeCommitRequest = new FastTradeCommitRequest();
        fastTradeCommitRequest.setAccount(null);//测试信息，自己调整 (可不传)
//        fastTradeCommitRequest.setAccountNo(Main.accountId);
        fastTradeCommitRequest.setAcqId("01");
        fastTradeCommitRequest.setAmount(50000.01);
//        fastTradeCommitRequest.setBankId(Main.bankId);
//        fastTradeCommitRequest.setBizId(System.currentTimeMillis()+"1512461625410");//测试信息，自己调整  (和短信单号保持一致)
        fastTradeCommitRequest.setBizId("1512471220173");
        fastTradeCommitRequest.setBizTp("99");
        fastTradeCommitRequest.setCcy("CNY");
        fastTradeCommitRequest.setChannelId(FuqianlaUtlity.CHANNEL_ID);
//        fastTradeCommitRequest.setCustomerName(Main.name);
        fastTradeCommitRequest.setDescription("2017042710244403179");
//        fastTradeCommitRequest.setIdentNo(Main.cardId);
        fastTradeCommitRequest.setMerchId(FuqianlaUtlity.MERCHANT_ID);
//        fastTradeCommitRequest.setMobileNum(Main.phone);//测试信息，自己调整
        fastTradeCommitRequest.setNotifyUrl("https://547a11ed.ngrok.io/NTB/rest/web/notice/chanpinBuyNotice");
        fastTradeCommitRequest.setPaymentTp("02");
        fastTradeCommitRequest.setProductInfo("LTB_一次性结算_RR");
        fastTradeCommitRequest.setSendTime(formart.format(new Date()));
        fastTradeCommitRequest.setSmsCode("859100");//测试信息，自己调整  (依赖短信结果)024607
        fastTradeCommitRequest.setTokenCd("12051853490314NUmFT4jfp2");//测试信息，自己调整  (依赖短信结果)12051620560314s7Iu6el9IY
        fastTradeCommitRequest.setUserID("1");//测试信息，自己调整
        fastTradeCommitRequest.setUserIP("127.0.0.1");
        fastTradeCommitRequest.setVersionNo("1.06");

        return fastTradeCommitRequest;
    }
}
