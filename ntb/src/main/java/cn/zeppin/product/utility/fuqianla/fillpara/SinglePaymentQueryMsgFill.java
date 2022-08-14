package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.data.SinglePaymentQueryRequest;

public class SinglePaymentQueryMsgFill {
	public static SinglePaymentQueryRequest fillMsg(){
		SinglePaymentQueryRequest queryRequest = new SinglePaymentQueryRequest();
		 queryRequest.setVersionNo("1.06");
		   queryRequest.setChannelId("00000000008");
		   queryRequest.setMerchId("0080340");
		   queryRequest.setBizId("1512555342200");
		   queryRequest.setSendTime(System.currentTimeMillis()+"");
		 //  queryRequest.setSignInfo("d68c5cf64f1ddde64616c1348338175b");
		return queryRequest;
		
	}

}
