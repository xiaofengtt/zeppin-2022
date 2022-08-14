package cn.zeppin.product.utility.fuqianla.fillpara;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import cn.zeppin.product.utility.fuqianla.FuqianlaUtlity;
import cn.zeppin.product.utility.fuqianla.data.SinglePaymentRequest;
public class SinglePaymentRequestMsgFill {
  
	public static SinglePaymentRequest fillMsg(){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 SinglePaymentRequest bean = new SinglePaymentRequest();
	     bean.setAccountFlag("0");
	     bean.setAccountName("刘运涛");
	     bean.setAccountNo("6214830164014572");
	     bean.setAccountType("0");
	     bean.setAcqId("01");
	     bean.setAmount(10.02);
	     bean.setBankId("0308");
	     bean.setBizId(System.currentTimeMillis()+"");
         bean.setBizTp("99");
         bean.setCcy("CNY");
         bean.setChannelId(FuqianlaUtlity.CHANNEL_ID);
         bean.setCity("130100");
         bean.setDescription("单笔代付");
         bean.setIdentNo("372330199210225111");
         bean.setIdentType("111");
         bean.setMerchId(FuqianlaUtlity.MERCHANT_ID);
         bean.setMobileNum("18612033494");
         bean.setNotifyUrl(FuqianlaUtlity.noticeWithdrawURL);
         bean.setOpenBankName("招商银行");
         bean.setPaymentTp("04");
         bean.setPmtChnTp("THIRD");
         bean.setProductInfo("单笔代付");
         bean.setProvince("130000");
         bean.setRemark("SherlockBlaze Wonderful测试");
         bean.setSendTime(sdf.format((new Timestamp(System.currentTimeMillis()))));
         bean.setVersionNo("1.06");
	     //	bean.setSignInfo("d6d60c8776bce3dde65404f89a5f9420");
			return bean;
	}
}
