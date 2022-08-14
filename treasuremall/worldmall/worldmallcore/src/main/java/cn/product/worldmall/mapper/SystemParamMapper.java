/**
 * 
 */
package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.util.MyMapper;

/**
 *
 */
public interface SystemParamMapper extends MyMapper<SystemParam> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<SystemParam> getListByParams(Map<String, Object> params);
}
