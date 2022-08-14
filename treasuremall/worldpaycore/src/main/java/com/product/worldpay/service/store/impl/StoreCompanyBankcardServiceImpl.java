/**
 * 
 */
package com.product.worldpay.service.store.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.CompanyBankcardDao;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.entity.CompanyBankcard;
import com.product.worldpay.entity.CompanyBankcard.CompanyBankcardStatus;
import com.product.worldpay.service.store.StoreCompanyBankcardService;
import com.product.worldpay.vo.store.CompanyBankcardVO;


@Service("storeCompanyBankcardService")
public class StoreCompanyBankcardServiceImpl implements StoreCompanyBankcardService {

	@Autowired
	private CompanyBankcardDao companyBankcardDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
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
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("bankcard not exist !");
			return;
		}
		if(!cb.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission to access !");
			return;
		}
		
		CompanyBankcardVO vo = new CompanyBankcardVO(cb);

		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyBankcard companyBankcard = (CompanyBankcard) paramsMap.get("companyBankcard");
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
    	companyBankcard.setUuid(UUID.randomUUID().toString());
    	companyBankcard.setCompany(admin.getCompany());
    	companyBankcard.setStatus(CompanyBankcardStatus.NORMAL);
    	companyBankcard.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.companyBankcardDao.insert(companyBankcard);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("binding successed !");
	}
	
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null || CompanyBankcardStatus.DELETE.equals(cb.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("bankcard not exist !");
			return;
		}
		if(!cb.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission !");
			return;
		}
		
		cb.setStatus(CompanyBankcardStatus.DELETE);
		this.companyBankcardDao.update(cb);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
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
		if(!cb.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission !");
			return;
		}
		
		cb.setStatus(status);
		this.companyBankcardDao.update(cb);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
