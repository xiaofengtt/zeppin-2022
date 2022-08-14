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

import cn.zeppin.product.ntb.backadmin.dao.api.IBkControllerDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkController;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class BkControllerDAO extends BaseDAO<BkController, String> implements IBkControllerDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
	@CachePut(value = "controllers", key = "#controller.uuid")
	public BkController insert(BkController controller){
		return super.insert(controller);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
	@CacheEvict(value = "controllers", key = "#controller.uuid")
	public BkController delete(BkController controller){
		return super.delete(controller);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
	@CachePut(value = "controllers", key = "#controller.uuid")
	public BkController update(BkController controller){
		return super.update(controller);
	}
	
	/**
	 * 通过唯一UUID得到Controller
	 */
	@Override
	@Cacheable(cacheNames = "controllers", key = "#uuid")
	public BkController get(String uuid){
		return super.get(uuid);
	}
	
	/**
	 * 获取所有功能信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return super.getBySQL("select * from bk_controller order by sort", resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select b.uuid, b.name, b.description, b.sort from bk_controller b where 1=1 ");
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
			builder.append(" order by b.sort ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from bk_controller b where 1=1 ");
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
