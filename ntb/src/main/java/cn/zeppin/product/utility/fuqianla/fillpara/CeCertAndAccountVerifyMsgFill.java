package cn.zeppin.product.utility.fuqianla.fillpara;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.CeCertAndAccountVerifyRequest;

public class CeCertAndAccountVerifyMsgFill {
    public static CeCertAndAccountVerifyRequest fillMsg() {
    	DateFormat formart = new SimpleDateFormat("yyyyMMddHHmmss");
        CeCertAndAccountVerifyRequest bean = new CeCertAndAccountVerifyRequest();
        bean.setAccountFlag("0");
        bean.setVersionNo("1.01");
        bean.setAcqId("01");
        bean.setChannelId(FuqianlaUtlity.CHANNEL_ID);
        bean.setMerchId(FuqianlaUtlity.MERCHANT_ID);
        bean.setBizId(System.currentTimeMillis()+"");
        bean.setPaymentTp("43");
        bean.setAccountFlag("0");
        bean.setSendTime(formart.format(new Date()));
//        bean.setAccountName(Main.name);   //测试信息，自己调整
//        bean.setAccountNo(Main.accountId); //测试信息，自己调整
//        bean.setIdentType("111");
//        bean.setIdentNo(Main.cardId); //测试信息，自己调整
//        bean.setRemark("");
//        bean.setBankId(Main.bankId); //测试信息，自己调整
//        bean.setMobileNum(Main.phone); //测试信息，自己调整

        return bean;
    }
    
}
