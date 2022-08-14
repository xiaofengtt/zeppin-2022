/**
 * 
 */
package com.product.worldpay.service.store.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.service.store.StoreCompanyService;
import com.product.worldpay.vo.store.CompanyVO;

@Service("storeCompanyService")
public class StoreCompanyServiceImpl implements StoreCompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
    	
    	Company company = companyDao.get(admin.getCompany());
		if (company == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company not exist !");
			return;
		}
		
		CompanyVO vo = new CompanyVO(company);
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Company company = (Company) paramsMap.get("company");
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
    	
    	Company c = companyDao.get(admin.getCompany());
		if (company == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company not exist !");
			return;
		}
		
		c.setCompanyPublic(company.getCompanyPublic());
		c.setCompanyPrivate(null);
		c.setWhiteUrl(company.getWhiteUrl());
		
		this.companyDao.update(c);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("edit successed !");
	}
}
