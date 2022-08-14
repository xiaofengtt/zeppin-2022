/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;

public interface ITSsControllerMethodDAO extends IBaseDAO<TSsControllerMethod, String> {
	
	/**
	 * 根据参数查询结果
	 * @param inputParams
	 * @return  List<TSsControllerMethod>
	 */
	List<TSsControllerMethod> getList(Map<String, String> inputParams);
}
