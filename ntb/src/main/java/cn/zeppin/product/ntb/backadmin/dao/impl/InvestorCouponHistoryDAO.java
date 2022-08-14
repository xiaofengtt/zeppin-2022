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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponHistoryDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorCouponHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorCouponHistoryDAO extends BaseDAO<InvestorCouponHistory, String> implements IInvestorCouponHistoryDAO {
	/**
	 * 向数据库添加一条InvestorCouponHistory数据
	 * 向缓存investorCouponHistorys添加一条InvestorCouponHistory记录，key值为uuid
	 * @param investorCouponHistory
	 * @return investorCouponHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "investorCouponHistorys", key = "'investorCouponHistorys:' + #investorCouponHistory.uuid")}, evict={@CacheEvict(value = "listInvestorCouponHistorys", allEntries = true)})
	public InvestorCouponHistory insert(InvestorCouponHistory investorCouponHistory) {
		return super.insert(investorCouponHistory);
	}

	/**
	 * 向数据库删除一条InvestorCouponHistory数据
	 * 在缓存investorCouponHistorys中删除一条InvestorCouponHistory记录，key值为uuid
	 * @param investorCouponHistory
	 * @return investorCouponHistory
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorCouponHistorys", key = "'investorCouponHistorys:' + #investorCouponHistory.uuid"), @CacheEvict(value = "listInvestorCouponHistorys", allEntries = true)})
	public InvestorCouponHistory delete(InvestorCouponHistory investorCouponHistory) {
		return super.delete(investorCouponHistory);
	}

	/**
	 * 向数据库更新一条InvestorCouponHistory数据
	 * 在缓存investorCouponHistorys中更新一条InvestorCouponHistory记录，key值为uuid
	 * @param investorCouponHistory
	 * @return investorCouponHistory
	 */
	@Override
	@Caching(put={@CachePut(value = "investorCouponHistorys", key = "'investorCouponHistorys:' + #investorCouponHistory.uuid")}, evict={@CacheEvict(value = "listInvestorCouponHistorys", allEntries = true)})
	public InvestorCouponHistory update(InvestorCouponHistory investorCouponHistory) {
		return super.update(investorCouponHistory);
	}

	/**
	 * 根据uuid得到一个InvestorCouponHistory信息
	 * 向缓存investorCouponHistorys添加一条InvestorCouponHistory记录，key值为uuid
	 * @param uuid
	 * @return investorCouponHistory
	 */
	@Override
	@Cacheable(value = "investorCouponHistorys", key = "'investorCouponHistorys:' + #uuid")
	public InvestorCouponHistory get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from investor_coupon_history b order by b.createtime", resultClass);
	}

	@Override
	@Cacheable(value = "listInvestorCouponHistorys")
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.investor_product_buy as investorProductBuy, b.coupon, b.price, b.createtime, b.creator, b.status,"
				+ " c.coupon_price as couponPrice, c.coupon_name as couponName, c.coupon_type as couponType "
				+ " from investor_coupon_history b left join coupon c on b.coupon=c.uuid where 1=1 ");
		//名称
		if (inputParams.get("investorProductBuy") != null && !"".equals(inputParams.get("investorProductBuy"))) {
			builder.append(" and b.investor_product_buy = '" + inputParams.get("investorProductBuy") + "' ");
		}
		
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "'");
		}
		
		if (inputParams.get("price") != null && !"".equals(inputParams.get("price"))) {
			builder.append(" and b.price = " + inputParams.get("price"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("investorAccountHistory") != null && !"".equals(inputParams.get("investorAccountHistory"))) {
			builder.append(" and b.investor_account_history = '" + inputParams.get("investorAccountHistory") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status in ('normal') ");
		}
		
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
			builder.append(" order by b.createtime desc");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor_coupon_history b where 1=1 ");
		
		//名称
		if (inputParams.get("investorProductBuy") != null && !"".equals(inputParams.get("investorProductBuy"))) {
			builder.append(" and b.investor_product_buy = '" + inputParams.get("investorProductBuy") + "' ");
		}
		
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "'");
		}
		
		if (inputParams.get("price") != null && !"".equals(inputParams.get("price"))) {
			builder.append(" and b.price = " + inputParams.get("price"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		if (inputParams.get("investorAccountHistory") != null && !"".equals(inputParams.get("investorAccountHistory"))) {
			builder.append(" and b.investor_account_history = '" + inputParams.get("investorAccountHistory") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and b.status in ('normal') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	@Caching(evict={ @CacheEvict(value = "listInvestorCouponHistorys", allEntries = true)})
	public void insert(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into investor_coupon_history(uuid,investor_product_buy,coupon,price,createtime,creator,status,investor_account_history) values(?,?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}
	
	@Override
	@Caching(evict={@CacheEvict(value = "investorCouponHistorys", allEntries = true), @CacheEvict(value = "listInvestorCouponHistorys", allEntries = true)})
	public void update(List<Object[]> paras) {
		StringBuilder builder = new StringBuilder();
		builder.append("update investor_coupon_history set dividend=? where uuid=? ");
		super.insert(builder.toString(), paras);
	}

}
