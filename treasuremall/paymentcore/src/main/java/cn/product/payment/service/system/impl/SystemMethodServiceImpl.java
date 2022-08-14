package cn.product.payment.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.MethodDao;
import cn.product.payment.service.system.SystemMethodService;

/**
 * 方法
 */
@Service("systemMethodService")
public class SystemMethodServiceImpl implements SystemMethodService{

    @Autowired
    private MethodDao methodDao;
    
   /**
    * 初始化shiro权限
    */
	@Override
	public void loadFilterChainDefinitions(InputParams params, DataResult<Object> result) {
		result.setData(this.methodDao.loadFilterChainDefinitions());
		result.setStatus(ResultStatusType.SUCCESS);
	}

}
