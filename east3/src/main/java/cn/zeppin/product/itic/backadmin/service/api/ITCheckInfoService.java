/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.service.base.IBaseService;


public interface ITCheckInfoService extends IBaseService<TCheckInfo, String> {
	
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
	 * @return  List<TCheckInfo>
	 */
	 List<TCheckInfo> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts);
	 
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @return  List<Object[]>
	 */
	 List<Object[]> getCountListForUser(Map<String, String> inputParams);
		 
	 /**
	  * 审核
	  * @param checkList
	  * @param status
	  * @param TSsOperatorVO
	  */
	 void check(List<TCheckInfo> checkList, String status, TSsOperatorVO operator);
	 
	 /**
	  * 批量添加
	  * @param datasMap
	  * @param operator
	  * @return
	  */
	 void insertAll(Map<String, List<Map<String, Object>>> datasMap, TSsOperatorVO operator);
	 
	 /**
	  * 同步数据
	  */
	 Map<String,Object> sync(TSsOperatorVO operator, Map<String,Boolean> datatypeMap);
	 
	 /**
	  * 中间库同步数据
	  */
	 Map<String,Object> middleSync(TSsOperatorVO operator, Map<String,Boolean> datatypeMap);
}
