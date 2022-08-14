/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TArea;
import cn.zeppin.product.itic.core.service.base.IBaseService;


public interface ITAreaService extends IBaseService<TArea, String> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return  List<TArea>
	 */
	 List<TArea> getList(Map<String, String> inputParams);
	 
}
