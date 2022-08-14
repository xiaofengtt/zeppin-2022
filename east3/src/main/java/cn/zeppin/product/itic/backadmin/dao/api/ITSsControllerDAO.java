/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsController;

public interface ITSsControllerDAO extends IBaseDAO<TSsController, String> {
	
	/**
	 * 获取全部功能列表
	 * @return  List<TKjXtxmzzkjqkmb>
	 */
	 List<TSsController> getAll();
}
