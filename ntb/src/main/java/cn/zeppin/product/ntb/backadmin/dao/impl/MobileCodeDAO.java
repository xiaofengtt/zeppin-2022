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

import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class MobileCodeDAO extends BaseDAO<MobileCode, String> implements IMobileCodeDAO {


	/**
	 * 向数据库添加一条MobileCode数据
	 * 向缓存MobileCodes添加一条MobileCode记录，key值为uuid
	 * 清空缓存listPageMobileCodes的所有记录
	 * @param MobileCode
	 * @return MobileCode
	 */
	@Override
	@Caching(put={@CachePut(value = "MobileCodes", key = "'MobileCodes:' + #mobileCode.uuid")}, evict={@CacheEvict(value = "listPageMobileCodes", allEntries = true)})
	public MobileCode insert(MobileCode mobileCode) {
		return super.insert(mobileCode);
	}

	/**
	 * 向数据库删除一条MobileCode数据
	 * 在缓存MobileCodes中删除一条MobileCode记录，key值为uuid
	 * 清空缓存listPageMobileCodes的所有记录
	 * @param MobileCode
	 * @return MobileCode
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "MobileCodes", key = "'MobileCodes:' + #mobileCode.uuid"), @CacheEvict(value = "listPageMobileCodes", allEntries = true), 
			@CacheEvict(value = "listPageBranchMobileCodes", allEntries = true)})
	public MobileCode delete(MobileCode mobileCode) {
		return super.delete(mobileCode);
	}

	/**
	 * 向数据库更新一条MobileCode数据
	 * 在缓存MobileCodes中更新一条MobileCode记录，key值为uuid
	 * 清空缓存listPageMobileCodes的所有记录
	 * @param MobileCode
	 * @return MobileCode
	 */
	@Override
	@Caching(put={@CachePut(value = "MobileCodes", key = "'MobileCodes:' + #mobileCode.uuid")}, evict={@CacheEvict(value = "listPageMobileCodes", allEntries = true),
			@CacheEvict(value = "listPageBranchMobileCodes", allEntries = true)})
	public MobileCode update(MobileCode mobileCode) {
		return super.update(mobileCode);
	}

	/**
	 * 根据uuid得到一个MobileCode信息
	 * 向缓存MobileCodes添加一条MobileCode记录，key值为uuid
	 * @param uuid
	 * @return MobileCode
	 */
	@Override
	@Cacheable(value = "MobileCodes", key = "'MobileCodes:' + #uuid")
	public MobileCode get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageMobileCodes，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageMobileCodes")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.mobile, b.code, b.content, b.status, b.type, b.creator, b.createtime, b.creator_type as creatorType "
				+ " from mobile_code b where 1=1 ");
		//名称
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and b.mobile like '%" + inputParams.get("mobile") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and b.type = '" + inputParams.get("type") + "' ");
		}
		//创建人类型
		if (inputParams.get("creatorType") != null && !"".equals(inputParams.get("creatorType"))) {
			builder.append(" and b.creator_type = '" + inputParams.get("creatorType") + "' ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("b.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by b.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from mobile_code b where 1=1 ");
		//名称
		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and b.mobile like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('normal','disable') ");
		}
		//类型
		if (inputParams.get("type") != null && !"".equals(inputParams.get("type"))) {
			builder.append(" and b.type = '" + inputParams.get("type") + "' ");
		}
		//创建人类型
		if (inputParams.get("creatorType") != null && !"".equals(inputParams.get("creatorType"))) {
			builder.append(" and b.creator_type = '" + inputParams.get("creatorType") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public void insert(List<Object[]> paras) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("insert into mobile_code(uuid,mobile,code,content,type,creator,createtime,creator_type,status) values(?,?,?,?,?,?,?,?,?)");
		super.insert(builder.toString(), paras);
	}
}
