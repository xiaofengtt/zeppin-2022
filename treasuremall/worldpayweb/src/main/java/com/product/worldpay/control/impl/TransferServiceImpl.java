package com.product.worldpay.control.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.product.worldpay.control.ControlService;
import com.product.worldpay.control.TransferService;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.controller.base.ResultManager;

@Service("transferService")
public class TransferServiceImpl implements TransferService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
	
	@Reference(interfaceClass = ControlService.class, version="${payment.service.version}", group="${payment.service.coregroup}")
	private ControlService controlService;

	@Override
	public Result execute(InputParams params) {
		
		try {
			logger.info("service=" + params.getService() + ", method=" + params.getMethod());
			return this.controlService.execute(params);
		} catch (Exception e) {
			logger.error("ControlServiceImplError", e);
			return ResultManager.createDataResult(null);
		}
	}

	@Override
	public Result apiExecute(InputParams params) {
		try {
			logger.info("service=" + params.getService() + ", method=" + params.getMethod());
			return this.controlService.apiExecute(params);
		} catch (Exception e) {
			logger.error("ControlServiceImplError", e);
			return ResultManager.createApiResult();
		}
	}

}
