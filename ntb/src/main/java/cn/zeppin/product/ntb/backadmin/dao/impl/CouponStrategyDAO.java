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

import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CouponStrategyDAO extends BaseDAO<CouponStrategy, String> implements ICouponStrategyDAO {


	/**
	 * 向数据库添加一条CouponStrategy数据
	 * 向缓存couponStrategys添加一条CouponStrategy记录，key值为uuid
	 * 清空缓存listCouponStrategys的所有记录
	 * @param couponStrategy
	 * @return CouponStrategy
	 */
	@Override
	@Caching(put={@CachePut(value = "couponStrategys", key = "'couponStrategy:' + #couponStrategy.uuid")}, evict={@CacheEvict(value = "listCouponStrategys", allEntries = true)})
	public CouponStrategy insert(CouponStrategy couponStrategy) {
		return super.insert(couponStrategy);
	}

	/**
	 * 数据库删除一条CouponStrategy数据
	 * 在缓存couponStrategys中删除一条CouponStrategy记录，key值为uuid
	 * 清空缓存listCouponStrategys的所有记录
	 * @param couponStrategy
	 * @return CouponStrategy
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "couponStrategys", key = "'couponStrategy:' + #couponStrategy.uuid"), @CacheEvict(value = "listCouponStrategys", allEntries = true)})
	public CouponStrategy delete(CouponStrategy couponStrategy) {
		return super.delete(couponStrategy);
	}

	/**
	 * 向数据库更新一条CouponStrategy数据
	 * 在缓存couponStrategys中更新一条CouponStrategy记录，key值为uuid
	 * 清空缓存listCouponStrategys的所有记录
	 * @param couponStrategy
	 * @return CouponStrategy
	 */
	@Override
	@Caching(put={@CachePut(value = "couponStrategys", key = "'couponStrategy:' + #couponStrategy.uuid")}, evict={@CacheEvict(value = "listCouponStrategys", allEntries = true)})
	public CouponStrategy update(CouponStrategy couponStrategy) {
		return super.update(couponStrategy);
	}

	/**
	 * 根据uuid得到一个CouponStrategy信息
	 * 向缓存couponStrategys添加一条CouponStrategy记录，key值为uuid
	 * 清空缓存listCouponStrategys的所有记录
	 * @param uuid
	 * @return CouponStrategy
	 */
	@Override
	@Cacheable(cacheNames = "couponStrategys", key = "'couponStrategy:' + #uuid")
	public CouponStrategy get(String uuid) {
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
		builder.append(" select c.uuid, c.strategy_identification as strategyIdentification, c.coupon,"
				+ "c.expiry_date as expiryDate, c.createtime, c.creator, c.status "
				+ " from coupon_strategy c where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (c.strategy_identification like '%" + inputParams.get("name") + "%' ");
			builder.append(" or c.coupon like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and c.coupon = '" + inputParams.get("coupon") + "' ");
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
			builder.append(" and c.status in ('open','close') ");
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
		builder.append(" select count(*) from coupon_strategy c where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (c.strategy_identification like '%" + inputParams.get("name") + "%' ");
			builder.append(" or c.coupon like '%" + inputParams.get("name") + "%') ");
		}
		
		if (inputParams.get("coupon") != null && !"".equals(inputParams.get("coupon"))) {
			builder.append(" and c.coupon = '" + inputParams.get("coupon") + "' ");
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
			builder.append(" and c.status in ('open','close') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select c.status, count(*) as count from coupon_strategy c where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and c.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and c.status in ('open','close') ");//全部
		}
		builder.append(" group by c.status");
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public boolean isExistOperatorByStrategy(String strategy, String uuid) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from coupon_strategy b where strategy_identification=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {strategy,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
}
