/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.LinkedList;
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
	 List<Entity> getWebListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, LinkedList<String> sortList, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询WEB结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getWebCount(Map<String, String> inputParams);
	
	/**
	 * 获取募集产品分状态列表
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取募集产品分阶段列表
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStageList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 修改认购中阶段（开启认购）
	 */
	void updateStageCollect();
	
	/**
	 * 修改募集中 募集完成（(collect_amount-real_collect)< min_invest_amount）
	 */
	void updateStageUninvestByCollect();
	
	/**
	 * 修改待投资阶段（结束认购）
	 */
	void updateStageUninvest();
	
	/**
	 * 修改待投资阶段（投资结束）
	 */
	void updateStageInvested();
	
	/**
	 * 修改收益中阶段
	 */
	void updateStageProfit();
	
	/**
	 * 修改结算阶段（投资完成）
	 */
	void updateStageBalance();
	
	/**
	 * 修改结算阶段（投资完成）
	 */
	void updateStageBalance4Finish();
	
	/**
	 * 验证同名的募集产品信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	boolean isExistBankFinancialProductPublishByName(String name, String uuid);
	
	/**
	 * 验证同编号的募集产品信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	boolean isExistBankFinancialProductPublishByScode(String scode, String uuid);
	
	/**
	 * 查询已发布的产品的银行列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getBankList(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
}
