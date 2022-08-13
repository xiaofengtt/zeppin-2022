/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.Manager;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IManagerDAO extends IBaseDAO<Manager, String> {
	
	/**
	 * 根据参数查询manager结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	 /**
	  * 根据参数查询manager个数
	  * @param inputParams
	  * @return
	  */
	 Integer getCount(Map<String, String> inputParams);

	
	/**
	 * 验证同身份证的manager是否已经存在
	 * @param idcard
	 * @param uuid
	 * @return
	 */
	boolean isExistManagerByIdcard(String idcard, String uuid);
	
	/**
	 * 验证同手机号的manager是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	boolean isExistManagerByMobile(String mobile, String uuid);
}
