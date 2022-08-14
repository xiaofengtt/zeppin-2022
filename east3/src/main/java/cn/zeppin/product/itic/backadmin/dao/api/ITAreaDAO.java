/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TArea;

public interface ITAreaDAO extends IBaseDAO<TArea, String> {

	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return  List<TArea>
	 */
	 List<TArea> getList(Map<String, String> inputParams);
}
