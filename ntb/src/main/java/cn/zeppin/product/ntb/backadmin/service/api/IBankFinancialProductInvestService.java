/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IBankFinancialProductInvestService extends IBaseService<BankFinancialProductInvest, String> {
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 获取银行理财产品投资分状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取银行理财产品投资分阶段列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStageList(Class<? extends Entity> resultClass);
}
