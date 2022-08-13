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

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestOperateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BankFinancialProductInvestOperateDAO extends BaseDAO<BankFinancialProductInvestOperate, String> implements IBankFinancialProductInvestOperateDAO {


	/**
	 * 向数据库添加一条BankFinancialProductInvestOperate数据
	 * @param bankFinancialProductInvestOperate
	 * @return BankFinancialProductInvestOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductInvestOperates", key = "'bankFinancialProductInvestOperates:' + #bankFinancialProductInvestOperate.uuid")})
	public BankFinancialProductInvestOperate insert(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		return super.insert(bankFinancialProductInvestOperate);
	}

	/**
	 * 向数据库删除一条BankFinancialProductInvestOperate数据
	 * @param bankFinancialProductInvestOperate
	 * @return BankFinancialProductInvestOperate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "bankFinancialProductInvestOperates", key = "'bankFinancialProductInvestOperates:' + #bankFinancialProductInvestOperate.uuid")})
	public BankFinancialProductInvestOperate delete(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		return super.delete(bankFinancialProductInvestOperate);
	}

	/**
	 * 向数据库更新一条BankFinancialProductInvestOperate数据
	 * @param bankFinancialProductInvestOperate
	 * @return BankFinancialProductInvestOperate
	 */
	@Override
	@Caching(put={@CachePut(value = "bankFinancialProductInvestOperates", key = "'bankFinancialProductInvestOperates:' + #bankFinancialProductInvestOperate.uuid")})
	public BankFinancialProductInvestOperate update(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		return super.update(bankFinancialProductInvestOperate);
	}

	/**
	 * 根据uuid得到一个BankFinancialProductInvestOperate信息
	 * @param uuid
	 * @return BankFinancialProductInvestOperate
	 */
	@Override
	@Cacheable(value = "bankFinancialProductInvestOperates", key = "'bankFinancialProductInvestOperates:' + #uuid")
	public BankFinancialProductInvestOperate get(String uuid) {
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
		builder.append(" select bfpio.uuid,bfpio.bank_financial_product_invest as bankFinancialProductInvest,bfpio.type,bfpio.value,"
				+ "bfpio.reason,bfpio.status,bfpio.checker,bfpio.checktime,bfpio.creator,bfpio.createtime");
		builder.append(" from bank_financial_product_invest_operate bfpio left join bank_financial_product_invest bfpi on bfpio.bank_financial_product_invest = bfpi.uuid ");
		builder.append(" left join bank_financial_product_publish bfpp on bfpi.bank_financial_product_publish = bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfpp.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bfpio.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and bfpio.status in ('unchecked','checked','unpassed') ");
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfpio.type = '" + inputParams.get("type") + "' ");
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
		builder.append(" select count(*) from bank_financial_product_invest_operate bfpio left join bank_financial_product_invest bfpi on bfpio.bank_financial_product_invest = bfpi.uuid ");
		builder.append(" left join bank_financial_product_publish bfpp on bfpi.bank_financial_product_publish = bfpp.uuid where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and bfpp.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bfpio.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and bfpio.status in ('unchecked','checked','unpassed') ");//全部
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and bfpio.type = '" + inputParams.get("type") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取银行理财产品投资操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpio.status, count(*) as count from bank_financial_product_invest_operate bfpio group by bfpio.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 获取银行理财产品投资操作分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select bfpio.type as status, count(*) as count from bank_financial_product_invest_operate bfpio where 1=1");
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and bfpio.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and bfpio.status in ('unchecked','checked','unpassed') ");//全部
		}
		builder.append(" group by bfpio.type");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
