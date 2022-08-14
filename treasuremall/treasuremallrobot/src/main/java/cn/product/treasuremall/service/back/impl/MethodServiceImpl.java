package cn.product.treasuremall.service.back.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.dao.MethodDao;
import cn.product.treasuremall.service.back.MethodService;

@Service("methodService")
public class MethodServiceImpl implements MethodService{

    @Autowired
    private MethodDao methodDao;

	@Override
	public void loadFilterChainDefinitions(InputParams params, DataResult<Object> result) {
		result.setData(this.methodDao.loadFilterChainDefinitions());
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

}
