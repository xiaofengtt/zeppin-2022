/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TSsOperatorMenu;

/**
 * @author hehe
 *
 */
public interface ITSsOperatorMenuService {
	
	/**
	 * 获取角色获取权限列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<TSsOperatorMenu> getList(Map<String, String> inputParams);

}
