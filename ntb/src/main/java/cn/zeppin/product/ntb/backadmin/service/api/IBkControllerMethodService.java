/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * 
 * 
 * @author Clark.R
 * @since 2016年11月26日
 */

public interface IBkControllerMethodService extends IBaseService<BkControllerMethod, String> {
	
	/**
	 * 通过主键得到对象
	 * @param method
	 * @return
	 */
	public BkControllerMethod get(String uuid);

	/**
	 * 添加控制器方法
	 * @param method
	 * @return
	 */
	public BkControllerMethod insert(BkControllerMethod method);
	
	/**
	 * 获取所有功能方法信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass);
	
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
