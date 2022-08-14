package cn.product.worldmall.control.transfer.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.service.ControlService;
import cn.product.worldmall.control.transfer.TransferService;

public class TransferServiceImpl implements TransferService {
	
	@Reference(interfaceClass = ControlService.class, version="${worldmall.service.version}", group="${worldmall.service.group}")
	private ControlService controlService;
	private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

	@SuppressWarnings("unchecked")
	public void execute(InputParams inputParams, DataResult<Object> result) {
		long start = System.currentTimeMillis();
		logger.info("START INVOKE WEBAPP..."+ "service="+ inputParams.getService() + "   method="+inputParams.getMethod());

		// 转换成Json
		logger.info("inputObject==" + inputParams.getParams());
		// 调用WebApp服务
		DataResult<Object> resultData = (DataResult<Object>) controlService.execute(inputParams);
		result.setData(resultData.getData());
		result.setPageNum(resultData.getPageNum());
		result.setPageSize(resultData.getPageSize());
		result.setTotalPageCount(resultData.getTotalPageCount());
		result.setTotalResultCount(resultData.getTotalResultCount());
		result.setMessage(resultData.getMessage());
		result.setStatus(resultData.getStatus());
		result.setOperation(resultData.getOperation());
		result.setRedirect(resultData.getRedirect());
		result.setUrl(resultData.getUrl());
		long end = System.currentTimeMillis();
		logger.info("END INVOKE WEBAPP..."+ String.valueOf(end - start) + "ms");
	}
}
