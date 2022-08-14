/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IQcbEmployeeProductBuyAgreementDAO extends IBaseDAO<QcbEmployeeProductBuyAgreement, String> {
	/**
	 * 根据参数查询QcbEmployeeAccountHistory总数
	 * @param inputParams
	 * @return Integer
	 */
	Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 public Boolean getCheckScode(String scode);
}
