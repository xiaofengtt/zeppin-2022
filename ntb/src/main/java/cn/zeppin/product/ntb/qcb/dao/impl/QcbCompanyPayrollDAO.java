/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyPayrollDAO extends BaseDAO<QcbCompanyPayroll, String> implements IQcbCompanyPayrollDAO {


	/**
	 * 向数据库添加一条qcbCompanyPayroll数据
	 * @param qcbCompanyPayroll
	 * @return qcbCompanyPayroll
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyPayrolls", key = "'qcbCompanyPayrolls:' + #qcbCompanyPayroll.uuid")})
	public QcbCompanyPayroll insert(QcbCompanyPayroll qcbCompanyPayroll) {
		return super.insert(qcbCompanyPayroll);
	}

	/**
	 * 向数据库删除一条qcbCompanyPayroll数据
	 * @param qcbCompanyPayroll
	 * @return qcbCompanyPayroll
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyPayrolls", key = "'qcbCompanyPayrolls:' + #qcbCompanyPayroll.uuid")})
	public QcbCompanyPayroll delete(QcbCompanyPayroll qcbCompanyPayroll) {
		return super.delete(qcbCompanyPayroll);
	}

	/**
	 * 向数据库更新一条QcbCompanyPayrolls数据
	 * @param qcbCompanyPayrolls
	 * @return QcbCompanyPayrolls
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyPayrolls", key = "'qcbCompanyPayrolls:' + #qcbCompanyPayroll.uuid")})
	public QcbCompanyPayroll update(QcbCompanyPayroll qcbCompanyPayroll) {
		return super.update(qcbCompanyPayroll);
	}

	/**
	 * 根据uuid得到一个QcbCompanyPayrolls信息
	 * @param uuid
	 * @return QcbCompanyPayrolls
	 */
	@Override
	@Cacheable(value = "qcbCompanyPayrolls", key = "'qcbCompanyPayrolls:' + #uuid")
	public QcbCompanyPayroll get(String uuid) {
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
		builder.append(" select qcp.uuid,qcp.qcb_company as qcbCompany,qcp.title,qcp.type,qcp.remark,qcp.status,qcp.column_data as columnData,qcp.reward,"
				+ "qcp.pay_time as payTime,qcp.flag_sms as flagSms,qcp.flag_hide as flagHide,qcp.sourcefile,qcp.creator,qcp.createtime"
				+ " from qcb_company_payroll qcp  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcp.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcp.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and qcp.title like '%" + inputParams.get("title") + "%' ");
		}
		
		if (inputParams.get("payTime") != null && "1".equals(inputParams.get("payTime"))) {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			builder.append(" and unix_timestamp(qcp.pay_time) < unix_timestamp('").append(time).append("') ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcp.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcp.status in ('submit','failed','success') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcp.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcp.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_payroll qcp where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcp.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcp.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and qcp.title like '%" + inputParams.get("title") + "%' ");
		}
		
		if (inputParams.get("payTime") != null && "1".equals(inputParams.get("payTime"))) {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			builder.append(" and unix_timestamp(qcp.pay_time) < unix_timestamp('").append(time).append("') ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcp.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcp.status in ('submit','failed','success') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
