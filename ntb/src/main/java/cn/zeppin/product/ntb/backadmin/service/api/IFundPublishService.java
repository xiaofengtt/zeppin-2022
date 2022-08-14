/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IFundPublishService extends IBaseService<FundPublish, String> {
	
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
	
	/**
	 * 更新活期理财净值
	 */
	public void netvalueUpdate();
	
	/**
	 * 第二天没净值时短信提醒
	 */
	public void netvalueMessage();
	
	/**
	 * 获取活期募集总额
	 */
	public BigDecimal getTotalAmount();
	
	/**
	 * 获取活期余额
	 */
	public BigDecimal getAccountBalance();
}
