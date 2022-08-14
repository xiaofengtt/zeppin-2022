package cn.zeppin.product.utility.fuqianla.fillpara;

import cn.zeppin.product.utility.fuqianla.data.BatchReceiptRequest;

/**
 * @Author: Sherlock Blaze
 * @Description:
 * @Date: Create in 19:05 27/09/2017
 * @Modified By:
 */
public class BatchReceiptRequestMsgFill {
    public static BatchReceiptRequest fillMsg(){
        BatchReceiptRequest batchReceiptRequest = new BatchReceiptRequest();

        batchReceiptRequest.setAccountFlag("0");
        batchReceiptRequest.setAccountName("陶闯");
        batchReceiptRequest.setLoanProtocol("11");
        batchReceiptRequest.setAccountNo("6222083500005973139");
        batchReceiptRequest.setAcqId("01");
        batchReceiptRequest.setAmount(0.01);
        batchReceiptRequest.setBankId("0102");
        batchReceiptRequest.setBizId("sherlockBlazeManYo");
        batchReceiptRequest.setBizTp("99");
        batchReceiptRequest.setChannelId("00000000008");
        batchReceiptRequest.setIdentNo("232330199601113011");
        batchReceiptRequest.setIdentType("111");
        batchReceiptRequest.setMerchId("0080340");
        batchReceiptRequest.setMobileNum("15546351139");
        batchReceiptRequest.setNotifyUrl("http://mlfcf.cn/interfaces/assets/notify");
        batchReceiptRequest.setPaymentTp("06");
        batchReceiptRequest.setPmtChnTp("THIRD");
        batchReceiptRequest.setReserveFlag("0");
        batchReceiptRequest.setReserveTime("20170927033302");
        batchReceiptRequest.setSendTime("20170927033302");
        batchReceiptRequest.setVersionNo("1.06");

        return batchReceiptRequest;
    }
}
