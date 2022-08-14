package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.Bank;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.Bank.BankStatus;
import cn.product.worldmall.service.back.BankService;
import cn.product.worldmall.vo.back.BankVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("bankService")
public class BankServiceImpl implements BankService{
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的银行信息的总数
		Integer totalResultCount = bankDao.getCountByParams(searchMap);
		//查询符合条件的银行信息列表
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

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取银行信息
		Bank bank = bankDao.get(uuid);
		if (bank == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		//界面返回封装对象
		BankVO bankVO = new BankVO(bank);
		
		//资源信息
		Resource resource = resourceDao.get(bank.getLogo());
		if (resource != null) {
			bankVO.setLogoUrl(resource.getUrl());
		}
		result.setData(bankVO);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String shortName = paramsMap.get("shortName") == null ? "" : paramsMap.get("shortName").toString();
		String logo = paramsMap.get("logo") == null ? "" : paramsMap.get("logo").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String icon = paramsMap.get("icon") == null ? "" : paramsMap.get("icon").toString();
		String color = paramsMap.get("color") == null ? "" : paramsMap.get("color").toString();
		String iconColor = paramsMap.get("iconColor") == null ? "" : paramsMap.get("iconColor").toString();
		//验证是否有重名的情况
		if (bankDao.isExistBankByName(name,null)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该名称的银行已存在！");
			return;
		}
		
		//创建银行信息
		Bank bank = new Bank();
		bank.setUuid(UUID.randomUUID().toString());
		bank.setName(name);
		bank.setShortName(shortName);
		bank.setStatus(status);
		bank.setLogo(logo);
		bank.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		bank.setColor(color);
		bank.setIcon(icon);
		bank.setIconColor(iconColor);
		
		bankDao.insert(bank);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String shortName = paramsMap.get("shortName") == null ? "" : paramsMap.get("shortName").toString();
		String logo = paramsMap.get("logo") == null ? "" : paramsMap.get("logo").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String icon = paramsMap.get("icon") == null ? "" : paramsMap.get("icon").toString();
		String color = paramsMap.get("color") == null ? "" : paramsMap.get("color").toString();
		String iconColor = paramsMap.get("iconColor") == null ? "" : paramsMap.get("iconColor").toString();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取银行信息
		Bank bank = bankDao.get(uuid);
		if(bank != null && uuid.equals(bank.getUuid())){
			//验证是否有重名的情况
			if (bankDao.isExistBankByName(name,uuid)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该名称的银行已存在！");
				return;
			}
			
			//修改银行信息
			bank.setName(name);
			bank.setShortName(shortName);
			bank.setLogo(logo);
			bank.setStatus(status);
			
			bank.setColor(color);
			bank.setIcon(icon);
			bank.setIconColor(iconColor);
			
			bankDao.update(bank);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("保存成功");
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取银行信息
		Bank bank = bankDao.get(uuid);
		if(bank != null && uuid.equals(bank.getUuid())){
			//删除银行信息
			bank.setStatus(BankStatus.DELETE);
			bank.setName(bank.getName() + "_#" + bank.getUuid());
			bankDao.update(bank);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("删除成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}
	
}
