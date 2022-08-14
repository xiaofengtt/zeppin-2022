/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IFundDAO extends IBaseDAO<Fund, String> {
	
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
	 * 获取活期理财分状态列表
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 验证同名的活期理财信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	boolean isExistFundByName(String name, String uuid);
	
	/**
	 * 验证同编号的活期理财是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	boolean isExistFundByScode(String scode, String uuid);
}
