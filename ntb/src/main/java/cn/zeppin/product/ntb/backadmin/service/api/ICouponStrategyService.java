/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;
/**
 *
 */
public interface ICouponStrategyService extends IBaseService<CouponStrategy, String> {
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
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
	 * 获取分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 批量处理--更新
	 * @param listUpdate
	 */
	public void updateBatch(List<CouponStrategy> listUpdate);
	
	/**
	 * 验证优惠券策略投放ID是否已经存在
	 * @param name
	 * @return
	 */
	public boolean isExistOperatorByStrategy(String strategy, String uuid);
}
