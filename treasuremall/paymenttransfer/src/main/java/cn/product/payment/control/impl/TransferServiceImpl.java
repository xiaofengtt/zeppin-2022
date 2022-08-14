package cn.product.payment.control.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.product.payment.control.ControlService;
import cn.product.payment.control.TransferService;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;

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

}
