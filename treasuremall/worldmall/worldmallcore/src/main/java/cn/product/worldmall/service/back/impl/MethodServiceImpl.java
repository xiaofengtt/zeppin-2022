package cn.product.worldmall.service.back.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.MethodDao;
import cn.product.worldmall.service.back.MethodService;

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
