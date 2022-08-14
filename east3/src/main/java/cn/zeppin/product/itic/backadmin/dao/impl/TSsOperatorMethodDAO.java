/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorMethodDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMethod;


@Repository
public class TSsOperatorMethodDAO extends BaseDAO<TSsOperatorMethod, String> implements ITSsOperatorMethodDAO {
	
	@Override
	public TSsOperatorMethod insert(TSsOperatorMethod t) {
		return super.insert(t);
	}
	
	@Override
	public TSsOperatorMethod delete(TSsOperatorMethod t) {
		return super.delete(t);
	}
	
	@Override
	public TSsOperatorMethod update(TSsOperatorMethod t) {
		return super.update(t);
	}
	
	@Override
	public TSsOperatorMethod get(String id) {
		return super.get(id);
	}

	@Override
	public List<TSsOperatorMethod> getList(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from TSsOperatorMethod where 1=1 ");
		//用户
		if (inputParams.get("operator") != null && !"".equals(inputParams.get("operator"))) {
			builder.append(" and operator='" + inputParams.get("operator") + "' ");
		}
		//功能
		if (inputParams.get("controller") != null && !"".equals(inputParams.get("controller"))) {
			builder.append(" and controller='" + inputParams.get("controller") + "' ");
		}
		return this.getByHQL(builder.toString());
	}
	
	/**
	 * 删除用户的所有功能权限
	 */
	@Override
	public void deleteByOperator(TSsOperator operator) {
		StringBuilder builder = new StringBuilder();
		builder.append(" delete from TSsOperatorMethod where operator='").append(operator.getId()).append("'");
		this.executeHQL(builder.toString());
	}

	@Override
	public List<TSsOperatorMethod> getByOperator(TSsOperator operator) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from TSsOperatorMethod where operator='").append(operator.getId()).append("'");
		List<TSsOperatorMethod> permissions = this.getByHQL(hql.toString());
		return permissions;
	}
}
