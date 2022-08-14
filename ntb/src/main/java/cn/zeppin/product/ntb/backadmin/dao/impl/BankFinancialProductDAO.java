/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BankFinancialProductDAO extends BaseDAO<BankFinancialProduct, String> implements IBankFinancialProductDAO {


	/**
	 * 向数据库添加一条BankFinancialProduct数据
	 * @param bankFinancialProduct
	 * @return BankFinancialProduct
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProducts", key = "'bankFinancialProducts:' + #bankFinancialProduct.uuid")})
	public BankFinancialProduct insert(BankFinancialProduct bankFinancialProduct) {
		return super.insert(bankFinancialProduct);
	}

	/**
	 * 向数据库删除一条BankFinancialProduct数据
	 * @param bankFinancialProduct
	 * @return BankFinancialProduct
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProducts", key = "'bankFinancialProducts:' + #bankFinancialProduct.uuid")})
	public BankFinancialProduct delete(BankFinancialProduct bankFinancialProduct) {
		return super.delete(bankFinancialProduct);
	}

	/**
	 * 向数据库更新一条BankFinancialProduct数据
	 * @param bankFinancialProduct
	 * @return BankFinancialProduct
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProducts", key = "'bankFinancialProducts:' + #bankFinancialProduct.uuid")})
	public BankFinancialProduct update(BankFinancialProduct bankFinancialProduct) {
		return super.update(bankFinancialProduct);
	}

	/**
	 * 根据uuid得到一个BankFinancialProduct信息
	 * @param uuid
	 * @return BankFinancialProduct
	 */
	@Override
	@Cacheable(value = "bankFinancialProducts", key = "'bankFinancialProducts:' + #uuid")
	public BankFinancialProduct get(String uuid) {
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
		builder.append(" select bfp.uuid,bfp.name,bfp.scode,bfp.url,bfp.type,bfp.stage,bfp.status,bfp.target,bfp.custodian,bfp.target_annualized_return_rate as targetAnnualizedReturnRate,"
				+ " bfp.total_amount as totalAmount,bfp.collect_starttime as collectStarttime,bfp.collect_endtime as collectEndtime,bfp.term,bfp.record_date as recordDate,bfp.guarantee_status as guaranteeStatus,"
				+ " bfp.value_date as valueDate,bfp.maturity_date as maturityDate,bfp.flag_purchase as flagPurchase, bfp.flag_redemption as flagRedemption, bfp.flag_flexible as flagFlexible,bfp.currency_type as currencyType,"
				+ " bfp.risk_level as riskLevel,bfp.net_worth as netWorth,bfp.creator,bfp.createtime,bfp.area as area,bfp.min_invest_amount as minInvestAmount,bfp.max_invest_amount as maxInvestAmount, bfp.min_invest_amount_add as minInvestAmountAdd,"
				+ " bfp.account_balance as accountBalance, bfp.investment, bfp.total_redeem as totalRedeem, bfp.total_return as totalReturn, bfp.is_redeem as isRedeem from bank_financial_product bfp where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		//阶段
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			builder.append(" and bfp.stage = '" + inputParams.get("stage") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bfp.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and bfp.status in ('checked','deleted') ");
		}
		
		//其他搜索条件
		//term
		if (inputParams.get("term") != null && !"".equals(inputParams.get("term"))) {
			switch (inputParams.get("term")) {
			case "1":
				builder.append(" and bfp.term <= 60");
				break;
			case "2":
				builder.append(" and bfp.term > 60 and bfp.term < 121");
				break;
			case "3":
				builder.append(" and bfp.term > 120 and bfp.term < 181");
				break;
			case "4":
				builder.append(" and bfp.term > 180 and bfp.term < 366");
				break;
			case "5":
				builder.append(" and bfp.term > 365");
				break;

			default:
				break;
			}
		}
		//预期收益
		if (inputParams.get("income") != null && !"".equals(inputParams.get("income"))) {
			switch (inputParams.get("income")) {
			case "1":
				builder.append(" and bfp.target_annualized_return_rate <= 3");
				break;
			case "2":
				builder.append(" and bfp.target_annualized_return_rate > 3 and bfp.target_annualized_return_rate <= 4");
				break;
			case "3":
				builder.append(" and bfp.target_annualized_return_rate > 4 and bfp.target_annualized_return_rate <= 5");
				break;
			case "4":
				builder.append(" and bfp.target_annualized_return_rate > 5 and bfp.target_annualized_return_rate <= 6");
				break;
			case "5":
				builder.append(" and bfp.target_annualized_return_rate > 6");
				break;

			default:
				break;
			}
		}
		//type
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfp.type = '" + inputParams.get("type") + "'");
		}
		//riskLevel
		if (inputParams.get("riskLevel") != null && !"".equals(inputParams.get("riskLevel"))) {
			builder.append(" and bfp.risk_level = '" + inputParams.get("riskLevel") + "'");
		}
		//custodian
		if (inputParams.get("custodian") != null && !"".equals(inputParams.get("custodian"))) {
			builder.append(" and bfp.custodian = '" + inputParams.get("custodian") + "'");
		}
		//redeem
		if (inputParams.get("redeem") != null && !"".equals(inputParams.get("redeem"))) {
			builder.append(" and bfp.flag_redemption = '" + inputParams.get("redeem") + "'");
		}
		//isRedeem
		if (inputParams.get("isRedeem") != null && !"".equals(inputParams.get("isRedeem"))) {
			builder.append(" and bfp.is_redeem = '" + inputParams.get("isRedeem") + "'");
		}
		//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and bfp.investment > 0 ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by bfp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by bfp.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	/**
	 * 获取银行理财产品总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bank_financial_product bfp where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		//阶段
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			builder.append(" and bfp.stage = '" + inputParams.get("stage") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bfp.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and bfp.status in ('checked','deleted') ");//全部
		}
		//其他搜索条件
		if (inputParams.get("term") != null && !"".equals(inputParams.get("term"))) {
			switch (inputParams.get("term")) {
			case "1":
				builder.append(" and bfp.term <= 60");
				break;
			case "2":
				builder.append(" and bfp.term > 60 and bfp.term < 121");
				break;
			case "3":
				builder.append(" and bfp.term > 120 and bfp.term < 181");
				break;
			case "4":
				builder.append(" and bfp.term > 180 and bfp.term < 366");
				break;
			case "5":
				builder.append(" and bfp.term > 365");
				break;

			default:
				break;
			}
		}
		//预期收益
		if (inputParams.get("income") != null && !"".equals(inputParams.get("income"))) {
			switch (inputParams.get("income")) {
			case "1":
				builder.append(" and bfp.target_annualized_return_rate <= 3");
				break;
			case "2":
				builder.append(" and bfp.target_annualized_return_rate > 3 and bfp.target_annualized_return_rate <= 4");
				break;
			case "3":
				builder.append(" and bfp.target_annualized_return_rate > 4 and bfp.target_annualized_return_rate <= 5");
				break;
			case "4":
				builder.append(" and bfp.target_annualized_return_rate > 5 and bfp.target_annualized_return_rate <= 6");
				break;
			case "5":
				builder.append(" and bfp.target_annualized_return_rate > 6");
				break;

			default:
				break;
			}
		}
		//type
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfp.type = '" + inputParams.get("type") + "'");
		}
		//riskLevel
		if (inputParams.get("riskLevel") != null && !"".equals(inputParams.get("riskLevel"))) {
			builder.append(" and bfp.risk_level = '" + inputParams.get("riskLevel") + "'");
		}
		//custodian
		if (inputParams.get("custodian") != null && !"".equals(inputParams.get("custodian"))) {
			builder.append(" and bfp.custodian = '" + inputParams.get("custodian") + "'");
		}
		//redeem
		if (inputParams.get("redeem") != null && !"".equals(inputParams.get("redeem"))) {
			builder.append(" and bfp.flag_redemption = '" + inputParams.get("redeem") + "'");
		}
		//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and bfp.investment > 0 ");
		}
		//isRedeem
		if (inputParams.get("isRedeem") != null && !"".equals(inputParams.get("isRedeem"))) {
			builder.append(" and bfp.is_redeem = '" + inputParams.get("isRedeem") + "'");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 查询可发布理财产品列表
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@Override
	public List<Entity> getPublishListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select bfp.uuid,bfp.name,bfp.scode,bfp.url,bfp.type,bfp.stage,bfp.status,bfp.target,bfp.custodian,bfp.target_annualized_return_rate as targetAnnualizedReturnRate,"
				+ " bfp.total_amount as totalAmount,bfp.collect_starttime as collectStarttime,bfp.collect_endtime as collectEndtime,bfp.term,bfp.record_date as recordDate,bfp.guarantee_status as guaranteeStatus,"
				+ " bfp.value_date as valueDate,bfp.maturity_date as maturityDate,bfp.flag_purchase as flagPurchase, bfp.flag_redemption as flagRedemption, bfp.flag_flexible as flagFlexible,bfp.currency_type as currencyType,"
				+ " bfp.risk_level as riskLevel,bfp.net_worth as netWorth,bfp.creator,bfp.createtime,bfp.area as area,bfp.min_invest_amount as minInvestAmount,bfp.max_invest_amount as maxInvestAmount, bfp.min_invest_amount_add as minInvestAmountAdd,"
				+ " bfp.account_balance as accountBalance, bfp.investment, bfp.total_redeem as totalRedeem, bfp.total_return as totalReturn, bfp.is_redeem as isRedeem "
				+ " from bank_financial_product bfp left join bank_financial_product_publish bfpp on bfpp.bank_financial_product = bfp.uuid ");
		builder.append(" left join bank b on bfp.custodian = b.uuid ");
		builder.append("where bfp.status='checked' and bfpp.uuid is null ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (bfp.name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.name like '%" + inputParams.get("name") + "%') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by bfp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by bfp.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 查询可发布理财产品个数
	 * @return
	 */
	@Override
	public Integer getPublishCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bank_financial_product bfp left join bank_financial_product_publish bfpp on bfpp.bank_financial_product = bfp.uuid ");
		builder.append(" left join bank b on bfp.custodian = b.uuid ");
		builder.append(" where bfp.status='checked' and bfpp.uuid is null ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (bfp.name like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.name like '%" + inputParams.get("name") + "%') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取银行理财产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfp.status, count(*) as count from bank_financial_product bfp group by bfp.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取银行理财产品分阶段列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStageList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfp.stage as status, count(*) as count from bank_financial_product bfp where bfp.status='checked'");
		//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and bfp.investment > 0 ");
		}
		builder.append(" group by bfp.stage");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 修改待投资阶段
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProducts")})
	public void updateStageCollect() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product set stage='collect' where status='checked' and ");
		builder.append(" unix_timestamp(collect_starttime) < unix_timestamp('").append(time).append("') and unix_timestamp(collect_endtime) > unix_timestamp('").append(time).append("')");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 修改收益中阶段
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProducts")})
	public void updateStageIncome() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product set stage='income' where status='checked' and ");
		builder.append(" unix_timestamp(value_date) < unix_timestamp('").append(time).append("') and unix_timestamp(maturity_date) > unix_timestamp('").append(time).append("')");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 修改投资完成阶段
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProducts")})
	public void updateStageFinished() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product set stage='finished' where status='checked' and ");
		builder.append(" unix_timestamp(maturity_date) < unix_timestamp('").append(time).append("')");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 验证同名的银行理财产品信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductByName(String name, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bank_financial_product b where status='checked' and name=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {name,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证同编号的银行理财产品信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductByScode(String scode, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bank_financial_product b where status='checked' and scode=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {scode,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
