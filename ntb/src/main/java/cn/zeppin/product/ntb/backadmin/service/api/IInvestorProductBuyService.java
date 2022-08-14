/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;
/**
 *
 */
public interface IInvestorProductBuyService extends IBaseService<InvestorProductBuy, String> {
	/**
	 * 根据参数查询InvestorProductBuy总数
	 * @param inputParams
	 * @return Integer
	 */
	Integer getCount(Map<String, String> inputParams);
	
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
	
	public void updateBatch(List<InvestorProductBuy> listUpdate, List<InvestorProductBuy> listInsert);
	
	public void updateStage();
	
	/**
	 * 申购列表查询
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass);
	
	/**
	 * 根据参数查询InvestorProductBuy总数
	 * @param inputParams
	 * @return Integer
	 */
	Integer getAllUserCount(Map<String, String> inputParams);
	
	/**
	 * 申购列表查询
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	List<Entity> getAllUserListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	
}
