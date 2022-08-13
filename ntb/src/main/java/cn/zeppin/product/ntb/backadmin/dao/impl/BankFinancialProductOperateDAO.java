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

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BankFinancialProductOperateDAO extends BaseDAO<BankFinancialProductOperate, String> implements IBankFinancialProductOperateDAO {


	/**
	 * 向数据库添加一条BankFinancialProductOperate数据
	 * @param bankFinancialProductOperate
	 * @return BankFinancialProductOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductOperates", key = "'bankFinancialProductOperates:' + #bankFinancialProductOperate.uuid")})
	public BankFinancialProductOperate insert(BankFinancialProductOperate bankFinancialProductOperate) {
		return super.insert(bankFinancialProductOperate);
	}

	/**
	 * 向数据库删除一条BankFinancialProductOperate数据
	 * @param bankFinancialProductOperate
	 * @return BankFinancialProductOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductOperates", key = "'bankFinancialProductOperates:' + #bankFinancialProductOperate.uuid")})
	public BankFinancialProductOperate delete(BankFinancialProductOperate bankFinancialProductOperate) {
		return super.delete(bankFinancialProductOperate);
	}

	/**
	 * 向数据库更新一条BankFinancialProductOperate数据
	 * @param bankFinancialProductOperate
	 * @return BankFinancialProductOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductOperates", key = "'bankFinancialProductOperates:' + #bankFinancialProductOperate.uuid")})
	public BankFinancialProductOperate update(BankFinancialProductOperate bankFinancialProductOperate) {
		return super.update(bankFinancialProductOperate);
	}

	/**
	 * 根据uuid得到一个BankFinancialProductOperate信息
	 * @param uuid
	 * @return BankFinancialProductOperate
	 */
	@Override
	@Cacheable(value = "bankFinancialProductOperates", key = "'bankFinancialProductOperates:' + #uuid")
	public BankFinancialProductOperate get(String uuid) {
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
		builder.append(" select bfpo.uuid,bfpo.bank_financial_product as bankFinancialProduct,bfpo.type,bfpo.value,"
				+ "bfpo.reason,bfpo.status,bfpo.checker,bfpo.checktime,bfpo.creator,bfpo.createtime,bfpo.submittime");
		builder.append(" from bank_financial_product_operate bfpo left join bank_financial_product bfp on bfpo.bank_financial_product = bfp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfpo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and bfpo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfpo.status in ('draft','unchecked','checked','unpassed') ");
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfpo.type = '" + inputParams.get("type") + "' ");
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpo.creator = '" + inputParams.get("creator") + "' ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by bfpo.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by bfpo.createtime desc ");
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
		builder.append(" select count(*) from bank_financial_product_operate bfpo left join bank_financial_product bfp on bfpo.bank_financial_product = bfp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfp.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfpo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and bfpo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfpo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfpo.type = '" + inputParams.get("type") + "' ");
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpo.creator = '" + inputParams.get("creator") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取银行理财产品操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpo.status, count(*) as count from bank_financial_product_operate bfpo where 1=1");
		if (inputParams.get("status") != null && "all".equals(inputParams.get("status"))) {
			builder.append(" and bfpo.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的(审核-去掉草稿)
		} else {
			builder.append(" and bfpo.status in ('draft','unchecked','checked','unpassed') ");//全部-去掉已删除的
		}
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpo.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by bfpo.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取银行理财产品操作分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpo.type as status, count(*) as count from bank_financial_product_operate bfpo where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			if("all".equals(inputParams.get("status"))){//审核列表用
				builder.append(" and bfpo.status in ('unchecked','checked','unpassed') ");
			} else {
				builder.append(" and bfpo.status = '" + inputParams.get("status") + "' ");
			}
		}else{
			builder.append(" and bfpo.status in ('draft','unchecked','checked','unpassed') ");//全部
		}
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and bfpo.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by bfpo.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
