/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.Tcustomers;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.IBaseService;

/**
 * 
 * 
 * @author L
 * @since 2018年04月16日
 */
public interface ITcustomersService extends IBaseService<Tcustomers, Integer> {
	
    /**
     * 通过主键得到对象
     * @param uuid
     * @return
     */
	public Tcustomers get(Integer uuid);

	/**
	 * 添加
	 * @param bkController
	 * @return
	 */
	public Tcustomers insert(Tcustomers area);
	
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
	 List<Tcustomers> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);

}
