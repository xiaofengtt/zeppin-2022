/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsMenu;

/**
 * @author hehe
 *
 */
public interface ITSsMenuDAO  extends IBaseDAO<TSsMenu, String> {
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	List<TSsMenu> getAll();
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	List<TSsMenu> getList(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	List<TSsMenu> getListForOperator(Map<String, String> inputParams);
	
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);

}
