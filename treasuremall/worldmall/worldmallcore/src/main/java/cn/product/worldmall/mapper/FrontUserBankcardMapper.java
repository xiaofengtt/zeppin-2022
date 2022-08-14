/**
 * 
 */
package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.util.MyMapper;

/**
 *
 */
public interface FrontUserBankcardMapper extends MyMapper<FrontUserBankcard> {
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<FrontUserBankcard> getListByParams(Map<String, Object> params);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCountByParams(Map<String, Object> params);
}
