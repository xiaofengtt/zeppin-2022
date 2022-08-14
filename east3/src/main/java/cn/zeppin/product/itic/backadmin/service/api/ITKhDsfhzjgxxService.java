/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TKhDsfhzjgxx;
import cn.zeppin.product.itic.core.service.base.IBaseService;


public interface ITKhDsfhzjgxxService extends IBaseService<TKhDsfhzjgxx, String> {
	
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return  List<Entity>
	 */
	 List<TKhDsfhzjgxx> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);
	 
	 /**
	  * 批量添加
	  * @param datasList
	  * @param operator
	  * @return
	  */
	 void insertAll(List<Map<String, Object>> datasList, TSsOperatorVO operator);

	/**
	  * 删除数据
	  */
	void deleteWholeData(TKhDsfhzjgxx t, TSsOperatorVO operator);
}
