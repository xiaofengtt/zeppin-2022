/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class InvestorProductBuyDAO extends BaseDAO<InvestorProductBuy, String> implements IInvestorProductBuyDAO {


	/**
	 * 向数据库添加一条InvestorProductBuy数据
	 * 向缓存investorProductBuys添加一条InvestorProductBuy记录，key值为uuid
	 * 清空缓存listInvestorProductBuys的所有记录
	 * @param investorProductBuy
	 * @return InvestorProductBuy
	 */
	@Override
	@Caching(put={@CachePut(value = "investorProductBuys", key = "'investorProductBuy:' + #investorProductBuy.uuid")}, evict={@CacheEvict(value = "listInvestorProductBuys", allEntries = true)})
	public InvestorProductBuy insert(InvestorProductBuy investorProductBuy) {
		return super.insert(investorProductBuy);
	}

	/**
	 * 数据库删除一条InvestorProductBuy数据
	 * 在缓存investorProductBuys中删除一条InvestorProductBuy记录，key值为uuid
	 * 清空缓存listInvestorProductBuys的所有记录
	 * @param investorProductBuy
	 * @return InvestorProductBuy
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorProductBuys", key = "'investorProductBuy:' + #investorProductBuy.uuid"), @CacheEvict(value = "listInvestorProductBuys", allEntries = true)})
	public InvestorProductBuy delete(InvestorProductBuy investorProductBuy) {
		return super.delete(investorProductBuy);
	}

	/**
	 * 向数据库更新一条InvestorProductBuy数据
	 * 在缓存investorProductBuys中更新一条InvestorProductBuy记录，key值为uuid
	 * 清空缓存listInvestorProductBuys的所有记录
	 * @param investorProductBuy
	 * @return InvestorProductBuy
	 */
	@Override
	@Caching(put={@CachePut(value = "investorProductBuys", key = "'investorProductBuy:' + #investorProductBuy.uuid")}, evict={@CacheEvict(value = "listInvestorProductBuys", allEntries = true)})
	public InvestorProductBuy update(InvestorProductBuy investorProductBuy) {
		return super.update(investorProductBuy);
	}

	/**
	 * 根据uuid得到一个InvestorProductBuy信息
	 * 向缓存investorProductBuys添加一条InvestorProductBuy记录，key值为uuid
	 * 清空缓存listInvestorProductBuys的所有记录
	 * @param uuid
	 * @return InvestorProductBuy
	 */
	@Override
	@Cacheable(cacheNames = "investorProductBuys", key = "'investorProductBuy:' + #uuid")
	public InvestorProductBuy get(String uuid) {
		return super.get(uuid);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor_product_buy ipb where 1=1 ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ipb.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and ipb.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and ipb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and ipb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and ipb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and ipb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ipb.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getList(Map<String, String> inputParams, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ipb.uuid,ipb.investor,ipb.product,ipb.total_amount as totalAmount,ipb.total_return as totalReturn,ipb.stage, "
				+ "ipb.createtime,ipb.total_redeem as totalRedeem,ipb.account_balance as accountBalance, ipb.type from investor_product_buy ipb where 1=1 ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ipb.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and ipb.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and ipb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and ipb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and ipb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and ipb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ipb.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ipb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by ipb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ipb.uuid,ipb.investor,ipb.product,ipb.total_amount as totalAmount,ipb.total_return as totalReturn,ipb.stage, "
				+ "ipb.createtime,ipb.total_redeem as totalRedeem,ipb.account_balance as accountBalance, ipb.type from investor_product_buy ipb where 1=1 ");
		//名称
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ipb.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and ipb.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and ipb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and ipb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and ipb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and ipb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ipb.createtime > '" + inputParams.get("createtime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ipb.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by ipb.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listInvestorProductBuys")})
	public void updateConfirmStage() {
		StringBuilder builder = new StringBuilder();
//		builder.append("update investor_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='profit' where i.type='bank_product' and i.stage='confirming' and b.stage in ('uninvest','invested','profit') and b.flag_buy=0 ");
		builder.append("update investor_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='profit' where i.type='bank_product' and i.stage<>'finished' and b.stage in ('uninvest','invested','profit') and b.flag_buy=0 ");//测试使用，上线时改回去
		super.executeSQL(builder.toString());
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listInvestorProductBuys")})
	public void updatePofitStage() {
		StringBuilder builder = new StringBuilder();
//		builder.append("update investor_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='balance' where i.type='bank_product' and i.stage='profit' and b.stage in ('balance')");
		builder.append("update investor_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='balance' where i.type='bank_product' and i.stage<>'finished' and b.stage in ('balance')");//测试使用，上线时改回去
		super.executeSQL(builder.toString());
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listInvestorProductBuys")})
	public void updateBalanceStage() {
		StringBuilder builder = new StringBuilder();
//		builder.append("update investor_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='finished' where i.type='bank_product' and i.stage='balance' and b.stage in ('finished')");
		builder.append("update investor_product_buy i left join bank_financial_product_publish b on i.product=b.uuid set i.stage='finished' where i.type='bank_product' and i.stage<>'finished' and b.stage in ('finished')");//测试使用，上线时改回去
		super.executeSQL(builder.toString());
	}

	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ipb.product as product, ipb.type as type, bfpp.term as term, bfpp.target_annualized_return_rate returnRate, date_format(bfpp.maturity_date,'%Y-%m-%d') as createtime,sum(ipb.total_amount) as price from investor_product_buy ipb left join bank_financial_product_publish bfpp on ipb.product=bfpp.uuid "
				+ "where 1=1 "
//				+ "and ipb.stage<>'finished'"//是否需要查询了历史预期提现金额
				+ " ");
		
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and ipb.type in (" + inputParams.get("type") + ") ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and bfpp.maturity_date >= '" + Timestamp.valueOf(inputParams.get("starttime").toString() + " 00:00:00") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and bfpp.maturity_date <= '" + Timestamp.valueOf(inputParams.get("endtime").toString() + " 23:59:59") + "' ");
		}
		
		builder.append(" group by ipb.product,date_format(bfpp.maturity_date,'%Y%m%d')");
		
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public List<Entity> getAllUserListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		
		//临时表名
		Random random = new Random();
		int x = random.nextInt(89999);
		x = x + 10000;
		String tableName = "tmp_gauc" + x;
		
		//将企财宝员工数据加入临时表
		builder.append("create temporary table ").append(tableName);
		builder.append(" select qepb.uuid,qepb.qcb_employee as user,'qcbEmployee' as userType,qepb.product,qepb.total_amount as totalAmount,");
		builder.append("qepb.total_return as totalReturn,qepb.stage,qepb.createtime,qepb.total_redeem as totalRedeem,qepb.account_balance as accountBalance, qepb.type");
		builder.append(" from qcb_employee_product_buy qepb where 1=1 ");
		if (inputParams.get("qcbEmployee") != null && !"".equals(inputParams.get("qcbEmployee"))) {
			builder.append(" and qepb.qcb_employee = '" + inputParams.get("qcbEmployee") + "' ");
		}
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and qepb.product = '" + inputParams.get("product") + "' ");
		}
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and qepb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and qepb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and qepb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and qepb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and qepb.createtime > '" + inputParams.get("createtime") + "' ");
		}
		this.executeSQL(builder.toString());
				
		//将牛投理财用户数据加入临时表
		builder = new StringBuilder();
		builder.append("insert ").append(tableName);
		builder.append(" select ipb.uuid,ipb.investor as user,'investor' as userType,ipb.product,ipb.total_amount as totalAmount,");
		builder.append("ipb.total_return as totalReturn,ipb.stage,ipb.createtime,ipb.total_redeem as totalRedeem,ipb.account_balance as accountBalance, ipb.type");
		builder.append(" from investor_product_buy ipb where 1=1 ");
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and ipb.investor = '" + inputParams.get("investor") + "' ");
		}
		
		if (inputParams.get("product") != null && !"".equals(inputParams.get("product"))) {
			builder.append(" and ipb.product = '" + inputParams.get("product") + "' ");
		}
		
		if (inputParams.get("totalAmount") != null && !"".equals(inputParams.get("totalAmount"))) {
			builder.append(" and ipb.total_amount = " + inputParams.get("totalAmount") + " ");
		}
		
		if (inputParams.get("totalReturn") != null && !"".equals(inputParams.get("totalReturn"))) {
			builder.append(" and ipb.total_return = " + inputParams.get("totalReturn") + " ");
		}
		
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			if("transaction".equals(inputParams.get("stage"))){
				builder.append(" and ipb.stage in ('confirming','balance') ");
			} else {
				builder.append(" and ipb.stage = '" + inputParams.get("stage") + "' ");
			}
		}
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and ipb.createtime > '" + inputParams.get("createtime") + "' ");
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
}
