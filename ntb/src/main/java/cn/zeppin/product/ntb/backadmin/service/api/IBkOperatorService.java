package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

public interface IBkOperatorService extends IBaseService<BkOperator, String> {
	
	
	/**
	 * 通过账号得到BkOperator对象
	 * @param loginname
	 * @return BkOperator
	 */
	public BkOperator getByLoginname(String loginname);
	
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
	 * 验证同名的管理员是否已经存在
	 * @param name
	 * @return
	 */
	public boolean isExistOperatorByName(String name, String uuid);
	

	/**
	 * 验证同手机号的管理员是否已经存在
	 * @param mobile
	 * @return
	 */
	public boolean isExistOperatorByMobile(String mobile, String uuid);
}
