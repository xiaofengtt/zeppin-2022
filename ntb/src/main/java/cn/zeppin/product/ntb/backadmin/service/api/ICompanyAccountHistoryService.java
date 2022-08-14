/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;
/**
 *
 */
public interface ICompanyAccountHistoryService extends IBaseService<CompanyAccountHistory, String> {
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
	
	public void insertBatch(List<CompanyAccountHistory> listInsert);
	
	/**
	 * 获取历史记录分类型列表
	 * @param inputParams
	 * @return
	 */
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
}
