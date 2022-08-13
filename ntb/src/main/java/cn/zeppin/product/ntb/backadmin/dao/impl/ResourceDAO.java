/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IResourceDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.Resource;

/**
 *
 */

@Repository
public class ResourceDAO extends BaseDAO<Resource, String> implements IResourceDAO {


	/**
	 * 向数据库添加一条Resource数据
	 * 向缓存resources添加一条Resource记录，key值为uuid
	 * 清空缓存listResources的所有记录
	 * @param resource
	 * @return Resource
	 */
	@Override
	@Caching(put={@CachePut(value = "resources", key = "'resource:' + #resource.uuid")}, evict={@CacheEvict(value = "listResources", allEntries = true)})
	public Resource insert(Resource resource) {
		return super.insert(resource);
	}

	/**
	 * 数据库删除一条Resource数据
	 * 在缓存resources中删除一条Resource记录，key值为uuid
	 * 清空缓存listResources的所有记录
	 * @param resource
	 * @return Resource
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "resources", key = "'resource:' + #resource.uuid"), @CacheEvict(value = "listResources", allEntries = true)})
	public Resource delete(Resource resource) {
		return super.delete(resource);
	}

	/**
	 * 向数据库更新一条Resource数据
	 * 在缓存resources中更新一条Resource记录，key值为uuid
	 * 清空缓存listResources的所有记录
	 * @param resource
	 * @return Resource
	 */
	@Override
	@Caching(put={@CachePut(value = "resources", key = "'resource:' + #resource.uuid")}, evict={@CacheEvict(value = "listResources", allEntries = true)})
	public Resource update(Resource resource) {
		return super.update(resource);
	}

	/**
	 * 根据uuid得到一个Resource信息
	 * 向缓存resources添加一条Resource记录，key值为uuid
	 * 清空缓存listResources的所有记录
	 * @param uuid
	 * @return Resource
	 */
	@Override
	@Cacheable(cacheNames = "resources", key = "'resource:' + #uuid")
	public Resource get(String uuid) {
		return super.get(uuid);
	}

}
