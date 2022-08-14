package cn.product.worldmall.control;

import java.lang.reflect.Method;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.api.base.ResultManager;
import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.service.ControlService;
import cn.product.worldmall.util.NormalException;

/**
 * 后台服务接口接入 
 * (interfaceClass = ControlService.class, version="${worldmall.service.version}", group="${worldmall.service.apigroup}")
 */
@Service(interfaceClass = ControlService.class, version="${worldmall.service.version}", group="${worldmall.service.apigroup}")
public class ControlServiceImpl implements ControlService, BeanFactoryAware {
	private static final Logger logger = LoggerFactory.getLogger(ControlServiceImpl.class);
	@Autowired
	private BeanFactory factory;
	
	@Override
	public Result execute(InputParams params) {
		DataResult<Object> result = ResultManager.createDataResult("Successful!");
		try {
			if(params != null) {
				logger.info(params.getService()+"----"+params.getMethod());
				Object object = factory.getBean(params.getService());
				Method mth = object.getClass().getMethod(params.getMethod(), InputParams.class, DataResult.class);
				mth.invoke(object, params, result);
				logger.info("------------"+result.getData()+"--"+result.getMessage());
			}else {
				throw new NormalException("InputParams can't be null !!!");
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error("ControlServiceImpl error!!", e);
			result = ResultManager.createDataResult("Failed!"+e.getMessage());
			result.setStatus(ResultStatusType.FAILED);
		}
		return result;
	}

	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.factory = factory;
	}

}
