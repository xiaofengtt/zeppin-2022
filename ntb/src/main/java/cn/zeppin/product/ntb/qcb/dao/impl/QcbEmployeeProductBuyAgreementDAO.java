/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyAgreementDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class QcbEmployeeProductBuyAgreementDAO extends BaseDAO<QcbEmployeeProductBuyAgreement, String> implements IQcbEmployeeProductBuyAgreementDAO {


	/**
	 * 向数据库添加一条QcbEmployeeProductBuyAgreement数据
	 * @param QcbEmployeeProductBuyAgreement
	 * @return QcbEmployeeProductBuyAgreement
	 */
	@Override
	@Caching(put={@CachePut(value = "QcbEmployeeProductBuyAgreements", key = "'QcbEmployeeProductBuyAgreements:' + #QcbEmployeeProductBuyAgreement.uuid")})
	public QcbEmployeeProductBuyAgreement insert(QcbEmployeeProductBuyAgreement QcbEmployeeProductBuyAgreement) {
		return super.insert(QcbEmployeeProductBuyAgreement);
	}

	/**
	 * 向数据库删除一条QcbEmployeeProductBuyAgreement数据
	 * @param QcbEmployeeProductBuyAgreement
	 * @return QcbEmployeeProductBuyAgreement
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "QcbEmployeeProductBuyAgreements", key = "'QcbEmployeeProductBuyAgreements:' + #QcbEmployeeProductBuyAgreement.uuid")})
	public QcbEmployeeProductBuyAgreement delete(QcbEmployeeProductBuyAgreement QcbEmployeeProductBuyAgreement) {
		return super.delete(QcbEmployeeProductBuyAgreement);
	}

	/**
	 * 向数据库更新一条QcbEmployeeProductBuyAgreement数据
	 * @param QcbEmployeeProductBuyAgreement
	 * @return QcbEmployeeProductBuyAgreement
	 */
	@Override
	@Caching(put={@CachePut(value = "QcbEmployeeProductBuyAgreements", key = "'QcbEmployeeProductBuyAgreements:' + #QcbEmployeeProductBuyAgreement.uuid")})
	public QcbEmployeeProductBuyAgreement update(QcbEmployeeProductBuyAgreement QcbEmployeeProductBuyAgreement) {
		return super.update(QcbEmployeeProductBuyAgreement);
	}

	/**
	 * 根据uuid得到一个QcbEmployeeProductBuyAgreement信息
	 * @param uuid
	 * @return QcbEmployeeProductBuyAgreement
	 */
	@Override
	@Cacheable(value = "QcbEmployeeProductBuyAgreements", key = "'QcbEmployeeProductBuyAgreements:' + #uuid")
	public QcbEmployeeProductBuyAgreement get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询QcbEmployeeProductBuyAgreement总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_employee_product_buy_agreement qepba where 1=1 ");
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qepba.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("records") != null && !"".equals(inputParams.get("records"))) {
			builder.append(" and qepba.records = '" + inputParams.get("records") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qepba.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qepba.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and qepba.scode = '" + inputParams.get("scode") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qepba.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qepba.status in ('success','finished','confirming') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qepba.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qepba.uuid,qepba.qcb_employee as qcbEmployee,qepba.records,qepba.name,qepba.type,"
				+ "qepba.status,qepba.scode,qepba.url,qepba.createtime from qcb_employee_product_buy_agreement qepba where 1=1 ");
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qepba.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("records") != null && !"".equals(inputParams.get("records"))) {
			builder.append(" and qepba.records = '" + inputParams.get("records") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qepba.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qepba.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and qepba.scode = '" + inputParams.get("scode") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qepba.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qepba.status in ('success','finished','confirming') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qepba.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qepba.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qepba.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Boolean getCheckScode(String scode) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from qcb_employee_product_buy_agreement i where i.scode=?0");
		//编辑时检测uuid
		Object[] paras = {scode};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
