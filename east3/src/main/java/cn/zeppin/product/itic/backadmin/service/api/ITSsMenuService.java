/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TSsMenu;
import cn.zeppin.product.itic.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface ITSsMenuService extends IBaseService<TSsMenu, String>{
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<TSsMenu> getAll();
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<TSsMenu> getListForOperator(Map<String, String> inputParams);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<TSsMenu>
	 */
	public List<TSsMenu> getList(Map<String, String> inputParams);
	
}
