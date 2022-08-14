/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorAccountHistoryDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorAccountHistoryDAO extends BaseDAO<InvestorAccountHistory, String> implements IInvestorAccountHistoryDAO {


	/**
	 * 向数据库添加一条investorAccountHistory数据
	 * @param investorAccountHistory
	 * @return investorAccountHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "investorAccountHistorys", key = "'investorAccountHistorys:' + #investorAccountHistory.uuid")})
	public InvestorAccountHistory insert(InvestorAccountHistory investorAccountHistory) {
		return super.insert(investorAccountHistory);
	}

	/**
	 * 向数据库删除一条investorAccountHistory数据
	 * @param investorAccountHistory
	 * @return investorAccountHistory
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorAccountHistorys", key = "'investorAccountHistorys:' + #investorAccountHistory.uuid")})
	public InvestorAccountHistory delete(InvestorAccountHistory investorAccountHistory) {
		return super.delete(investorAccountHistory);
	}

	/**
	 * 向数据库更新一条investorAccountHistory数据
	 * @param investorAccountHistory
	 * @return investorAccountHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "investorAccountHistorys", key = "'investorAccountHistorys:' + #investorAccountHistory.uuid")})
	public InvestorAccountHistory update(InvestorAccountHistory investorAccountHistory) {
		return super.update(investorAccountHistory);
	}

	/**
	 * 根据uuid得到一个investorAccountHistory信息
	 * @param uuid
	 * @return investorAccountHistory
	 */
	@Override
	@Cacheable(value = "investorAccountHistorys", key = "'investorAccountHistorys:' + #uuid")
	public InvestorAccountHistory get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询investorAccountHistory总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor_account_history iah "
				+ "left join investor i on iah.investor=i.uuid "
				+ "left outer join bank_financial_product_publish bfpp on iah.product=bfpp.uuid where 1=1");

		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and iah.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and iah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and iah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and iah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and iah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and iah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and iah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and iah.processing_status = '" + inputParams.get("processingStatus") + "' ");
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
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
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
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select iah.uuid,iah.investor,iah.order_id as orderId,iah.order_type as orderType,iah.account_balance as accountBalance,"
				+ " iah.income,iah.pay,iah.status,iah.createtime,iah.type,iah.order_num as orderNum,iah.company_account as companyAccount,"
				+ " iah.product,iah.product_type as productType, iah.poundage, iah.processing_status as processingStatus,"
				+ " iah.bankcard, iah.process_company_account as processCompanyAccount, iah.process_creator as processCreator,"
				+ " iah.process_createtime as processCreatetime"
				+ " from investor_account_history iah left outer join bank_financial_product_publish bfpp on iah.product=bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and iah.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and iah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and iah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and iah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and iah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and iah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and iah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and iah.processing_status = '" + inputParams.get("processingStatus") + "' ");
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
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
//		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime")) && inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
//			builder.append(" and iah.createtime between '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' and '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59.9999") + "' ");
//		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("iah.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by iah.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
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
		builder.append(" select iah.uuid,iah.investor,iah.order_id as orderId,iah.order_type as orderType,iah.account_balance as accountBalance,"
				+ " iah.income,iah.pay,iah.status,iah.createtime,iah.type,iah.order_num as orderNum,iah.company_account as companyAccount,"
				+ " iah.product,iah.product_type as productType, iah.poundage, iah.bankcard, iah.processing_status as processingStatus,"
				+ " iah.process_company_account as processCompanyAccount, iah.process_creator as processCreator, iah.process_createtime as processCreatetime "
				+ " from investor_account_history iah left join investor i on iah.investor=i.uuid where 1=1 ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and iah.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and iah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and iah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and iah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and iah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and iah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and iah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and iah.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} else {
			builder.append(" and (iah.processing_status <>'' and iah.processing_status is not null) ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("iah.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by iah.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Double getTotalReturnByMonth4Investor(String uuid) {
		StringBuilder sql = new StringBuilder();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
		String currentTime = Utlity.timeSpanToDateString(m)+" 00:00:00";
		sql.append("select SUM(iah.income) from investor_account_history iah where iah.type='dividend'");
		sql.append(" and iah.createtime > '").append(currentTime).append("'");
		sql.append(" and iah.investor = '").append(uuid).append("' ");
		sql.append(" group by iah.investor ");
		
		return Double.valueOf(super.getResultBySQL(sql.toString()) == null ? "0.00": super.getResultBySQL(sql.toString()).toString());
	}

	@Override
	public Double getTotalReturnByYear4Investor(String uuid) {
		StringBuilder sql = new StringBuilder();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date m = c.getTime();
		String currentTime = Utlity.timeSpanToDateString(m)+" 00:00:00";
		sql.append("select SUM(iah.income) from investor_account_history iah where iah.type='dividend'");
		sql.append(" and iah.createtime > '").append(currentTime).append("'");
		sql.append(" and iah.investor = '").append(uuid).append("' ");
		sql.append(" group by iah.investor ");
		
		return Double.valueOf(super.getResultBySQL(sql.toString()) == null ? "0.00": super.getResultBySQL(sql.toString()).toString());
	}

	@Override
	public Boolean getCheckOrderNum(String orderNum) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from investor_account_history i where i.order_num=?0");
		//编辑时检测uuid
		Object[] paras = {orderNum};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select iah.processing_status as status, count(*) as count "
				+ "from investor_account_history iah LEFT JOIN investor i on iah.investor=i.uuid "
				+ "where iah.type='withdraw' ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and iah.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and iah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and iah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and iah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and iah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and iah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and iah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and iah.processing_status = '" + inputParams.get("processingStatus") + "' ");
		} else {
			builder.append(" and (iah.processing_status <>'' and iah.processing_status is not null) ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		builder.append(" group by iah.processing_status");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public InvestorAccountHistory getByOrderNum(String orderNum, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select iah.uuid,iah.investor,iah.order_id as orderId,iah.order_type as orderType,iah.account_balance as accountBalance,"
				+ " iah.income,iah.pay,iah.status,iah.createtime,iah.type,iah.order_num as orderNum,iah.company_account as companyAccount,"
				+ " iah.product,iah.product_type as productType, iah.poundage, iah.bankcard, iah.processing_status as processingStatus,"
				+ " iah.process_company_account as processCompanyAccount, iah.process_creator as processCreator, iah.process_createtime as processCreatetime ");
		builder.append(" and iah.order_num='" + orderNum + "' ");
		//编辑时检测uuid
		List<Entity> list = super.getBySQL(builder.toString(), -1, -1, resultClass);
		if(list != null && !list.isEmpty()){
			return (InvestorAccountHistory) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select date_format(iah.createtime,'%Y-%m-%d') as createtime,iah.type,sum(iah.income) as recharge,sum(iah.pay) as withdraw from investor_account_history iah "
				+ " left outer join bank_financial_product_publish bfpp on iah.product=bfpp.uuid where 1=1 ");
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type in (" + inputParams.get("type") + ") ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
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
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		builder.append(" group by date_format(iah.createtime,'%Y%m%d'),iah.type");
		
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountByInvestor(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) "
				+ " from investor_account_history iah left join investor i on iah.investor=i.uuid "
				+ " left outer join bank_financial_product_publish bfpp on iah.product=bfpp.uuid where 1=1 ");
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and iah.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and iah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and iah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and iah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and iah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and iah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and iah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and iah.processing_status = '" + inputParams.get("processingStatus") + "' ");
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
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getListForPageByInvestor(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select iah.uuid,iah.investor,iah.order_id as orderId,iah.order_type as orderType,iah.account_balance as accountBalance,"
				+ " iah.income,iah.pay,iah.status,iah.createtime,iah.type,iah.order_num as orderNum,iah.company_account as companyAccount,"
				+ " iah.product,iah.product_type as productType, iah.poundage "
				+ " from investor_account_history iah left join investor i on iah.investor=i.uuid "
				+ " left outer join bank_financial_product_publish bfpp on iah.product=bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and iah.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and iah.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and iah.order_id = '" + inputParams.get("order") + "' ");
		}
		
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and iah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and iah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
		}
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type = '" + inputParams.get("type") + "' ");
		}
		
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and iah.type in (" + inputParams.get("serviceType") + ") ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and iah.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("processingStatus") != null && !"".equals(inputParams.get("processingStatus"))) {
			builder.append(" and iah.processing_status = '" + inputParams.get("processingStatus") + "' ");
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
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
//		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime")) && inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
//			builder.append(" and iah.createtime between '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' and '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59.9999") + "' ");
//		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("iah.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by iah.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public void insert(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into investor_account_history(uuid,investor,order_id,order_type,income,pay,account_balance,status,createtime"
				+ ",type,order_num,company_account,product,product_type,poundage,bankcard,processing_status,process_company_account"
				+ ",process_creator,process_createtime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}
	
	@Override
	public List<Entity> getListForPageByUser(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		
		//临时表名
		Random random = new Random();
		int x = random.nextInt(89999);
		x = x + 10000;
		String tableName = "tmp_glfpbu" + x;
		
		//将企财宝员工数据加入临时表
		builder.append("create temporary table ").append(tableName);
		builder.append(" select qeh.uuid,qeh.qcb_employee as user,'qcbEmployee' as userType,qeh.order_id as orderId,qeh.order_type as orderType,"
				+ "qeh.account_balance as accountBalance, qeh.income,qeh.pay,qeh.status,qeh.createtime,qeh.type,qeh.order_num as orderNum,"
				+ "qeh.product,qeh.product_type as productType, qeh.poundage "
				+ " from qcb_employee_history qeh left join qcb_employee qe on qeh.qcb_employee=qe.uuid "
				+ " left outer join bank_financial_product_publish bfpp on qeh.product=bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and qeh.qcb_employee = '" + inputParams.get("investor") + "' ");
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
		this.executeSQL(builder.toString());
				
		//将牛投理财用户数据加入临时表
		builder = new StringBuilder();
		builder.append("insert ").append(tableName);
		builder.append(" select iah.uuid,iah.investor as user,'investor' as userType,iah.order_id as orderId,iah.order_type as orderType,"
				+ "iah.account_balance as accountBalance,iah.income,iah.pay,iah.status,iah.createtime,iah.type,iah.order_num as orderNum,"
				+ " iah.product,iah.product_type as productType, iah.poundage "
				+ " from investor_account_history iah left join investor i on iah.investor=i.uuid "
				+ " left outer join bank_financial_product_publish bfpp on iah.product=bfpp.uuid where 1=1 ");
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and iah.investor = '" + inputParams.get("investor") + "' ");
		}
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.mobile like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and iah.product = '" + inputParams.get("product") + "' ");
		}
		if (inputParams.get("order") != null && !"".equals(inputParams.get("order"))) {
			builder.append(" and iah.order_id = '" + inputParams.get("order") + "' ");
		}
		if (inputParams.get("orderType") != null && !"".equals(inputParams.get("orderType"))) {
			builder.append(" and iah.order_type = '" + inputParams.get("orderType") + "' ");
		}
		if (inputParams.get("accountBalance") != null && !"".equals(inputParams.get("accountBalance"))) {
			builder.append(" and iah.account_balance = " + inputParams.get("accountBalance") + " ");
		}
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and iah.status = '" + inputParams.get("status") + "' ");
		}
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and iah.type = '" + inputParams.get("type") + "' ");
		}
		if (inputParams.get("serviceType") != null && !"".equals(inputParams.get("serviceType"))) {
			builder.append(" and iah.type in (" + inputParams.get("serviceType") + ") ");
		}
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and iah.createtime > '" + inputParams.get("createtime") + "' ");
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
			builder.append(" and iah.createtime >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and iah.createtime <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		this.executeSQL(builder.toString());
		
		//从临时表中获取数据
		builder = new StringBuilder();
		builder.append(" select * from ").append(tableName);
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append(sort);
				comma = ",";
			}
		}else{
			builder.append(" order by createtime desc");
		}
		List<Entity> list = super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
				
		//删除临时表
		builder = new StringBuilder();
		builder.append("drop table ").append(tableName).append(";");
		this.executeSQL(builder.toString());
		
		return list;
	}
	
	/**
	 * 获取活期募集总额
	 */
	public Double getCurrentTotalAmount(){
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(pay-income) from investor_account_history where type in ('cur_buy','cur_return')");
		Object obj = super.getResultBySQL(builder.toString());
		if(obj != null){
			return Double.valueOf(obj.toString());
		}else{
			return 0.0;
		}
	}
}
