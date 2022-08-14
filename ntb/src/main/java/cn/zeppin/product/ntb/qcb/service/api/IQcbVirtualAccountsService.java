/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbVirtualAccountsService extends IBaseService<QcbVirtualAccounts, String> {
	
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
	 * 获取分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 批量添加
	 * @param companyAccount
	 * @param start
	 * @param end
	 * @param creator
	 */
	public void batchAdd(String companyAccount, String start, String end, String creator);
	
	/**
	 * 批量删除
	 * @param companyAccount
	 * @param start
	 * @param end
	 */
	public void batchDelete(String companyAccount, String start, String end);
}
