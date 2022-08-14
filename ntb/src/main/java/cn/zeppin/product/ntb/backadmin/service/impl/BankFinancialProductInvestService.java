/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductInvestService extends BaseService implements IBankFinancialProductInvestService {

	@Autowired
	private IBankFinancialProductInvestDAO bankFinancialProductInvestDAO;
	
	@Override
	public BankFinancialProductInvest insert(BankFinancialProductInvest bankFinancialProductInvest) {
		return bankFinancialProductInvestDAO.insert(bankFinancialProductInvest);
	}

	@Override
	public BankFinancialProductInvest delete(BankFinancialProductInvest bankFinancialProductInvest) {
		return bankFinancialProductInvestDAO.delete(bankFinancialProductInvest);
	}

	@Override
	public BankFinancialProductInvest update(BankFinancialProductInvest bankFinancialProductInvest) {
		return bankFinancialProductInvestDAO.update(bankFinancialProductInvest);
	}

	@Override
	public BankFinancialProductInvest get(String uuid) {
		return bankFinancialProductInvestDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return bankFinancialProductInvestDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductInvestDAO.getCount(inputParams);
	}
//	
//	/**
//	 * 获取银行理财产品投资分状态列表
//	 * @param resultClass
//	 * @return  List<Entity>
//	 */
//	@Override
//	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
//		return bankFinancialProductInvestDAO.getStatusList(resultClass);
//	}
//	
//	/**
//	 * 获取银行理财产品投资分阶段列表
//	 * @param resultClass
//	 * @return  List<Entity>
//	 */
//	@Override
//	public List<Entity> getStageList(Class<? extends Entity> resultClass) {
//		return bankFinancialProductInvestDAO.getStageList(resultClass);
//	}
}