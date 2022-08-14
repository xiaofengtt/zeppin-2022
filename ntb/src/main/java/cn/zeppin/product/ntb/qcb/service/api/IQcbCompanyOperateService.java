/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbCompanyOperateService extends IBaseService<QcbCompanyOperate, String> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据企业获取信息
	 * @param qcbCompany
	 * @return
	 */
	public QcbCompanyOperate getByQcbCompany(String qcbCompany);
	
	/**
	 * 企财宝添加认证信息
	 * @param qcbCompanyOperate
	 * @return
	 */
	public QcbCompanyOperate qcbCompanyInsert(QcbCompanyOperate qcbCompanyOperate);
	
	/**
	 * 企财宝更新认证信息
	 * @param qcbCompanyOperate
	 * @return
	 */
	public QcbCompanyOperate qcbCompanyUpdate(QcbCompanyOperate qcbCompanyOperate);
	
	/**
	 * 企财宝企业认证审核
	 * @param qcbCompanyOperateCheck
	 * @return
	 */
	public void check(QcbCompanyOperate qco) throws TransactionException;
}
