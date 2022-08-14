/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITinNumberDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TinNumber;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class TinNumberDAO extends BaseDAO<TinNumber, Integer> implements ITinNumberDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "inNumbers", key = "'inNumbers:' + #inNumber.uuid")}, evict={@CacheEvict(value = "allinNumbers", allEntries = true)})
	public TinNumber insert(TinNumber inNumber){
		return super.insert(inNumber);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
//	@Caching(evict={@CacheEvict(value = "inNumbers", key = "'inNumbers:' + #inNumber.uuid"), @CacheEvict(value = "allinNumbers", allEntries = true)})
	public TinNumber delete(TinNumber inNumber){
		return super.delete(inNumber);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "inNumbers", key = "'inNumbers:' + #inNumber.uuid")}, evict={@CacheEvict(value = "allinNumbers", allEntries = true)})
	public TinNumber update(TinNumber inNumber){
		return super.update(inNumber);
	}
	
	@Override
//	@Cacheable(value = "inNumbers", key = "'inNumbers:' + #uuid")
	public TinNumber get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id, tp.TC_TEL as tcTel, tp.[STATUS] as status "
				+ " from TIN_NUMBER tp where 1=1 ");
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.[STATUS] = '" + inputParams.get("status") + "'");
		} else {
			builder.append(" and tp.[STATUS] in ('normal','used','error')");
		}
		//
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.TC_TEL = '" + inputParams.get("tcTel") + "'");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.ID desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from TIN_NUMBER tp where 1=1 ");
		
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.[STATUS] = '" + inputParams.get("status") + "'");
		} else {
			builder.append(" and tp.[STATUS] in ('normal','used','error')");
		}
		//
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.TC_TEL = '" + inputParams.get("tcTel") + "'");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<TinNumber> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TinNumber tp where 1=1 ");
		
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.id <> " + inputParams.get("id"));
		}
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.status = '" + inputParams.get("status") + "'");
		} else {
			builder.append(" and tp.status in ('normal','used','error')");
		}
		//
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.tcTel = '" + inputParams.get("tcTel") + "'");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.id desc ");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}
}
