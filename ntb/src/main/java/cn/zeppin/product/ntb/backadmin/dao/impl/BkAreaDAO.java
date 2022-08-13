/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkAreaDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class BkAreaDAO extends BaseDAO<BkArea, String> implements IBkAreaDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
	@Caching(put={@CachePut(value = "areas", key = "'areas:' + #area.uuid")}, evict={@CacheEvict(value = "allAreas", allEntries = true)})
	public BkArea insert(BkArea area){
		return super.insert(area);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "areas", key = "'areas:' + #area.uuid"), @CacheEvict(value = "allAreas", allEntries = true)})
	public BkArea delete(BkArea area){
		return super.delete(area);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
	@Caching(put={@CachePut(value = "areas", key = "'areas:' + #area.uuid")}, evict={@CacheEvict(value = "allAreas", allEntries = true)})
	public BkArea update(BkArea area){
		return super.update(area);
	}
	
	/**
	 * 通过唯一UUID得到Controller
	 */
	@Override
	@Cacheable(value = "areas", key = "'areas:' + #uuid")
	public BkArea get(String uuid){
		return super.get(uuid);
	}
	
	/**
	 * 获取所有功能信息
	 * @param resultClass
	 * @return
	 */
	@Override
	@Cacheable(value = "allAreas")
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select * from bk_area", resultClass);
	}
}
