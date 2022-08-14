package cn.zeppin.product.utility.fuqianla.fillpara;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.FastMsgRequest;

/**
 * 添加快捷短信请求参数
 * */
public class FastMsgFill {
    public static FastMsgRequest fillMsg(){
        /**
         * http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayGetMsgV1_06.htm?
         * accountNo=6216617800011109554&
         * acqId=01&amount=0.01&bankId=0104&
         * cardTp=01&channelId=00000000008&
         * customerName=文昌学&identNo=46000719881218499X
         * &identType=111&merchId=0080340&mobileNum=13876430318
         * &paymentTp=11&reqId=1509691978&sendTime=20171103145258
         * &txnTp=07&userID=1&versionNo=1.06&zo3n0wl2m7tsedxkmv4wxo7cat7muz3pz1lo0td3rhl1fulfbnri2434hvqw7k93
         * &signInfo=a0ea721d98add65148429b34216ea8a0
         * */
    	DateFormat formart = new SimpleDateFormat("yyyyMMddHHmmss");
    	long time = System.currentTimeMillis();
        FastMsgRequest fastMsgRequest = new FastMsgRequest();
        fastMsgRequest.setAccountNo("6214830164014572");//测试信息，自己调整
        fastMsgRequest.setAcqId("01");
        fastMsgRequest.setAmount(10.01);//测试信息，自己调整
        fastMsgRequest.setBankId("0308");//测试信息，自己调整
        fastMsgRequest.setCardTp("01");
        fastMsgRequest.setCcy("CNY");
        fastMsgRequest.setChannelId(FuqianlaUtlity.CHANNEL_ID);
        fastMsgRequest.setCustomerName("刘运涛");//测试信息，自己调整
        fastMsgRequest.setIdentNo("372330199210225111");//测试信息，自己调整
        fastMsgRequest.setIdentType("111");
        fastMsgRequest.setMerchId(FuqianlaUtlity.MERCHANT_ID);
        fastMsgRequest.setMobileNum("18612033494");//测试信息，自己调整
        fastMsgRequest.setPaymentTp("11");
        fastMsgRequest.setReqId(String.valueOf(time));
        fastMsgRequest.setSendTime(formart.format(new Date(time)));
        fastMsgRequest.setTxnTp("07");
        fastMsgRequest.setUserID("2");//测试信息，自己调整
        fastMsgRequest.setVersionNo("1.06");
//        fastMsgRequest.setAuthId("0102217011712051619571117fA4UDZ1L");

        return fastMsgRequest;
    }
    public static void main(String[] args) {
    	DateFormat formart = new SimpleDateFormat("yyyyMMddHHmmss");
    	System.out.println(formart.format(new Date(System.currentTimeMillis())));
	}
}
