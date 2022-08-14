/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IQcbCompanyAccountHistoryDAO extends IBaseDAO<QcbCompanyAccountHistory, String> {
	
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
	 * 根据参数查询提现结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	List<Entity> getListForWithdrawPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	
	/**
	 * 校验订单号是否已存在
	 * @param orderNum
	 * @return
	 */
	Boolean checkOrderNum(String orderNum);
	
	/**
	 * 统计用查询语句
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	
	/**
	 * 企财宝企业提现状态列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	List<Entity> getWithdrawStatusList(Map<String, String> searchMap, Class<? extends Entity> resultClass);
}
