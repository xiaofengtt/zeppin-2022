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

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class FundDAO extends BaseDAO<Fund, String> implements IFundDAO {


	/**
	 * 向数据库添加一条Fund数据
	 * @param fund
	 * @return Fund
	 */
	@Override
	@Caching(put={@CachePut(value = "funds", key = "'funds:' + #fund.uuid")})
	public Fund insert(Fund fund) {
		return super.insert(fund);
	}

	/**
	 * 向数据库删除一条Fund数据
	 * @param fund
	 * @return Fund
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "funds", key = "'funds:' + #fund.uuid")})
	public Fund delete(Fund fund) {
		return super.delete(fund);
	}

	/**
	 * 向数据库更新一条Fund数据
	 * @param fund
	 * @return Fund
	 */
	@Override
	@Caching(put={@CachePut(value = "funds", key = "'funds:' + #fund.uuid")})
	public Fund update(Fund fund) {
		return super.update(fund);
	}

	/**
	 * 根据uuid得到一个Fund信息
	 * @param uuid
	 * @return Fund
	 */
	@Override
	@Cacheable(value = "funds", key = "'funds:' + #uuid")
	public Fund get(String uuid) {
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
		builder.append(" select f.uuid, f.name, f.scode, f.risk_level as riskLevel, f.type, f.status from fund f where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and f.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and f.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and f.status in ('unchecked','checked','unpassed') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("f.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by f.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from fund f where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and f.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and f.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and f.status in ('unchecked','checked','unpassed') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	/**
	 * 获取基金分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("select f.status, count(*) as count from fund f group by f.status");
		return super.getBySQL(builder.toString(), resultClass);
	}
}
