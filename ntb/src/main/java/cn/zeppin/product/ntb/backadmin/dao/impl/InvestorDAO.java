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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorDAO extends BaseDAO<Investor, String> implements IInvestorDAO {


	/**
	 * 向数据库添加一条investor数据
	 * @param investor
	 * @return investor
	 */
	@Override
	@Caching(put={@CachePut(value = "investors", key = "'investors:' + #investor.uuid")})
	public Investor insert(Investor investor) {
		return super.insert(investor);
	}

	/**
	 * 向数据库删除一条investor数据
	 * @param investor
	 * @return investor
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investors", key = "'investors:' + #investor.uuid")})
	public Investor delete(Investor investor) {
		return super.delete(investor);
	}

	/**
	 * 向数据库更新一条BankFinancialProduct数据
	 * @param bankFinancialProduct
	 * @return BankFinancialProduct
	 */
	@Override
	@Caching(put={@CachePut(value = "investors", key = "'investors:' + #investor.uuid")})
	public Investor update(Investor investor) {
		return super.update(investor);
	}

	/**
	 * 根据uuid得到一个BankFinancialProduct信息
	 * @param uuid
	 * @return BankFinancialProduct
	 */
	@Override
	@Cacheable(value = "investors", key = "'investors:' + #uuid")
	public Investor get(String uuid) {
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
		builder.append(" select i.uuid,i.nickname,i.realname,i.idcard,i.mobile,i.email,i.status,i.total_amount as totalAmount,"
				+ "i.total_return as totalReturn,i.account_balance as accountBalance,i.createtime,i.referrer,i.regist_source as registSource,i.last_login_time as lastLoginTime,"
				+ "i.last_login_ip as lastLoginIp from investor i where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and i.uuid like '%" + inputParams.get("uuid") + "%' ");
		}
		
		//名称
		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and i.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		if (inputParams.get("nickname") != null && !"".equals(inputParams.get("nickname"))) {
			builder.append(" and i.nickname like '%" + inputParams.get("nickname") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and i.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and i.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		if (inputParams.get("email") != null && !"".equals(inputParams.get("email"))) {
			builder.append(" and i.email = '" + inputParams.get("email") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and i.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and i.status in ('normal','disable','locked','unopen') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("i.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by i.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor i where 1=1 ");
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and i.uuid like '%" + inputParams.get("uuid") + "%' ");
		}
		
		//名称
		if (inputParams.get("realname") != null && !"".equals(inputParams.get("realname"))) {
			builder.append(" and i.realname like '%" + inputParams.get("realname") + "%' ");
		}
		
		if (inputParams.get("nickname") != null && !"".equals(inputParams.get("nickname"))) {
			builder.append(" and i.nickname like '%" + inputParams.get("nickname") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and i.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and i.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		if (inputParams.get("email") != null && !"".equals(inputParams.get("email"))) {
			builder.append(" and i.email = '" + inputParams.get("email") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and i.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and i.status in ('normal','disable','locked','unopen') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取银行理财产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select i.status, count(*) as count from investor i group by i.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
