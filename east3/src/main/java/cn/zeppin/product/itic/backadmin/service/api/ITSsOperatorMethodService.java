/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMethod;
import cn.zeppin.product.itic.core.service.base.IBaseService;

public interface ITSsOperatorMethodService extends IBaseService<TSsOperatorMethod, String> {
	
	/**
	 * 根据参数查询结果
	 * @param inputParams
	 * @return
	 */
	public List<TSsOperatorMethod> getList(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果
	 * @param inputParams
	 * @return
	 */
	public void updateAll(List<TSsOperator> operators, List<TSsControllerMethod> methods, List<String> menus);
	
	/**
	 * 通过用户获取权限
	 * @param operator
	 * @return
	 */
	public List<TSsOperatorMethod> getByOperator(TSsOperator operator);
}
