/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;
/**
 *
 */
public interface IQcbCompanyHistoryOperateService extends IBaseService<QcbCompanyHistoryOperate, String> {
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
	 * @param companyAccountOperate
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(QcbCompanyHistoryOperate QcbCompanyHistoryOperate) throws TransactionException;
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
}
