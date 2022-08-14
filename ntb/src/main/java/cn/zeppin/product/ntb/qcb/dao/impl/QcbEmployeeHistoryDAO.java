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
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeHistoryDAO;

/**
 * @author hehe
 *
 */

@Repository
public class QcbEmployeeHistoryDAO extends BaseDAO<QcbEmployeeHistory, String> implements IQcbEmployeeHistoryDAO {


	/**
	 * 向数据库添加一条qcbEmployeeHistory数据
	 * @param qcbEmployeeHistory
	 * @return qcbEmployeeHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeHistorys", key = "'qcbEmployeeHistorys:' + #qcbEmployeeHistory.uuid")})
	public QcbEmployeeHistory insert(QcbEmployeeHistory qcbEmployeeHistory) {
		return super.insert(qcbEmployeeHistory);
	}

	/**
	 * 向数据库删除一条qcbEmployeeHistory数据
	 * @param qcbEmployeeHistory
	 * @return qcbEmployeeHistory
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "qcbEmployeeHistorys", key = "'qcbEmployeeHistorys:' + #qcbEmployeeHistory.uuid")})
	public QcbEmployeeHistory delete(QcbEmployeeHistory qcbEmployeeHistory) {
		return super.delete(qcbEmployeeHistory);
	}

	/**
	 * 向数据库更新一条QcbEmployeeHistorys数据
	 * @param qcbEmployeeHistorys
	 * @return QcbEmployeeHistorys
	 */
	@Override
	@Caching(put={@CachePut(value = "qcbEmployeeHistorys", key = "'qcbEmployeeHistorys:' + #qcbEmployeeHistory.uuid")})
	public QcbEmployeeHistory update(QcbEmployeeHistory qcbEmployeeHistory) {
		return super.update(qcbEmployeeHistory);
	}

	/**
	 * 根据uuid得到一个QcbEmployeeHistorys信息
	 * @param uuid
	 * @return QcbEmployeeHistorys
	 */
	@Override
	@Cacheable(value = "qcbEmployeeHistorys", key = "'qcbEmployeeHistorys:' + #uuid")
	public QcbEmployeeHistory get(String uuid) {
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
		builder.append(" select qeh.uuid,qeh.qcb_employee as qcbEmployee,qeh.order_id as orderId,qeh.order_type as orderType,"
				+ "qeh.order_num as orderNum,qeh.income,qeh.pay,qeh.account_balance as accountBalance,qeh.status,qeh.createtime,"
				+ "qeh.type,qeh.qcb_company as qcbCompany,qeh.product,qeh.product_type as productType,qeh.poundage,qeh.bankcard,qeh.company_account as companyAccount,"
				+ "qeh.qcb_company_payroll_records as qcbCompanyPayrollRecords,qeh.process_company_account as processCompanyAccount,"
				+ "qeh.processing_status as processingStatus,qeh.process_creator as processCreator,qeh.process_createtime as processCreatetime"
				+ " from qcb_employee_history qeh  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qeh.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qeh.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}

		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qeh.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qeh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and qeh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("qcbCompanyPayrollRecords") != null && !"".equals(inputParams.get("qcbCompanyPayrollRecords"))) {
			builder.append(" and qeh.qcb_company_payroll_records = '" + inputParams.get("qcbCompanyPayrollRecords") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and qeh.order_id = '" + inputParams.get("order") + "' ");
		}

		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and qeh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qeh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		}

		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qeh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qeh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeh.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("transferType") != null && !"".equals(inputParams.get("transferType"))) {
			builder.append(" and qeh.type in ('buy','dividend','return','cur_buy','cur_return') ");
		}
		
		if (inputParams.get("currentType") != null && "1".equals(inputParams.get("currentType"))) {
			builder.append(" and qeh.type in ('cur_buy','cur_return') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qeh.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qeh.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_employee_history qeh left join qcb_employee qe on qeh.qcb_employee=qe.uuid where 1=1 ");

		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qeh.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qeh.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}

		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qeh.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qeh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and qeh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("qcbCompanyPayrollRecords") != null && !"".equals(inputParams.get("qcbCompanyPayrollRecords"))) {
			builder.append(" and qeh.qcb_company_payroll_records = '" + inputParams.get("qcbCompanyPayrollRecords") + "' ");
		}

		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and qeh.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and qeh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qeh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} 
//		else {
//			builder.append(" and (qeh.processing_status <>'' and qeh.processing_status is not null) ");
//		}

		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qeh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qeh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeh.type = '" + inputParams.get("type") + "' ");
		}

		if (inputParams.get("transferType") != null && !"".equals(inputParams.get("transferType"))) {
			builder.append(" and qeh.type in ('buy','dividend','return','cur_buy','cur_return') ");
		}
		
		if (inputParams.get("currentType") != null && "1".equals(inputParams.get("currentType"))) {
			builder.append(" and qeh.type in ('cur_buy','cur_return') ");
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
	public List<Entity> getListForWithdrawPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select qeh.uuid,qeh.qcb_employee as qcbEmployee,qeh.order_id as orderId,qeh.order_type as orderType,"
				+ "qeh.order_num as orderNum,qeh.income,qeh.pay,qeh.account_balance as accountBalance,qeh.status,qeh.createtime,"
				+ "qeh.type,qeh.qcb_company as qcbCompany,qeh.product,qeh.product_type as productType,qeh.poundage,qeh.bankcard,qeh.company_account as companyAccount,"
				+ "qeh.qcb_company_payroll_records as qcbCompanyPayrollRecords,qeh.process_company_account as processCompanyAccount,"
				+ "qeh.processing_status as processingStatus,qeh.process_creator as processCreator,qeh.process_createtime as processCreatetime"
				+ " from qcb_employee_history qeh left join qcb_employee qe on qeh.qcb_employee=qe.uuid where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qeh.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qeh.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}

		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qeh.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qeh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and qeh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("qcbCompanyPayrollRecords") != null && !"".equals(inputParams.get("qcbCompanyPayrollRecords"))) {
			builder.append(" and qeh.qcb_company_payroll_records = '" + inputParams.get("qcbCompanyPayrollRecords") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qeh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} else {
			builder.append(" and (qeh.processing_status <>'' and qeh.processing_status is not null) ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qeh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qeh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeh.type = '" + inputParams.get("type") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("qeh.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by qeh.createtime desc ");
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
		builder.append(" select 1 from qcb_employee_history qeh where qeh.order_num=?0");
		//编辑时检测uuid
		Object[] paras = {orderNum};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Entity> getWithdrawStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select qeh.processing_status as status, count(*) as count "
				+ "from qcb_employee_history qeh LEFT JOIN qcb_employee qe on qeh.qcb_employee=qe.uuid "
				+ "where qeh.type='withdraw' ");
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		builder.append(" group by qeh.processing_status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select date_format(qeh.createtime,'%Y-%m-%d') as createtime,qeh.type,sum(qeh.income) as recharge,sum(qeh.pay) as withdraw from qcb_employee_history qeh "
				+ " left outer join bank_financial_product_publish bfpp on qeh.product=bfpp.uuid where 1=1 ");
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeh.type in (" + inputParams.get("type") + ") ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("productName") != null && !"".equals(inputParams.get("productName"))) {
			builder.append(" and bfpp.name like '%" + inputParams.get("productName") + "%' ");
		}
		
		if (inputParams.get("productStage") != null && !"".equals(inputParams.get("productStage"))) {
			builder.append(" and bfpp.stage = '" + inputParams.get("productStage") + "' ");
		}
		
		if (inputParams.get("productCustodian") != null && !"".equals(inputParams.get("productCustodian"))) {
			builder.append(" and bfpp.custodian = '" + inputParams.get("productCustodian") + "' ");
		}
		
		if (inputParams.get("productRedeem") != null && !"".equals(inputParams.get("productRedeem"))) {
			builder.append(" and bfpp.flag_redemption = '" + inputParams.get("productRedeem") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qeh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qeh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		builder.append(" group by date_format(qeh.createtime,'%Y%m%d'),qeh.type");
		
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public void insert(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into qcb_employee_history(uuid,qcb_employee,order_id,order_type,order_num,income,pay,account_balance,"
				+ "status,createtime,type,qcb_company,company_account,product,product_type,poundage,bankcard,qcb_company_payroll_records,"
				+ "processing_status,process_company_account,process_creator,process_createtime) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}
	
	@Override
	public Integer getCountByQcbEmployee(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) "
				+ " from qcb_employee_history qeh left join qcb_employee qe on qeh.qcb_employee=qe.uuid "
				+ " left outer join bank_financial_product_publish bfpp on qeh.product=bfpp.uuid where 1=1 ");
		
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qeh.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qeh.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and qeh.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and qeh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and qeh.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeh.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and qeh.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qeh.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qeh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		}
		
		if (inputParams.get("productName") != null && !"".equals(inputParams.get("productName"))) {
			builder.append(" and bfpp.name like '%" + inputParams.get("productName") + "%' ");
		}
		
		if (inputParams.get("productStage") != null && !"".equals(inputParams.get("productStage"))) {
			builder.append(" and bfpp.stage = '" + inputParams.get("productStage") + "' ");
		}
		
		if (inputParams.get("productCustodian") != null && !"".equals(inputParams.get("productCustodian"))) {
			builder.append(" and bfpp.custodian = '" + inputParams.get("productCustodian") + "' ");
		}
		
		if (inputParams.get("productRedeem") != null && !"".equals(inputParams.get("productRedeem"))) {
			builder.append(" and bfpp.flag_redemption = '" + inputParams.get("productRedeem") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qeh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qeh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取活期募集总额
	 */
	public Double getCurrentTotalAmount(){
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(pay-income) from qcb_employee_history where type in ('cur_buy','cur_return')");
		Object obj = super.getResultBySQL(builder.toString());
		if(obj != null){
			return Double.valueOf(obj.toString());
		}else{
			return 0.0;
		}
	}

	@Override
	public Integer getCountForWithdraw(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from qcb_employee_history qeh left join qcb_employee qe on qeh.qcb_employee=qe.uuid where 1=1 ");

		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and qeh.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qeh.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}

		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and qeh.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qeh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and qeh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("qcbCompanyPayrollRecords") != null && !"".equals(inputParams.get("qcbCompanyPayrollRecords"))) {
			builder.append(" and qeh.qcb_company_payroll_records = '" + inputParams.get("qcbCompanyPayrollRecords") + "' ");
		}

		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and qeh.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and qeh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and qeh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} else {
			builder.append(" and (qeh.processing_status <>'' and qeh.processing_status is not null) ");
		}

		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and qeh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and qeh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and qeh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and qeh.type = '" + inputParams.get("type") + "' ");
		}

		if (inputParams.get("transferType") != null && !"".equals(inputParams.get("transferType"))) {
			builder.append(" and qeh.type in ('buy','dividend','return','cur_buy','cur_return') ");
		}
		
		if (inputParams.get("currentType") != null && "1".equals(inputParams.get("currentType"))) {
			builder.append(" and qeh.type in ('cur_buy','cur_return') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
}
