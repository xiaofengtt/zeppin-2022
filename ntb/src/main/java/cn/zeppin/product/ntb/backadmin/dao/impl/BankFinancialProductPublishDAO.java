/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BankFinancialProductPublishDAO extends BaseDAO<BankFinancialProductPublish, String> implements IBankFinancialProductPublishDAO {


	/**
	 * 向数据库添加一条BankFinancialProductPublish数据
	 * @param bankFinancialProductPublish
	 * @return BankFinancialProductPublish
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductPublishs", key = "'bankFinancialProductPublishs:' + #bankFinancialProductPublish.uuid")})
	public BankFinancialProductPublish insert(BankFinancialProductPublish bankFinancialProductPublish) {
		return super.insert(bankFinancialProductPublish);
	}

	/**
	 * 向数据库删除一条BankFinancialProductPublish数据
	 * @param bankFinancialProductPublish
	 * @return BankFinancialProductPublish
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs", key = "'bankFinancialProductPublishs:' + #bankFinancialProductPublish.uuid")})
	public BankFinancialProductPublish delete(BankFinancialProductPublish bankFinancialProductPublish) {
		return super.delete(bankFinancialProductPublish);
	}

	/**
	 * 向数据库更新一条BankFinancialProductPublish数据
	 * @param bankFinancialProductPublish
	 * @return BankFinancialProductPublish
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductPublishs", key = "'bankFinancialProductPublishs:' + #bankFinancialProductPublish.uuid")})
	public BankFinancialProductPublish update(BankFinancialProductPublish bankFinancialProductPublish) {
		return super.update(bankFinancialProductPublish);
	}

	/**
	 * 根据uuid得到一个BankFinancialProductPublish信息
	 * @param uuid
	 * @return BankFinancialProductPublish
	 */
	@Override
	@Cacheable(value = "bankFinancialProductPublishs", key = "'bankFinancialProductPublishs:' + #uuid")
	public BankFinancialProductPublish get(String uuid) {
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
		builder.append(" select bfp.uuid, bfp.bank_financial_product as bankFinancialProduct, bfp.name,bfp.scode,bfp.url,bfp.type,bfp.status,bfp.stage,bfp.target,bfp.custodian,bfp.target_annualized_return_rate as targetAnnualizedReturnRate,"
				+ " bfp.total_amount as totalAmount,bfp.collect_amount as collectAmount,bfp.collect_starttime as collectStarttime,bfp.collect_endtime as collectEndtime,bfp.term,bfp.record_date as recordDate,bfp.guarantee_status as guaranteeStatus,"
				+ " bfp.value_date as valueDate,bfp.maturity_date as maturityDate,bfp.flag_purchase as flagPurchase, bfp.flag_redemption as flagRedemption, bfp.flag_flexible as flagFlexible,bfp.currency_type as currencyType,"
				+ " bfp.risk_level as riskLevel,bfp.net_worth as netWorth,bfp.creator,bfp.createtime,bfp.area as area,bfp.min_invest_amount as minInvestAmount,bfp.max_invest_amount as maxInvestAmount, bfp.min_invest_amount_add as minInvestAmountAdd,"
				+ " bfp.account_balance as accountBalance, bfp.investment, bfp.total_redeem as totalRedeem, bfp.total_return as totalReturn, bfp.real_return_rate as realReturnRate, real_return as realReturn, real_collect as realCollect from bank_financial_product_publish bfp where 1=1 ");
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
		//investType
		if (inputParams.get("investType") != null && "1".equals(inputParams.get("investType"))) {
			builder.append(" and bfp.stage in ('uninvest', 'collect', 'invested', 'profit') ");
			builder.append(" and bfp.account_balance > 0 ");
		}
		//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and (bfp.investment > 0 or bfp.account_balance > 0) ");
		}
		//balanceType
		if (inputParams.get("balanceType") != null && !"".equals(inputParams.get("balanceType"))) {
			if(BankFinancialProductPublishStage.BALANCE.equals(inputParams.get("balanceType"))){
				builder.append(" and bfp.stage in ('profit', 'balance') ");
			}else if(BankFinancialProductPublishStage.FINISHED.equals(inputParams.get("balanceType"))){
				builder.append(" and bfp.stage = 'finished' ");
			}
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
	 * 获取募集产品总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bank_financial_product_publish bfp where 1=1 ");
		//银行理财产品
		if (inputParams.get("bankFinancialProduct") != null && !"".equals(inputParams.get("bankFinancialProduct"))) {
			builder.append(" and bfp.bank_financial_product = '" + inputParams.get("bankFinancialProduct") + "' ");
		}
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
		//investType
		if (inputParams.get("investType") != null && "1".equals(inputParams.get("investType"))) {
			builder.append(" and bfp.stage in ('uninvest', 'collect', 'invested', 'profit') ");
			builder.append(" and bfp.account_balance > 0 ");
		}
		//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and (bfp.investment > 0 or bfp.account_balance > 0) ");
		}
		//balanceType
		if (inputParams.get("balanceType") != null && !"".equals(inputParams.get("balanceType"))) {
			if(BankFinancialProductPublishStage.BALANCE.equals(inputParams.get("balanceType"))){
				builder.append(" and bfp.stage in ('profit', 'balance') ");
			}else if(BankFinancialProductPublishStage.FINISHED.equals(inputParams.get("balanceType"))){
				builder.append(" and bfp.stage = 'finished' ");
			}
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
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 根据参数查询WEB结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getWebListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, LinkedList<String> sortList,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select bfp.uuid, bfp.bank_financial_product as bankFinancialProduct, bfp.name, bfp.shortname,bfp.scode,bfp.url,bfp.type,bfp.status,bfp.stage,bfp.target,bfp.custodian,bfp.target_annualized_return_rate as targetAnnualizedReturnRate,"
				+ "bfp.total_amount as totalAmount,bfp.collect_amount as collectAmount,bfp.collect_starttime as collectStarttime,bfp.collect_endtime as collectEndtime,bfp.term,bfp.record_date as recordDate,bfp.guarantee_status as guaranteeStatus, bfp.real_return_rate as realReturnRate,"
				+ "bfp.value_date as valueDate,bfp.maturity_date as maturityDate,bfp.flag_purchase as flagPurchase, bfp.flag_redemption as flagRedemption, bfp.flag_flexible as flagFlexible,bfp.currency_type as currencyType,"
				+ "bfp.risk_level as riskLevel,bfp.net_worth as netWorth,bfp.creator,bfp.createtime,bfp.area as area,bfp.min_invest_amount as minInvestAmount,bfp.max_invest_amount as maxInvestAmount, bfp.min_invest_amount_add as minInvestAmountAdd, bfp.flag_buy as flagBuy "
				+ "from bank_financial_product_publish bfp where bfp.stage <> 'exception' and bfp.status='checked' ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		//阶段
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			builder.append(" and bfp.stage = '" + inputParams.get("stage") + "' ");
		}
		//custodian
		if (inputParams.get("custodian") != null && !"".equals(inputParams.get("custodian"))) {
			builder.append(" and bfp.custodian in " + inputParams.get("custodian"));
		}
		//currencyType
		if (inputParams.get("currencyType") != null && !"".equals(inputParams.get("currencyType"))) {
			builder.append(" and bfp.currency_type in " + inputParams.get("currencyType"));
		}
		//guaranteeStatus
		if (inputParams.get("guaranteeStatus") != null && !"".equals(inputParams.get("guaranteeStatus"))) {
			builder.append(" and bfp.guarantee_status in " + inputParams.get("guaranteeStatus"));
		}
		//flagBuy
		if (inputParams.get("flagBuy") != null && !"".equals(inputParams.get("flagBuy"))) {
			builder.append(" and bfp.flag_buy = " + inputParams.get("flagBuy") + " ");
		}
		
		//term
		if (inputParams.get("term") != null && !"".equals(inputParams.get("term"))) {
			String[] terms = inputParams.get("term").split(",");
			if(terms != null && terms.length > 0){
				builder.append(" and (");
				for(String term: terms){
					switch (term) {
					case "1":
						builder.append(" bfp.term <= 60 ");
						break;
					case "2":
						builder.append(" (bfp.term > 60 and bfp.term < 121) ");
						break;
					case "3":
						builder.append(" (bfp.term > 120 and bfp.term < 181) ");
						break;
					case "4":
						builder.append(" (bfp.term > 180 and bfp.term < 366) ");
						break;
					case "5":
						builder.append(" bfp.term > 365 ");
						break;
					default:
						break;
					}
					builder.append("or");
				}
				builder.delete(builder.length() - 2, builder.length());
				builder.append(")");
			}
		}
		// 排序
		if (sortList != null && sortList.size() > 0) {
			builder.append(" order by bfp.flag_buy desc,");
			for(String sorts : sortList){
				String[] sortArray = sorts.split("-");
				builder.append("bfp." + sortArray[0] + " " + sortArray[1]);
				builder.append(",");
			}
			builder.delete(builder.length() -1,  builder.length());
		}
		else {
			builder.append(" order by bfp.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取银行理财产品WEB发布总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getWebCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bank_financial_product_publish bfp where bfp.stage <> 'exception' and bfp.status='checked' ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		//阶段
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			builder.append(" and bfp.stage = '" + inputParams.get("stage") + "' ");
		}
		//custodian
		if (inputParams.get("custodian") != null && !"".equals(inputParams.get("custodian"))) {
			builder.append(" and bfp.custodian in " + inputParams.get("custodian"));
		}
		//currencyType
		if (inputParams.get("currencyType") != null && !"".equals(inputParams.get("currencyType"))) {
			builder.append(" and bfp.currency_type in " + inputParams.get("currencyType"));
		}
		//currencyType
		if (inputParams.get("guaranteeStatus") != null && !"".equals(inputParams.get("guaranteeStatus"))) {
			builder.append(" and bfp.guarantee_status in " + inputParams.get("guaranteeStatus"));
		}
		//flagBuy
		if (inputParams.get("flagBuy") != null && !"".equals(inputParams.get("flagBuy"))) {
			builder.append(" and bfp.flag_buy = " + inputParams.get("flagBuy") + " ");
		}
		
		//term
		if (inputParams.get("term") != null && !"".equals(inputParams.get("term"))) {
			String[] terms = inputParams.get("term").split(",");
			if(terms != null && terms.length > 0){
				builder.append(" and (");
				for(String term: terms){
					switch (term) {
					case "1":
						builder.append(" bfp.term <= 60 ");
						break;
					case "2":
						builder.append(" (bfp.term > 60 and bfp.term < 121) ");
						break;
					case "3":
						builder.append(" (bfp.term > 120 and bfp.term < 181) ");
						break;
					case "4":
						builder.append(" (bfp.term > 180 and bfp.term < 366) ");
						break;
					case "5":
						builder.append(" bfp.term > 365 ");
						break;
					default:
						break;
					}
					builder.append("or");
				}
				builder.delete(builder.length() - 2, builder.length());
				builder.append(")");
			}
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取募集产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpp.status, count(*) as count from bank_financial_product_publish bfpp group by bfpp.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取募集产品分阶段列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStageList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpp.stage as status, count(*) as count from bank_financial_product_publish bfpp where bfpp.status='checked'");
		//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and (bfpp.investment > 0 or bfpp.account_balance > 0) ");
		}
		builder.append(" group by bfpp.stage");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 修改认购中阶段（开启认购）
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs")})
	public void updateStageCollect() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product_publish set stage='collect',flag_buy=1 where status='checked' and stage = 'unstart' and ");
		builder.append(" unix_timestamp(collect_starttime) < unix_timestamp('").append(time).append("') and unix_timestamp(collect_endtime) > unix_timestamp('").append(time).append("')");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 修改募集中 募集完成（(collect_amount-real_collect)< min_invest_amount）
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs")})
	public void updateStageUninvestByCollect() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product_publish set stage='uninvest',flag_buy=1 where status='checked' and stage = 'collect' and ");
		builder.append(" (collect_amount-real_collect) < min_invest_amount and (unix_timestamp(collect_endtime) >= unix_timestamp('").append(time).append("') ");
		builder.append(" and unix_timestamp(collect_starttime) <= unix_timestamp('").append(time).append("')) ");
		super.executeSQL(builder.toString());
	}
	/**
	 * 修改待投资阶段（结束认购）
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs")})
	public void updateStageUninvest() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product_publish set stage='uninvest',flag_buy=0 where status='checked' and stage in ('collect','uninvest') and ");
		builder.append(" real_collect > 0 and unix_timestamp(collect_endtime) < unix_timestamp('").append(time).append("') ");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 修改待投资阶段（投资结束）
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs")})
	public void updateStageInvested() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product_publish set stage='finished',flag_buy=0 where status='checked' and stage = 'collect' and ");
		builder.append(" real_collect=0 and unix_timestamp(collect_endtime) < unix_timestamp('").append(time).append("') ");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 修改收益中阶段
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs")})
	public void updateStageProfit() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product_publish set stage='profit',flag_buy=0 where status='checked' and stage in ('uninvest','invested') and ");
		builder.append(" unix_timestamp(value_date) < unix_timestamp('").append(time).append("') and unix_timestamp(maturity_date) > unix_timestamp('").append(time).append("')");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 修改结算阶段（投资完成）
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs")})
	public void updateStageBalance() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product_publish set stage='balance',flag_buy=0 where status='checked' and stage = 'profit' and");
		builder.append(" unix_timestamp(maturity_date) < unix_timestamp('").append(time).append("') and (account_balance>0 or investment>0)");
		super.executeSQL(builder.toString());
	}
	
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishs")})
	public void updateStageBalance4Finish() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update bank_financial_product_publish set stage='finished',flag_buy=0 where status='checked' and stage = 'profit' and");
		builder.append(" unix_timestamp(maturity_date) < unix_timestamp('").append(time).append("') and (account_balance=0 and investment=0)");
		super.executeSQL(builder.toString());
	}
	
	/**
	 * 验证同名的募集产品信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductPublishByName(String name, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bank_financial_product_publish b where status='checked' and name=?0 ");
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
	 * 验证同编号的募集产品信息是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistBankFinancialProductPublishByScode(String scode, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from bank_financial_product_publish b where status='checked' and scode=?0 ");
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

	@Override
	public List<Entity> getBankList(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.name, b.short_name as shortName, b.icon_color as iconColor, r.url as iconColorUrl, b.status, b.single_limit as singleLimit, b.daily_limit as dailyLimit "
				+ "from bank_financial_product_publish bf "
				+ "left join bank b on bf.custodian=b.uuid "
				+ "left join resource r on b.icon_color=r.uuid "
				+ "where bf.status='checked' and bf.stage <> 'exception' ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		
		//名称
		if (inputParams.get("bankName") != null && !"".equals(inputParams.get("bankName"))) {
			builder.append(" and (b.name like '%" + inputParams.get("bankName") + "%' or b.short_name like '%" + inputParams.get("bankName") + "%') ");
		}
		
		if (inputParams.get("flagBinding") != null && !"".equals(inputParams.get("flagBinding"))) {
			builder.append(" and b.flag_binding = " + inputParams.get("flagBinding") + " ");
		}
		
		//flagBuy
		if (inputParams.get("flagBuy") != null && !"".equals(inputParams.get("flagBuy"))) {
			builder.append(" and bf.flag_buy = " + inputParams.get("flagBuy") + " ");
		}
		
		builder.append(" group by bf.custodian ");
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("b.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by b.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

}
