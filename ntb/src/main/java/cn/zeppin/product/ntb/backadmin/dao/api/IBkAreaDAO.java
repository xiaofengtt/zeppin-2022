/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */
public interface IBkAreaDAO extends IBaseDAO<BkArea, String> {

	/**
	 * 获取所有省份地区信息
	 * @param resultClass
	 * @return
	 */
	List<Entity> getAll(Class<? extends Entity> resultClass);
}
