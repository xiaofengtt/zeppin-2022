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
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAdminDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyAdminDAO extends BaseDAO<QcbCompanyAdmin, String> implements IQcbCompanyAdminDAO {


	/**
	 * 向数据库添加一条qcbCompanyAdmin数据
	 * @param qcbCompanyAdmin
	 * @return qcbCompanyAdmin
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAdmins", key = "'qcbCompanyAdmins:' + #qcbCompanyAdmin.uuid")})
	public QcbCompanyAdmin insert(QcbCompanyAdmin qcbCompanyAdmin) {
		return super.insert(qcbCompanyAdmin);
	}

	/**
	 * 向数据库删除一条qcbCompanyAdmin数据
	 * @param qcbCompanyAdmin
	 * @return qcbCompanyAdmin
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyAdmins", key = "'qcbCompanyAdmins:' + #qcbCompanyAdmin.uuid")})
	public QcbCompanyAdmin delete(QcbCompanyAdmin qcbCompanyAdmin) {
		return super.delete(qcbCompanyAdmin);
	}

	/**
	 * 向数据库更新一条QcbCompanyAdmins数据
	 * @param qcbCompanyAdmins
	 * @return QcbCompanyAdmins
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAdmins", key = "'qcbCompanyAdmins:' + #qcbCompanyAdmin.uuid")})
	public QcbCompanyAdmin update(QcbCompanyAdmin qcbCompanyAdmin) {
		return super.update(qcbCompanyAdmin);
	}

	/**
	 * 根据uuid得到一个QcbCompanyAdmins信息
	 * @param uuid
	 * @return QcbCompanyAdmins
	 */
	@Override
	@Cacheable(value = "qcbCompanyAdmins", key = "'qcbCompanyAdmins:' + #uuid")
	public QcbCompanyAdmin get(String uuid) {
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
		builder.append(" select qca.uuid,qca.qcb_company as qcbCompany,qca.qcb_admin as qcbAdmin,qca.role,"
				+ " qca.status,qca.createtime,qca.creator"
				+ " from qcb_company_admin qca  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qca.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qca.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qca.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbAdmin") != null && !"".equals(inputParams.get("qcbAdmin"))) {
			builder.append(" and qca.qcb_admin = '" + inputParams.get("qcbAdmin") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qca.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qca.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qca.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qca.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qca.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_admin qca where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qca.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qca.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("qcbAdmin") != null && !"".equals(inputParams.get("qcbAdmin"))) {
			builder.append(" and qca.qcb_admin = '" + inputParams.get("qcbAdmin") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qca.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qca.status in ('normal','disable') ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qca.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
