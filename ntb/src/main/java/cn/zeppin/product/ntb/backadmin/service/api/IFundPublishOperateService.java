/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.FundPublishOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IFundPublishOperateService extends IBaseService<FundPublishOperate, String> {
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
	 * 审核
	 * @param fundPublishOperate
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(FundPublishOperate fundPublishOperate) throws TransactionException;
	
	/**
	 * 获取募集产品操作分状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 获取募集产品操作分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
}