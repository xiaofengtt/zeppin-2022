package cn.zeppin.product.utility.fuqianla.fillpara;
import cn.zeppin.product.utility.fuqianla.data.SingleReceiptQueryRequest;

public class SingleReceiptQueryMsgFill {

	public static SingleReceiptQueryRequest fillMsg(){
		SingleReceiptQueryRequest  bean = new SingleReceiptQueryRequest();
		bean.setBizId("03s2324387");
		bean.setChannelId("13571113171");
		bean.setMerchId("0000360");
		bean.setSendTime("20170814181357");
	//	bean.setSignInfo("84df3c5eeb59ad0bde7c164a79b81bd0");
		bean.setVersionNo("1.05");
		return bean;
		
	}
}
