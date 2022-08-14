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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundPublishOperateDAO extends BaseDAO<FundPublishOperate, String> implements IFundPublishOperateDAO {
	
	/**
	 * 向数据库添加一条FundPublishOperate数据
	 * @param fundPublishOperate
	 * @return FundPublishOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundPublishOperates", key = "'fundPublishOperates:' + #fundPublishOperate.uuid")})
	public FundPublishOperate insert(FundPublishOperate fundPublishOperate) {
		return super.insert(fundPublishOperate);
	}

	/**
	 * 向数据库删除一条FundPublishOperate数据
	 * @param fundPublishOperate
	 * @return FundPublishOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundPublishOperates", key = "'fundPublishOperates:' + #fundPublishOperate.uuid")})
	public FundPublishOperate delete(FundPublishOperate fundPublishOperate) {
		return super.delete(fundPublishOperate);
	}

	/**
	 * 向数据库更新一条FundPublishOperate数据
	 * @param fundPublishOperate
	 * @return FundPublishOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundPublishOperates", key = "'fundPublishOperates:' + #fundPublishOperate.uuid")})
	public FundPublishOperate update(FundPublishOperate fundPublishOperate) {
		return super.update(fundPublishOperate);
	}

	/**
	 * 根据uuid得到一个FundPublishOperate信息
	 * @param uuid
	 * @return FundPublishOperate
	 */
	@Override
	@Cacheable(value = "fundPublishOperates", key = "'fundPublishOperates:' + #uuid")
	public FundPublishOperate get(String uuid) {
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
		builder.append(" select fpo.uuid,fpo.fund_publish as fundPublish,fpo.type,fpo.value,"
				+ "fpo.reason,fpo.status,fpo.checker,fpo.checktime,fpo.creator,fpo.createtime,fpo.submittime");
		builder.append(" from fund_publish_operate fpo left join fund_publish fp on fpo.fund_publish = fp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and fp.name like '%" + inputParams.get("name") + "%' ");
		}
		//募集产品
		if (inputParams.get("fundPublish") != null && !"".equals(inputParams.get("fundPublish"))) {
			builder.append(" and fpo.fund_publish = '" + inputParams.get("fundPublish") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and fpo.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and fpo.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and fpo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and fpo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and fpo.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fpo.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by fpo.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by fpo.createtime desc ");
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
		builder.append(" select count(*) from fund_publish_operate fpo left join fund_publish fp on fpo.fund_publish = fp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and fp.name like '%" + inputParams.get("name") + "%' ");
		}
		//募集产品
		if (inputParams.get("fundPublish") != null && !"".equals(inputParams.get("fundPublish"))) {
			builder.append(" and fpo.fund_publish = '" + inputParams.get("fundPublish") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and fpo.status in ('unchecked','checked','unpassed') ");
			} else if("editor".equals(inputParams.get("status"))){
				builder.append(" and fpo.status in ('draft','unchecked','unpassed') ");
			} else {
				builder.append(" and fpo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and fpo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and fpo.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fpo.creator = '" + inputParams.get("creator") + "' ");
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
		builder.append("select fpo.status, count(*) as count from fund_publish_operate fpo where 1=1");
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and fpo.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		} else {
			builder.append(" and fpo.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fpo.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by fpo.status");
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
		builder.append("select fpo.type as status, count(*) as count from fund_publish_operate fpo where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and fpo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and fpo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and fpo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and fpo.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by fpo.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
