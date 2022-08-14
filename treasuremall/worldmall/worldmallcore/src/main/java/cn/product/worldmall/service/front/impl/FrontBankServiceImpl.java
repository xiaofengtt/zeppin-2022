package cn.product.worldmall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.Bank;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.Bank.BankStatus;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.service.front.FrontBankService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.BankVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("frontBankService")
public class FrontBankServiceImpl implements FrontBankService{
	
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private SystemParamDao systemParamDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", BankStatus.NORMAL);
		searchMap.put("type", type);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		Integer totalResultCount = bankDao.getCountByParams(searchMap);
		List<Bank> list = bankDao.getListByParams(searchMap);
		
		List<BankVO> listvo = new ArrayList<BankVO>();
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		for(Bank bank : list) {
			//界面返回封装对象
			BankVO bankVO = new BankVO(bank);
			
			//资源信息
			Resource resource = resourceDao.get(bank.getLogo());
			if (resource != null) {
				bankVO.setLogoUrl(pathUrl+resource.getUrl());
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
