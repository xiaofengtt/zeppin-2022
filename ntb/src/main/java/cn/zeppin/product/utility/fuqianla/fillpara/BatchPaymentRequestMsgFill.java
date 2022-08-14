package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.BatchPaymentRequest;

/**
 * @Author: Sherlock Blaze
 * @Description:
 * @Date: Create in 11:38 25/09/2017
 * @Modified By:
 */
public class BatchPaymentRequestMsgFill {
    public static BatchPaymentRequest[] fillMsg() {
        BatchPaymentRequest[] batchPaymentRequest = new BatchPaymentRequest[1];
        batchPaymentRequest[0] = new BatchPaymentRequest();
//        batchPaymentRequest[1] = new BatchPaymentRequest();
//        batchPaymentRequest[2] = new BatchPaymentRequest();

        for (BatchPaymentRequest element : batchPaymentRequest) {
            /**
             * <account>CE0001234567910000499CNY003692</account><accountFlag>0</accountFlag>
             <accountName>郭志强</accountName><accountNo>6216600100001705977</accountNo>
             <accountType>0</accountType><acqId>01</acqId>
             <amount>0.01</amount><assgCdtMop></assgCdtMop>
             <bankId>0104</bankId><bizId>T32820170919103324141</bizId>
             <bizTp>01</bizTp><ccy>CNY</ccy><channelId>00000000999</channelId><city>110100</city>
             <description>批量代付</description><exMerchId></exMerchId><extend1>extend1</extend1><extend2>extend2</extend2>
             <merchId>0000180</merchId><mobileNum>15011581332</mobileNum>
             <notifyUrl>http://10.100.140.62:8902/fire-phoenix/notice.htm</notifyUrl>
             <openBankName>工商银行</openBankName><paymentTp>06</paymentTp><pmtChnTp>THIRD</pmtChnTp>
             <productInfo>批量代付</productInfo><productNum>10</productNum><province>110000</province>
             <remark>批量代付</remark><reserveFlag>0</reserveFlag><reserveTime>20170919104824</reserveTime>
             <sendTime>20170919103324</sendTime><signInfo>db8c2f28a226121f91ddda472d0658fe</signInfo>
             <versionNo>1.03</versionNo>
             * */
            element.setBizId(System.currentTimeMillis()+"");
//            element.setAccount("CE0001234567910000499CNY003692");
            element.setAccountFlag("0");
            element.setAccountName("刘瑞豪");
            element.setAccountNo("6212260200017492170");
            element.setAccountType("0");
            element.setAcqId("01");
            element.setAmount(0.01);
            element.setBankId("0104");
            element.setBizTp("01");
            element.setCcy("CNY");
            element.setCity("110100");
            element.setDescription("批量代付");
            element.setExtend1("extend1");
            element.setExtend2("extend2");
            element.setMobileNum("13439221210");
            element.setNotifyUrl("https://547a11ed.ngrok.io/NTB/rest/web/notice/chanpinBuyNotice");
            element.setOpenBankName("工商银行");
            element.setPaymentTp("04");//批量06 单笔04
            element.setPmtChnTp("THIRD");
            element.setProductInfo("单笔代付");
            element.setProductNum(10);
            element.setProvince("110000");
            element.setRemark("批量代付");
            element.setReserveFlag("0");
            element.setReserveTime("20170919104824");
            element.setSendTime("20170919103324");
            element.setVersionNo("1.06");

            element.setMerchId(FuqianlaUtlity.MERCHANT_ID);
            element.setChannelId(FuqianlaUtlity.CHANNEL_ID);


        }

        return batchPaymentRequest;
    }
}
