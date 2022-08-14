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

import cn.zeppin.product.ntb.backadmin.dao.api.ICouponDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CouponDAO extends BaseDAO<Coupon, String> implements ICouponDAO {


	/**
	 * 向数据库添加一条Coupon数据
	 * 向缓存coupons添加一条Coupon记录，key值为uuid
	 * 清空缓存listCoupons的所有记录
	 * @param coupon
	 * @return Coupon
	 */
	@Override
	@Caching(put={@CachePut(value = "coupons", key = "'coupon:' + #coupon.uuid")}, evict={@CacheEvict(value = "listCoupons", allEntries = true)})
	public Coupon insert(Coupon coupon) {
		return super.insert(coupon);
	}

	/**
	 * 数据库删除一条Coupon数据
	 * 在缓存coupons中删除一条Coupon记录，key值为uuid
	 * 清空缓存listCoupons的所有记录
	 * @param coupon
	 * @return Coupon
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "coupons", key = "'coupon:' + #coupon.uuid"), @CacheEvict(value = "listCoupons", allEntries = true)})
	public Coupon delete(Coupon coupon) {
		return super.delete(coupon);
	}

	/**
	 * 向数据库更新一条Coupon数据
	 * 在缓存coupons中更新一条Coupon记录，key值为uuid
	 * 清空缓存listCoupons的所有记录
	 * @param coupon
	 * @return Coupon
	 */
	@Override
	@Caching(put={@CachePut(value = "coupons", key = "'coupon:' + #coupon.uuid")}, evict={@CacheEvict(value = "listCoupons", allEntries = true)})
	public Coupon update(Coupon coupon) {
		return super.update(coupon);
	}

	/**
	 * 根据uuid得到一个Coupon信息
	 * 向缓存coupons添加一条Coupon记录，key值为uuid
	 * 清空缓存listCoupons的所有记录
	 * @param uuid
	 * @return Coupon
	 */
	@Override
	@Cacheable(cacheNames = "coupons", key = "'coupon:' + #uuid")
	public Coupon get(String uuid) {
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
		builder.append(" select c.uuid, c.coupon_name as couponName, c.coupon_type as couponType, c.coupon_price as couponPrice,"
				+ " c.min_invest_account as minInvestAccount,c.expiry_date as expiryDate, c.deadline, c.createtime, c.creator, c.status "
				+ " from coupon c where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.coupon_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("couponName") != null && !"".equals(inputParams.get("couponName"))) {
			builder.append(" and c.coupon_name = '" + inputParams.get("couponName") + "' ");
		}
		
		if (inputParams.get("couponType") != null && !"".equals(inputParams.get("couponType"))) {
			builder.append(" and c.coupon_type = '" + inputParams.get("couponType") + "' ");
		}
		//
		if (inputParams.get("couponPrice") != null && !"".equals(inputParams.get("couponPrice"))) {
			builder.append(" and c.coupon_price = " + inputParams.get("couponPrice"));
		}
		
		if (inputParams.get("minInvestAccount") != null && !"".equals(inputParams.get("minInvestAccount"))) {
			builder.append(" and c.min_invest_account = " + inputParams.get("minInvestAccount"));
		}
		
		if (inputParams.get("deadline") != null && !"".equals(inputParams.get("deadline"))) {
			builder.append(" and c.deadline = " + inputParams.get("deadline"));
		}
		//类型
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and c.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and c.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and c.creator = '" + inputParams.get("creator") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and c.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and c.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("c.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by c.createtime desc ");
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
		builder.append(" select count(*) from coupon c where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and c.coupon_name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("couponName") != null && !"".equals(inputParams.get("couponName"))) {
			builder.append(" and c.coupon_name = '" + inputParams.get("couponName") + "' ");
		}
		
		if (inputParams.get("couponType") != null && !"".equals(inputParams.get("couponType"))) {
			builder.append(" and c.coupon_type = '" + inputParams.get("couponType") + "' ");
		}
		//
		if (inputParams.get("couponPrice") != null && !"".equals(inputParams.get("couponPrice"))) {
			builder.append(" and c.coupon_price = " + inputParams.get("couponPrice"));
		}
		
		if (inputParams.get("minInvestAccount") != null && !"".equals(inputParams.get("minInvestAccount"))) {
			builder.append(" and c.min_invest_account = " + inputParams.get("minInvestAccount"));
		}
		
		if (inputParams.get("deadline") != null && !"".equals(inputParams.get("deadline"))) {
			builder.append(" and c.deadline = " + inputParams.get("deadline"));
		}
		//类型
		if (inputParams.get("expiryDate") != null && !"".equals(inputParams.get("expiryDate"))) {
			builder.append(" and c.expiry_date = '" + inputParams.get("expiryDate") + "' ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and c.createtime = '" + inputParams.get("createtime") + "' ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and c.creator = '" + inputParams.get("creator") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and c.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and c.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select c.type as status, count(*) as count from coupon c where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and c.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and c.status in ('normal','disable') ");//全部
		}
		builder.append(" group by c.coupon_type");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select c.status, count(*) as count from coupon c where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and c.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and c.status in ('normal','disable') ");//全部
		}
		builder.append(" group by c.status");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public void updateExpiryDate() {
		// TODO Auto-generated method stub
		Timestamp time = new Timestamp(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append("update coupon set status='disable' where status = 'normal' and ");
		builder.append(" unix_timestamp(expiry_date) < unix_timestamp('").append(time).append("') ");
		super.executeSQL(builder.toString());
	}
	
}
