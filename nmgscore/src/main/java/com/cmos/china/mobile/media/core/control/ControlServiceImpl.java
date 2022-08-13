package com.cmos.china.mobile.media.core.control;

import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.dao.DataAccessException;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.service.IControlService;
import com.cmos.core.util.ControlConstants;

/**
 * 后台服务统一接入
 */
public class ControlServiceImpl implements IControlService, BeanFactoryAware {
	private static Logger logger = LoggerFactory.getServiceLog(ControlServiceImpl.class);
	private BeanFactory factory;

	public void setBeanFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public OutputObject execute(InputObject inputObject) {
		OutputObject outputObject = new OutputObject(ControlConstants.RETURN_CODE.IS_OK);
		try {
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
			if (inputObject != null) {
				Object object = factory.getBean(inputObject.getService());
				Method mth = object.getClass().getMethod(inputObject.getMethod(), InputObject.class, OutputObject.class);
				mth.invoke(object, inputObject ,outputObject);
			} else {
				throw new Exception("InputObject can't be null !!!");
			}
		} catch (Exception e) {
			// 异常处理
			logger.error("ControlServiceImplError",e);
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			if(e.getCause() instanceof DataAccessException){
				outputObject.setReturnMessage("数据操作失败!");
            }else {
            	outputObject.setReturnMessage(e.getMessage() == null ? e.getCause().getMessage() : e.getMessage());
            }
		} finally{
			//saveUserOperLog(start, System.currentTimeMillis(), inObj, outObj);
		}
		return outputObject;
	}
}
