/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * 新增Area信息
	 */
	@Override
	@Caching(put={@CachePut(value = "areas", key = "'areas:' + #area.uuid")}, evict={@CacheEvict(value = "allAreas", allEntries = true), @CacheEvict(value = "listPageAreas", allEntries = true)})
	public BkArea insert(BkArea area){
		return super.insert(area);
	}
	
	/**
	 * 删除Area信息
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "areas", key = "'areas:' + #area.uuid"), @CacheEvict(value = "allAreas", allEntries = true), @CacheEvict(value = "listPageAreas", allEntries = true)})
	public BkArea delete(BkArea area){
		return super.delete(area);
	}
	
	/**
	 * 修改Area信息
	 */
	@Override
	@Caching(put={@CachePut(value = "areas", key = "'areas:' + #area.uuid")}, evict={@CacheEvict(value = "allAreas", allEntries = true), @CacheEvict(value = "listPageAreas", allEntries = true)})
	public BkArea update(BkArea area){
		return super.update(area);
	}
	
	/**
	 * 通过唯一UUID得到Area
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
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	@Cacheable(value = "listPageAreas")
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select ba.uuid, ba.name, ba.level, ba.pid, ba.scode from bk_area ba where 1=1 ");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and ba.name like '%" + inputParams.get("name") + "%' ");
		}
		
		//级别
		if (inputParams.get("level") != null && !"".equals(inputParams.get("level"))) {
			builder.append(" and ba.level = " + inputParams.get("level") + " ");
		}
		
		//父级地区
		if (inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))) {
			builder.append(" and ba.pid = '" + inputParams.get("pid") + "' ");
		}
		
		//scode
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and ba.scode like '" + inputParams.get("scode") + "%' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			builder.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				builder.append(comma);
				builder.append("ba.").append(sort);
				comma = ",";
			}
		}
		else {
			builder.append(" order by ba.level, ba.scode ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}
	
	/**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bk_area ba where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and ba.name like '%" + inputParams.get("name") + "%' ");
		}
		
		//级别
		if (inputParams.get("level") != null && !"".equals(inputParams.get("level"))) {
			builder.append(" and ba.level = " + inputParams.get("level") + " ");
		}
		
		//父级地区
		if (inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))) {
			builder.append(" and ba.pid = '" + inputParams.get("pid") + "' ");
		}
		
		//scode
		if (inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))) {
			builder.append(" and ba.scode like '" + inputParams.get("scode") + "%' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
	
	/**
	 * 获取地区全称
	 * @param inputParams
	 * @return 
	 */
	@Override
	public List<String> getFullName(String uuid) {
		
		ArrayList<String> nameList = new ArrayList<String>();
		
		BkArea area = this.get(uuid);
		if(area != null){
			nameList.add(area.getName());
			
			while(area.getLevel() > 1){
				area = this.get(area.getPid());
				nameList.add(0, area.getName());
			}
		}
		
		return nameList;
	}
}
