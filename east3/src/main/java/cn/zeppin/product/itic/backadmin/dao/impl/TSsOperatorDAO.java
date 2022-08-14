/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperator;


@Repository
public class TSsOperatorDAO extends BaseDAO<TSsOperator, String> implements ITSsOperatorDAO {

	@Override
	public TSsOperator insert(TSsOperator operator) {
		return super.insert(operator);
	}
	
	@Override
	public TSsOperator delete(TSsOperator operator) {
		return super.delete(operator);
	}
	
	@Override
	public TSsOperator update(TSsOperator operator) {
		return super.update(operator);
	}
	
	@Override
	public TSsOperator get(String id) {
		return super.get(id);
	}

	/**
	 * 通过登录账号得到TSsOperator对象
	 * @param loginname
	 * @return 
	 */
	public TSsOperator getByLoginname(String loginname) {
		String hql = "from TSsOperator where code='" + loginname + "'";;
		List<TSsOperator> operators = this.getByHQL(hql);
		if (operators != null && operators.size() > 0) {
			return operators.get(0);
		}
		return null;
	}
	
	/**
	 * 获取用户列表
	 * @return
	 */
	public List<TSsOperator> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts){
		StringBuilder builder = new StringBuilder();
		builder.append("from TSsOperator o where 1=1");
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and o.role = '" + inputParams.get("role") + "' ");
		}
		
		//姓名
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and o.name like '%" + inputParams.get("name") + "%' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by o.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}else{
			builder.append(" order by o.code");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from T_SS_OPERATOR o where 1=1");
		
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and o.role = '" + inputParams.get("role") + "' ");
		}
		
		//姓名
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and o.name like '%" + inputParams.get("name") + "%' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
