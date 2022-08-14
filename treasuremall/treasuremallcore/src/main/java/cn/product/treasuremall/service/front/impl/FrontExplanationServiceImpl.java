package cn.product.treasuremall.service.front.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.service.front.FrontExplanationService;

@Service("frontExplanationService")
public class FrontExplanationServiceImpl implements FrontExplanationService{
	
	
	@Autowired
	private SystemParamDao systemParamDao;


	@Override
	public void recharge(InputParams params, DataResult<Object> result) {
		SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_EXPLANATION);
		String explanation = "";
		if(sp != null) {
			explanation = sp.getParamValue();
		}
		result.setData(explanation);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功");
		return;
	}


	@Override
	public void withdraw(InputParams params, DataResult<Object> result) {
		SystemParam sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_EXPLANATION);
		String explanation = "";
		if(sp != null) {
			explanation = sp.getParamValue();
		}
		result.setData(explanation);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功");
		return;
	}
	
}
