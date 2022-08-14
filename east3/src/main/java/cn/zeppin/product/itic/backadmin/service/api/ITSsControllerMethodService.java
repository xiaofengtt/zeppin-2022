/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.service.base.IBaseService;

public interface ITSsControllerMethodService extends IBaseService<TSsControllerMethod, String> {
	/**
	 * 根据参数查询结果
	 * @param inputParams
	 * @return
	 */
	public List<TSsControllerMethod> getList(Map<String, String> inputParams);
	
}
