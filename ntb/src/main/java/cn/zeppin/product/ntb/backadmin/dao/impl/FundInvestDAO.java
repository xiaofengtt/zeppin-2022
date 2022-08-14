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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundInvestDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.FundInvest;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundInvestDAO extends BaseDAO<FundInvest, String> implements IFundInvestDAO {


	/**
	 * 向数据库添加一条FundInvest数据
	 * @param bankFinancialProductInvest
	 * @return FundInvest
	 */
	@Override
	@Caching(put={@CachePut(value = "fundInvests", key = "'fundInvests:' + #fundInvest.uuid")})
	public FundInvest insert(FundInvest fundInvest) {
		return super.insert(fundInvest);
	}

	/**
	 * 向数据库删除一条FundInvest数据
	 * @param fundInvest
	 * @return FundInvest
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundInvests", key = "'fundInvests:' + #fundInvest.uuid")})
	public FundInvest delete(FundInvest fundInvest) {
		return super.delete(fundInvest);
	}

	/**
	 * 向数据库更新一条FundInvest数据
	 * @param fundInvest
	 * @return FundInvest
	 */
	@Override
	@Caching(put={@CachePut(value = "fundInvests", key = "'fundInvests:' + #fundInvest.uuid")})
	public FundInvest update(FundInvest fundInvest) {
		return super.update(fundInvest);
	}

	/**
	 * 根据uuid得到一个FundInvest信息
	 * @param uuid
	 * @return FundInvest
	 */
	@Override
	@Cacheable(value = "fundInvests", key = "'fundInvests:' + #uuid")
	public FundInvest get(String uuid) {
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
	public List<Entity> getList(Map<String, String> inputParams, String sorts, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select f.uuid, f.fund as fund, f.fund_publish as fundPublish,")
				.append(" f.company_account as companyAccount,f.account_balance as accountBalance,f.total_amount as totalAmount,")
				.append(" f.type, f.creator,f.createtime from fund_invest f where 1=1 ");
		
		//企业账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and f.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		//投资产品
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and f.fund = '" + inputParams.get("fund") + "' ");
		}
		//募集产品
		if (inputParams.get("fundPublish") != null && !"".equals(inputParams.get("fundPublish"))) {
			builder.append(" and f.fund_publish = '" + inputParams.get("fundPublish") + "' ");
		}
		//阶段
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and f.type = '" + inputParams.get("type") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by f.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by f.createtime desc ");
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
		builder.append(" select f.uuid, f.fund as fund, f.fund_publish as fundPublish,")
				.append(" f.company_account as companyAccount,f.account_balance as accountBalance,f.total_amount as totalAmount,")
				.append(" f.type, f.creator,f.createtime from fund_invest f where 1=1 ");
		
		//企业账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and f.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		//投资产品
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and f.fund = '" + inputParams.get("fund") + "' ");
		}
		//募集产品
		if (inputParams.get("fundPublish") != null && !"".equals(inputParams.get("fundPublish"))) {
			builder.append(" and f.fund_publish = '" + inputParams.get("fundPublish") + "' ");
		}
		//阶段
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and f.type = '" + inputParams.get("type") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by f.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by f.fund_publish asc ");
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
		builder.append(" select count(*) from fund_invest f where 1=1 ");
		//企业账户
		if (inputParams.get("companyAccount") != null && !"".equals(inputParams.get("companyAccount"))) {
			builder.append(" and f.company_account = '" + inputParams.get("companyAccount") + "' ");
		}
		//投资产品
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and f.fund = '" + inputParams.get("fund") + "' ");
		}
		//募集产品
		if (inputParams.get("fundPublish") != null && !"".equals(inputParams.get("fundPublish"))) {
			builder.append(" and f.fund_publish = '" + inputParams.get("fundPublish") + "' ");
		}
		//阶段
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and f.type = '" + inputParams.get("type") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	/**
	 * 获取银行理财产品投资分阶段列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStageList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select fi.stage as status, count(*) as count from fund_invest fi group by fi.stage");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取活期投资总额
	 * @return
	 */
	public Double getTotalCurrentInvest(){
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(total_amount) from fund_invest where type='invest' and fund_publish='").append(FundPublishUuid.CURRENT).append("'");
		Object obj = super.getResultBySQL(builder.toString());
		if(obj != null){
			return Double.valueOf(obj.toString());
		}else{
			return 0.0;
		}
	}
	
	/**
	 * 获取活期赎回总额
	 * @return
	 */
	public Double getTotalCurrentRedeem(){
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(total_amount) from fund_invest where type='redeem' and fund_publish='").append(FundPublishUuid.CURRENT).append("'");
		Object obj = super.getResultBySQL(builder.toString());
		if(obj != null){
			return Double.valueOf(obj.toString());
		}else{
			return 0.0;
		}
	}
}
