/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.zeppin.product.itic.core.entity.TmsList;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.IBaseService;

/**
 * 
 * 
 * @author L
 * @since 2018年04月16日
 */
public interface ITmsListService extends IBaseService<TmsList, Integer> {
	
    /**
     * 通过主键得到对象
     * @param uuid
     * @return
     */
	public TmsList get(Integer uuid);

	/**
	 * 添加
	 * @param bkController
	 * @return
	 */
	public TmsList insert(TmsList area);
	
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
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<TmsList> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);

	 /**
	  * 处理异步通知
	  * @param resultMap
	  * @param Jsonmap
	  * @param map
	 * @throws Exception 
	  */
	 void noticeProcess(Map<String, Object> resultMap, JSONObject Jsonmap, Map<String, Object> map) throws Exception;
	 
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForSearchPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCountForSearchPage(Map<String, String> inputParams);
}
