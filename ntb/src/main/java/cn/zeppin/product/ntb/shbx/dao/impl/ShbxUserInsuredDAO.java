/**
 * 
 */
package cn.zeppin.product.ntb.shbx.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.ShbxUserInsured;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserInsuredDAO;

/**
 * @author hehe
 *
 */

@Repository
public class ShbxUserInsuredDAO extends BaseDAO<ShbxUserInsured, String> implements IShbxUserInsuredDAO {


	/**
	 * 向数据库添加一条shbxUserInsured数据
	 * @param shbxUserInsured
	 * @return shbxUserInsured
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUserInsureds", key = "'shbxUserInsureds:' + #shbxUserInsured.uuid")})
	public ShbxUserInsured insert(ShbxUserInsured shbxUserInsured) {
		return super.insert(shbxUserInsured);
	}

	/**
	 * 向数据库删除一条shbxUserInsured数据
	 * @param shbxUserInsured
	 * @return shbxUserInsured
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "shbxUserInsureds", key = "'shbxUserInsureds:' + #shbxUserInsured.uuid")})
	public ShbxUserInsured delete(ShbxUserInsured shbxUserInsured) {
		return super.delete(shbxUserInsured);
	}

	/**
	 * 向数据库更新一条ShbxUserInsureds数据
	 * @param shbxUserInsureds
	 * @return ShbxUserInsureds
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxUserInsureds", key = "'shbxUserInsureds:' + #shbxUserInsured.uuid")})
	public ShbxUserInsured update(ShbxUserInsured shbxUserInsured) {
		return super.update(shbxUserInsured);
	}

	/**
	 * 根据uuid得到一个ShbxUserInsureds信息
	 * @param uuid
	 * @return ShbxUserInsureds
	 */
	@Override
	@Cacheable(value = "shbxUserInsureds", key = "'shbxUserInsureds:' + #uuid")
	public ShbxUserInsured get(String uuid) {
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
		builder.append(" select sui.uuid,sui.shbx_user as shbxUser,sui.shbx_insured as shbxInsured,sui.creator,sui.status,sui.createtime"
				+ " from shbx_user_insured sui  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and sui.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))) {
			builder.append(" and sui.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}
		
		if (inputParams.get("shbxInsured") != null && !"".equals(inputParams.get("shbxInsured"))) {
			builder.append(" and sui.shbx_insured = '" + inputParams.get("shbxInsured") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and sui.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and sui.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("sui.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by sui.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from shbx_user_insured sui where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and sui.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("suiEmployee") != null && !"".equals(inputParams.get("suiEmployee"))) {
			builder.append(" and sui.sui_employee = '" + inputParams.get("suiEmployee") + "' ");
		}
		
		if (inputParams.get("shbxInsured") != null && !"".equals(inputParams.get("shbxInsured"))) {
			builder.append(" and sui.shbx_insured = '" + inputParams.get("shbxInsured") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and sui.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and sui.status in ('normal','disable') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
