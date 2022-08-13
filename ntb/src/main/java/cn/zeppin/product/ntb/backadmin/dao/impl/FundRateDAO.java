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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundRateDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.FundRate;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundRateDAO extends BaseDAO<FundRate, String> implements IFundRateDAO {


	/**
	 * 向数据库添加一条FundRate数据
	 * @param fundRate
	 * @return FundRate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundRates", key = "'fundRates:' + #fundRate.uuid")})
	public FundRate insert(FundRate fundRate) {
		return super.insert(fundRate);
	}

	/**
	 * 向数据库删除一条FundRate数据
	 * @param fundRate
	 * @return FundRate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundRates", key = "'fundRates:' + #fundRate.uuid")})
	public FundRate delete(FundRate fundRate) {
		return super.delete(fundRate);
	}

	/**
	 * 向数据库更新一条FundRate数据
	 * @param fundRate
	 * @return FundRate
	 */
	@Override
	@Caching(put={@CachePut(value = "fundRates", key = "'fundRates:' + #fundRate.uuid")})
	public FundRate update(FundRate fundRate) {
		return super.update(fundRate);
	}
	
	/**
	 * 根据uuid得到一个FundRate信息
	 * @param uuid
	 * @return FundRate
	 */
	@Override
	@Cacheable(value = "fundRates", key = "'fundRates:' + #uuid")
	public FundRate get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 向数据库删除一条FundRate数据
	 * @param fundRate
	 * @return FundRate
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundRates")})
	public void deleteByType(Fund fund, String rateType) {
		StringBuilder builder = new StringBuilder();
		builder.append("delete from fund_rate where fund='").append(fund.getUuid()).append("' and type='").append(rateType).append("'");
		this.executeSQL(builder.toString());
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
		builder.append(" select fr.* from fund_rate fr where 1=1 ");
		//名称
		if (inputParams.get("fund") != null && !"".equals(inputParams.get("fund"))) {
			builder.append(" and fr.fund = '" + inputParams.get("fund") + "' ");
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
			builder.append(" order by fr.lowlimit ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
}
