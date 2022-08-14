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
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollRecordsDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyPayrollRecordsDAO extends BaseDAO<QcbCompanyPayrollRecords, String> implements IQcbCompanyPayrollRecordsDAO {


	/**
	 * 向数据库添加一条qcbCompanyPayrollRecords数据
	 * @param qcbCompanyPayrollRecords
	 * @return qcbCompanyPayrollRecords
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyPayrollRecordss", key = "'qcbCompanyPayrollRecordss:' + #qcbCompanyPayrollRecords.uuid")})
	public QcbCompanyPayrollRecords insert(QcbCompanyPayrollRecords qcbCompanyPayrollRecords) {
		return super.insert(qcbCompanyPayrollRecords);
	}

	/**
	 * 向数据库删除一条qcbCompanyPayrollRecords数据
	 * @param qcbCompanyPayrollRecords
	 * @return qcbCompanyPayrollRecords
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyPayrollRecordss", key = "'qcbCompanyPayrollRecordss:' + #qcbCompanyPayrollRecords.uuid")})
	public QcbCompanyPayrollRecords delete(QcbCompanyPayrollRecords qcbCompanyPayrollRecords) {
		return super.delete(qcbCompanyPayrollRecords);
	}

	/**
	 * 向数据库更新一条QcbCompanyPayrollRecordss数据
	 * @param qcbCompanyPayrollRecordss
	 * @return QcbCompanyPayrollRecordss
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyPayrollRecordss", key = "'qcbCompanyPayrollRecordss:' + #qcbCompanyPayrollRecords.uuid")})
	public QcbCompanyPayrollRecords update(QcbCompanyPayrollRecords qcbCompanyPayrollRecords) {
		return super.update(qcbCompanyPayrollRecords);
	}

	/**
	 * 根据uuid得到一个QcbCompanyPayrollRecordss信息
	 * @param uuid
	 * @return QcbCompanyPayrollRecordss
	 */
	@Override
	@Cacheable(value = "qcbCompanyPayrollRecordss", key = "'qcbCompanyPayrollRecordss:' + #uuid")
	public QcbCompanyPayrollRecords get(String uuid) {
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
		builder.append(" select qcpr.uuid,qcpr.qcb_company_payroll as qcbCompanyPayroll,qcpr.qcb_company as qcbCompany,qcpr.pay_time as payTime,"
				+ "qcpr.qcb_employee as qcbEmployee,qcpr.price,qcpr.value,qcpr.data,qcpr.status,qcpr.creator,qcpr.createtime"
				+ " from qcb_company_payroll_records qcpr  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcpr.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcpr.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcpr.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("qcbCompanyPayroll") != null && !"".equals(inputParams.get("qcbCompanyPayroll"))) {
			builder.append(" and qcpr.qcb_company_payroll = '" + inputParams.get("qcbCompanyPayroll") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcpr.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcpr.status in ('draft','confirm','success') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcpr.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcpr.data ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_payroll_records qcpr where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcpr.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcpr.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcpr.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("qcbCompanyPayroll") != null && !"".equals(inputParams.get("qcbCompanyPayroll"))) {
			builder.append(" and qcpr.qcb_company_payroll = '" + inputParams.get("qcbCompanyPayroll") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcpr.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcpr.status in ('draft','confirm','success') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getListForWebPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qcpr.uuid,qcpr.qcb_company_payroll as qcbCompanyPayroll,qcpr.qcb_company as qcbCompany,qcpr.pay_time as payTime,"
				+ "qcpr.qcb_employee as qcbEmployee,qcpr.price,qcpr.value,qcpr.data,qcpr.status,qcpr.creator,qcpr.createtime"
				+ " from qcb_company_payroll_records qcpr  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcpr.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcpr.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcpr.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("qcbCompanyPayroll") != null && !"".equals(inputParams.get("qcbCompanyPayroll"))) {
			builder.append(" and qcpr.qcb_company_payroll = '" + inputParams.get("qcbCompanyPayroll") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcpr.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcpr.status in ('confirm','success') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcpr.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcpr.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForWeb(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_payroll_records qcpr where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcpr.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcpr.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcpr.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("qcbCompanyPayroll") != null && !"".equals(inputParams.get("qcbCompanyPayroll"))) {
			builder.append(" and qcpr.qcb_company_payroll = '" + inputParams.get("qcbCompanyPayroll") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcpr.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcpr.status in ('confirm','success') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
