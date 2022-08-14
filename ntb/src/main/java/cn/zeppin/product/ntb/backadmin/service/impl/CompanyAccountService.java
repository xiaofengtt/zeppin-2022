/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class CompanyAccountService extends BaseService implements ICompanyAccountService {

	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Override
	public CompanyAccount insert(CompanyAccount companyAccount) {
		return companyAccountDAO.insert(companyAccount);
	}

	@Override
	public CompanyAccount delete(CompanyAccount companyAccount) {
		return companyAccountDAO.delete(companyAccount);
	}

	@Override
	public CompanyAccount update(CompanyAccount companyAccount) {
		return companyAccountDAO.update(companyAccount);
	}

	@Override
	public CompanyAccount get(String uuid) {
		return companyAccountDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询CompanyAccount结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return companyAccountDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return companyAccountDAO.getCount(inputParams);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return companyAccountDAO.getTypeList(inputParams, resultClass);
	}

	@Override
	public void updateBatch(List<CompanyAccount> listUpdate) {
		for(CompanyAccount ca : listUpdate){
			this.companyAccountDAO.update(ca);
		}
	}
	
	/**
	 * 验证企业账户号是否已经存在
	 * @param accountNum
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistAccountNum(String name, String uuid) {
		return companyAccountDAO.isExistAccountNum(name, uuid);
	}

}
