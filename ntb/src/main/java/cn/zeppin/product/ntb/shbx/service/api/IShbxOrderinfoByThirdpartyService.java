/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IShbxOrderinfoByThirdpartyService extends IBaseService<ShbxOrderinfoByThirdparty, String> {
	
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
	
	public void insertBatch(List<ShbxOrderinfoByThirdparty> listOrder);

}
