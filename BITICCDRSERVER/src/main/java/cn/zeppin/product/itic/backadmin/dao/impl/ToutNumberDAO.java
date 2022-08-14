/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.IToutNumberDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.ToutNumber;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class ToutNumberDAO extends BaseDAO<ToutNumber, Integer> implements IToutNumberDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "outNumbers", key = "'outNumbers:' + #outNumber.uuid")}, evict={@CacheEvict(value = "alloutNumbers", allEntries = true)})
	public ToutNumber insert(ToutNumber outNumber){
		return super.insert(outNumber);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
//	@Caching(evict={@CacheEvict(value = "outNumbers", key = "'outNumbers:' + #outNumber.uuid"), @CacheEvict(value = "alloutNumbers", allEntries = true)})
	public ToutNumber delete(ToutNumber outNumber){
		return super.delete(outNumber);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "outNumbers", key = "'outNumbers:' + #outNumber.uuid")}, evict={@CacheEvict(value = "alloutNumbers", allEntries = true)})
	public ToutNumber update(ToutNumber outNumber){
		return super.update(outNumber);
	}
	
	@Override
//	@Cacheable(value = "outNumbers", key = "'outNumbers:' + #uuid")
	public ToutNumber get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id,tp.TO_MOBILE as toMobile,tp.TO_TEL as toTel, tp.[STATUS] as status "
				+ " from TOUT_NUMBER tp where 1=1 ");
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
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
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
		builder.append(" select count(*) from TOUT_NUMBER tp where 1=1 ");
		
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
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<ToutNumber> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("from ToutNumber tp where 1=1 ");
		
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
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.toMobile = '" + inputParams.get("toMobile") + "'");
		}
		//
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.toTel = '" + inputParams.get("toTel") + "'");
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
