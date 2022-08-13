/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IBankFinancialProductPublishDAO extends IBaseDAO<BankFinancialProductPublish, String> {
	
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
	 * 根据参数查询WEB结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getWebListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询WEB结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getWebCount(Map<String, String> inputParams);
	
	/**
	 * 获取银行理财产品发布分状态列表
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取银行理财产品发布分阶段列表
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStageList(Class<? extends Entity> resultClass);
	
	/**
	 * 修改待投资阶段
	 */
	void updateStageCollect();
	
	/**
	 * 修改收益中阶段
	 */
	void updateStageIncome();
	
	/**
	 * 修改完成投资阶段
	 */
	void updateStageFinished();
	
	/**
	 * 验证同名的银行理财产品发布信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	boolean isExistBankFinancialProductPublishByName(String name, String uuid);
	
	/**
	 * 验证同编号的银行理财产品发布信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	boolean isExistBankFinancialProductPublishByScode(String scode, String uuid);
}
