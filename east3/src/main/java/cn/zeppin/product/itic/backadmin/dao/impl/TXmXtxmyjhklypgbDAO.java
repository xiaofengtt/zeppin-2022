/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtxmyjhklypgbDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TXmXtxmyjhklypgb;

@Repository
public class TXmXtxmyjhklypgbDAO extends BaseDAO<TXmXtxmyjhklypgb, String> implements ITXmXtxmyjhklypgbDAO {
	
	@Override
	public TXmXtxmyjhklypgb insert(TXmXtxmyjhklypgb t){
		return super.insert(t);
	}
	
	@Override
	public TXmXtxmyjhklypgb delete(TXmXtxmyjhklypgb t){
		return super.delete(t);
	}
	
	@Override
	public TXmXtxmyjhklypgb update(TXmXtxmyjhklypgb t){
		return super.update(t);
	}
	
	@Override
	public TXmXtxmyjhklypgb get(String id) {
		return super.get(id);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from T_XM_XTXMYJHKLYPGB t where 1=1");
		
		//起止时间
		if(inputParams.get("starttime") != null && inputParams.get("endtime") != null && !"".equals(inputParams.get("starttime")) && !"".equals(inputParams.get("endtime"))){
			builder.append(" and t.updatetime <= to_date('").append(inputParams.get("endtime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
			builder.append(" and t.updatetime >= to_date('").append(inputParams.get("starttime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
		}

		//项目ID
		if(inputParams.get("xtxmbm") != null && !"".equals(inputParams.get("xtxmbm"))){
			builder.append(" and t.xtxmbm = '").append(inputParams.get("xtxmbm")).append("' ");
		}
		
		//项目ID
		if(inputParams.get("products") != null && !"".equals(inputParams.get("products"))){
			builder.append(" and t.xtxmbm in ").append(inputParams.get("products")).append(" ");
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
	public List<TXmXtxmyjhklypgb> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TXmXtxmyjhklypgb t where 1=1 ");
		
		//起止时间
		if(inputParams.get("starttime") != null && inputParams.get("endtime") != null && !"".equals(inputParams.get("starttime")) && !"".equals(inputParams.get("endtime"))){
			builder.append(" and t.updatetime <= to_date('").append(inputParams.get("endtime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
			builder.append(" and t.updatetime >= to_date('").append(inputParams.get("starttime").toString()).append("','yyyy-mm-dd hh24:mi:ss') ");
		}

		//项目ID
		if(inputParams.get("xtxmbm") != null && !"".equals(inputParams.get("xtxmbm"))){
			builder.append(" and t.xtxmbm = '").append(inputParams.get("xtxmbm")).append("' ");
		}
		
		//项目ID
		if(inputParams.get("products") != null && !"".equals(inputParams.get("products"))){
			builder.append(" and t.xtxmbm in ").append(inputParams.get("products")).append(" ");
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
		builder.append(" update T_XM_XTXMYJHKLYPGB t set cjrq='").append(cjrq).append("' , ctltime=sysdate where 1=1");
		
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
