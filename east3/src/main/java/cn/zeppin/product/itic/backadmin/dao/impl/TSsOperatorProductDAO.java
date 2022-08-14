/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorProductDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsOperatorProduct;

@Repository
public class TSsOperatorProductDAO extends BaseDAO<TSsOperatorProduct, String> implements ITSsOperatorProductDAO {
	
	@Override
	public TSsOperatorProduct insert(TSsOperatorProduct t){
		return super.insert(t);
	}
	
	@Override
	public TSsOperatorProduct delete(TSsOperatorProduct t){
		return super.delete(t);
	}
	
	@Override
	public TSsOperatorProduct update(TSsOperatorProduct t){
		return super.update(t);
	}
	
	@Override
	public TSsOperatorProduct get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TSsOperatorProduct> getList(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append("select distinct t from TSsOperatorProduct t where 1=1 ");
		//数据类型
		if(inputParams.get("opcode") != null && !"".equals(inputParams.get("opcode"))){
			builder.append(" and t.opcode='").append(inputParams.get("opcode")).append("' ");
		}
		
		//数据ID
		if(inputParams.get("productcodes") != null && !"".equals(inputParams.get("productcodes"))){
			builder.append(" and t.productcode in ").append(inputParams.get("productcodes")).append(" ");
		}
		
		return super.getByHQL(builder.toString());
	}

}
