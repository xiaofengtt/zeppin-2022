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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundPublishDAO extends BaseDAO<FundPublish, String> implements IFundPublishDAO {


	/**
	 * 向数据库添加一条FundPublish数据
	 * @param fundPublish
	 * @return FundPublish
	 */
	@Override
	@Caching(put={@CachePut(value = "fundPublishs", key = "'fundPublishs:' + #fundPublish.uuid")})
	public FundPublish insert(FundPublish fundPublish) {
		return super.insert(fundPublish);
	}

	/**
	 * 向数据库删除一条FundPublish数据
	 * @param fundPublish
	 * @return FundPublish
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundPublishs", key = "'fundPublishs:' + #fundPublish.uuid")})
	public FundPublish delete(FundPublish fundPublish) {
		return super.delete(fundPublish);
	}

	/**
	 * 向数据库更新一条FundPublish数据
	 * @param fundPublish
	 * @return FundPublish
	 */
	@Override
	@Caching(put={@CachePut(value = "fundPublishs", key = "'fundPublishs:' + #fundPublish.uuid")})
	public FundPublish update(FundPublish fundPublish) {
		return super.update(fundPublish);
	}

	/**
	 * 根据uuid得到一个FundPublish信息
	 * @param uuid
	 * @return FundPublish
	 */
	@Override
	@Cacheable(value = "fundPublishs", key = "'fundPublishs:' + #uuid")
	public FundPublish get(String uuid) {
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
		builder.append(" select f.uuid, f.name, f.scode, f.shortname, f.type, f.status, f.gp, f.custodian, f.flag_structured as flagStructured, "
				+ "f.structured_type as structuredType, f.structured_remark as structuredRemark, f.style, f.risk_level as riskLevel,"
				+ "f.credit_level as creditLevel, f.performance_level as performanceLevel, f.flag_purchase as flagPurchase, f.flag_redemption as flagRedemption,"
				+ "f.planing_scale as planingScale, f.actual_scale as actualScale, f.gp_purchase_scale as gpPurchaseScale, f.lastest_scale as lastestScale,"
				+ "f.setuptime, f.collect_starttime as collectStarttime, f.collect_endtime as collectEndtime, f.purchase_starttime as purchaseStarttime,"
				+ "f.purchase_endtime as purchaseEndtime, f.goal, f.invest_idea as investIdea, f.invest_scope as investScope, f.invest_staregy as investStaregy,"
				+ "f.invest_standard as investStandard, f.revenue_feature as revenueFeature, f.risk_management as riskManagement, f.net_worth as netWorth,"
				+ "f.creator, f.createtime from fund_publish f where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and f.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and f.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and f.status in ('checked') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("f.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by f.createtime desc ");
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
		builder.append(" select count(*) from fund_publish f where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and f.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and f.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and f.status in ('checked') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
