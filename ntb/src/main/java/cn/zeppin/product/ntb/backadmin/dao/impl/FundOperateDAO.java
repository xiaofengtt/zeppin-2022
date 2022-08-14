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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.FundOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundOperateDAO extends BaseDAO<FundOperate, String> implements IFundOperateDAO {
	
	/**
	 * 向数据库添加一条FundOperate数据
	 * @param fundOperate
	 * @return FundOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundOperates", key = "'fundOperates:' + #fundOperate.uuid")})
	public FundOperate insert(FundOperate fundOperate) {
		return super.insert(fundOperate);
	}

	/**
	 * 向数据库删除一条FundOperate数据
	 * @param fundOperate
	 * @return FundOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundOperates", key = "'fundOperates:' + #fundOperate.uuid")})
	public FundOperate delete(FundOperate fundOperate) {
		return super.delete(fundOperate);
	}

	/**
	 * 向数据库更新一条FundOperate数据
	 * @param fundOperate
	 * @return FundOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundOperates", key = "'fundOperates:' + #fundOperate.uuid")})
	public FundOperate update(FundOperate fundOperate) {
		return super.update(fundOperate);
	}

	/**
	 * 根据uuid得到一个FundOperate信息
	 * @param uuid
	 * @return FundOperate
	 */
	@Override
	@Cacheable(value = "fundOperates", key = "'fundOperates:' + #uuid")
	public FundOperate get(String uuid) {
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
		builder.append(" select fo.uuid,fo.fund,fo.type,fo.value,fo.reason,fo.status,fo.checker,fo.checktime,fo.creator,fo.createtime,fo.submittime");
		builder.append(" from fund_operate fo left join fund fp on fo.fund = fp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and fp.name like '%" + inputParams.get("name") + "%' ");
		}
		//募集产品
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and fo.fund = '" + inputParams.get("fund") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and fo.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and fo.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and fo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and fo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and fo.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fo.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by fo.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by fo.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取募集产品操作总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from fund_operate fo left join fund fp on fo.fund = fp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and fp.name like '%" + inputParams.get("name") + "%' ");
		}
		//募集产品
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and fo.fund = '" + inputParams.get("fund") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and fo.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and fo.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and fo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and fo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and fo.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fo.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取活期理财操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select fo.status, count(*) as count from fund_operate fo where 1=1");
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and fo.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		} else {
			builder.append(" and fo.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fo.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by fo.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取活期理财操作分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select fo.type as status, count(*) as count from fund_operate fo where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and fo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and fo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and fo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fo.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by fo.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
