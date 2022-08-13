/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BankFinancialProductInvestDAO extends BaseDAO<BankFinancialProductInvest, String> implements IBankFinancialProductInvestDAO {


	/**
	 * 向数据库添加一条BankFinancialProductInvest数据
	 * @param bankFinancialProductInvest
	 * @return BankFinancialProductInvest
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductInvests", key = "'bankFinancialProductInvests:' + #bankFinancialProductInvest.uuid")})
	public BankFinancialProductInvest insert(BankFinancialProductInvest bankFinancialProductInvest) {
		return super.insert(bankFinancialProductInvest);
	}

	/**
	 * 向数据库删除一条BankFinancialProductInvest数据
	 * @param bankFinancialProductInvest
	 * @return BankFinancialProductInvest
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductInvests", key = "'bankFinancialProductInvests:' + #bankFinancialProductInvest.uuid")})
	public BankFinancialProductInvest delete(BankFinancialProductInvest bankFinancialProductInvest) {
		return super.delete(bankFinancialProductInvest);
	}

	/**
	 * 向数据库更新一条BankFinancialProductInvest数据
	 * @param bankFinancialProductInvest
	 * @return BankFinancialProductInvest
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductInvests", key = "'bankFinancialProductInvests:' + #bankFinancialProductInvest.uuid")})
	public BankFinancialProductInvest update(BankFinancialProductInvest bankFinancialProductInvest) {
		return super.update(bankFinancialProductInvest);
	}

	/**
	 * 根据uuid得到一个BankFinancialProductInvest信息
	 * @param uuid
	 * @return BankFinancialProductInvest
	 */
	@Override
	@Cacheable(value = "bankFinancialProductInvests", key = "'bankFinancialProductInvests:' + #uuid")
	public BankFinancialProductInvest get(String uuid) {
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
		builder.append(" select bfp.uuid, bfp.bank_financial_product as bankFinancialProduct, bfp.bank_financial_product_publish as bankFinancialProductPublish,")
				.append(" bfp.amount,bfp.redeem_amount as redeemAmount,bfp.invest_income as investIncome,bfp.return_capital as returnCapital,")
				.append(" bfp.return_interest as returnInterest,bfp.platfom_income as platfomIncome,bfp.status, bfp.stage, bfp.creator,bfp.createtime from bank_financial_product_invest bfp where 1=1 ");
		//阶段
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			builder.append(" and bfp.stage = '" + inputParams.get("stage") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bfp.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and bfp.status in ('unchecked','checked','deleted') ");
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
	 * 获取银行理财产品投资总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bank_financial_product_invest bfp where 1=1 ");
		//阶段
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage"))) {
			builder.append(" and bfp.stage = '" + inputParams.get("stage") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bfp.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and bfp.status in ('unchecked','checked','deleted') ");//全部
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取银行理财产品投资分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpi.status, count(*) as count from bank_financial_product_invest bfpi group by bfpi.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取银行理财产品投资分阶段列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStageList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpi.stage as status, count(*) as count from bank_financial_product_invest bfpi where bfpi.status='checked' group by bfpi.stage");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
