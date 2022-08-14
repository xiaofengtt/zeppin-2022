package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IAdvertDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Advert;
import cn.zeppin.product.ntb.core.entity.base.Entity;

@Repository
public class AdvertDAO extends BaseDAO<Advert, String> implements IAdvertDAO {

	@Override
	@Caching(put = { @CachePut(value = "adverts", key = "'adverts:' + #advert.uuid") }, evict = {
			@CacheEvict(value = "listPageAdverts", allEntries = true) })
	public Advert insert(Advert advert) {
		return super.insert(advert);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "adverts", key = "'adverts:' + #advert.uuid"),
			@CacheEvict(value = "listPageAdverts", allEntries = true),
			@CacheEvict(value = "listPageBranchAdverts", allEntries = true) })
	public Advert delete(Advert advert) {
		return super.delete(advert);
	}

	@Override
	@Caching(put={@CachePut(value = "adverts", key = "'adverts:' + #advert.uuid")}, evict={@CacheEvict(value = "listPageAdverts", allEntries = true),
			@CacheEvict(value = "listPageBranchAdverts", allEntries = true) })
	public Advert update(Advert advert) {
		return super.update(advert);
	}

	@Override
	@Cacheable(value = "adverts", key = "'adverts:' + #uuid")
	public Advert get(String uuid) {
		// TODO Auto-generated method stub
		return super.get(uuid);
	}

	/**
	 * 根据参数查询结果列表(带分页、排序), 将查询结果存入缓存listPageBanks，key为所有参数名+参数值
	 * 
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageAdverts")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(
				" select a.uuid, a.title,a.picture, r.url as pictureUrl,a.creator,bko.realname as creatorRealname, a.createtime,a.status"
						+ " from advert a left join resource r on a.picture=r.uuid"
						+ " left join bk_operator bko on a.creator=bko.uuid where 1=1");
		// 名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and a.title like '%" + inputParams.get("title") + "%' ");
		}
		// 状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and a.status = '" + inputParams.get("status") + "' ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("a.").append(sort);
				comma = ",";
			}
		} else {
			builder.append(" order by a.createtime desc ");
		}
		return super.getBySQL(builder.toString(), resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from advert a where 1=1 ");
		// 名称
		if (inputParams.get("title") != null && !"".equals(inputParams.get("title"))) {
			builder.append(" and a.title like '%" + inputParams.get("title") + "%' ");
		}
		// 状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and a.status = '" + inputParams.get("status") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "listPageAdverts") })
	public void updateStatusToInvalid() {
		StringBuilder builder = new StringBuilder();
		builder.append(" update advert set status='disable'");
		super.executeSQL(builder.toString());
	}
}
