/**
 * 
 */
package com.product.worldpay.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.CompanyBankcardDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyBankcard;
import com.product.worldpay.entity.CompanyBankcard.CompanyBankcardStatus;
import com.product.worldpay.service.system.SystemCompanyBankcardService;
import com.product.worldpay.vo.system.CompanyBankcardVO;


@Service("systemCompanyBankcardService")
public class SystemCompanyBankcardServiceImpl implements SystemCompanyBankcardService {

	@Autowired
	private CompanyBankcardDao companyBankcardDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String bank = paramsMap.get("bank") == null ? null : paramsMap.get("bank").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("bank", bank);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyBankcardDao.getCountByParams(searchMap);
		List<CompanyBankcard> list = companyBankcardDao.getListByParams(searchMap);
		
		List<CompanyBankcardVO> voList = new ArrayList<CompanyBankcardVO>();
		for(CompanyBankcard cb : list){
			CompanyBankcardVO vo = new CompanyBankcardVO(cb);
			
			Company c = this.companyDao.get(cb.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
			}
			
			voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("bankcard not exist !");
			return;
		}
		
		CompanyBankcardVO vo = new CompanyBankcardVO(cb);

		Company c = this.companyDao.get(cb.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
		}
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!CompanyBankcardStatus.NORMAL.equals(status) && !CompanyBankcardStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null || CompanyBankcardStatus.DELETE.equals(cb.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("bankcard not exist !");
			return;
		}
		
		cb.setStatus(status);
		this.companyBankcardDao.update(cb);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
