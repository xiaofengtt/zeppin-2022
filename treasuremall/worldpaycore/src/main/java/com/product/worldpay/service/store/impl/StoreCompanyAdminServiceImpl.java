package com.product.worldpay.service.store.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.CompanyAdminDao;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.entity.CompanyAdmin.CompanyAdminStatus;
import com.product.worldpay.service.store.StoreCompanyAdminService;
import com.product.worldpay.util.PasswordHelper;

@Service("storeCompanyAdminService")
public class StoreCompanyAdminServiceImpl implements StoreCompanyAdminService{
	
	@Autowired
	private CompanyAdminDao companyAdminDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin companyAdmin = companyAdminDao.get(uuid);
		if(companyAdmin == null || CompanyAdminStatus.DELETE.equals(companyAdmin.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company admin not exist !");
			return;
		}
		result.setData(companyAdmin);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void password(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyAdmin companyAdmin = (CompanyAdmin) paramsMap.get("companyAdmin");
		String password = paramsMap.get("password").toString();
		String newPassword = paramsMap.get("newPassword").toString();
		
		CompanyAdmin ca = companyAdminDao.get(companyAdmin.getUuid());
		if(companyAdmin == null || CompanyAdminStatus.DELETE.equals(companyAdmin.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company admin not exist !");
			return;
		}
		if(!ca.getPassword().equals(PasswordHelper.getEncryptPassword(companyAdmin, password))){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("old password error !");
			return;
		}
		
		ca.setPassword(newPassword);
		PasswordHelper.encryptPassword(ca);
		companyAdminDao.update(ca);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("edit successed !");
	}
	
	@Override
	public void getByMobile(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String username = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	CompanyAdmin ca = this.companyAdminDao.getByMobile(username);
    	
    	result.setData(ca);
    	result.setStatus(ResultStatusType.SUCCESS);
	}
}
