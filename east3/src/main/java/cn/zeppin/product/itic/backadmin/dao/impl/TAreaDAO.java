/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITAreaDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TArea;

@Repository
public class TAreaDAO extends BaseDAO<TArea, String> implements ITAreaDAO {
	
	@Override
	public TArea insert(TArea t){
		return super.insert(t);
	}
	
	@Override
	public TArea delete(TArea t){
		return super.delete(t);
	}
	
	@Override
	public TArea update(TArea t){
		return super.update(t);
	}
	
	@Override
	public TArea get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TArea> getList(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TArea t where 1=1 ");
		//级别
		if(inputParams.get("level") != null && !"".equals(inputParams.get("level"))){
			builder.append(" and t.arealevel=").append(inputParams.get("level")).append(" ");
		}
		
		//编码
		if(inputParams.get("scode") != null && !"".equals(inputParams.get("scode"))){
			builder.append(" and t.scode='").append(inputParams.get("scode")).append("' ");
		}
			
		//pid
		if(inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))){
			builder.append(" and t.pid='").append(inputParams.get("pid")).append("' ");
		}
		
		builder.append(" order by t.scode");
		
		return super.getByHQL(builder.toString());
	}
}
