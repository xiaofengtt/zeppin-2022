/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IShbxSecurityOrderService extends IBaseService<ShbxSecurityOrder, String> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	
	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	public void insertBatch(List<ShbxSecurityOrder> listOrder);

	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 批量更新
	 * @param listUpdate
	 */
	public void updateBatch(List<ShbxSecurityOrder> listUpdate);
}
