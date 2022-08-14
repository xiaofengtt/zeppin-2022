/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TJyXtzjmjjfpl;

public interface ITJyXtzjmjjfplDAO extends IBaseDAO<TJyXtzjmjjfpl, String> {

	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return  List<TJyXtzjmjjfpl>
	 */
	 List<TJyXtzjmjjfpl> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);

	 /**
	  * 根据参数更新提交时间
	  * @param inputParams
	  * @param cjrq
	  * @param ctltime
	  * @return
	  */	
	void submitData(Map<String,String> inputParams, String cjrq);
}
