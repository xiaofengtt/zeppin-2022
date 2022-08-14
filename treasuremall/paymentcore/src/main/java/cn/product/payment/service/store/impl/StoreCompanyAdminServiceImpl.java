package cn.product.payment.service.store.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.CompanyAdminDao;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.CompanyAdmin.CompanyAdminStatus;
import cn.product.payment.service.store.StoreCompanyAdminService;
import cn.product.payment.util.PasswordHelper;

/**
 * 商户管理员
 * @author Administrator
 *
 */
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
			result.setMessage("商户管理员不存在");
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
			result.setMessage("商户管理员不存在");
			return;
		}
		if(!ca.getPassword().equals(PasswordHelper.getEncryptPassword(companyAdmin, password))){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("原密码错误！");
			return;
		}
		
		ca.setPassword(newPassword);
		//密码加密
		PasswordHelper.encryptPassword(ca);
		companyAdminDao.update(ca);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
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
