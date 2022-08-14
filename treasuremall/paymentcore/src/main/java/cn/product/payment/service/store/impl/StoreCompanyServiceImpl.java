/**
 * 
 */
package cn.product.payment.service.store.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.service.store.StoreCompanyService;
import cn.product.payment.vo.store.CompanyVO;

/**
 * 商户
 * @author Administrator
 *
 */
@Service("storeCompanyService")
public class StoreCompanyServiceImpl implements StoreCompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
    	
    	Company company = companyDao.get(admin.getCompany());
    	CompanyAccount ca = companyAccountDao.get(admin.getCompany());
		if (company == null || ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户不存在！");
			return;
		}
		
		CompanyVO vo = new CompanyVO(company, ca);
		
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
			result.setMessage("商户不存在！");
			return;
		}
		
		c.setCompanyPublic(company.getCompanyPublic());
		c.setCompanyPrivate(null);
		c.setWhiteUrl(company.getWhiteUrl());
		
		this.companyDao.update(c);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
}
