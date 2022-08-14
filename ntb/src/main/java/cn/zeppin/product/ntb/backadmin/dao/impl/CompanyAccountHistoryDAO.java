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

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CompanyAccountHistoryDAO extends BaseDAO<CompanyAccountHistory, String> implements ICompanyAccountHistoryDAO {


	/**
	 * 向数据库添加一条CompanyAccountHistory数据
	 * 向缓存companyAccountHistorys添加一条CompanyAccountHistory记录，key值为uuid
	 * 清空缓存listCompanyAccountHistorys的所有记录
	 * @param companyAccountHistory
	 * @return CompanyAccountHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountHistorys", key = "'companyAccountHistory:' + #companyAccountHistory.uuid")}, evict={@CacheEvict(value = "listCompanyAccountHistorys", allEntries = true)})
	public CompanyAccountHistory insert(CompanyAccountHistory companyAccountHistory) {
		return super.insert(companyAccountHistory);
	}

	/**
	 * 数据库删除一条CompanyAccountHistory数据
	 * 在缓存companyAccountHistorys中删除一条CompanyAccountHistory记录，key值为uuid
	 * 清空缓存listCompanyAccountHistorys的所有记录
	 * @param companyAccountHistory
	 * @return CompanyAccountHistory
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "companyAccountHistorys", key = "'companyAccountHistory:' + #companyAccountHistory.uuid"), @CacheEvict(value = "listCompanyAccountHistorys", allEntries = true)})
	public CompanyAccountHistory delete(CompanyAccountHistory companyAccountHistory) {
		return super.delete(companyAccountHistory);
	}

	/**
	 * 向数据库更新一条CompanyAccountHistory数据
	 * 在缓存companyAccountHistorys中更新一条CompanyAccountHistory记录，key值为uuid
	 * 清空缓存listCompanyAccountHistorys的所有记录
	 * @param companyAccountHistory
	 * @return CompanyAccountHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "companyAccountHistorys", key = "'companyAccountHistory:' + #companyAccountHistory.uuid")}, evict={@CacheEvict(value = "listCompanyAccountHistorys", allEntries = true)})
	public CompanyAccountHistory update(CompanyAccountHistory companyAccountHistory) {
		return super.update(companyAccountHistory);
	}

	/**
	 * 根据uuid得到一个CompanyAccountHistory信息
	 * 向缓存companyAccountHistorys添加一条CompanyAccountHistory记录，key值为uuid
	 * 清空缓存listCompanyAccountHistorys的所有记录
	 * @param uuid
	 * @return CompanyAccountHistory
	 */
	@Override
	@Cacheable(cacheNames = "companyAccountHistorys", key = "'companyAccountHistory:' + #uuid")
	public CompanyAccountHistory get(String uuid) {
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
		builder.append(" select cah.uuid, cah.company_account_in as companyAccountIn, cah.company_account_out as companyAccountOut, "
				+ " cah.bank_financial_product as bankFinancialProduct, cah.investor, cah.qcb_company_account as qcbCompanyAccount, "
				+ "cah.related, cah.total_amount as totalAmount, cah.poundage, cah.type, cah.status, cah.creator, cah.createtime,"
				+ "cah.qcb_employee as qcbEmployee, cah.fund, cah.account_balance as accountBalance, cah.account_balance_in as accountBalanceIn, "
				+ "cah.shbx_user as shbxUser, cah.shbx_user_history as shbxUserHistory"
				+ " from company_account_history cah where 1=1 ");
		//名称
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and (cah.company_account_in = '" + inputParams.get("companyAccount") + "' ");
			builder.append(" or cah.company_account_out = '" + inputParams.get("companyAccount") + "') ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and cah.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and cah.status in ('normal','disable') ");
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type")) && !"all".equals(inputParams.get("type")) ) {
			builder.append(" and cah.type = '" + inputParams.get("type") + "' ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("cah.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by cah.createtime desc ");
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
		builder.append(" select count(*) from company_account_history cah where 1=1 ");
		//名称
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and (cah.company_account_in = '" + inputParams.get("companyAccount") + "' ");
			builder.append(" or cah.company_account_out = '" + inputParams.get("companyAccount") + "') ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and cah.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and cah.status in ('normal','disable') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type")) && !"all".equals(inputParams.get("type")) ) {
			builder.append(" and cah.type = '" + inputParams.get("type") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取历史记录分类型列表
	 * @param inputParams
	 * @return
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select cah.type as status, count(*) as count from company_account_history cah where 1=1 ");
		
		//名称
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and (cah.company_account_in = '" + inputParams.get("companyAccount") + "' ");
			builder.append(" or cah.company_account_out = '" + inputParams.get("companyAccount") + "') ");
		}
				
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and cah.status = '" + inputParams.get("status") + "' ");
		}
		
		builder.append(" group by cah.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
