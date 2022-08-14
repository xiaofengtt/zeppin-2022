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
import cn.product.score.dao.BankDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.entity.Bank;
import cn.product.score.entity.Bank.BankStatus;
import cn.product.score.entity.Resource;
import cn.product.score.service.front.FrontBankService;
import cn.product.score.vo.front.BankVO;

@Service("frontBankService")
public class FrontBankServiceImpl implements FrontBankService{
	
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
//		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", BankStatus.NORMAL);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		Integer totalResultCount = bankDao.getCountByParams(searchMap);
		List<Bank> list = bankDao.getListByParams(searchMap);
		
		List<BankVO> listvo = new ArrayList<BankVO>();
		for(Bank bank : list) {
			//界面返回封装对象
			BankVO bankVO = new BankVO(bank);
			
			//资源信息
			Resource resource = resourceDao.get(bank.getLogo());
			if (resource != null) {
				bankVO.setLogoUrl(resource.getUrl());
			}
			listvo.add(bankVO);
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}
	
}
