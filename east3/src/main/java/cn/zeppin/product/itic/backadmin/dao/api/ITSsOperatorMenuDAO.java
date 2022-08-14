/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;


import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMenu;

/**
 * @author hehe
 *
 */
public interface ITSsOperatorMenuDAO extends IBaseDAO<TSsOperatorMenu, String> {
	/**
	 * 获取角色获取权限列表
	 * @param inputParams
	 * @return List<TSsOperatorMenu>
	 */
	List<TSsOperatorMenu> getList(Map<String, String> inputParams);
	
	/**
	 * 删除用户的所有权限
	 * @param operator
	 * @return 
	 */
	void deleteByOperator(TSsOperator operator);
	
}
