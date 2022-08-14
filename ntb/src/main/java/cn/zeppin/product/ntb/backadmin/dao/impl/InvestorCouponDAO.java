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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorCouponDAO extends BaseDAO<InvestorCoupon, String> implements IInvestorCouponDAO {
	/**
	 * 向数据库添加一条InvestorCoupon数据
	 * 向缓存investorCoupons添加一条InvestorCoupon记录，key值为uuid
	 * @param investorCoupon
	 * @return investorCoupon
	 */
	@Override
	@Caching(put={@CachePut(value = "investorCoupons", key = "'investorCoupons:' + #investorCoupon.uuid")}, evict={@CacheEvict(value = "listInvestorCoupons", allEntries = true)})
	public InvestorCoupon insert(InvestorCoupon investorCoupon) {
		return super.insert(investorCoupon);
	}

	/**
	 * 向数据库删除一条InvestorCoupon数据
	 * 在缓存investorCoupons中删除一条InvestorCoupon记录，key值为uuid
	 * @param investorCoupon
	 * @return investorCoupon
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorCoupons", key = "'investorCoupons:' + #investorCoupon.uuid"), @CacheEvict(value = "listInvestorCoupons", allEntries = true)})
	public InvestorCoupon delete(InvestorCoupon investorCoupon) {
		return super.delete(investorCoupon);
	}

	/**
	 * 向数据库更新一条InvestorCoupon数据
	 * 在缓存investorCoupons中更新一条InvestorCoupon记录，key值为uuid
	 * @param investorCoupon
	 * @return investorCoupon
	 */
	@Override
	@Caching(put={@CachePut(value = "investorCoupons", key = "'investorCoupons:' + #investorCoupon.uuid")}, evict={@CacheEvict(value = "listInvestorCoupons", allEntries = true)})
	public InvestorCoupon update(InvestorCoupon investorCoupon) {
		return super.update(investorCoupon);
	}

	/**
	 * 根据uuid得到一个InvestorCoupon信息
	 * 向缓存investorCoupons添加一条InvestorCoupon记录，key值为uuid
	 * @param uuid
	 * @return investorCoupon
	 */
	@Override
	@Cacheable(value = "investorCoupons", key = "'investorCoupons:' + #uuid")
	public InvestorCoupon get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select b.* from investor_coupon b order by b.createtime", resultClass);
	}

	@Override
	@Cacheable(value = "listInvestorCoupons")
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.coupon, b.investor, b.createtime, b.creator, b.status, b.expiry_date as expiryDate"
				+ " from investor_coupon b left join coupon c on b.coupon=c.uuid where 1=1 ");
		//名称
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "' ");
		}
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and b.investor = '" + inputParams.get("investor") + "'");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status in (" + inputParams.get("status") + ") ");
		} else {
			builder.append(" and b.status in ('unuse','used','expired') ");//不包括删除和未激活的
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.coupon_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("minInvestAccount") != null && !"".equals(inputParams.get("minInvestAccount"))) {
			builder.append(" and c.min_invest_account <= " + inputParams.get("minInvestAccount"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and b.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
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
		builder.append(" select count(*) from investor_coupon b left join coupon c on b.coupon=c.uuid where 1=1 ");
		//名称
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and b.coupon = '" + inputParams.get("coupon") + "' ");
		}
		
		if (inputParams.get("investor") != null && !"".equals(inputParams.get("investor"))) {
			builder.append(" and b.investor = '" + inputParams.get("investor") + "'");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status in (" + inputParams.get("status") + ") ");
		} else {
			builder.append(" and b.status in ('unuse','used','expired') ");//不包括删除和未激活的
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.coupon_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("minInvestAccount") != null && !"".equals(inputParams.get("minInvestAccount"))) {
			builder.append(" and c.min_invest_account <= " + inputParams.get("minInvestAccount"));
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and b.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public void insert(List<Object[]> paras) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("insert into investor_coupon(uuid,coupon,investor,createtime,creator,status,expiry_date) values(?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}

	@Override
	public void updateExpiryDate() {
		// TODO Auto-generated method stub
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update investor_coupon set status='expired' where status<>'expired' and ");
		builder.append(" unix_timestamp(expiry_date) < unix_timestamp('").append(time).append("') ");
		super.executeSQL(builder.toString());
	}

}
