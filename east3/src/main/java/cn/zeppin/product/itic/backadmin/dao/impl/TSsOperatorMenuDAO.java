/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorMenuDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMenu;

@Repository
public class TSsOperatorMenuDAO extends BaseDAO<TSsOperatorMenu, String>
		implements ITSsOperatorMenuDAO {
	/**
	 * 获取用户权限列表
	 */
	@Override
	public List<TSsOperatorMenu> getList(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" from TSsOperatorMenu where 1=1 ");
		//角色
		if (inputParams.get("operator") != null && !"".equals(inputParams.get("operator"))) {
			builder.append(" and operator='" + inputParams.get("operator") + "' ");
		}
		return this.getByHQL(builder.toString());
	}
	
	/**
	 * 删除用户的所有页面权限
	 */
	@Override
	public void deleteByOperator(TSsOperator operator) {
		StringBuilder builder = new StringBuilder();
		builder.append(" delete from TSsOperatorMenu where operator='").append(operator.getId()).append("'");
		this.executeHQL(builder.toString());
	}
	
	@Override
	public TSsOperatorMenu insert(TSsOperatorMenu permission) {
		return super.insert(permission);
	}

	@Override
	public TSsOperatorMenu delete(TSsOperatorMenu permission) {
		return super.delete(permission);
	}

	@Override
	public TSsOperatorMenu update(TSsOperatorMenu permission) {
		return super.update(permission);
	}

	@Override
	public TSsOperatorMenu get(String id) {
		return super.get(id);
	}

}
