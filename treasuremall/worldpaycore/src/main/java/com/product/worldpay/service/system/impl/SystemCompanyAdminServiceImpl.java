/**
 * 
 */
package com.product.worldpay.service.system.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.CompanyAdminDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.entity.CompanyAdmin.CompanyAdminStatus;
import com.product.worldpay.service.system.SystemCompanyAdminService;
import com.product.worldpay.util.PasswordHelper;

@Service("systemCompanyAdminService")
public class SystemCompanyAdminServiceImpl implements SystemCompanyAdminService {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAdminDao companyAdminDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyAdminDao.getCountByParams(searchMap);
		List<CompanyAdmin> list = companyAdminDao.getListByParams(searchMap);
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	
    	CompanyAdmin ca = companyAdminDao.get(uuid);
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company admin not exist !");
			return;
		}
		result.setData(ca);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyAdmin companyAdmin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		if(!CompanyAdminStatus.NORMAL.equals(companyAdmin.getStatus()) && !CompanyAdminStatus.DISABLE.equals(companyAdmin.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		Company company = this.companyDao.get(companyAdmin.getCompany());
		if(company == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company not exist !");
			return;
		}
		
		CompanyAdmin ca = this.companyAdminDao.getByMobile(companyAdmin.getMobile());
		if(ca != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("mobile number is existed !");
			return;
		}
		
		companyAdmin.setUuid(UUID.randomUUID().toString());
		PasswordHelper.encryptPassword(companyAdmin);
		companyAdmin.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.companyAdminDao.insert(companyAdmin);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyAdmin companyAdmin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		CompanyAdmin ca = companyAdminDao.get(companyAdmin.getUuid());
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company admin not exist !");
			return;
		}
		
		if(!CompanyAdminStatus.NORMAL.equals(companyAdmin.getStatus()) && !CompanyAdminStatus.DISABLE.equals(companyAdmin.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		CompanyAdmin tca = this.companyAdminDao.getByMobile(companyAdmin.getMobile());
		if(tca != null && !tca.getUuid().equals(companyAdmin.getUuid())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("mobile number is existed !");
			return;
		}
		
		ca.setUsername(companyAdmin.getUsername());
		ca.setEmail(companyAdmin.getEmail());
		ca.setMobile(companyAdmin.getMobile());
		ca.setStatus(companyAdmin.getStatus());
		this.companyAdminDao.update(ca);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("edit successed !");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!CompanyAdminStatus.NORMAL.equals(status) && !CompanyAdminStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		CompanyAdmin companyAdmin = companyAdminDao.get(uuid);
		if (companyAdmin == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company admin not exist !");
			return;
		}
		
		companyAdmin.setStatus(status);
		this.companyAdminDao.update(companyAdmin);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
