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

import cn.zeppin.product.ntb.backadmin.dao.api.IAliCertificationDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.AliCertification;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class AliCertificationDAO extends BaseDAO<AliCertification, String> implements IAliCertificationDAO {


	/**
	 * 向数据库添加一条aliCertification数据
	 * 向缓存aliCertifications添加一条aliCertification记录，key值为uuid
	 * 清空缓存listPageAliCertifications,listAliCertifications的所有记录
	 * @param aliCertification
	 * @return AliCertification
	 */
	@Override
	@Caching(put={@CachePut(value = "aliCertifications", key = "'aliCertifications:' + #aliCertification.uuid")},
	evict={@CacheEvict(value = "listPageAliCertifications", allEntries = true), @CacheEvict(value = "listAliCertifications", allEntries = true)})
	public AliCertification insert(AliCertification aliCertification) {
		return super.insert(aliCertification);
	}

	/**
	 * 向数据库删除一条aliCertification数据
	 * 在缓存banks中删除一条aliCertification记录，key值为uuid
	 * 清空缓存listPagealiCertifications,listaliCertifications的所有记录
	 * @param aliCertification
	 * @return aliCertification
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "aliCertifications", key = "'aliCertifications:' + #aliCertification.uuid"),
			@CacheEvict(value = "listPageAliCertifications", allEntries = true),@CacheEvict(value = "listAliCertifications", allEntries = true)})
	public AliCertification delete(AliCertification aliCertification) {
		return super.delete(aliCertification);
	}

	/**
	 * 向数据库更新一条aliCertification数据
	 * 在缓存aliCertifications中更新一条aliCertification记录，key值为uuid
	 * 清空缓存listPagealiCertifications,listaliCertifications的所有记录
	 * @param aliCertification
	 * @return aliCertification
	 */
	@Override
	@Caching(put={@CachePut(value = "aliCertifications", key = "'aliCertifications:' + #aliCertification.uuid")},
	evict={@CacheEvict(value = "listPageAliCertifications", allEntries = true),@CacheEvict(value = "listAliCertifications", allEntries = true)})
	public AliCertification update(AliCertification aliCertification) {
		return super.update(aliCertification);
	}

	/**
	 * 根据uuid得到一个aliCertification信息
	 * 向缓存aliCertifications添加一条aliCertification记录，key值为uuid
	 * @param uuid
	 * @return aliCertification
	 */
	@Override
	@Cacheable(value = "aliCertifications", key = "'aliCertifications:' + #uuid")
	public AliCertification get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表,
	 * 将查询结果存入缓存listaliCertifications，key为所有参数名+参数值
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listAliCertifications")
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ac.* from ali_certification ac where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.uuid = '" + inputParams.get("uuid") + "' ");
		}
		//creator product_code
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ac.creator = '" + inputParams.get("creator") + "' ");
		}
		if (inputParams.get("productCode") != null && !"".equals(inputParams.get("productCode"))) {
			builder.append(" and ac.product_code = '" + inputParams.get("productCode") + "' ");
		}
		//状态
		if (inputParams.get("code") != null && !"".equals(inputParams.get("code"))) {
			builder.append(" and ac.code = '" + inputParams.get("code") + "' ");
		}
		if (inputParams.get("subCode") != null && !"".equals(inputParams.get("subCode"))) {
			builder.append(" and ac.sub_code = '" + inputParams.get("subCode") + "' ");
		}
		
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPagealiCertifications，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageAliCertifications")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ac.* from ali_certification ac where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.bank = '" + inputParams.get("uuid") + "' ");
		}
		//creator product_code
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ac.creator = '" + inputParams.get("creator") + "' ");
		}
		if (inputParams.get("productCode") != null && !"".equals(inputParams.get("productCode"))) {
			builder.append(" and ac.product_code = '" + inputParams.get("productCode") + "' ");
		}
		//状态
		if (inputParams.get("code") != null && !"".equals(inputParams.get("code"))) {
			builder.append(" and ac.code = '" + inputParams.get("code") + "' ");
		}
		if (inputParams.get("subCode") != null && !"".equals(inputParams.get("subCode"))) {
			builder.append(" and ac.sub_code = '" + inputParams.get("subCode") + "' ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ac.").append(sort);
				comma = ",";
			}
		}else {
			builder.append(" order by ac.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from ali_certification ac where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.bank = '" + inputParams.get("uuid") + "' ");
		}
		//业务编码和验证标识码
		if (inputParams.get("binNo") != null && !"".equals(inputParams.get("binNo"))) {
			builder.append(" and ac.bin_no = '" + inputParams.get("binNo") + "' ");
		}
		if (inputParams.get("verifyCode") != null && !"".equals(inputParams.get("verifyCode"))) {
			builder.append(" and ac.verify_code like '%" + inputParams.get("verifyCode") + "%' ");
		}
		//creator product_code
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and ac.creator = '" + inputParams.get("creator") + "' ");
		}
		if (inputParams.get("productCode") != null && !"".equals(inputParams.get("productCode"))) {
			builder.append(" and ac.product_code = '" + inputParams.get("productCode") + "' ");
		}
		//状态
		if (inputParams.get("code") != null && !"".equals(inputParams.get("code"))) {
			builder.append(" and ac.code = '" + inputParams.get("code") + "' ");
		}
		if (inputParams.get("subCode") != null && !"".equals(inputParams.get("subCode"))) {
			builder.append(" and ac.sub_code = '" + inputParams.get("subCode") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

}
