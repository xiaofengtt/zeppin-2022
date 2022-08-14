package cn.product.payment.control;

import java.lang.reflect.Method;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.payment.controller.base.ApiResult;
import cn.product.payment.controller.base.ApiResult.ApiResultStatus;
import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.util.api.ApiResultUtlity.ApiResultCode;
import cn.product.payment.util.api.PaymentException;

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
		DataResult<Object> result = ResultManager.createDataResult("接口调用成功！");
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
			result = ResultManager.createDataResult("接口调用失败！"+e.getMessage());
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
