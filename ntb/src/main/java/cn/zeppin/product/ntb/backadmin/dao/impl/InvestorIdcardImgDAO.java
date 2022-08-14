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

import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorIdcardImgDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class InvestorIdcardImgDAO extends BaseDAO<InvestorIdcardImg, String> implements IInvestorIdcardImgDAO {


	/**
	 * 向数据库添加一条InvestorIdcardImg数据
	 * 向缓存investorIdcardImgs添加一条InvestorIdcardImg记录，key值为uuid
	 * 清空缓存listPageInvestorIdcardImgs,listInvestorIdcardImgs的所有记录
	 * @param investorIdcardImgs
	 * @return InvestorIdcardImgs
	 */
	@Override
	@Caching(put={@CachePut(value = "investorIdcardImgs", key = "'investorIdcardImgs:' + #investorIdcardImg.uuid")},
	evict={@CacheEvict(value = "listPageInvestorIdcardImgs", allEntries = true), @CacheEvict(value = "listInvestorIdcardImgs", allEntries = true)})
	public InvestorIdcardImg insert(InvestorIdcardImg investorIdcardImg) {
		return super.insert(investorIdcardImg);
	}

	/**
	 * 向数据库删除一条investorIdcardImg数据
	 * 在缓存banks中删除一条investorIdcardImg记录，key值为uuid
	 * 清空缓存listPageinvestorIdcardImgs,listinvestorIdcardImgs的所有记录
	 * @param investorIdcardImg
	 * @return investorIdcardImg
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "investorIdcardImgs", key = "'investorIdcardImgs:' + #investorIdcardImg.uuid"),
			@CacheEvict(value = "listPageInvestorIdcardImgs", allEntries = true),@CacheEvict(value = "listInvestorIdcardImgs", allEntries = true)})
	public InvestorIdcardImg delete(InvestorIdcardImg investorIdcardImg) {
		return super.delete(investorIdcardImg);
	}

	/**
	 * 向数据库更新一条investorIdcardImg数据
	 * 在缓存investorIdcardImgs中更新一条investorIdcardImg记录，key值为uuid
	 * 清空缓存listPageinvestorIdcardImgs,listinvestorIdcardImgs的所有记录
	 * @param investorIdcardImg
	 * @return investorIdcardImg
	 */
	@Override
	@Caching(put={@CachePut(value = "investorIdcardImgs", key = "'investorIdcardImgs:' + #investorIdcardImg.uuid")},
	evict={@CacheEvict(value = "listPageInvestorIdcardImgs", allEntries = true),@CacheEvict(value = "listInvestorIdcardImgs", allEntries = true)})
	public InvestorIdcardImg update(InvestorIdcardImg investorIdcardImg) {
		return super.update(investorIdcardImg);
	}

	/**
	 * 根据uuid得到一个investorIdcardImg信息
	 * 向缓存investorIdcardImgs添加一条investorIdcardImg记录，key值为uuid
	 * @param uuid
	 * @return investorIdcardImg
	 */
	@Override
	@Cacheable(value = "investorIdcardImgs", key = "'investorIdcardImgs:' + #uuid")
	public InvestorIdcardImg get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表,
	 * 将查询结果存入缓存listinvestorIdcardImgs，key为所有参数名+参数值
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listInvestorIdcardImgs")
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ii.* from investor_idcard_img ii where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		//
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ii.creator = '" + inputParams.get("creator") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('unchecked','checked','unpassed') ");
		}
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageInvestorIdcardImgs，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageinvestorIdcardImgs")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ii.uuid, ii.imgface, ii.imgback, ii.status, ii.creator, ii.createtime from investor_idcard_img ii where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		//
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ii.creator = '" + inputParams.get("creator") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('unchecked','checked','unpassed') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ii.").append(sort);
				comma = ",";
			}
		}else {
			builder.append(" order by ii.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor_idcard_img ii where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		//
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ii.creator = '" + inputParams.get("creator") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('unchecked','checked','unpassed') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getListForBackPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ii.uuid, ii.imgface, ii.imgback, ii.status, ii.creator, ii.createtime, ii.checker, ii.checktime, ii.reason, "
				+ "i.realname as name, i.idcard "
				+ "from investor_idcard_img ii left join investor i on ii.creator=i.uuid where 1=1 and i.status <> 'deleted' ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		//
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ii.creator = '" + inputParams.get("creator") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('unchecked','checked','unpassed') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ii.").append(sort);
				comma = ",";
			}
		}else {
			builder.append(" order by ii.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForBackPage(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from investor_idcard_img ii left join investor i on ii.creator=i.uuid where 1=1 and i.status <> 'deleted' ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ii.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (i.realname like '%" + inputParams.get("name") + "%' ");
			builder.append(" or i.idcard like '%" + inputParams.get("name") + "%') ");
		}
		//
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ii.creator = '" + inputParams.get("creator") + "' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and ii.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and ii.status in ('unchecked','checked','unpassed') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("select ii.status, count(*) as count from investor_idcard_img ii where 1=1");
		
		builder.append(" and ii.status in ('unchecked','checked','unpassed') ");//全部-去掉已删除的
		
		//创建人
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ii.creator = '" + inputParams.get("creator") + "' ");
		}
		builder.append(" group by ii.status");
		return super.getBySQL(builder.toString(), resultClass);
	}

}
