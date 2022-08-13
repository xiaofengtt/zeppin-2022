/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkControllerMethodDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class BkControllerMethodDAO extends BaseDAO<BkControllerMethod, String> implements IBkControllerMethodDAO {

	/**
	 * 新增一条Controller的Method信息
	 */
	@Override
	@CachePut(value = "methods", key = "#method.uuid")
	public BkControllerMethod insert(BkControllerMethod method) {
		return super.insert(method);
	}
	
	/**
	 * 删除一条Controller的Method信息
	 */
	@Override
	@CacheEvict(value = "methods", key = "#method.uuid")
	public BkControllerMethod delete(BkControllerMethod method) {
		return super.delete(method);
	}
	
	/**
	 * 修改一条Controller的Method信息
	 */
	@Override
	@CachePut(value = "methods", key = "#method.uuid")
	public BkControllerMethod update(BkControllerMethod method) {
		return super.update(method);
	}
	
	/**
	 * 通过唯一标识UUID得到一条Controller的Method信息
	 */
	@Override
	@Cacheable(cacheNames = "methods", key = "#uuid")
	public BkControllerMethod get(String uuid) {
		return super.get(uuid);
	}
	
	/**
	 * 获取所有功能方法信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select * from bk_controller_method", resultClass);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.name, b.controller, b.description from bk_controller_method b where 1=1 ");
		//名称
		if (inputParams.get("controller") != null && !"".equals(inputParams.get("controller"))) {
			builder.append(" and b.controller = '" + inputParams.get("controller") + "' ");
		}
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name = '" + inputParams.get("name") + "' ");
		}
		//状态
		if (inputParams.get("description") != null && !"".equals(inputParams.get("description"))) {
			builder.append(" and b.description like '%" + inputParams.get("description") + "%' ");
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
			builder.append(" order by b.name desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bk_controller_method b where 1=1 ");
		//名称
		if (inputParams.get("controller") != null && !"".equals(inputParams.get("controller"))) {
			builder.append(" and b.controller = '" + inputParams.get("controller") + "' ");
		}
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and b.name = '" + inputParams.get("name") + "' ");
		}
		//状态
		if (inputParams.get("description") != null && !"".equals(inputParams.get("description"))) {
			builder.append(" and b.description like '%" + inputParams.get("description") + "%' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
