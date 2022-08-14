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

import cn.zeppin.product.ntb.backadmin.dao.api.IAliQrcodeDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.AliQrcode;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class AliQrcodeDAO extends BaseDAO<AliQrcode, String> implements IAliQrcodeDAO {


	/**
	 * 向数据库添加一条aliQrcode数据
	 * 向缓存aliQrcodes添加一条aliQrcode记录，key值为uuid
	 * 清空缓存listPageAliQrcodes,listAliQrcodes的所有记录
	 * @param aliQrcode
	 * @return AliQrcode
	 */
	@Override
	@Caching(put={@CachePut(value = "aliQrcodes", key = "'aliQrcodes:' + #aliQrcode.uuid")},
	evict={@CacheEvict(value = "listPageAliQrcodes", allEntries = true), @CacheEvict(value = "listAliQrcodes", allEntries = true)})
	public AliQrcode insert(AliQrcode aliQrcode) {
		return super.insert(aliQrcode);
	}

	/**
	 * 向数据库删除一条aliQrcode数据
	 * 在缓存banks中删除一条aliQrcode记录，key值为uuid
	 * 清空缓存listPagealiQrcodes,listaliQrcodes的所有记录
	 * @param aliQrcode
	 * @return aliQrcode
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "aliQrcodes", key = "'aliQrcodes:' + #aliQrcode.uuid"),
			@CacheEvict(value = "listPageAliQrcodes", allEntries = true),@CacheEvict(value = "listAliQrcodes", allEntries = true)})
	public AliQrcode delete(AliQrcode aliQrcode) {
		return super.delete(aliQrcode);
	}

	/**
	 * 向数据库更新一条aliQrcode数据
	 * 在缓存aliQrcodes中更新一条aliQrcode记录，key值为uuid
	 * 清空缓存listPagealiQrcodes,listaliQrcodes的所有记录
	 * @param aliQrcode
	 * @return aliQrcode
	 */
	@Override
	@Caching(put={@CachePut(value = "aliQrcodes", key = "'aliQrcodes:' + #aliQrcode.uuid")},
	evict={@CacheEvict(value = "listPageAliQrcodes", allEntries = true),@CacheEvict(value = "listAliQrcodes", allEntries = true)})
	public AliQrcode update(AliQrcode aliQrcode) {
		return super.update(aliQrcode);
	}

	/**
	 * 根据uuid得到一个aliQrcode信息
	 * 向缓存aliQrcodes添加一条aliQrcode记录，key值为uuid
	 * @param uuid
	 * @return aliQrcode
	 */
	@Override
	@Cacheable(value = "aliQrcodes", key = "'aliQrcodes:' + #uuid")
	public AliQrcode get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表,
	 * 将查询结果存入缓存listaliQrcodes，key为所有参数名+参数值
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listAliQrcodes")
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ac.uuid, ac.price_index as priceIndex, ac.price_cn as priceCN, ac.price_urlcode as priceUrlcode from ali_qrcode ac where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("priceCN") != null && !"".equals(inputParams.get("priceCN"))) {
			builder.append(" and ac.price_cn = '" + inputParams.get("priceCN") + "' ");
		}
		if (inputParams.get("priceIndex") != null && !"".equals(inputParams.get("priceIndex"))) {
			builder.append(" and ac.price_index = " + inputParams.get("priceIndex") + " ");
		}
		if (inputParams.get("priceUrlcode") != null && !"".equals(inputParams.get("priceUrlcode"))) {
			builder.append(" and ac.price_urlcode = '" + inputParams.get("priceUrlcode") + "' ");
		}
		
		return super.getBySQL(builder.toString(), resultClass);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPagealiQrcodes，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageAliQrcodes")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ac.uuid, ac.price_index as priceIndex, ac.price_cn as priceCN, ac.price_urlcode as priceUrlcode from ali_qrcode ac where 1=1 ");
		//
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("priceCN") != null && !"".equals(inputParams.get("priceCN"))) {
			builder.append(" and ac.price_cn = '" + inputParams.get("priceCN") + "' ");
		}
		if (inputParams.get("priceIndex") != null && !"".equals(inputParams.get("priceIndex"))) {
			builder.append(" and ac.price_index = " + inputParams.get("priceIndex") + " ");
		}
		if (inputParams.get("priceUrlcode") != null && !"".equals(inputParams.get("priceUrlcode"))) {
			builder.append(" and ac.price_urlcode = '" + inputParams.get("priceUrlcode") + "' ");
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
			builder.append(" order by ac.price_index asc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from ali_qrcode ac where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and ac.uuid = '" + inputParams.get("uuid") + "' ");
		}
		if (inputParams.get("priceCN") != null && !"".equals(inputParams.get("priceCN"))) {
			builder.append(" and ac.price_cn = '" + inputParams.get("priceCN") + "' ");
		}
		if (inputParams.get("priceIndex") != null && !"".equals(inputParams.get("priceIndex"))) {
			builder.append(" and ac.price_index = " + inputParams.get("priceIndex") + " ");
		}
		if (inputParams.get("priceUrlcode") != null && !"".equals(inputParams.get("priceUrlcode"))) {
			builder.append(" and ac.price_urlcode = '" + inputParams.get("priceUrlcode") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

}
