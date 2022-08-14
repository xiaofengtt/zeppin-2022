/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMethod;

public interface ITSsOperatorMethodDAO extends IBaseDAO<TSsOperatorMethod, String> {
	
	/**
	 * 根据参数查询结果
	 * @param inputParams
	 * @return List<TSsOperatorMethod>
	 */
	List<TSsOperatorMethod> getList(Map<String, String> inputParams);
	
	/**
	 * 删除用户的所有权限
	 * @param operator
	 * @return 
	 */
	void deleteByOperator(TSsOperator operator);
	
	/**
	 * 通过用户获取权限
	 * @param operator
	 * @return
	 */
	public List<TSsOperatorMethod> getByOperator(TSsOperator operator);
}
