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
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyOperateDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyOperateDAO extends BaseDAO<QcbCompanyOperate, String> implements IQcbCompanyOperateDAO {


	/**
	 * 向数据库添加一条qcbCompanyOperate数据
	 * @param qcbCompanyOperate
	 * @return qcbCompanyOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyOperates", key = "'qcbCompanyOperates:' + #qcbCompanyOperate.uuid")})
	public QcbCompanyOperate insert(QcbCompanyOperate qcbCompanyOperate) {
		return super.insert(qcbCompanyOperate);
	}

	/**
	 * 向数据库删除一条qcbCompanyOperate数据
	 * @param qcbCompanyOperate
	 * @return qcbCompanyOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyOperates", key = "'qcbCompanyOperates:' + #qcbCompanyOperate.uuid")})
	public QcbCompanyOperate delete(QcbCompanyOperate qcbCompanyOperate) {
		return super.delete(qcbCompanyOperate);
	}

	/**
	 * 向数据库更新一条QcbCompanyOperates数据
	 * @param qcbCompanyOperates
	 * @return QcbCompanyOperates
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyOperates", key = "'qcbCompanyOperates:' + #qcbCompanyOperate.uuid")})
	public QcbCompanyOperate update(QcbCompanyOperate qcbCompanyOperate) {
		return super.update(qcbCompanyOperate);
	}

	/**
	 * 根据uuid得到一个QcbCompanyOperates信息
	 * @param uuid
	 * @return QcbCompanyOperates
	 */
	@Override
	@Cacheable(value = "qcbCompanyOperates", key = "'qcbCompanyOperates:' + #uuid")
	public QcbCompanyOperate get(String uuid) {
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
		builder.append(" select qco.uuid,qco.qcb_company as qcbCompany,qco.type,qco.value,qco.old,qco.status,"
				+ " qco.reason,qco.checker,qco.checktime,qco.creator,qco.createtime"
				+ " from qcb_company_operate qco  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qco.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qco.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qco.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qco.status in ('unchecked','checked','unpassed') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qco.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qco.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_operate qco where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qco.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qco.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qco.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qco.status in ('unchecked','checked','unpassed') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 根据企业获取信息
	 * @param qcbCompany
	 * @return
	 */
	@Override
	public QcbCompanyOperate getByQcbCompany(String qcbCompany) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from QcbCompanyOperate qco where 1=1");
		builder.append(" and qco.qcbCompany='"+qcbCompany+"'");
		List<QcbCompanyOperate> operates = this.getByHQL(builder.toString());
		if (operates != null && operates.size() > 0) {
			return operates.get(0);
		}
		return null;
	}
}
