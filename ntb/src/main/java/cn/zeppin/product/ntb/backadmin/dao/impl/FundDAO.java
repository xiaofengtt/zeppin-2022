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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundDAO extends BaseDAO<Fund, String> implements IFundDAO {


	/**
	 * 向数据库添加一条Fund数据
	 * @param fund
	 * @return Fund
	 */
	@Override
	@Caching(put={@CachePut(value = "funds", key = "'funds:' + #fund.uuid")})
	public Fund insert(Fund fund) {
		return super.insert(fund);
	}

	/**
	 * 向数据库删除一条Fund数据
	 * @param fund
	 * @return Fund
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "funds", key = "'funds:' + #fund.uuid")})
	public Fund delete(Fund fund) {
		return super.delete(fund);
	}

	/**
	 * 向数据库更新一条Fund数据
	 * @param fund
	 * @return Fund
	 */
	@Override
	@Caching(put={@CachePut(value = "funds", key = "'funds:' + #fund.uuid")})
	public Fund update(Fund fund) {
		return super.update(fund);
	}

	/**
	 * 根据uuid得到一个Fund信息
	 * @param uuid
	 * @return Fund
	 */
	@Override
	@Cacheable(value = "funds", key = "'funds:' + #uuid")
	public Fund get(String uuid) {
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
				+ "f.account_balance as accountBalance, f.creator, f.createtime from fund f where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and f.name like '%" + inputParams.get("name") + "%' ");
		}
		//管理银行
		if (inputParams.get("gp") != null && !"".equals(inputParams.get("gp"))) {
			builder.append(" and f.gp = '" + inputParams.get("gp") + "' ");
		}
		//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and f.account_balance > 0 ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and f.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and f.status in ('unchecked','checked','unpassed') ");
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
		builder.append(" select count(*) from fund f where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and f.name like '%" + inputParams.get("name") + "%' ");
		}
		//管理银行
		if (inputParams.get("gp") != null && !"".equals(inputParams.get("gp"))) {
			builder.append(" and f.gp = '" + inputParams.get("gp") + "' ");
		}//invested
		if (inputParams.get("invested") != null && "1".equals(inputParams.get("invested"))) {
			builder.append(" and f.account_balance > 0 ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and f.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and f.status in ('unchecked','checked','unpassed') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	/**
	 * 获取活期理财分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select f.status, count(*) as count from fund f group by f.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 验证同名的活期理财是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistFundByName(String name, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from fund f where status='checked' and name=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {name,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证同编号的活期理财是否已经存在
	 * @param scode
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistFundByScode(String scode, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from fund f where status='checked' and scode=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {scode,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
