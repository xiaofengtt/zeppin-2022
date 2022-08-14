/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.service.base.IBaseService;


public interface ITSsLogService extends IBaseService<TSsLog, String> {
	
	/**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return  List<TSsLog>
	 */
	List<TSsLog> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);
	
	/**
	  * 回滚
	  * @return
	  */
	void rollback(TSsLog t, TSsOperatorVO operator);
}
