/**
 * 
 */
package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.UserRecharge;
import com.product.worldpay.util.MyMapper;
import com.product.worldpay.vo.system.StatusCountVO;

/**
 *
 */
public interface UserRechargeMapper extends MyMapper<UserRecharge> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<UserRecharge> getListByParams(Map<String, Object> params);
	
	/**
	 * 获取待处理数据量
	 */
	public List<StatusCountVO> getCheckingChannelList();
}
