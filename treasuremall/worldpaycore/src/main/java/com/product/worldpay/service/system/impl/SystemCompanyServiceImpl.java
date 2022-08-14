/**
 * 
 */
package com.product.worldpay.service.system.impl;

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
import com.product.worldpay.dao.AdminDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.Company.CompanyStatus;
import com.product.worldpay.service.system.SystemCompanyService;
import com.product.worldpay.util.PaymentUtil;
import com.product.worldpay.util.Utlity;
import com.product.worldpay.vo.system.CompanyVO;

@Service("systemCompanyService")
public class SystemCompanyServiceImpl implements SystemCompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String code = paramsMap.get("code") == null ? null : paramsMap.get("code").toString();
		String name = paramsMap.get("name") == null ? null : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("code", code);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyDao.getCountByParams(searchMap);
		List<Company> list = companyDao.getListByParams(searchMap);
		
		List<CompanyVO> voList = new ArrayList<CompanyVO>();
		for(Company company : list){
			CompanyVO vo = new CompanyVO(company);
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
    	
		Company company = companyDao.get(uuid);
		if (company == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		CompanyVO vo = new CompanyVO(company);
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Company company = (Company) paramsMap.get("company");
		
		if(!CompanyStatus.NORMAL.equals(company.getStatus()) && !CompanyStatus.DISABLE.equals(company.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		if(!Utlity.checkStringNull(company.getCompanyPrivate()) && !Utlity.checkStringNull(company.getCompanyPublic())){
			if(!PaymentUtil.checkKeys(company.getCompanyPrivate(), company.getCompanyPublic())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("company secret error !");
				return;
			}
		}
		
		if(company.getRechargePoundage() == null && company.getRechargePoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("recharge poundage can not null !");
			return;
		}
		
		if(company.getRechargePoundage() != null && company.getRechargePoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("recharge poundage choose only one type !");
			return;
		}
		
		if(company.getWithdrawPoundage() == null && company.getWithdrawPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("withdraw poundage can not null !");
			return;
		}
		
		if(company.getWithdrawPoundage() != null && company.getWithdrawPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("withdraw poundage choose only one type !");
			return;
		}
		
		company.setUuid(UUID.randomUUID().toString());
		Map<String, String> keyResult = companyDao.getCompanyKeys(company);
		company.setSystemPrivate(keyResult.get("privateKey"));
		company.setSystemPublic(keyResult.get("publicKey"));
		
		if(!PaymentUtil.checkKeys(company.getSystemPrivate(), company.getSystemPublic())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("create error, try again later !");
			return;
		}
		
		company.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.companyDao.insertCompany(company);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Company company = (Company) paramsMap.get("company");
		
		if(!CompanyStatus.NORMAL.equals(company.getStatus()) && !CompanyStatus.DISABLE.equals(company.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		if(!Utlity.checkStringNull(company.getCompanyPrivate()) && !Utlity.checkStringNull(company.getCompanyPublic())){
			if(!PaymentUtil.checkKeys(company.getCompanyPrivate(), company.getCompanyPublic())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("company secret error !");
				return;
			}
		}
		
		if(company.getRechargePoundage() == null && company.getRechargePoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("recharge poundage can not null !");
			return;
		}
		
		if(company.getRechargePoundage() != null && company.getRechargePoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("recharge poundage choose only one type !");
			return;
		}
		
		if(company.getWithdrawPoundage() == null && company.getWithdrawPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("withdraw poundage can not null !");
			return;
		}
		
		if(company.getWithdrawPoundage() != null && company.getWithdrawPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("withdraw poundage choose only one type !");
			return;
		}
		
		Company c = companyDao.get(company.getUuid());
		if (c == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		c.setName(company.getName());
		c.setCompanyPrivate(company.getCompanyPrivate());
		c.setCompanyPublic(company.getCompanyPublic());
		c.setWhiteUrl(company.getWhiteUrl());
		c.setRechargePoundage(company.getRechargePoundage());
		c.setRechargePoundageRate(company.getRechargePoundageRate());
		c.setWithdrawPoundage(company.getWithdrawPoundage());
		c.setWithdrawPoundageRate(company.getWithdrawPoundageRate());
		c.setStatus(company.getStatus());
		this.companyDao.update(c);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("edit successed !");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!CompanyStatus.NORMAL.equals(status) && !CompanyStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		Company company = companyDao.get(uuid);
		if (company == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		company.setStatus(status);
		this.companyDao.update(company);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
