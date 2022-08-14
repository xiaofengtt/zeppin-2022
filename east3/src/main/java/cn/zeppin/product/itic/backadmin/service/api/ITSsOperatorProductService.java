/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TSsOperatorProduct;
import cn.zeppin.product.itic.core.service.base.IBaseService;


public interface ITSsOperatorProductService extends IBaseService<TSsOperatorProduct, String> {
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return List<TSsOperatorProduct>
	 */
	 List<TSsOperatorProduct> getList(Map<String, String> inputParams);
	 
}
