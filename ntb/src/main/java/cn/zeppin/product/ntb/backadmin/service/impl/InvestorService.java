/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class InvestorService extends BaseService implements IInvestorService {

	@Autowired
	private IInvestorDAO investorDAO;
	
	@Override
	public Investor insert(Investor investor) {
		return investorDAO.insert(investor);
	}

	@Override
	public Investor delete(Investor investor) {
		investor.setStatus(BankFinancialProductStatus.DELETED);
		return investorDAO.update(investor);
	}

	@Override
	public Investor update(Investor investor) {
		return investorDAO.update(investor);
	}

	@Override
	public Investor get(String uuid) {
		return investorDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Bank结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return investorDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return investorDAO.getCount(inputParams);
	}
	
	/**
	 * 获取银行理财产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return investorDAO.getStatusList(resultClass);
	}
}
