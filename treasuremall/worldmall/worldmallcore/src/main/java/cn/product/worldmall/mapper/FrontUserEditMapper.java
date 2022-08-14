/**
 * 
 */
package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserEdit;
import cn.product.worldmall.util.MyMapper;

/**
 *
 */
public interface FrontUserEditMapper extends MyMapper<FrontUserEdit> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<FrontUserEdit> getListByParams(Map<String, Object> params);
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<FrontUserEdit> getLeftListByParams(Map<String, Object> params);
}
