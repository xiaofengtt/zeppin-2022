/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IBankFinancialProductInvestOperateDAO extends IBaseDAO<BankFinancialProductInvestOperate, String> {
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 获取银行理财产品投资操作分状态列表
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取银行理财产品投资操作分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
}
