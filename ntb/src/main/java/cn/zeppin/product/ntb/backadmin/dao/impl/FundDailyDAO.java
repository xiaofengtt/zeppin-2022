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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDailyDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.FundDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundDailyDAO extends BaseDAO<FundDaily, String> implements IFundDailyDAO {


	/**
	 * 向数据库添加一条FundDaily数据
	 * @param FundDaily
	 * @return FundDaily
	 */
	@Override
	@Caching(put={@CachePut(value = "fundDailys", key = "'fundDailys:' + #fundDaily.uuid")})
	public FundDaily insert(FundDaily fundDaily) {
		return super.insert(fundDaily);
	}

	/**
	 * 向数据库删除一条FundDaily数据
	 * @param FundDaily
	 * @return FundDaily
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundDailys", key = "'fundDailys:' + #fundDaily.uuid")})
	public FundDaily delete(FundDaily fundDaily) {
		return super.delete(fundDaily);
	}

	/**
	 * 向数据库更新一条FundDaily数据
	 * @param FundDaily
	 * @return FundDaily
	 */
	@Override
	@Caching(put={@CachePut(value = "fundDailys", key = "'fundDailys:' + #fundDaily.uuid")})
	public FundDaily update(FundDaily fundDaily) {
		return super.update(fundDaily);
	}

	/**
	 * 根据uuid得到一个FundDaily信息
	 * @param uuid
	 * @return FundDaily
	 */
	@Override
	@Cacheable(value = "fundDailys", key = "'fundDailys:' + #uuid")
	public FundDaily get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询BankFinancialProductDaily总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from fund_daily fr where 1=1 ");
		//名称
		if (inputParams.get("bankFinancialProduct") != null && !"".equals(inputParams.get("bankFinancialProduct"))) {
			builder.append(" and fr.bank_financial_product = '" + inputParams.get("bankFinancialProduct") + "' ");
		}
		
		if (inputParams.get("statistime") != null && !"".equals(inputParams.get("statistime"))) {
			builder.append(" and fr.statistime = '" + inputParams.get("statistime") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and fr.statistime > '" + inputParams.get("starttime") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and fr.statistime <= '" + inputParams.get("endtime") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
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
		builder.append(" select fr.uuid as uuid,fr.fund ,fr.netvalue as netValue, fr.statistime as statistime, fr.creator as creator, fr.createtime as createtime from fund_daily fr where 1=1 ");
		//名称
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and fr.fund = '" + inputParams.get("fund") + "' ");
		}
		
		if (inputParams.get("statistime") != null && !"".equals(inputParams.get("statistime"))) {
			builder.append(" and fr.statistime = '" + inputParams.get("statistime") + "' ");
		}
		
		if (inputParams.get("starttime") != null && !"".equals(inputParams.get("starttime"))) {
			builder.append(" and fr.statistime > '" + inputParams.get("starttime") + "' ");
		}
		
		if (inputParams.get("endtime") != null && !"".equals(inputParams.get("endtime"))) {
			builder.append(" and fr.statistime <= '" + inputParams.get("endtime") + "' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("fr.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by fr.statistime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
}
