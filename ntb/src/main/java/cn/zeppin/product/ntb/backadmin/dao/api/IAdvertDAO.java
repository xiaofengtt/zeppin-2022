/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.Advert;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */
public interface IAdvertDAO extends IBaseDAO<Advert, String> {

	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * 
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return List<Entity>
	 */
	List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * 
	 * @param inputParams
	 * @return
	 */
	Integer getCount(Map<String, String> inputParams);

	/**
	 * 将所有数据状态设为无效
	 */
	void updateStatusToInvalid();
}
