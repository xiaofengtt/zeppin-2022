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

import cn.zeppin.product.ntb.backadmin.dao.api.IManagerDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Manager;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class MangerDAO extends BaseDAO<Manager, String> implements IManagerDAO {


	/**
	 * 向数据库添加一条Manager数据
	 * 向缓存managers添加一条Manager记录，key值为uuid
	 * 清空缓存listPageManagers的所有记录
	 * @param manager
	 * @return Manager
	 */
	@Override
	@Caching(put={@CachePut(value = "managers", key = "'manager:' + #manager.uuid")}, evict={@CacheEvict(value = "listPageManagers", allEntries = true)})
	public Manager insert(Manager manager) {
		return super.insert(manager);
	}

	/**
	 * 向数据库删除一条Manager数据
	 * 在缓存managers中删除一条Manager记录，key值为uuid
	 * 清空缓存listPageManagers的所有记录
	 * @param manager
	 * @return Manager
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "managers", key = "'manager:' + #manager.uuid"), @CacheEvict(value = "listPageManagers", allEntries = true)})
	public Manager delete(Manager manager) {
		return super.delete(manager);
	}

	/**
	 * 向数据库更新一条Manager数据
	 * 在缓存managers中更新一条Manager记录，key值为uuid
	 * 清空缓存listPageManagers的所有记录
	 * @param manager
	 * @return Manager
	 */
	@Override
	@Caching(put={@CachePut(value = "managers", key = "'manager:' + #manager.uuid")}, evict={@CacheEvict(value = "listPageManagers", allEntries = true)})
	public Manager update(Manager manager) {
		return super.update(manager);
	}
	
	/**
	 * 根据uuid得到一个Manager信息
	 * 向缓存managers添加一条Manager记录，key值为uuid
	 * @param uuid
	 * @return Manager
	 */
	@Override
	@Cacheable(cacheNames = "managers", key = "'manager:' + #uuid")
	public Manager get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * 将查询结果存入缓存listPageManagers，key为所有参数名+参数值
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(cacheNames = "listPageManagers")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select m.uuid, m.name, m.type, m.graduation, m.education, m.score, m.resume, m.workage, ");
		builder.append(" m.mobile, m.idcard, m.email, m.photo, r.url as photoUrl, m.status, m.creator, o.name as creatorName, m.createtime ");
		builder.append(" from manager m left join resource r on m.photo=r.uuid, bk_operator o where m.creator=o.uuid ");
		//姓名
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and m.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and m.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and m.status in ('normal','disable') ");
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("m.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by m.createtime desc ");
		}
		
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from manager m where 1=1 ");
		//姓名
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and m.name like '%" + inputParams.get("name") + "%' ");
		}
		//状态
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and m.status = '" + inputParams.get("status") + "' ");
		}else{
			builder.append(" and m.status in ('normal','disable') ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 验证同身份证主理人信息是否已经存在
	 * @param idcard
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistManagerByIdcard(String idcard, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from manager m where m.idcard=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and m.uuid != ?1");
		}
		Object[] paras = {idcard ,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证同手机的主理人信息是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistManagerByMobile(String mobile, String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select 1 from manager m where m.mobile=?0 ");
		//编辑时检测uuid
		if(uuid != null){
			builder.append(" and m.uuid != ?1");
		}
		Object[] paras = {mobile ,uuid};
		Object result = super.getResultBySQL(builder.toString(), paras);
		if (result != null) {
			return true;
		}
		return false;
	}
}
