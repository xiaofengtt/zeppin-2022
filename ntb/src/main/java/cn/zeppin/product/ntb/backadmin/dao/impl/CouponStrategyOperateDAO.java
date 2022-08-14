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

import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */

@Repository
public class CouponStrategyOperateDAO extends BaseDAO<CouponStrategyOperate, String> implements ICouponStrategyOperateDAO {


	/**
	 * 向数据库添加一条CouponStrategyOperate数据
	 * 向缓存couponStrategyOperates添加一条CouponStrategyOperate记录，key值为uuid
	 * 清空缓存listCouponStrategyOperates的所有记录
	 * @param couponStrategyOperate
	 * @return CouponStrategyOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "couponStrategyOperates", key = "'couponStrategyOperate:' + #couponStrategyOperate.uuid")}, evict={@CacheEvict(value = "listCouponStrategyOperates", allEntries = true)})
	public CouponStrategyOperate insert(CouponStrategyOperate couponStrategyOperate) {
		return super.insert(couponStrategyOperate);
	}

	/**
	 * 数据库删除一条CouponStrategyOperate数据
	 * 在缓存couponStrategyOperates中删除一条CouponStrategyOperate记录，key值为uuid
	 * 清空缓存listCouponStrategyOperates的所有记录
	 * @param couponStrategyOperate
	 * @return CouponStrategyOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "couponStrategyOperates", key = "'couponStrategyOperate:' + #couponStrategyOperate.uuid"), @CacheEvict(value = "listCouponStrategyOperates", allEntries = true)})
	public CouponStrategyOperate delete(CouponStrategyOperate couponStrategyOperate) {
		return super.delete(couponStrategyOperate);
	}

	/**
	 * 向数据库更新一条CouponStrategyOperate数据
	 * 在缓存couponStrategyOperates中更新一条CouponStrategyOperate记录，key值为uuid
	 * 清空缓存listCouponStrategyOperates的所有记录
	 * @param couponStrategyOperate
	 * @return CouponStrategyOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "couponStrategyOperates", key = "'couponStrategyOperate:' + #couponStrategyOperate.uuid")}, evict={@CacheEvict(value = "listCouponStrategyOperates", allEntries = true)})
	public CouponStrategyOperate update(CouponStrategyOperate couponStrategyOperate) {
		return super.update(couponStrategyOperate);
	}

	/**
	 * 根据uuid得到一个CouponStrategyOperate信息
	 * 向缓存couponStrategyOperates添加一条CouponStrategyOperate记录，key值为uuid
	 * 清空缓存listCouponStrategyOperates的所有记录
	 * @param uuid
	 * @return CouponStrategyOperate
	 */
	@Override
	@Cacheable(cacheNames = "couponStrategyOperates", key = "'couponStrategyOperate:' + #uuid")
	public CouponStrategyOperate get(String uuid) {
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
		builder.append(" select cao.uuid,cao.coupon_strategy as couponStrategy,cao.type,cao.value,"
				+ "cao.reason,cao.status,cao.checker,cao.checktime,cao.creator,cao.createtime,cao.submittime");
		builder.append(" from coupon_strategy_operate cao left join coupon_strategy ca on cao.coupon_strategy = ca.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (ca.strategy_identification like '%" + inputParams.get("name") + "%' ");
			builder.append(" or cao.value like '%\"strategyIdentification\":\"%" + inputParams.get("name") + "%\",\"uuid\"%') ");
		}
		//企业账户
		if (inputParams.get("couponStrategy") != null && !"".equals(inputParams.get("couponStrategy"))) {
			builder.append(" and cao.coupon_strategy = '" + inputParams.get("couponStrategy") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cao.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and cao.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and cao.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and cao.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by cao.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by cao.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from coupon_strategy_operate cao left join coupon_strategy ca on cao.coupon_strategy = ca.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (ca.strategy_identification like '%" + inputParams.get("name") + "%' ");
			builder.append(" or cao.value like '%\"strategyIdentification\":\"%" + inputParams.get("name") + "%\",\"uuid\"%') ");
		}
		//企业账户
		if (inputParams.get("couponStrategy") != null && !"".equals(inputParams.get("couponStrategy"))) {
			builder.append(" and cao.coupon_strategy = '" + inputParams.get("couponStrategy") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cao.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and cao.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and cao.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and cao.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select cao.status, count(*) as count from coupon_strategy_operate cao where 1=1");
		builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		//状态
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and cao.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by cao.status");
		return super.getBySQL(builder.toString(), resultClass);
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
		builder.append("select cao.type as status, count(*) as count from coupon_strategy_operate cao where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and cao.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and cao.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and cao.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and cao.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by cao.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
