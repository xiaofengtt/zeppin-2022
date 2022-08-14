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

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollFeedbackDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyPayrollFeedbackDAO extends BaseDAO<QcbCompanyPayrollFeedback, String> implements IQcbCompanyPayrollFeedbackDAO {


	/**
	 * 向数据库添加一条qcbCompanyPayrollFeedback数据
	 * @param qcbCompanyPayrollFeedback
	 * @return qcbCompanyPayrollFeedback
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyPayrollFeedbacks", key = "'qcbCompanyPayrollFeedbacks:' + #qcbCompanyPayrollFeedback.uuid")})
	public QcbCompanyPayrollFeedback insert(QcbCompanyPayrollFeedback qcbCompanyPayrollFeedback) {
		return super.insert(qcbCompanyPayrollFeedback);
	}

	/**
	 * 向数据库删除一条qcbCompanyPayrollFeedback数据
	 * @param qcbCompanyPayrollFeedback
	 * @return qcbCompanyPayrollFeedback
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyPayrollFeedbacks", key = "'qcbCompanyPayrollFeedbacks:' + #qcbCompanyPayrollFeedback.uuid")})
	public QcbCompanyPayrollFeedback delete(QcbCompanyPayrollFeedback qcbCompanyPayrollFeedback) {
		return super.delete(qcbCompanyPayrollFeedback);
	}

	/**
	 * 向数据库更新一条QcbCompanyPayrollFeedbacks数据
	 * @param qcbCompanyPayrollFeedbacks
	 * @return QcbCompanyPayrollFeedbacks
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyPayrollFeedbacks", key = "'qcbCompanyPayrollFeedbacks:' + #qcbCompanyPayrollFeedback.uuid")})
	public QcbCompanyPayrollFeedback update(QcbCompanyPayrollFeedback qcbCompanyPayrollFeedback) {
		return super.update(qcbCompanyPayrollFeedback);
	}

	/**
	 * 根据uuid得到一个QcbCompanyPayrollFeedbacks信息
	 * @param uuid
	 * @return QcbCompanyPayrollFeedbacks
	 */
	@Override
	@Cacheable(value = "qcbCompanyPayrollFeedbacks", key = "'qcbCompanyPayrollFeedbacks:' + #uuid")
	public QcbCompanyPayrollFeedback get(String uuid) {
		return super.get(uuid);
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
		builder.append(" select qcpf.uuid,qcpf.qcb_company_payroll as qcbCompanyPayroll,qcpf.qcb_company_payroll_records as qcbCompanyPayrollRecords,"
				+ "qcpf.content,qcpf.status,qcpf.creator,qcpf.createtime"
				+ " from qcb_company_payroll_feedback qcpf where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcpf.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("qcbCompanyPayroll") != null && !"".equals(inputParams.get("qcbCompanyPayroll"))) {
			builder.append(" and qcpf.qcb_company_payroll = '" + inputParams.get("qcbCompanyPayroll") + "' ");
		}
		
		if (inputParams.get("qcbCompanyPayrollRecords") != null && !"".equals(inputParams.get("qcbCompanyPayrollRecords"))) {
			builder.append(" and qcpf.qcb_company_payroll_records = '" + inputParams.get("qcbCompanyPayrollRecords") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcpf.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcpf.status in ('normal','unread') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcpf.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcpf.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_payroll_feedback qcpf where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcpf.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("qcbCompanyPayroll") != null && !"".equals(inputParams.get("qcbCompanyPayroll"))) {
			builder.append(" and qcpf.qcb_company_payroll = '" + inputParams.get("qcbCompanyPayroll") + "' ");
		}
		
		if (inputParams.get("qcbCompanyPayrollRecords") != null && !"".equals(inputParams.get("qcbCompanyPayrollRecords"))) {
			builder.append(" and qcpf.qcb_company_payroll_records = '" + inputParams.get("qcbCompanyPayrollRecords") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcpf.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcpf.status in ('normal','unread') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
