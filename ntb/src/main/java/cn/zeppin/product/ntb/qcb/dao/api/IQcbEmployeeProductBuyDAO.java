/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */
public interface IQcbEmployeeProductBuyDAO extends IBaseDAO<QcbEmployeeProductBuy, String> {
	/**
	 * 根据参数查询QcbEmployeeProductBuy总数
	 * @param inputParams
	 * @return Integer
	 */
	Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	List<Entity> getList(Map<String, String> inputParams, String sorts, Class<? extends Entity> resultClass);
	
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
	 * 更新产品确认状态（根据募集产品状态）
	 */
	public void updateConfirmStage();
	
	/**
	 * 更新产品持有状态（根据募集产品状态）
	 */
	public void updatePofitStage();
	
	/**
	 * 更新产品持有状态（根据募集产品状态）
	 */
	public void updateBalanceStage();
	
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
}
