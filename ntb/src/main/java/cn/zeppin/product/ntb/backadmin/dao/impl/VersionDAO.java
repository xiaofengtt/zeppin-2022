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

import cn.zeppin.product.ntb.backadmin.dao.api.IVersionDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class VersionDAO extends BaseDAO<Version, String> implements IVersionDAO {


	/**
	 * 向数据库添加一条Version数据
	 * 向缓存versions添加一条Version记录，key值为uuid
	 * 清空缓存listPageVersions的所有记录
	 * @param version
	 * @return Version
	 */
	@Override
	@Caching(put={@CachePut(value = "versions", key = "'versions:' + #version.uuid")}, evict={@CacheEvict(value = "listPageVersions", allEntries = true)})
	public Version insert(Version version) {
		return super.insert(version);
	}

	/**
	 * 向数据库删除一条Version数据
	 * 在缓存versions中删除一条Version记录，key值为uuid
	 * 清空缓存listPageVersions的所有记录
	 * @param version
	 * @return Version
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "versions", key = "'versions:' + #version.uuid"), @CacheEvict(value = "listPageVersions", allEntries = true), 
			@CacheEvict(value = "listPageBranchVersions", allEntries = true)})
	public Version delete(Version version) {
		return super.delete(version);
	}

	/**
	 * 向数据库更新一条Version数据
	 * 在缓存versions中更新一条Version记录，key值为uuid
	 * 清空缓存listPageVersions的所有记录
	 * @param version
	 * @return Version
	 */
	@Override
	@Caching(put={@CachePut(value = "versions", key = "'versions:' + #version.uuid")}, evict={@CacheEvict(value = "listPageVersions", allEntries = true),
			@CacheEvict(value = "listPageBranchVersions", allEntries = true)})
	public Version update(Version version) {
		return super.update(version);
	}

	/**
	 * 根据uuid得到一个Version信息
	 * 向缓存versions添加一条Version记录，key值为uuid
	 * @param uuid
	 * @return Version
	 */
	@Override
	@Cacheable(value = "versions", key = "'versions:' + #uuid")
	public Version get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageVersions，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageVersions")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.version, b.version_name as versionName, b.version_des as versionDes, b.device, b.flag_compel as flagCompel,"
				+ " b.createtime, b.creator, b.resource, b.status "
				+ " from version b where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.version like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.version_name like '%" + inputParams.get("name") + "%') ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('published','unpublish','disable') ");
		}
		
		//名称
		if (inputParams.get("version") != null && !"".equals(inputParams.get("version"))) {
			builder.append(" and b.version = " + inputParams.get("version") + " ");
		}
		
		if (inputParams.get("versionUp") != null && !"".equals(inputParams.get("versionUp"))) {
			builder.append(" and b.version > " + inputParams.get("versionUp"));
		}
		
		if (inputParams.get("versionName") != null && !"".equals(inputParams.get("versionName"))) {
			builder.append(" and b.version_name = '" + inputParams.get("versionName") + "' ");
		}
		
		if (inputParams.get("versionDes") != null && !"".equals(inputParams.get("versionDes"))) {
			builder.append(" and b.version_des = '" + inputParams.get("versionDes") + "' ");
		}
		
		if (inputParams.get("device") != null && !"".equals(inputParams.get("device"))) {
			builder.append(" and b.device = '" + inputParams.get("device") + "' ");
		}
		
		if (inputParams.get("flagCompel") != null && !"".equals(inputParams.get("flagCompel"))) {
			builder.append(" and b.flag_compel = " + inputParams.get("flagCompel") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = " + inputParams.get("createtime") + " ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = " + inputParams.get("creator") + " ");
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
			builder.append(" order by b.version desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from version b where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (b.version like '%" + inputParams.get("name") + "%' ");
			builder.append(" or b.version_name like '%" + inputParams.get("name") + "%') ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and b.status = '" + inputParams.get("status") + "' ");
		}
		else{
			builder.append(" and b.status in ('published','unpublish','disable') ");
		}
		
		//名称
		if (inputParams.get("version") != null && !"".equals(inputParams.get("version"))) {
			builder.append(" and b.version = " + inputParams.get("version") + " ");
		}
		
		if (inputParams.get("versionUp") != null && !"".equals(inputParams.get("versionUp"))) {
			builder.append(" and b.version > " + inputParams.get("versionUp"));
		}
		
		if (inputParams.get("versionName") != null && !"".equals(inputParams.get("versionName"))) {
			builder.append(" and b.version_name = '" + inputParams.get("versionName") + "' ");
		}
		
		if (inputParams.get("versionDes") != null && !"".equals(inputParams.get("versionDes"))) {
			builder.append(" and b.version_des = '" + inputParams.get("versionDes") + "' ");
		}
		
		if (inputParams.get("device") != null && !"".equals(inputParams.get("device"))) {
			builder.append(" and b.device = '" + inputParams.get("device") + "' ");
		}
		
		if (inputParams.get("flagCompel") != null && !"".equals(inputParams.get("flagCompel"))) {
			builder.append(" and b.flag_compel = " + inputParams.get("flagCompel") + " ");
		}
		
		if (inputParams.get("createtime") != null && !"".equals(inputParams.get("createtime"))) {
			builder.append(" and b.createtime = " + inputParams.get("createtime") + " ");
		}
		
		if (inputParams.get("creator") != null && !"".equals(inputParams.get("creator"))) {
			builder.append(" and b.creator = " + inputParams.get("creator") + " ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	/**
	 * 验证同名的银行信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistVersionByName(Integer name, String device, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from version b where b.version=?0 and b.device=?1 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and uuid != ?2");
		}
		Object[] paras = {name,device,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("select b.status, count(*) as count from version b where 1=1 ");
		
		if (inputParams.get("device") != null && !"".equals(inputParams.get("device"))) {
			builder.append(" and b.device = '" + inputParams.get("device") + "' ");
		}
		
		builder.append(" and b.status in ('published','unpublish','disable') ");
		builder.append(" group by b.status");
		return super.getBySQL(builder.toString(), resultClass);
	}

}
