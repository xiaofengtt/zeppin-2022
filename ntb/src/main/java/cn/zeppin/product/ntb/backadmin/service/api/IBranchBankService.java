/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IBranchBankService extends IBaseService<BranchBank, String> {
	
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
	 * 验证同名的支行信息是否已经存在
	 * @param bank
	 * @param name
	 * @return
	 */
	public boolean isExistBranchBank(String bank, String name, String uuid);
}
