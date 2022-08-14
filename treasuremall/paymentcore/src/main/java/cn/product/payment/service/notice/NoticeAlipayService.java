package cn.product.payment.service.notice;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface NoticeAlipayService {
	
	public void companyNotice(InputParams params, DataResult<Object> result);
}
