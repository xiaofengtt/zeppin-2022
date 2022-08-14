/**
 * 
 */
package cn.zeppin.product.ntb.shbx.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserHistoryDAO;

/**
 * @author hehe
 *
 */

@Repository
public class ShbxUserHistoryDAO extends BaseDAO<ShbxUserHistory, String> implements IShbxUserHistoryDAO {


	/**
	 * 向数据库添加一条shbxUserHistory数据
	 * @param shbxUserHistory
	 * @return shbxUserHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUserHistorys", key = "'shbxUserHistorys:' + #shbxUserHistory.uuid")})
	public ShbxUserHistory insert(ShbxUserHistory shbxUserHistory) {
		return super.insert(shbxUserHistory);
	}

	/**
	 * 向数据库删除一条shbxUserHistory数据
	 * @param shbxUserHistory
	 * @return shbxUserHistory
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "shbxUserHistorys", key = "'shbxUserHistorys:' + #shbxUserHistory.uuid")})
	public ShbxUserHistory delete(ShbxUserHistory shbxUserHistory) {
		return super.delete(shbxUserHistory);
	}

	/**
	 * 向数据库更新一条ShbxUserHistorys数据
	 * @param shbxUserHistorys
	 * @return ShbxUserHistorys
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUserHistorys", key = "'shbxUserHistorys:' + #shbxUserHistory.uuid")})
	public ShbxUserHistory update(ShbxUserHistory shbxUserHistory) {
		return super.update(shbxUserHistory);
	}

	/**
	 * 根据uuid得到一个ShbxUserHistorys信息
	 * @param uuid
	 * @return ShbxUserHistorys
	 */
	@Override
	@Cacheable(value = "shbxUserHistorys", key = "'shbxUserHistorys:' + #uuid")
	public ShbxUserHistory get(String uuid) {
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
		builder.append(" select suh.uuid,suh.shbx_user as shbxUser,suh.order_id as orderId,suh.order_type as orderType,"
				+ "suh.order_num as orderNum,suh.income,suh.pay,suh.account_balance as accountBalance,suh.status,suh.createtime,"
				+ "suh.type,suh.product,suh.product_type as productType,suh.poundage,suh.bankcard,suh.company_account as companyAccount,"
				+ "suh.shbx_security_order as shbxSecurityOrder,suh.process_company_account as processCompanyAccount,"
				+ "suh.processing_status as processingStatus,suh.process_creator as processCreator,suh.process_createtime as processCreatetime"
				+ " from shbx_user_history suh  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and suh.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and suh.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and suh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and suh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("shbxSecurityOrder") != null && !"".equals(inputParams.get("shbxSecurityOrder"))) {
			builder.append(" and suh.shbx_security_order = '" + inputParams.get("shbxSecurityOrder") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and suh.order_id = '" + inputParams.get("order") + "' ");
		}

		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and suh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and suh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		}

		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and suh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and suh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and suh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and suh.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("transferType") != null && !"".equals(inputParams.get("transferType"))) {
			builder.append(" and suh.type in ('buy','dividend','return','cur_buy','cur_return') ");
		}
		
		if (inputParams.get("currentType") != null && "1".equals(inputParams.get("currentType"))) {
			builder.append(" and suh.type in ('cur_buy','cur_return') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("suh.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by suh.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from shbx_user_history suh left join shbx_user qe on suh.shbx_user=qe.uuid where 1=1 ");

		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and suh.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and suh.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and suh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and suh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("shbxSecurityOrder") != null && !"".equals(inputParams.get("shbxSecurityOrder"))) {
			builder.append(" and suh.shbx_security_order = '" + inputParams.get("shbxSecurityOrder") + "' ");
		}

		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and suh.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and suh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and suh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} 
//		else {
//			builder.append(" and (suh.processing_status <>'' and suh.processing_status is not null) ");
//		}

		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and suh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and suh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and suh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and suh.type = '" + inputParams.get("type") + "' ");
		}

		if (inputParams.get("transferType") != null && !"".equals(inputParams.get("transferType"))) {
			builder.append(" and suh.type in ('buy','dividend','return','cur_buy','cur_return') ");
		}
		
		if (inputParams.get("currentType") != null && "1".equals(inputParams.get("currentType"))) {
			builder.append(" and suh.type in ('cur_buy','cur_return') ");
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
		builder.append(" select suh.uuid,suh.shbx_user as shbxUser,suh.order_id as orderId,suh.order_type as orderType,"
				+ "suh.order_num as orderNum,suh.income,suh.pay,suh.account_balance as accountBalance,suh.status,suh.createtime,"
				+ "suh.type,suh.product,suh.product_type as productType,suh.poundage,suh.bankcard,suh.company_account as companyAccount,"
				+ "suh.shbx_security_order as shbxSecurityOrder,suh.process_company_account as processCompanyAccount,"
				+ "suh.processing_status as processingStatus,suh.process_creator as processCreator,suh.process_createtime as processCreatetime"
				+ " from shbx_user_history suh left join shbx_user qe on suh.shbx_user=qe.uuid where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and suh.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and suh.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and suh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and suh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("shbxSecurityOrder") != null && !"".equals(inputParams.get("shbxSecurityOrder"))) {
			builder.append(" and suh.shbx_security_order = '" + inputParams.get("shbxSecurityOrder") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and suh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} else {
			builder.append(" and (suh.processing_status <>'' and suh.processing_status is not null) ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and suh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and suh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and suh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and suh.type = '" + inputParams.get("type") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("suh.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by suh.createtime desc ");
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
		builder.append(" select 1 from shbx_user_history suh where suh.order_num=?0");
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
		builder.append("select suh.processing_status as status, count(*) as count "
				+ "from shbx_user_history suh LEFT JOIN shbx_user qe on suh.shbx_user=qe.uuid "
				+ "where suh.type='withdraw' ");
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		builder.append(" group by suh.processing_status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select date_format(suh.createtime,'%Y-%m-%d') as createtime,suh.type,sum(suh.income) as recharge,sum(suh.pay) as withdraw from shbx_user_history suh "
				+ " left outer join bank_financial_product_publish bfpp on suh.product=bfpp.uuid where 1=1 ");
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and suh.type in (" + inputParams.get("type") + ") ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and suh.status = '" + inputParams.get("status") + "' ");
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
			builder.append(" and suh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and suh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		builder.append(" group by date_format(suh.createtime,'%Y%m%d'),suh.type");
		
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public void insert(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into shbx_user_history(uuid,shbx_user,order_id,order_type,order_num,income,pay,account_balance,"
				+ "status,createtime,type,company_account,product,product_type,poundage,bankcard,"
				+ "processing_status,process_company_account,process_creator,process_createtime,shbx_security_order) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}
	
	@Override
	public Integer getCountByShbxUser(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) "
				+ " from shbx_user_history suh left join shbx_user qe on suh.shbx_user=qe.uuid "
				+ " left outer join bank_financial_product_publish bfpp on suh.product=bfpp.uuid where 1=1 ");
		
		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and suh.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and suh.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and suh.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and suh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and suh.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and suh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and suh.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and suh.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and suh.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and suh.processing_status = '" + inputParams.get("processingStatus") + "' ");
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
			builder.append(" and suh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and suh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取活期募集总额
	 */
	public Double getCurrentTotalAmount(){
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(pay-income) from shbx_user_history where type in ('cur_buy','cur_return')");
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
		builder.append(" select count(*) from shbx_user_history suh left join shbx_user qe on suh.shbx_user=qe.uuid where 1=1 ");

		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (qe.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or qe.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and suh.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and suh.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}

		if (inputParams.get("qcbCompany") != null && !"".equals(inputParams.get("qcbCompany"))) {
			builder.append(" and suh.qcb_company = '" + inputParams.get("qcbCompany") + "' ");
		}

		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and suh.product = '" + inputParams.get("product") + "' ");
		}

		if (inputParams.get("bankcard") != null && !"".equals(inputParams.get("bankcard"))) {
			builder.append(" and suh.bankcard = '" + inputParams.get("bankcard") + "' ");
		}

		if (inputParams.get("qcbCompanyPayrollRecords") != null && !"".equals(inputParams.get("qcbCompanyPayrollRecords"))) {
			builder.append(" and suh.qcb_company_payroll_records = '" + inputParams.get("qcbCompanyPayrollRecords") + "' ");
		}

		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and suh.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and suh.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and suh.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} else {
			builder.append(" and (suh.processing_status <>'' and suh.processing_status is not null) ");
		}

		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and suh.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and suh.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and suh.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and suh.type = '" + inputParams.get("type") + "' ");
		}

		if (inputParams.get("transferType") != null && !"".equals(inputParams.get("transferType"))) {
			builder.append(" and suh.type in ('buy','dividend','return','cur_buy','cur_return') ");
		}
		
		if (inputParams.get("currentType") != null && "1".equals(inputParams.get("currentType"))) {
			builder.append(" and suh.type in ('cur_buy','cur_return') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
}
