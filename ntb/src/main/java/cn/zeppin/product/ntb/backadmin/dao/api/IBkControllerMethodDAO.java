/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */
public interface IBkControllerMethodDAO extends IBaseDAO<BkControllerMethod, String> {
	
	/**
	 * 获取所有功能方法信息
	 * @param resultClass
	 * @return
	 */
	List<Entity> getAll(Class<? extends Entity> resultClass);
	
	/**
	 * 分页获取方法列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass);
	
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
}
