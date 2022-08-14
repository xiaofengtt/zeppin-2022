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
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyEmployeeDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyEmployeeDAO extends BaseDAO<QcbCompanyEmployee, String> implements IQcbCompanyEmployeeDAO {


	/**
	 * 向数据库添加一条qcbCompanyEmployee数据
	 * @param qcbCompanyEmployee
	 * @return qcbCompanyEmployee
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyEmployees", key = "'qcbCompanyEmployees:' + #qcbCompanyEmployee.uuid")})
	public QcbCompanyEmployee insert(QcbCompanyEmployee qcbCompanyEmployee) {
		return super.insert(qcbCompanyEmployee);
	}

	/**
	 * 向数据库删除一条qcbCompanyEmployee数据
	 * @param qcbCompanyEmployee
	 * @return qcbCompanyEmployee
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyEmployees", key = "'qcbCompanyEmployees:' + #qcbCompanyEmployee.uuid")})
	public QcbCompanyEmployee delete(QcbCompanyEmployee qcbCompanyEmployee) {
		return super.delete(qcbCompanyEmployee);
	}

	/**
	 * 向数据库更新一条QcbCompanyEmployees数据
	 * @param qcbCompanyEmployees
	 * @return QcbCompanyEmployees
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyEmployees", key = "'qcbCompanyEmployees:' + #qcbCompanyEmployee.uuid")})
	public QcbCompanyEmployee update(QcbCompanyEmployee qcbCompanyEmployee) {
		return super.update(qcbCompanyEmployee);
	}

	/**
	 * 根据uuid得到一个QcbCompanyEmployees信息
	 * @param uuid
	 * @return QcbCompanyEmployees
	 */
	@Override
	@Cacheable(value = "qcbCompanyEmployees", key = "'qcbCompanyEmployees:' + #uuid")
	public QcbCompanyEmployee get(String uuid) {
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
		builder.append(" select qcb.uuid,qcb.qcb_company as qcbCompany,qcb.qcb_employee as qcbEmployee,qcb.department, qcb.duty,"
				+ " qcb.creator,qcb.status,qcb.createtime"
				+ " from qcb_company_employee qcb  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcb.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("department") != null && !"".equals(inputParams.get("department"))) {
			builder.append(" and qcb.department = '" + inputParams.get("department") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcb.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("duty") != null && !"".equals(inputParams.get("duty"))) {
			builder.append(" and qcb.duty = '" + inputParams.get("duty") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcb.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qcb.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcb.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_employee qcb where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcb.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("department") != null && !"".equals(inputParams.get("department"))) {
			builder.append(" and qcb.department = '" + inputParams.get("department") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcb.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("duty") != null && !"".equals(inputParams.get("duty"))) {
			builder.append(" and qcb.duty = '" + inputParams.get("duty") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcb.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qcb.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcb.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getListForListPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qcb.uuid,qcb.qcb_company as qcbCompany,qcb.qcb_employee as qcbEmployee,qcb.department, qcb.duty,"
				+ " qcb.status,qe.realname,qe.idcard,qe.mobile "
				+ " from qcb_company_employee qcb left join qcb_employee qe on qcb.qcb_employee=qe.uuid  where 1=1 "
				+ " and qe.status in ('normal','disable') ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcb.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%')  ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("department") != null && !"".equals(inputParams.get("department"))) {
			builder.append(" and qcb.department = '" + inputParams.get("department") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcb.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("duty") != null && !"".equals(inputParams.get("duty"))) {
			builder.append(" and qcb.duty = '" + inputParams.get("duty") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcb.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qcb.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcb.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForList(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_employee qcb left join qcb_employee qe on qcb.qcb_employee=qe.uuid where 1=1 "
				+ " and qe.status in ('normal','disable') ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcb.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%')  ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qcb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("department") != null && !"".equals(inputParams.get("department"))) {
			builder.append(" and qcb.department = '" + inputParams.get("department") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcb.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("duty") != null && !"".equals(inputParams.get("duty"))) {
			builder.append(" and qcb.duty = '" + inputParams.get("duty") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcb.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qcb.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcb.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
