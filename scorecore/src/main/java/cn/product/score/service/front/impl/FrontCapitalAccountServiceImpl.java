package cn.product.score.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformStatus;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformTransType;
import cn.product.score.service.front.FrontCapitalAccountService;
import cn.product.score.vo.front.CapitalAccountVO;

@Service("frontCapitalAccountService")
public class FrontCapitalAccountServiceImpl implements FrontCapitalAccountService{

	@Autowired
	private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;

	@Override
	public void platformList(InputParams params, DataResult<Object> result) {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("status", CapitalPlatformStatus.NORMAL);
		searchMap.put("transType", CapitalPlatformTransType.RECHARGE);
		List<CapitalPlatform> list = capitalPlatformDao.getListByParams(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String capitalPlatform = paramsMap.get("capitalPlatform") == null ? "" : paramsMap.get("capitalPlatform").toString();
    	
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("capitalPlatform", capitalPlatform);
		searchMap.put("status", CapitalAccountStatus.NORMAL);
		
		List<CapitalAccount> list = capitalAccountDao.getListByParams(searchMap);
		List<CapitalAccountVO> voList = new ArrayList<CapitalAccountVO>();
		for(CapitalAccount ca : list){
			voList.add(new CapitalAccountVO(ca));
		}
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
}
