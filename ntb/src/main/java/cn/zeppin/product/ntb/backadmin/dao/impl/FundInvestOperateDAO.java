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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundInvestOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundInvestOperateDAO extends BaseDAO<FundInvestOperate, String> implements IFundInvestOperateDAO {


	/**
	 * 向数据库添加一条FundInvestOperate数据
	 * @param fundInvestOperate
	 * @return FundInvestOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundInvestOperates", key = "'fundInvestOperates:' + #fundInvestOperate.uuid")})
	public FundInvestOperate insert(FundInvestOperate fundInvestOperate) {
		return super.insert(fundInvestOperate);
	}

	/**
	 * 向数据库删除一条FundInvestOperate数据
	 * @param fundInvestOperate
	 * @return FundInvestOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundInvestOperates", key = "'fundInvestOperates:' + #fundInvestOperate.uuid")})
	public FundInvestOperate delete(FundInvestOperate fundInvestOperate) {
		return super.delete(fundInvestOperate);
	}

	/**
	 * 向数据库更新一条FundInvestOperate数据
	 * @param fundInvestOperate
	 * @return FundInvestOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundInvestOperates", key = "'fundInvestOperates:' + #fundInvestOperate.uuid")})
	public FundInvestOperate update(FundInvestOperate fundInvestOperate) {
		return super.update(fundInvestOperate);
	}

	/**
	 * 根据uuid得到一个FundInvestOperate信息
	 * @param uuid
	 * @return FundInvestOperate
	 */
	@Override
	@Cacheable(value = "fundInvestOperates", key = "'fundInvestOperates:' + #uuid")
	public FundInvestOperate get(String uuid) {
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
		builder.append(" select bfpio.uuid,bfpio.fund as fund,bfpio.type,bfpio.value,bfpio.reason,bfpio.status,bfpio.checker,bfpio.checktime,bfpio.creator,bfpio.createtime,bfpio.submittime");
		builder.append(" from fund_invest_operate bfpio left join fund bfp on bfpio.fund = bfp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		
		//外键
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and bfpio.fund = '" + inputParams.get("fund") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfpio.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and bfpio.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and bfpio.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfpio.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfpio.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpio.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by bfpio.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by bfpio.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取银行理财产品投资操作总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from fund_invest_operate bfpio");
		builder.append(" left join fund bfp on bfpio.fund = bfp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		
		//外键
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and bfpio.fund = '" + inputParams.get("fund") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfpio.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and bfpio.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and bfpio.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfpio.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfpio.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpio.creator = '" + inputParams.get("creator") + "' ");
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
		builder.append("select bfpio.status, count(*) as count from fund_invest_operate bfpio where 1=1");
		builder.append(" and bfpio.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		
		//状态
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and bfpio.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpio.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by bfpio.status");
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
		builder.append("select bfpio.type as status, count(*) as count from fund_invest_operate bfpio where 1=1");
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfpio.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and bfpio.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfpio.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpio.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by bfpio.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
