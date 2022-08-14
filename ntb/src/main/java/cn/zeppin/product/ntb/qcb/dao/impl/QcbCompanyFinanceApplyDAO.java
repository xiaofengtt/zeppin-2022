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
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyFinanceApplyDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyFinanceApplyDAO extends BaseDAO<QcbCompanyFinanceApply, String> implements IQcbCompanyFinanceApplyDAO {


	/**
	 * 向数据库添加一条qcbCompanyFinanceApply数据
	 * @param qcbCompanyFinanceApply
	 * @return qcbCompanyFinanceApply
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyFinanceApplys", key = "'qcbCompanyFinanceApplys:' + #qcbCompanyFinanceApply.uuid")})
	public QcbCompanyFinanceApply insert(QcbCompanyFinanceApply qcbCompanyFinanceApply) {
		return super.insert(qcbCompanyFinanceApply);
	}

	/**
	 * 向数据库删除一条qcbCompanyFinanceApply数据
	 * @param qcbCompanyFinanceApply
	 * @return qcbCompanyFinanceApply
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyFinanceApplys", key = "'qcbCompanyFinanceApplys:' + #qcbCompanyFinanceApply.uuid")})
	public QcbCompanyFinanceApply delete(QcbCompanyFinanceApply qcbCompanyFinanceApply) {
		return super.delete(qcbCompanyFinanceApply);
	}

	/**
	 * 向数据库更新一条QcbCompanyFinanceApplys数据
	 * @param qcbCompanyFinanceApplys
	 * @return QcbCompanyFinanceApplys
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyFinanceApplys", key = "'qcbCompanyFinanceApplys:' + #qcbCompanyFinanceApply.uuid")})
	public QcbCompanyFinanceApply update(QcbCompanyFinanceApply qcbCompanyFinanceApply) {
		return super.update(qcbCompanyFinanceApply);
	}

	/**
	 * 根据uuid得到一个QcbCompanyFinanceApplys信息
	 * @param uuid
	 * @return QcbCompanyFinanceApplys
	 */
	@Override
	@Cacheable(value = "qcbCompanyFinanceApplys", key = "'qcbCompanyFinanceApplys:' + #uuid")
	public QcbCompanyFinanceApply get(String uuid) {
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
		builder.append(" select qcfa.uuid,qcfa.qcb_company as qcbCompany,qcfa.contacts,qcfa.contacts_mobile as contactsMobile,"
				+ " qcfa.status,qcfa.checker,qcfa.checktime,qcfa.checkreason,qcfa.createtime,qcfa.creator"
				+ " from qcb_company_finance_apply qcfa  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcfa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcfa.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("contacts") != null && !"".equals(inputParams.get("contacts"))) {
			builder.append(" and qcfa.contacts = '" + inputParams.get("contacts") + "' ");
		}
		
		if (inputParams.get("contactsMobile") != null && !"".equals(inputParams.get("contactsMobile"))) {
			builder.append(" and qcfa.contacts_mobile = '" + inputParams.get("contactsMobile") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcfa.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcfa.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcfa.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcfa.status in ('unchecked','checked','unpassed') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcfa.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcfa.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_finance_apply qcfa where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcfa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcfa.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("contacts") != null && !"".equals(inputParams.get("contacts"))) {
			builder.append(" and qcfa.contacts = '" + inputParams.get("contacts") + "' ");
		}
		
		if (inputParams.get("contactsMobile") != null && !"".equals(inputParams.get("contactsMobile"))) {
			builder.append(" and qcfa.contacts_mobile = '" + inputParams.get("contactsMobile") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and qcfa.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcfa.createtime >= '" + Timestamp.valueOf(inputParams.get("createtime").toString() + " 00:00:00") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcfa.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and qcfa.status in ('unchecked','checked','unpassed') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getListForFinancePage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select qcfa.uuid,qcfa.contacts,qcfa.contacts_mobile as contactsMobile,qcfa.createtime as applytime,"
				+ " qa.uuid as qcbCompany,qa.merchant_id as qcbCompanyMerchantId,qa.name as qcbCompanyName ,qa.bk_area as qcbCompanyArea,"
				+ " qa.finance_status as status"
				+ " from qcb_company_account qa left join qcb_company_finance_apply qcfa on qa.uuid=qcfa.qcb_company where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qa.name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qa.merchant_id like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("merchantId") != null && !"".equals(inputParams.get("merchantId"))) {
			builder.append(" and qa.merchantId = '" + inputParams.get("merchantId") + "' ");
		}
		
		//财税服务开通状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qa.finance_status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qa.finance_status in ('normal','uncheck','unauth') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qa.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qa.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForFinance(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_account qa left join qcb_company_finance_apply qcfa on qa.uuid=qcfa.qcb_company where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qa.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qa.name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qa.merchant_id like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("merchantId") != null && !"".equals(inputParams.get("merchantId"))) {
			builder.append(" and qa.merchantId = '" + inputParams.get("merchantId") + "' ");
		}
		
		//财税服务开通状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qa.finance_status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and qa.finance_status in ('normal','uncheck','unauth') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getStatusListForFinance(Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("select qa.finance_status as status, count(*) as count from qcb_company_account qa "
				+ " where qa.finance_status in('normal','uncheck','unauth') group by qa.finance_status");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
