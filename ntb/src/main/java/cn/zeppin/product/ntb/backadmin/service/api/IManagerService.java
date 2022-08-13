/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.Manager;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IManagerService extends IBaseService<Manager, String> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);

	/**
	 * 验证同身份证的主理人信息是否已经存在
	 * @param idcard
	 * @param uuid
	 * @return
	 */
	public boolean isExistManagerByIdcard(String idcard, String uuid);
	
	/**
	 * 验证同手机的主理人信息是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistManagerByMobile(String mobile, String uuid);
	
	/**
	 * 删除主理人
	 * @param uuid
	 * @return 
	 */
	public Manager deleteManager(Manager manager);
}
