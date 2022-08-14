package com.product.worldpay.control;

import java.lang.reflect.Method;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.product.worldpay.controller.base.ApiResult;
import com.product.worldpay.controller.base.ApiResult.ApiResultStatus;
import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.controller.base.ResultManager;
import com.product.worldpay.util.api.ApiResultUtlity.ApiResultCode;
import com.product.worldpay.util.api.PaymentException;

/**
 * 后台服务接口接入 
 * 
 */
@Service(interfaceClass = ControlService.class, version="${payment.service.version}", group="${payment.service.group}")
public class ControlServiceImpl implements ControlService, BeanFactoryAware {

	@Autowired
	private BeanFactory factory;
	
	@Override
	public Result execute(InputParams params) {
		DataResult<Object> result = ResultManager.createDataResult("api success !");
		result.setStatus(ResultStatusType.SUCCESS);
		try {
			if(params != null) {
				Object object = factory.getBean(params.getService());
				Method mth = object.getClass().getMethod(params.getMethod(), InputParams.class, DataResult.class);
				mth.invoke(object, params, result);
			}else {
				throw new PaymentException("InputParams can't be null !!!");
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error("ControlServiceImpl error!!", e);
			result = ResultManager.createDataResult("api error ! "+e.getMessage());
			result.setStatus(ResultStatusType.FAILED);
		}
		return result;
	}
	
	@Override
	public Result apiExecute(InputParams params) {
		ApiResult result = ResultManager.createApiResult(null, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_SUCCESS);
		try {
			if(params != null) {
				Object object = factory.getBean(params.getService());
				Method mth = object.getClass().getMethod(params.getMethod(), InputParams.class, ApiResult.class);
				mth.invoke(object, params, result);
			}else {
				throw new PaymentException("InputParams can't be null !!!");
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error("ControlServiceImpl error!!", e);
			result = ResultManager.createApiResult(null, ApiResultStatus.FAIL, ApiResultCode.TRADE_ERROR);
		}
		return result;
	}
	
	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.factory = factory;
	}

}
