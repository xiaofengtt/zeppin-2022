/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;

public interface ITCheckInfoDAO extends IBaseDAO<TCheckInfo, String> {

	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return  List<TCheckInfo>
	 */
	 List<TCheckInfo> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @return  List<Object[]>
	 */
	List<Object[]> getCountListForUser(Map<String, String> inputParams);
	
	/**
	 * 根据实际数据删除
	 * @param inputParams
	 * @param inputParams
	 */
	void deleteByData(String datatype, String dataid);
}
