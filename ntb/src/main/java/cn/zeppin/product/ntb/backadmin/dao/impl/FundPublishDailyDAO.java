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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDailyDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundPublishDailyDAO extends BaseDAO<FundPublishDaily, String> implements IFundPublishDailyDAO {


	/**
	 * 向数据库添加一条FundPublishDaily数据
	 * @param FundPublishDaily
	 * @return FundPublishDaily
	 */
	@Override
	@Caching(put={@CachePut(value = "fundPublishDailys", key = "'fundPublishDailys:' + #fundPublishDaily.uuid")})
	public FundPublishDaily insert(FundPublishDaily fundPublishDaily) {
		return super.insert(fundPublishDaily);
	}

	/**
	 * 向数据库删除一条FundPublishDaily数据
	 * @param FundPublishDaily
	 * @return FundPublishDaily
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "fundPublishDailys", key = "'fundPublishDailys:' + #fundPublishDaily.uuid")})
	public FundPublishDaily delete(FundPublishDaily fundPublishDaily) {
		return super.delete(fundPublishDaily);
	}

	/**
	 * 向数据库更新一条FundPublishDaily数据
	 * @param FundPublishDaily
	 * @return FundPublishDaily
	 */
	@Override
	@Caching(put={@CachePut(value = "fundPublishDailys", key = "'fundPublishDailys:' + #fundPublishDaily.uuid")})
	public FundPublishDaily update(FundPublishDaily fundPublishDaily) {
		return super.update(fundPublishDaily);
	}

	/**
	 * 根据uuid得到一个FundPublishDaily信息
	 * @param uuid
	 * @return FundPublishDaily
	 */
	@Override
	@Cacheable(value = "fundPublishDailys", key = "'fundPublishDailys:' + #uuid")
	public FundPublishDaily get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询FundPublishDaily总数
	 * @param inputParams
	 * @return Integer
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams){
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from fund_publish_daily fr where 1=1 ");
		//名称
		if (inputParams.get("fundPublish") != null && !"".equals(inputParams.get("fundPublish"))) {
			builder.append(" and fr.fund_publish = '" + inputParams.get("fundPublish") + "' ");
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
		builder.append(" select fr.uuid,fr.fund_publish as fundPublish,fr.netvalue as netValue, fr.statistime , fr.creator, fr.createtime from fund_publish_daily fr where 1=1 ");
		//名称
		if (inputParams.get("fundPublish") != null && !"".equals(inputParams.get("fundPublish"))) {
			builder.append(" and fr.fund_publish = '" + inputParams.get("fundPublish") + "' ");
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
