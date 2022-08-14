/**
 * 
 */
package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.util.MyMapper;

/**
 *
 */
public interface FrontUserMessageMapper extends MyMapper<FrontUserMessage> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<FrontUserMessage> getListByParams(Map<String, Object> params);
}
