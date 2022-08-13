/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IBkMenuDAO  extends IBaseDAO<BkMenu, String> {
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	List<Entity> getAll(Class<? extends Entity> resultClass);
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);

}
