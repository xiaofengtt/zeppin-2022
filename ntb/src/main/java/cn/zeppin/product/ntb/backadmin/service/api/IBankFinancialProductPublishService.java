/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IBankFinancialProductPublishService extends IBaseService<BankFinancialProductPublish, String> {
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
	 * 根据参数查询WEB结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getWebListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, LinkedList<String> sortList, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询WEB结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getWebCount(Map<String, String> inputParams);
	
	/**
	 * 获取募集产品分状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取募集产品分阶段列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStageList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 根据时间修改阶段
	 */
	public void updateStage();
	
	/**
	 * 验证同名的募集产品信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	public boolean isExistBankFinancialProductPublishByName(String name, String uuid);
	
	/**
	 * 验证同编号的募集产品信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	public boolean isExistBankFinancialProductPublishByScode(String scode, String uuid);
	
	/**
	 * 批量更新
	 * @param listUpdate
	 */
	public void updateBatch(List<BankFinancialProductPublish> listUpdate);
	
	/**
	 * 查询已发布的产品的银行列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getBankList(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
}
