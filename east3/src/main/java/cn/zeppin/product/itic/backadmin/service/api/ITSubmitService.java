/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.Map;




public interface ITSubmitService {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return  List<TArea>
	 */
	 Map<String, String> submitDatas(String[] types,String starttime, String endtime, String time);
	 
}
