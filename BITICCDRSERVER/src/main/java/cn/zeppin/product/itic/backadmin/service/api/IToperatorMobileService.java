/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.IBaseService;

/**
 * 
 * 
 * @author L
 * @since 2018年04月16日
 */
public interface IToperatorMobileService extends IBaseService<ToperatorMobile, Integer> {
	
    /**
     * 通过主键得到对象
     * @param uuid
     * @return
     */
	public ToperatorMobile get(Integer uuid);

	/**
	 * 添加
	 * @param bkController
	 * @return
	 */
	public ToperatorMobile insert(ToperatorMobile area);
	
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
	 List<ToperatorMobile> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);

	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCountForNumPage(Map<String, String> inputParams);
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForNumPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	 
	 void insertProcess(ToperatorMobile tm);
}
