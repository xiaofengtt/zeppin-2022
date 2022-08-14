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
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountHistoryDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbCompanyAccountHistoryDAO extends BaseDAO<QcbCompanyAccountHistory, String> implements IQcbCompanyAccountHistoryDAO {


	/**
	 * 向数据库添加一条qcbCompanyAccountHistory数据
	 * @param qcbCompanyAccountHistory
	 * @return qcbCompanyAccountHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAccountHistorys", key = "'qcbCompanyAccountHistorys:' + #qcbCompanyAccountHistory.uuid")})
	public QcbCompanyAccountHistory insert(QcbCompanyAccountHistory qcbCompanyAccountHistory) {
		return super.insert(qcbCompanyAccountHistory);
	}

	/**
	 * 向数据库删除一条qcbCompanyAccountHistory数据
	 * @param qcbCompanyAccountHistory
	 * @return qcbCompanyAccountHistory
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbCompanyAccountHistorys", key = "'qcbCompanyAccountHistorys:' + #qcbCompanyAccountHistory.uuid")})
	public QcbCompanyAccountHistory delete(QcbCompanyAccountHistory qcbCompanyAccountHistory) {
		return super.delete(qcbCompanyAccountHistory);
	}

	/**
	 * 向数据库更新一条QcbCompanyAccountHistorys数据
	 * @param qcbCompanyAccountHistorys
	 * @return QcbCompanyAccountHistorys
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbCompanyAccountHistorys", key = "'qcbCompanyAccountHistorys:' + #qcbCompanyAccountHistory.uuid")})
	public QcbCompanyAccountHistory update(QcbCompanyAccountHistory qcbCompanyAccountHistory) {
		return super.update(qcbCompanyAccountHistory);
	}

	/**
	 * 根据uuid得到一个QcbCompanyAccountHistorys信息
	 * @param uuid
	 * @return QcbCompanyAccountHistorys
	 */
	@Override
	@Cacheable(value = "qcbCompanyAccountHistorys", key = "'qcbCompanyAccountHistorys:' + #uuid")
	public QcbCompanyAccountHistory get(String uuid) {
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
		builder.append(" select qcah.uuid,qcah.qcb_company as qcbCompany,qcah.order_id as orderId,qcah.order_type as orderType,qcah.order_num as orderNum,"
				+ " qcah.account_balance as accountBalance,qcah.income,qcah.pay,qcah.status,qcah.createtime,qcah.type,qcah.flag_sms as flagSms,"
				+ " qcah.company_account as companyAccount,qcah.product,qcah.product_type as productType,qcah.qcb_company_payroll as qcbCompanyPayroll,"
				+ " qcah.poundage,qcah.bankcard,qcah.processing_status as processingStatus,qcah.process_company_account as processCompanyAccount,"
				+ " qcah.process_creator as processCreator, qcah.process_createtime as processCreatetime, qcah.remark, qcah.paytime, qcah.other_price as otherPrice"
				+ " from qcb_company_account_history qcah  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcah.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcah.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qcah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and qcah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and qcah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qcah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			if("redeem".equals(inputParams.get("type"))){
				builder.append(" and qcah.type in ('return','dividend') ");
			} else if("pay".equals(inputParams.get("type"))){
				builder.append(" and qcah.type in ('payroll','expend') ");//支出
			} else {
				builder.append(" and qcah.type = '" + inputParams.get("type") + "' ");
			}
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and qcah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qcah.processing_status = '" + inputParams.get("processingStatus") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qcah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qcah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcah.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcah.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_company_account_history qcah left join qcb_company_account qca on qcah.qcb_company=qca.uuid where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcah.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qca.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcah.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qcah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and qcah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and qcah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qcah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			if("redeem".equals(inputParams.get("type"))){
				builder.append(" and qcah.type in ('return','dividend') ");
			} else if("pay".equals(inputParams.get("type"))){
				builder.append(" and qcah.type in ('payroll','expend') ");//支出
			} else {
				builder.append(" and qcah.type = '" + inputParams.get("type") + "' ");
			}
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and qcah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qcah.processing_status = '" + inputParams.get("processingStatus") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qcah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qcah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 根据参数查询提现结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForWithdrawPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qcah.uuid,qcah.qcb_company as qcbCompany,qcah.order_id as orderId,qcah.order_type as orderType,qcah.order_num as orderNum,"
				+ " qcah.account_balance as accountBalance,qcah.income,qcah.pay,qcah.status,qcah.createtime,qcah.type,qcah.flag_sms as flagSms,"
				+ " qcah.company_account as companyAccount,qcah.product,qcah.product_type as productType,qcah.qcb_company_payroll as qcbCompanyPayroll,"
				+ " qcah.poundage,qcah.bankcard,qcah.processing_status as processingStatus,qcah.process_company_account as processCompanyAccount,"
				+ " qcah.process_creator as processCreator, qcah.process_createtime as processCreatetime, qcah.remark, qcah.paytime, qcah.other_price as otherPrice"
				+ " from qcb_company_account_history qcah left join qcb_company_account qca on qcah.qcb_company=qca.uuid where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qcah.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qca.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcah.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qcah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and qcah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and qcah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qcah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			if("redeem".equals(inputParams.get("type"))){
				builder.append(" and qcah.type in ('return','dividend') ");
			} else if("pay".equals(inputParams.get("type"))){
				builder.append(" and qcah.type in ('payroll','expend') ");//支出
			} else {
				builder.append(" and qcah.type = '" + inputParams.get("type") + "' ");
			}
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and qcah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qcah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qcah.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} else {
			builder.append(" and (qcah.processing_status <>'' and qcah.processing_status is not null) ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qcah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qcah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qcah.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qcah.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	/**
	 * 校验订单号是否已存在
	 * @param orderNum
	 * @return
	 */
	@Override
	public Boolean checkOrderNum(String orderNum) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from qcb_company_account_history qcah where qcah.order_num=?0");
		//编辑时检测uuid
		Object[] paras = {orderNum};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		boolean flag = true;
		if(inputParams.containsKey("countType")){
			if("month".equals(inputParams.get("countType").toString())){//按月份
				flag = false;
			}
		}
		String formatStr = "%Y-%m-%d";
		String formatStr2 = "%Y%m%d";
		if(!flag){
			formatStr = "%Y年%m月";
			formatStr2 = "%Y%m";
		}
		builder.append(" select date_format(qcah.createtime,'"+formatStr+"') as createtime,sum(qcah.income) as income,sum(qcah.pay) as pay "
				+ " from qcb_company_account_history qcah "
				+ " where 1=1 ");
		
		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qcah.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qcah.type in (" + inputParams.get("type") + ") ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qcah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qcah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qcah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		builder.append(" group by date_format(qcah.createtime,'"+formatStr2+"')");
		
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public List<Entity> getWithdrawStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select qcah.processing_status as status, count(*) as count "
				+ "from qcb_company_account_history qcah LEFT JOIN qcb_company_account qca on qcah.qcb_company=qca.uuid "
				+ "where qcah.type='withdraw' ");
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and qca.name like '%" + inputParams.get("name") + "%' ");
		}
		
		builder.append(" group by qcah.processing_status");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
