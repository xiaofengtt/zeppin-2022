/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.FundRate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IFundService extends IBaseService<Fund, String> {
	
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
	 * 获取基金分状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 新增基金
	 * @param fund
	 * @param fundRateList
	 */
	public void add(Fund fund, List<FundRate> fundRateList);
	
	/**
	 * 编辑基金费率
	 * @param fund
	 * @param fundType
	 * @param fundRateList
	 */
	public void updateFundRate(Fund fund, String fundType, List<FundRate> fundRateList);
}
