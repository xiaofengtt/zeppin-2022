/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author L
 *
 */
public interface ITnumberRelationLogDAO extends IBaseDAO<TnumberRelationLog, Integer> {

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
	
}
