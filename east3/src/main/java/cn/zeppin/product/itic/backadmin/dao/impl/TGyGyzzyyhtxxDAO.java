/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITGyGyzzyyhtxxDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TGyGyzzyyhtxx;

@Repository
public class TGyGyzzyyhtxxDAO extends BaseDAO<TGyGyzzyyhtxx, String> implements ITGyGyzzyyhtxxDAO {
	
	@Override
	public TGyGyzzyyhtxx insert(TGyGyzzyyhtxx t){
		return super.insert(t);
	}
	
	@Override
	public TGyGyzzyyhtxx delete(TGyGyzzyyhtxx t){
		return super.delete(t);
	}
	
	@Override
	public TGyGyzzyyhtxx update(TGyGyzzyyhtxx t){
		return super.update(t);
	}
	
	@Override
	public TGyGyzzyyhtxx get(String id) {
		return super.get(id);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from T_GY_GYZZYYHTXX t where 1=1");
		
		//起止时间
		if(inputParams.get("starttime") != null && inputParams.get("endtime") != null && !"".equals(inputParams.get("starttime")) && !"".equals(inputParams.get("endtime"))){
			builder.append(" and t.updatetime <= to_date('").append(inputParams.get("endtime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
			builder.append(" and t.updatetime >= to_date('").append(inputParams.get("starttime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//状态
		if(inputParams.get("status") != null && !"".equals(inputParams.get("status"))){
			builder.append(" and t.status='").append(inputParams.get("status")).append("' ");
		}else{
			builder.append(" and t.status='normal' ");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<TGyGyzzyyhtxx> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TGyGyzzyyhtxx t where 1=1 ");
		
		//起止时间
		if(inputParams.get("starttime") != null && inputParams.get("endtime") != null && !"".equals(inputParams.get("starttime")) && !"".equals(inputParams.get("endtime"))){
			builder.append(" and t.updatetime <= to_date('").append(inputParams.get("endtime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
			builder.append(" and t.updatetime >= to_date('").append(inputParams.get("starttime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//状态
		if(inputParams.get("status") != null && !"".equals(inputParams.get("status"))){
			builder.append(" and t.status='").append(inputParams.get("status")).append("' ");
		}else{
			builder.append(" and t.status='normal' ");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by t.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}else{
			builder.append(" order by t.updatetime desc");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}

	@Override
	public void submitData(Map<String,String> inputParams, String cjrq) {
		StringBuilder builder = new StringBuilder();
		builder.append(" update T_GY_GYZZYYHTXX t set cjrq='").append(cjrq).append("' , ctltime=sysdate where 1=1");
		
		//起止时间
		if(inputParams.get("starttime") != null && inputParams.get("endtime") != null && !"".equals(inputParams.get("starttime")) && !"".equals(inputParams.get("endtime"))){
			builder.append(" and t.updatetime <= to_date('").append(inputParams.get("endtime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
			builder.append(" and t.updatetime >= to_date('").append(inputParams.get("starttime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//状态
		if(inputParams.get("status") != null && !"".equals(inputParams.get("status"))){
			builder.append(" and t.status='").append(inputParams.get("status")).append("' ");
		}else{
			builder.append(" and t.status='normal' ");
		}
		
		super.executeSQL(builder.toString());
	}
}
