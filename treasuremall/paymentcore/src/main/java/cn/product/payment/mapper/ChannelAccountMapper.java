/**
 * 
 */
package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.util.MyMapper;

/**
 *
 */
public interface ChannelAccountMapper extends MyMapper<ChannelAccount> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<ChannelAccount> getListByParams(Map<String, Object> params);
	
	/**
	 * 重启暂停账号
	 * @return
	 */
	public void rebootAllSuspend();
}
