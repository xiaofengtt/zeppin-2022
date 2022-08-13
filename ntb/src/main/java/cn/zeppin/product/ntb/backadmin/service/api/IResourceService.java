/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.service.base.IBaseService;
/**
 *
 */
public interface IResourceService extends IBaseService<Resource, String> {
	public Resource updateName(Resource resource);
}
