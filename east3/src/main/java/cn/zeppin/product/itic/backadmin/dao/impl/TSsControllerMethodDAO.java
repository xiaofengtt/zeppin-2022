/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsControllerMethodDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;


@Repository
public class TSsControllerMethodDAO extends BaseDAO<TSsControllerMethod, String> implements ITSsControllerMethodDAO {
	
	@Override
	public TSsControllerMethod insert(TSsControllerMethod t) {
		return super.insert(t);
	}
	
	@Override
	public TSsControllerMethod delete(TSsControllerMethod t) {
		return super.delete(t);
	}
	
	@Override
	public TSsControllerMethod update(TSsControllerMethod t) {
		return super.update(t);
	}
	
	@Override
	public TSsControllerMethod get(String id) {
		return super.get(id);
	}

	@Override
	public List<TSsControllerMethod> getList(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from TSsControllerMethod where 1=1 ");
		//功能
		if (inputParams.get("controller") != null && !"".equals(inputParams.get("controller"))) {
			builder.append(" and controller='" + inputParams.get("controller") + "' ");
		}
		
		//功能
		if (inputParams.get("controllers") != null && !"".equals(inputParams.get("controllers"))) {
			builder.append(" and controller in " + inputParams.get("controllers") + " ");
		}
				
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and name='" + inputParams.get("name") + "' ");
		}
		builder.append(" order by sort");
		return this.getByHQL(builder.toString());
	}
	
}
