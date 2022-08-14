/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDailyDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductDailyService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductDailyService extends BaseService implements IBankFinancialProductDailyService {

	@Autowired
	private IBankFinancialProductDailyDAO bankFinancialProductDailyDAO;
	@Autowired
	private IBankFinancialProductDAO bankFinancialProductDAO;
	
	@Override
	public BankFinancialProductDaily insert(BankFinancialProductDaily bankFinancialProductDaily) {
		return bankFinancialProductDailyDAO.insert(bankFinancialProductDaily);
	}

	@Override
	public BankFinancialProductDaily delete(BankFinancialProductDaily bankFinancialProductDaily) {
		return bankFinancialProductDailyDAO.delete(bankFinancialProductDaily);
	}

	@Override
	public BankFinancialProductDaily update(BankFinancialProductDaily bankFinancialProductDaily) {
		return bankFinancialProductDailyDAO.update(bankFinancialProductDaily);
	}

	@Override
	public BankFinancialProductDaily get(String uuid) {
		return bankFinancialProductDailyDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询BankFinancialProductDaily总数
	 * @param inputParams
	 * @return Integer
	 */
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductDailyDAO.getCount(inputParams);
	}
	
	/**
	 * 根据参数查询BankFinancialProductDaily结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return bankFinancialProductDailyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 增加每日净值，并同步更新对应的银行理财产品信息
	 * @param bankFinancialProductDaily
	 */
	@Override
	public BankFinancialProductDaily addDaily(BankFinancialProductDaily bankFinancialProductDaily) {
		if(bankFinancialProductDaily != null){
			bankFinancialProductDaily = this.bankFinancialProductDailyDAO.insert(bankFinancialProductDaily);
			if(bankFinancialProductDaily.getBankFinancialProduct() != null){
				BankFinancialProduct bfp = this.bankFinancialProductDAO.get(bankFinancialProductDaily.getBankFinancialProduct());
				if(bfp != null){
					bfp.setNetWorth(bankFinancialProductDaily.getNetValue());
					this.bankFinancialProductDAO.update(bfp);
					return bankFinancialProductDaily;
				} else{
					return null;
				}
			} else {
				return null;
			}
			
		} else {
			return null;
		}
	}
}
