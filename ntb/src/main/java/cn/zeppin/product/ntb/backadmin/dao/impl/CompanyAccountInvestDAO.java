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

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountInvestDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.CompanyAccountInvest;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CompanyAccountInvestDAO extends BaseDAO<CompanyAccountInvest, String> implements ICompanyAccountInvestDAO {


	/**
	 * 向数据库添加一条CompanyAccountInvest数据
	 * 向缓存companyAccountInvests添加一条CompanyAccountInvest记录，key值为uuid
	 * 清空缓存listCompanyAccountInvests的所有记录
	 * @param companyAccountInvest
	 * @return CompanyAccountInvest
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountInvests", key = "'companyAccountInvest:' + #companyAccountInvest.uuid")}, evict={@CacheEvict(value = "listCompanyAccountInvests", allEntries = true)})
	public CompanyAccountInvest insert(CompanyAccountInvest companyAccountInvest) {
		return super.insert(companyAccountInvest);
	}

	/**
	 * 数据库删除一条CompanyAccountInvest数据
	 * 在缓存companyAccountInvests中删除一条CompanyAccountInvest记录，key值为uuid
	 * 清空缓存listCompanyAccountInvests的所有记录
	 * @param companyAccountInvest
	 * @return CompanyAccountInvest
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "companyAccountInvests", key = "'companyAccountInvest:' + #companyAccountInvest.uuid"), @CacheEvict(value = "listCompanyAccountInvests", allEntries = true)})
	public CompanyAccountInvest delete(CompanyAccountInvest companyAccountInvest) {
		return super.delete(companyAccountInvest);
	}

	/**
	 * 向数据库更新一条CompanyAccountInvest数据
	 * 在缓存companyAccountInvests中更新一条CompanyAccountInvest记录，key值为uuid
	 * 清空缓存listCompanyAccountInvests的所有记录
	 * @param companyAccountInvest
	 * @return CompanyAccountInvest
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountInvests", key = "'companyAccountInvest:' + #companyAccountInvest.uuid")}, evict={@CacheEvict(value = "listCompanyAccountInvests", allEntries = true)})
	public CompanyAccountInvest update(CompanyAccountInvest companyAccountInvest) {
		return super.update(companyAccountInvest);
	}

	/**
	 * 根据uuid得到一个CompanyAccountInvest信息
	 * 向缓存companyAccountInvests添加一条CompanyAccountInvest记录，key值为uuid
	 * 清空缓存listCompanyAccountInvests的所有记录
	 * @param uuid
	 * @return CompanyAccountInvest
	 */
	@Override
	@Cacheable(cacheNames = "companyAccountInvests", key = "'companyAccountInvest:' + #uuid")
	public CompanyAccountInvest get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getList(Map<String, String> inputParams, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select cai.uuid, cai.company_account as companyAccount, cai.bank_financial_product as bankFinancialProduct, "
				+ " cai.account_balance as accountBalance, cai.total_amount as totalAmount, cai.total_redeem as totalRedeem, "
				+ " cai.total_return as totalReturn, cai.stage, cai.creator, cai.createtime "
				+ " from company_account_invest cai where 1=1 ");
		//账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and cai.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		
		//账户
		if (inputParams.get("bankFinancialProduct") != null && !"".equals(inputParams.get("bankFinancialProduct"))) {
			builder.append(" and cai.bank_financial_product = '" + inputParams.get("bankFinancialProduct") + "' ");
		}
		
		//类型
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage")) && !"all".equals(inputParams.get("stage")) ) {
			builder.append(" and cai.stage = '" + inputParams.get("stage") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("cai.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by cai.createtime desc ");
		}
		return super.getBySQL(builder.toString(), resultClass);
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
		builder.append(" select cai.uuid, cai.company_account as companyAccount, cai.bank_financial_product as bankFinancialProduct, "
				+ " cai.account_balance as accountBalance, cai.total_amount as totalAmount, cai.total_redeem as totalRedeem, "
				+ " cai.total_return as totalReturn, cai.stage, cai.creator, cai.createtime, o.realname as creatorName"
				+ " from company_account_invest cai left join bk_operator o on cai.creator=o.uuid where 1=1 ");
		//账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and cai.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		
		//账户
		if (inputParams.get("bankFinancialProduct") != null && !"".equals(inputParams.get("bankFinancialProduct"))) {
			builder.append(" and cai.bank_financial_product = '" + inputParams.get("bankFinancialProduct") + "' ");
		}
		
		//类型
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage")) && !"all".equals(inputParams.get("stage")) ) {
			builder.append(" and cai.stage = '" + inputParams.get("stage") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("cai.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by cai.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from company_account_invest cai where 1=1 ");
		//账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and cai.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		
		//账户
		if (inputParams.get("bankFinancialProduct") != null && !"".equals(inputParams.get("bankFinancialProduct"))) {
			builder.append(" and cai.bank_financial_product = '" + inputParams.get("bankFinancialProduct") + "' ");
		}
		
		//类型
		if (inputParams.get("stage") != null && !"".equals(inputParams.get("stage")) && !"all".equals(inputParams.get("stage")) ) {
			builder.append(" and cai.stage = '" + inputParams.get("stage") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
