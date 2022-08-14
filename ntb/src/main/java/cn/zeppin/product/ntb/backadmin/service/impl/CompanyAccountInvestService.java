/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountInvestDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountInvestService;
import cn.zeppin.product.ntb.core.entity.CompanyAccountInvest;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class CompanyAccountInvestService extends BaseService implements ICompanyAccountInvestService {

	@Autowired
	private ICompanyAccountInvestDAO companyAccountInvestDAO;
	
	@Override
	public CompanyAccountInvest insert(CompanyAccountInvest companyAccountInvest) {
		return companyAccountInvestDAO.insert(companyAccountInvest);
	}

	@Override
	public CompanyAccountInvest delete(CompanyAccountInvest companyAccountInvest) {
		return companyAccountInvestDAO.delete(companyAccountInvest);
	}

	@Override
	public CompanyAccountInvest update(CompanyAccountInvest companyAccountInvest) {
		return companyAccountInvestDAO.update(companyAccountInvest);
	}

	@Override
	public CompanyAccountInvest get(String uuid) {
		return companyAccountInvestDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询CompanyAccountTransfer结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return companyAccountInvestDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return companyAccountInvestDAO.getCount(inputParams);
	}
}
