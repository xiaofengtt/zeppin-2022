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

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BankFinancialProductPublishOperateDAO extends BaseDAO<BankFinancialProductPublishOperate, String> implements IBankFinancialProductPublishOperateDAO {
	
	/**
	 * 向数据库添加一条BankFinancialProductPublishOperate数据
	 * @param bankFinancialProductPublishOperate
	 * @return BankFinancialProductPublishOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductPublishOperates", key = "'bankFinancialProductPublishOperates:' + #bankFinancialProductPublishOperate.uuid")})
	public BankFinancialProductPublishOperate insert(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		return super.insert(bankFinancialProductPublishOperate);
	}

	/**
	 * 向数据库删除一条BankFinancialProductPublishOperate数据
	 * @param bankFinancialProductPublishOperate
	 * @return BankFinancialProductPublishOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductPublishOperates", key = "'bankFinancialProductPublishOperates:' + #bankFinancialProductPublishOperate.uuid")})
	public BankFinancialProductPublishOperate delete(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		return super.delete(bankFinancialProductPublishOperate);
	}

	/**
	 * 向数据库更新一条BankFinancialProductPublishOperate数据
	 * @param bankFinancialProductPublishOperate
	 * @return BankFinancialProductPublishOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductPublishOperates", key = "'bankFinancialProductPublishOperates:' + #bankFinancialProductPublishOperate.uuid")})
	public BankFinancialProductPublishOperate update(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		return super.update(bankFinancialProductPublishOperate);
	}

	/**
	 * 根据uuid得到一个BankFinancialProductPublishOperate信息
	 * @param uuid
	 * @return BankFinancialProductPublishOperate
	 */
	@Override
	@Cacheable(value = "bankFinancialProductPublishOperates", key = "'bankFinancialProductPublishOperates:' + #uuid")
	public BankFinancialProductPublishOperate get(String uuid) {
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
		builder.append(" select bfppo.uuid,bfppo.bank_financial_product_publish as bankFinancialProductPublish,bfppo.type,bfppo.value,"
				+ "bfppo.reason,bfppo.status,bfppo.checker,bfppo.checktime,bfppo.creator,bfppo.createtime,bfppo.submittime");
		builder.append(" from bank_financial_product_publish_operate bfppo left join bank_financial_product_publish bfpp on bfppo.bank_financial_product_publish = bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfpp.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfppo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and bfppo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfppo.status in ('draft','unchecked','checked','unpassed') ");
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfppo.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfppo.creator = '" + inputParams.get("creator") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by bfppo.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by bfppo.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 获取银行理财产品发布操作总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bank_financial_product_publish_operate bfppo left join bank_financial_product_publish bfpp on bfppo.bank_financial_product_publish = bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfpp.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfppo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and bfppo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfppo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfppo.type = '" + inputParams.get("type") + "' ");
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfppo.creator = '" + inputParams.get("creator") + "' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取银行理财产品发布操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfppo.status, count(*) as count from bank_financial_product_publish_operate bfppo where 1=1");
		builder.append(" and bfppo.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and bfppo.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		} else {
			builder.append(" and bfppo.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfppo.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by bfppo.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取银行理财产品发布操作分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfppo.type as status, count(*) as count from bank_financial_product_publish_operate bfppo where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfppo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and bfppo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfppo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfppo.creator = '" + inputParams.get("creator") + "' ");
		}		
		builder.append(" group by bfppo.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
