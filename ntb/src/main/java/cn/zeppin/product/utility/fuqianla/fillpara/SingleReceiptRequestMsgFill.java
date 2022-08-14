package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.data.SingleReceiptRequest;

/**
 * @author Sherlock Blaze
 * @date 05/11/2017-4:27 AM
 * @description
 */

public class SingleReceiptRequestMsgFill {
	 public static SingleReceiptRequest fillMsg(){
		   SingleReceiptRequest bean = new SingleReceiptRequest();
		    bean.setAccountFlag("0");
			bean.setAccountName("肖艳云");
			bean.setAccountNo("6212260200067551594");
		    bean.setAccountType("0");
			bean.setAcqId("01"); 
			bean.setAmount(0.01); 
			bean.setBankId("0102");
		    bean.setBizId("175181811432950");
			bean.setBizTp("01");
			bean.setCcy("CNY");
			bean.setChannelId("O001");
		    bean.setDescription("新结算-测试开发");
			bean.setIdentNo("362426199007168448");
			bean.setIdentType("111");
		    bean.setMerchId("0000105"); 
			bean.setMobileNum("15011126537");
		    bean.setNotifyUrl("http://10.100.140.59:8080/regression/notice");
		    bean.setPaymentTp("03");
			bean.setProductInfo("ordertool"); 
		    bean.setProductNum(10);
			bean.setRemark("万洪冰");
		    bean.setSendTime("20170918181143"); 
		    bean.setVersionNo("1.01");
			return bean;
	 }
}
