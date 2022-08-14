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
import cn.zeppin.product.ntb.core.entity.ShbxInsured;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxInsuredDAO;

/**
 * @author hehe
 *
 */

@Repository
public class ShbxInsuredDAO extends BaseDAO<ShbxInsured, String> implements IShbxInsuredDAO {


	/**
	 * 向数据库添加一条shbxInsured数据
	 * @param shbxInsured
	 * @return shbxInsured
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxInsureds", key = "'shbxInsureds:' + #shbxInsured.uuid")})
	public ShbxInsured insert(ShbxInsured shbxInsured) {
		return super.insert(shbxInsured);
	}

	/**
	 * 向数据库删除一条shbxInsured数据
	 * @param shbxInsured
	 * @return shbxInsured
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "shbxInsureds", key = "'shbxInsureds:' + #shbxInsured.uuid")})
	public ShbxInsured delete(ShbxInsured shbxInsured) {
		return super.delete(shbxInsured);
	}

	/**
	 * 向数据库更新一条ShbxInsureds数据
	 * @param shbxInsureds
	 * @return ShbxInsureds
	 */
	@Override
	@Caching(put={@CachePut(value = "shbxInsureds", key = "'shbxInsureds:' + #shbxInsured.uuid")})
	public ShbxInsured update(ShbxInsured shbxInsured) {
		return super.update(shbxInsured);
	}

	/**
	 * 根据uuid得到一个ShbxInsureds信息
	 * @param uuid
	 * @return ShbxInsureds
	 */
	@Override
	@Cacheable(value = "shbxInsureds", key = "'shbxInsureds:' + #uuid")
	public ShbxInsured get(String uuid) {
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
		builder.append(" select * from shbx_insured si  where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and si.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and si.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and si.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and si.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and si.status in ('normal','disable') ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("si.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by si.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from shbx_insured si where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and si.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and si.name like '%" + inputParams.get("name") + "%' ");
		}
		
		if (inputParams.get("idcard") != null && !"".equals(inputParams.get("idcard"))) {
			builder.append(" and si.idcard = '" + inputParams.get("idcard") + "' ");
		}
		
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and si.status = '" + inputParams.get("status") + "' ");
		} else {
			builder.append(" and si.status in ('normal','disable') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	@Override
	public boolean isExistShbxInsuredByIdcard(String idcard, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from shbx_insured si where idcard=?0");
		//编辑时检测uuid
		if(uuid != null && !"".equals(uuid)){
			builder.append(" and uuid != ?1");
		}
		Object[] paras = {idcard,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Entity> getListForShbxPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select si.* from shbx_insured si left join shbx_user_insured sui on si.uuid=sui.shbx_insured where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and si.uuid = '" + inputParams.get("uuid") + "' ");
		}

		if(inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))){
			builder.append(" and sui.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and si.name like '%" + inputParams.get("name") + "%' ");
		}

		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and si.mobile = '" + inputParams.get("mobile") + "' ");
		}
				
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and si.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and si.status in ('normal','disable') ");
		}
		
		builder.append(" group by si.uuid ");
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("si.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by si.createtime desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForShbx(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(distinct si.uuid) from shbx_insured si left join shbx_user_insured sui on si.uuid=sui.shbx_insured where 1=1 ");
		
		if (inputParams.get("uuid") != null && !"".equals(inputParams.get("uuid"))) {
			builder.append(" and si.uuid = '" + inputParams.get("uuid") + "' ");
		}
		
		if(inputParams.get("shbxUser") != null && !"".equals(inputParams.get("shbxUser"))){
			builder.append(" and sui.shbx_user = '" + inputParams.get("shbxUser") + "' ");
		}
		
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and si.name like '%" + inputParams.get("name") + "%' ");
		}

		if (inputParams.get("mobile") != null && !"".equals(inputParams.get("mobile"))) {
			builder.append(" and si.mobile = '" + inputParams.get("mobile") + "' ");
		}
		
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and si.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and si.status in ('normal','disable') ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
