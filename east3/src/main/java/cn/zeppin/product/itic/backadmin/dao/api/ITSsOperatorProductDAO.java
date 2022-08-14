/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperatorProduct;

public interface ITSsOperatorProductDAO extends IBaseDAO<TSsOperatorProduct, String> {

	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return  List<TSsOperatorProduct>
	 */
	 List<TSsOperatorProduct> getList(Map<String, String> inputParams);

}
