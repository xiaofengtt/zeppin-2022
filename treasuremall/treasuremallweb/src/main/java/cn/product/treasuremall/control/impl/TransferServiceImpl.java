package cn.product.treasuremall.control.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.api.base.ResultManager;
import cn.product.treasuremall.api.service.ControlService;
import cn.product.treasuremall.control.TransferService;

@Service("transferService")
public class TransferServiceImpl implements TransferService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
	
	@Reference(interfaceClass = ControlService.class, version="${treasuremall.service.version}", group="${treasuremall.service.apigroup}")
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
