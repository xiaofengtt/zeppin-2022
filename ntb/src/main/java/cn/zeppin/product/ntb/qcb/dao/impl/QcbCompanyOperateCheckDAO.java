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
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperateCheck;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyOperateCheckDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyOperateCheckDAO extends BaseDAO<QcbCompanyOperateCheck, String> implements IQcbCompanyOperateCheckDAO {


	/**
	 * 向数据库添加一条qcbCompanyOperateCheck数据
	 * @param qcbCompanyOperateCheck
	 * @return qcbCompanyOperateCheck
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyOperateChecks", key = "'qcbCompanyOperateChecks:' + #qcbCompanyOperateCheck.uuid")})
	public QcbCompanyOperateCheck insert(QcbCompanyOperateCheck qcbCompanyOperateCheck) {
		return super.insert(qcbCompanyOperateCheck);
	}

	/**
	 * 向数据库删除一条qcbCompanyOperateCheck数据
	 * @param qcbCompanyOperateCheck
	 * @return qcbCompanyOperateCheck
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyOperateChecks", key = "'qcbCompanyOperateChecks:' + #qcbCompanyOperateCheck.uuid")})
	public QcbCompanyOperateCheck delete(QcbCompanyOperateCheck qcbCompanyOperateCheck) {
		return super.delete(qcbCompanyOperateCheck);
	}

	/**
	 * 向数据库更新一条QcbCompanyOperateChecks数据
	 * @param qcbCompanyOperateChecks
	 * @return QcbCompanyOperateChecks
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyOperateChecks", key = "'qcbCompanyOperateChecks:' + #qcbCompanyOperateCheck.uuid")})
	public QcbCompanyOperateCheck update(QcbCompanyOperateCheck qcbCompanyOperateCheck) {
		return super.update(qcbCompanyOperateCheck);
	}

	/**
	 * 根据uuid得到一个QcbCompanyOperateChecks信息
	 * @param uuid
	 * @return QcbCompanyOperateChecks
	 */
	@Override
	@Cacheable(value = "qcbCompanyOperateChecks", key = "'qcbCompanyOperateChecks:' + #uuid")
	public QcbCompanyOperateCheck get(String uuid) {
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
		builder.append(" select qcoc.uuid,qcoc.qcb_company_operate as qcbCompanyOperate,qcoc.reason,qcoc.status,qcoc.creator,qcoc.createtime"
				+ " from qcb_company_operate_check qcoc where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcoc.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompanyOperate") != null && !"".equals(inputParams.get("qcbCompanyOperate"))) {
			builder.append(" and qcoc.qcb_company_operate = '" + inputParams.get("qcbCompanyOperate") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcoc.status = '" + inputParams.get("status") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcoc.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcoc.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_operate qcoc where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcoc.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompanyOperate") != null && !"".equals(inputParams.get("qcbCompanyOperate"))) {
			builder.append(" and qcoc.qcb_company_operate = '" + inputParams.get("qcbCompanyOperate") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcoc.status = '" + inputParams.get("status") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
