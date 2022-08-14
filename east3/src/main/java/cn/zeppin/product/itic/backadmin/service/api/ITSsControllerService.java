/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;

import cn.zeppin.product.itic.core.entity.TSsController;
import cn.zeppin.product.itic.core.service.base.IBaseService;

public interface ITSsControllerService extends IBaseService<TSsController, String> {
	
	/**
	 * 获取全部功能列表
	 * @return  List<TKjXtxmzzkjqkmb>
	 */
	 List<TSsController> getAll();
}
